package com.todo1.shoppingcart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.todo1.shoppingcart.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
	
	Page<Product> findAll(Pageable pageable);
	
}
