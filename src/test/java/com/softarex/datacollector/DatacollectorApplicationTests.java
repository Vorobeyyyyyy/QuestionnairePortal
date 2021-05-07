package com.softarex.datacollector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softarex.datacollector.model.entity.field.FieldType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class DatacollectorApplicationTests {
	@Test
	void contextLoads() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(FieldType.CHECKBOX));
	}

}
