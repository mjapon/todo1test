package com.todo1.shoppingcart.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo1.shoppingcart.model.Product;
import com.todo1.shoppingcart.service.ProductService;
import com.todo1.shoppingcart.util.Pager;

@Controller
public class HomeController {

	private static final int INIT_PAGE = 0;
	private static final int PAGE_SIZE = 10;

	private final ProductService productService;

	@Autowired
	public HomeController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/home")
	public String homeProducts(@RequestParam("page") Optional<Integer> page, Model model) {

		int queryPage = page.orElse(0) < 1 ? INIT_PAGE : page.get() - 1;

		Page<Product> products = this.productService.findAllProductsPageable(PageRequest.of(queryPage, PAGE_SIZE));

		Pager pager = new Pager(products);

		model.addAttribute("products", products);
		model.addAttribute("pager", pager);

		return "home";
	}

}
