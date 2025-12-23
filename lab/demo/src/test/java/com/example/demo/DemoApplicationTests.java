package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Direccion;
import com.example.demo.model.Persona;
import com.google.gson.Gson;

import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringWriter;
import java.io.StringReader;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@SpringBootTest
class DemoApplicationTests {

	private Persona persona = new Persona("Juan A. Pérez", 
			31, 
			new Direccion("Calle Falsa 123", "Madrid", 28013),
			"+34 600 123 456",
			"juan.perez@example.com");

	@Test
	void serializacionJSONB() {
		// Serialización
		Jsonb jsonb = JsonbBuilder.create();
		String json = jsonb.toJson(persona);
		
		// Deserialización
		Persona personaDeserializada = jsonb.fromJson(json, Persona.class);

		assertEquals(persona, personaDeserializada);
	}
	
	@Test
	void serializacionJackson() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		// Serialización
		String json = objectMapper.writeValueAsString(persona);
		
		// Deserialización
		Persona personaDeserializada = objectMapper.readValue(json, Persona.class);
		
		assertEquals(persona, personaDeserializada);
	}

	@Test
	void serializacionGson() {
		Gson gson = new Gson();
		
		// Serialización
		String json = gson.toJson(persona);
		// Deserialización
		Persona personaDeserializada = gson.fromJson(json, Persona.class);
		
		assertEquals(persona, personaDeserializada);
	}
	
	@Test
	void serializacionXML() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Persona.class);
		
		// Serialización
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer = new StringWriter();
	    marshaller.marshal(persona, writer);
		
	    // Deserialización
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Persona personaDeserializada = (Persona) unmarshaller.unmarshal(
				new StringReader(writer.toString()));
		
		assertEquals(persona, personaDeserializada);
	}
}
