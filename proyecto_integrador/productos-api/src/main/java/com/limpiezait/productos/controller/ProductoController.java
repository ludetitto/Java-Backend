package com.limpiezait.productos.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.limpiezait.productos.model.Producto;
import com.limpiezait.productos.service.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	private ProductoService productoService = ProductoService.getInstance();
	
	@GetMapping
	public Set<Producto> getAllProductos() {
		return productoService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProductoById(@PathVariable("id") Long id) {
		Producto producto = productoService.findById(id);
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
		Producto savedProducto = productoService.save(producto);
		return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Producto> updateProducto(@PathVariable("id") Long id, @RequestBody Producto producto) {
		Producto existingProducto = productoService.findById(id);
		if(existingProducto != null) {
			existingProducto.setId(producto.getId());
			existingProducto.setNombre(producto.getNombre());
			existingProducto.setDescripcion(producto.getDescripcion());
			existingProducto.setPrecio(producto.getPrecio());
			existingProducto.setUrlFoto(producto.getUrlFoto());
			return ResponseEntity.ok(existingProducto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProducto(@PathVariable("id") Long id) {
		if(productoService.findById(id) != null) {
			productoService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
