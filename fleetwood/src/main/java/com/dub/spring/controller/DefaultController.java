package com.dub.spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	
	@RequestMapping({"/", "/backHome", "/index"})
	public String home1(Model model) {
		 
		return "index"; 
	}
	
	 
	@RequestMapping("/login")
	public String login() {
	    
		return "login";
	}
	
}