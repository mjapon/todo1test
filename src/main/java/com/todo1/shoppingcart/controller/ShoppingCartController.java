package com.todo1.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import com.todo1.shoppingcart.exception.NotEnoughProductsInStockException;
import com.todo1.shoppingcart.service.ProductService;
import com.todo1.shoppingcart.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
	private final ShoppingCartService shoppingCartService;
	private final ProductService productService;

	@Autowired
	public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService) {
		this.shoppingCartService = shoppingCartService;
		this.productService = productService;
	}

	@GetMapping("/shoppingCart")
	public String shoppingCart(Model model) {
		model.addAttribute("products", this.shoppingCartService.getProductsInCart());
		model.addAttribute("total", this.shoppingCartService.getTotal());
		return "shoppingCart";
	}

	@GetMapping("/shoppingCart/addProduct/{productId}")
	public RedirectView addProductToCart(@PathVariable("productId") Long productId, Model model) {
		this.productService.findById(productId).ifPresent(shoppingCartService::addProduct);
		return new RedirectView("/shoppingCart");
	}

	@GetMapping("/shoppingCart/removeProduct/{productId}")
	public RedirectView removeProductFromCart(@PathVariable("productId") Long productId, Model model) {
		productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
		return new RedirectView("/shoppingCart");
	}

	@GetMapping("/shoppingCart/checkout")
	public String checkout(Model model) {
		try {
			shoppingCartService.checkout();
		} catch (NotEnoughProductsInStockException e) {
			model.addAttribute("outOfStockMessage", e.getMessage());
		}
		return shoppingCart(model);
	}

}
