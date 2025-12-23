package com.limpiezait.productos.repository;

import java.util.HashSet;
import java.util.Set;

import com.limpiezait.productos.model.Producto;

public class ProductoRepository {
	private Set<Producto> productos;
	
	public ProductoRepository() {
		productos = new HashSet<Producto>();
	}
	
	public Set<Producto> findAll() {
		return productos;
	}
	
	public Producto findById(Long id) {
		for(Producto producto : productos) {
			if(producto.getId().equals(id))
				return producto;
		}
		return null;
	}
	
	public Producto save(Producto producto) {
		productos.add(producto);
		return producto;
	}
	
	public void deleteById(Long id) {
		Producto producto = findById(id);
		
		if(producto != null) {
			productos.remove(producto);
		}
	}

	public Set<Producto> findByNombre(String nombre) {
		Set<Producto> productosCoincidentes = new HashSet<Producto>();
		
		for(Producto producto : productos) {
			if(producto.getNombre().toLowerCase().contains(nombre.toLowerCase()))
				productosCoincidentes.add(producto);
		}
		return productosCoincidentes;
	}
}
