package com.dub.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	
	@Autowired
	private SimpleUrlAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private SecurityContextLogoutHandler mySecurityContextLogoutHandler;
	
	@Value("${gateway-path}")
	private String gatewayPath; 
	
	@Value("${gateway-url}")
	private String gatewayUrl;
	
	@RequestMapping({"/", "/backHome", "/index"})
	public String home1(Model model) {
		 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth " + auth);
		 
		model.addAttribute("gatewayPath", gatewayPath); 
		return "index";
	 }
	
	@RequestMapping("/login")
    public String login(Model model) {	
		 
		model.addAttribute("gatewayPath", gatewayPath); 
        return "login";
    }
	

	@RequestMapping("/logout")
    public String logout(
    				HttpServletRequest request, 
    				HttpServletResponse response,
    				Model model) {	
		
		// reset defaultTargetUrl 
		myAuthenticationSuccessHandler.setDefaultTargetUrl(gatewayUrl + "/index");
		
		//invalidate current session
		HttpSession session = request.getSession();
		session.invalidate();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if (auth != null) { 
			mySecurityContextLogoutHandler.logout(request, response, auth);
		}
		
        return "redirect:" + gatewayUrl + "/login?logout";
    }
	
}
