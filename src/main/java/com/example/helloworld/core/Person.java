package com.example.helloworld.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.indexnine.chat.ChatItem;

@Entity
@Table(name = "people")
@NamedQueries({ @NamedQuery(name = "com.example.helloworld.core.Person.findAll", query = "SELECT p FROM Person p") })
public class Person implements Serializable {
	private static final long serialVersionUID = -2886004747302408035L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "os", nullable = true)
	private String os;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "status", nullable = true)
	private String status;

	private transient ChatItem lastMsg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ChatItem getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(ChatItem lastMsg) {
		this.lastMsg = lastMsg;
	}

}
