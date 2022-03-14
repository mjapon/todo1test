package com.todo1.shoppingcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	/*
	@GetMapping("/error")
	public String errorView() {
		return "error";
	}
	
	@GetMapping("/403")
    public String unauthorizedView() {
		return "403";
    }
    */

}
