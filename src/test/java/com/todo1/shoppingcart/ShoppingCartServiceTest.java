package com.todo1.shoppingcart;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.todo1.shoppingcart.model.Product;
import com.todo1.shoppingcart.service.ShoppingCartService;

public class ShoppingCartServiceTest {
	
	@BeforeEach()
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	private ShoppingCartService service;
	
	
	private Product getTestProduct() {
		Product product = new Product();
		product.setId(1L);
		product.setName("TEST");
		product.setDescription("TEST");
		product.setPrice(new BigDecimal("1.2"));
		product.setQuantity(10);
		
		return product;
	}
	
	@Test
	void testInventory() {			
		Product product = this.getTestProduct();
		assertTrue(this.service.checkInventory(product, 15));		
	}
	
	@Test
	void testTotal() {		
		Product product = this.getTestProduct();		
		this.service.addProduct(product);
		this.service.addProduct(product);		
		BigDecimal total =  this.service.getTotal();
		assertTrue(total.compareTo(new BigDecimal("2.4"))==0);
				
	}
	
	@Test
	void testAdd() {
		Product product = this.getTestProduct();		
		this.service.addProduct(product);		
		assertTrue(this.service.getProductsInCart().size()==1);		
	}
	
	@Test
	void testRemove() {
		Product product = this.getTestProduct();		
		this.service.addProduct(product);
		this.service.removeProduct(product);		
		assertTrue(this.service.getProductsInCart().size()==0);		
	}

}
