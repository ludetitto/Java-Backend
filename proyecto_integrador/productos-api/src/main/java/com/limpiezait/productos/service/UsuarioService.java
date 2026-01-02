package com.limpiezait.productos.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.limpiezait.productos.model.Usuario;

@Service
public class UsuarioService {
	private static UsuarioService instance = new UsuarioService();
	
	public static UsuarioService getInstance() {
		return instance;
	}
	
	public Usuario getRandomUser() {
		String url = "https://randomuser.me/api/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject userObject = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject();
        JsonObject nameObject = userObject.getAsJsonObject("name");
        
        Usuario user = new Usuario();
        user.setNombre(nameObject.get("first").getAsString());
        user.setApellido(nameObject.get("last").getAsString());
        user.setEmail(userObject.get("email").getAsString());
        user.setTelefono(userObject.get("phone").getAsString());
        user.setCelular(userObject.get("cell").getAsString());
        
        return user;
	}
}
