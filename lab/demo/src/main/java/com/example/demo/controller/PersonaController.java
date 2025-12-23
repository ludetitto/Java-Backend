package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Persona;
import com.example.demo.service.PersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private PersonaService personaService = PersonaService.getInstance();

    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Persona persona = personaService.findById(id);
        return new ResponseEntity<>(persona, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona savedPersona = personaService.save(persona);
        return new ResponseEntity<>(savedPersona, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(
            @PathVariable Long id,
            @RequestBody Persona personaDetails) {

        Persona existingPersona = personaService.findById(id);

        if (existingPersona != null) {
            existingPersona.setNombre(personaDetails.getNombre());
            existingPersona.setEdad(personaDetails.getEdad());
            existingPersona.setDireccion(personaDetails.getDireccion());
            existingPersona.setTelefono(personaDetails.getTelefono());
            existingPersona.setEmail(personaDetails.getEmail());

            return ResponseEntity.ok(existingPersona);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        if (personaService.findById(id) != null) {
            personaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
