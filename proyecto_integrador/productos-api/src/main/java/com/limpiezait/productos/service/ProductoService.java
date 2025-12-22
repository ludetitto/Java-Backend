package com.limpiezait.productos.service;

import java.util.Set;

import com.limpiezait.productos.model.Producto;
import com.limpiezait.productos.repository.ProductoRepository;

public class ProductoService {
	private static ProductoService instance = new ProductoService();
	private ProductoRepository productoRepository;
	
	public ProductoService() {
		productoRepository = new ProductoRepository();
	}
	
	public static ProductoService getInstance() {
		
		return instance;
	}
	
	public Set<Producto> findAll() {
		return productoRepository.findAll();
	}
	
	public Producto findById(Long id) {
		return productoRepository.findById(id);
	}
	
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}
	
	public void deleteById(Long id) {
		productoRepository.deleteById(id);
	}
	
	public Producto findByName(String nombre) {
		return productoRepository.findByNombre(nombre);
	}
}
