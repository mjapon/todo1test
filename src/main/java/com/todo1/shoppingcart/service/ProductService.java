package com.todo1.shoppingcart.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.todo1.shoppingcart.model.Product;
import com.todo1.shoppingcart.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	
	public Optional<Product> findById(Long productId){
		return this.productRepository.findById(productId);
		
	}
	
	public Iterable<Product> findAll() {
		return this.productRepository.findAll();		
	}
	
	public Page<Product> findAllProductsPageable(Pageable pageable) {
		return this.productRepository.findAll(pageable);
	}
	
}
