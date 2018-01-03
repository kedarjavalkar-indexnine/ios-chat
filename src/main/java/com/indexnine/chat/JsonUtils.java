package com.indexnine.chat;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class JsonUtils {

	public static String getJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Hibernate5Module()).setSerializationInclusion(Include.NON_NULL);
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}