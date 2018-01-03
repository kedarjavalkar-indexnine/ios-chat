package com.example.helloworld.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.indexnine.chat.ChatItem;
import com.indexnine.chat.ChatList;
import com.indexnine.chat.ChatRequest;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/chat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {
	public static HashMap<String, ChatList> chats = new HashMap<>();

	@GET
	@UnitOfWork
	@Path("/{timestamp}")
	public List<ChatItem> getAllChatList(@PathParam("timestamp") Long timestamp) {
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
					response.add(item);
				}
			}
		}

		return response;
	}

	@GET
	@UnitOfWork
	@Path("user/{userId}/{timestamp}")
	public List<ChatItem> getChatList(@PathParam("userId") LongParam userId, @PathParam("timestamp") Long timestamp) {
		ChatList list = chats.get("" + userId);
		if (list == null) {
			return new ChatList().getChats();
		} else {
			if (timestamp.equals(0l)) {
				return list.getChats();
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
						retlist.getChats().add(item);
					}
				}
				return retlist.getChats();
			}
		}
	}

	@POST
	@UnitOfWork
	@Path("user/{userId}")
	public List<ChatItem> addChat(@PathParam("userId") LongParam userId, ChatRequest req) {
		ChatList userChatList = chats.get("" + userId);

		if (userChatList == null) {
			userChatList = new ChatList();
		}

		ChatItem item = new ChatItem();
		item.setFrom(req.getFrom());
		item.setMsg(req.getText());
		item.setTs(new Date().getTime());

		userChatList.getChats().add(item);
		chats.put("" + userId, userChatList);

		return getChatList(userId, req.getLastTimestamp());
	}

}
