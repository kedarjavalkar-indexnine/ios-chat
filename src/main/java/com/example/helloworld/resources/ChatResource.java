package com.example.helloworld.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.indexnine.chat.ChatItem;
import com.indexnine.chat.ChatList;
import com.indexnine.chat.ChatRequest;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/chat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {
	public static HashMap<String, ChatList> chats = new HashMap<>();

	@POST
	@UnitOfWork
	public List<ChatItem> addChat(ChatRequest req) {
		String user = "";
		if (req.getFrom() != null) {
			user = req.getFrom();
		} else {
			user = req.getTo();
		}
		ChatList userChatList = chats.get(user);

		if (userChatList == null) {
			userChatList = new ChatList();
		}

		ChatItem item = new ChatItem();
		item.setFrom(req.getFrom());
		item.setTo(req.getTo());
		item.setMsg(req.getText());
		item.setStatus("unread");
		item.setTs(new Date().getTime());

		userChatList.getChats().add(item);
		chats.put(user, userChatList);

		return getChatList(user, req.getLastTimestamp(), null);
	}

	@PUT
	@UnitOfWork
	@Path("user/{user}/read/{timestamp}")
	public Response readChat(@PathParam("user") String user, @PathParam("timestamp") Long timestamp) {
		ChatList userChatList = chats.get(user);

		if (userChatList == null) {
			userChatList = new ChatList();
		}

		for (ChatItem item : userChatList.getChats()) {
			if (item.getTs() <= timestamp) {
				item.setStatus("read");
			}
		}
		chats.put(user, userChatList);

		return Response.ok().build();
	}

	@GET
	@UnitOfWork
	@Path("/{timestamp}")
	public List<ChatItem> getAllChatList(@PathParam("timestamp") Long timestamp, @QueryParam("status") String status) {
		List<ChatItem> response = new ArrayList<>();

		for (Map.Entry<String, ChatList> entry : chats.entrySet()) {
			boolean add = false;
			for (ChatItem item : entry.getValue().getChats()) {
				if (timestamp <= item.getTs()) {
					add = true;
				} else {
					add = false;
				}
				if (add) {
					if (status == null || item.getStatus().equals(status)) {
						response.add(item);
					}
				}
			}
		}
		return response;
	}

	@GET
	@UnitOfWork
	@Path("user/{user}/{timestamp}")
	public List<ChatItem> getChatList(@PathParam("user") String user, @PathParam("timestamp") Long timestamp,
			@QueryParam("status") String status) {
		ChatList list = chats.get(user);
		if (list == null) {
			return new ChatList().getChats();
		} else {
			ChatList retlist = new ChatList();
			boolean add = false;
			for (ChatItem item : list.getChats()) {
				if (timestamp <= item.getTs()) {
					add = true;
				} else {
					add = false;
				}
				if (add) {
					if (status == null || item.getStatus().equals(status)) {
						retlist.getChats().add(item);
					}
				}
			}
			return retlist.getChats();
		}
	}

}
