package com.todo1.shoppingcart.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.todo1.shoppingcart.exception.NotEnoughProductsInStockException;
import com.todo1.shoppingcart.model.Product;
import com.todo1.shoppingcart.repository.ProductRepository;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartService {

	private final ProductRepository productRepository;
	private Map<Product, Integer> products = new HashMap<Product, Integer>();

	@Autowired
	public ShoppingCartService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void addProduct(Product product) {
		if (products.containsKey(product)) {
			products.replace(product, products.get(product) + 1);
		} else {
			products.put(product, 1);
		}
	}

	public void removeProduct(Product product) {
		if (products.containsKey(product)) {
			if (products.get(product) > 1)
				products.replace(product, products.get(product) - 1);
			else if (products.get(product) == 1) {
				products.remove(product);
			}
		}
	}
	
	
	public Map<Product, Integer> getProductsInCart(){
		return this.products;
	}
	
	public boolean checkInventory(Product product, Integer quantity) {
		return product.getQuantity() < quantity;		
	}
	

	public void checkout() throws NotEnoughProductsInStockException {
		Product product;
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			Optional<Product> productOptional = productRepository.findById(entry.getKey().getId());
			if (productOptional.isPresent()) {
				product = productOptional.get();
				if (this.checkInventory(product, entry.getValue())) {				
					throw new NotEnoughProductsInStockException(product);
				}
				entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
				productRepository.save(entry.getKey());
			}
		}
		products.clear();
	}

	public BigDecimal getTotal() {
		return products.entrySet().stream()
				.map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
				.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

}
