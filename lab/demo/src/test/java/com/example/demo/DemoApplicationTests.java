package com.example.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
	
	@Test
	void getJSONObjectLabAdicional() throws JSONException {
		String json = """
			{
				"store": {
					"name": "Tienda Ejemplo",
					"owner": {
						"firstName": "Ana",
						"lastName": "Pérez"
					}
				},
				"location": {
					"address": {
						"street": "Avenida Siempre Viva",
						"number": 742
					},
					"city": "Springfield",
					"country": "USA"
				},
				"products": [
				   {
					   "id": 1,
					   "name": "Producto A",
					   "price": 10.0
				   },
				   {
					   "id": 2,
					   "name": "Producto B",
					   "price": 15.5
				   }
				 ]
		}
		""";
		
		JSONObject raiz = new JSONObject(json);
		
		JSONObject store = raiz.getJSONObject("store");
		assertEquals("Tienda Ejemplo", store.getString("name"));
		
		System.out.println("Local: ");
		System.out.println(store.getString("name"));
		JSONObject owner = store.getJSONObject("owner");
		System.out.println("Dueño: ");
		System.out.println(owner.getString("firstName") + " " + owner.getString("lastName"));
		assertEquals("Ana", owner.getString("firstName"));
	    assertEquals("Pérez", owner.getString("lastName"));
		
		JSONObject location = raiz.getJSONObject("location");
		System.out.println("Ubicación: ");
		JSONObject address = location.getJSONObject("address");
		System.out.println("Direccion: ");
		System.out.println(address.getString("street") + " " + address.getInt("number"));
		System.out.println(location.getString("city"));
		System.out.println(location.getString("country"));
		assertEquals("Avenida Siempre Viva", address.getString("street"));
	    assertEquals(742, address.getInt("number"));
	    assertEquals("Springfield", location.getString("city"));
	    assertEquals("USA", location.getString("country"));
		
		JSONArray products = raiz.getJSONArray("products");
		System.out.println("Productos: ");
		
		// En bucle
		/*
		for(int i = 0; i < products.length(); i++) {
			JSONObject product = products.getJSONObject(i);
			System.out.println(product.getInt("id") + " " + product.getString("name") + " " + product.getDouble("price"));
		}
		*/
		
		JSONObject productA = products.getJSONObject(0);
		System.out.println(productA.getInt("id") + " " + productA.getString("name") + " " + productA.getDouble("price"));
		assertEquals(1, productA.getInt("id"));
	    assertEquals("Producto A", productA.getString("name"));
	    assertEquals(10.0, productA.getDouble("price"));
	    
	    JSONObject productB = products.getJSONObject(1);
		System.out.println(productB.getInt("id") + " " + productB.getString("name") + " " + productB.getDouble("price"));
		assertEquals(2, productB.getInt("id"));
	    assertEquals("Producto B", productB.getString("name"));
	    assertEquals(15.5, productB.getDouble("price"));
	}
}
