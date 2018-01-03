package com.indexnine.chat;

public class ChatRequest {
	private Long userId;
	private String text;
	private String from;
	private Long lastTimestamp;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Long getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(Long lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

}
