package com.example.helloworld.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.helloworld.core.Person;
import com.example.helloworld.db.PersonDAO;
import com.indexnine.chat.ChatItem;
import com.indexnine.chat.ChatList;
import com.indexnine.chat.JsonUtils;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {
	private final PersonDAO peopleDAO;

	public PeopleResource(PersonDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}

	@POST
	@UnitOfWork
	public Person createPerson(Person person) {
		return peopleDAO.create(person);
	}

	@GET
	@UnitOfWork
	public Response listPeople() {
		List<Person> response = peopleDAO.findAll();
		for (Person person : response) {
			Long userId = person.getId();
			ChatList chatList = ChatResource.chats.get("" + userId);
			if (chatList != null) {
				for (ChatItem chatItem : chatList.getChats()) {
					person.setLastMsg(chatItem);
				}
			}
		}
		return Response.ok().entity(JsonUtils.getJson(response)).build();
	}

}
