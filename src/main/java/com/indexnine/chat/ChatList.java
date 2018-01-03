package com.indexnine.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatList {
	private List<ChatItem> chats = new ArrayList<>();

	public List<ChatItem> getChats() {
		return chats;
	}

	public void setChats(List<ChatItem> chats) {
		this.chats = chats;
	}

}
