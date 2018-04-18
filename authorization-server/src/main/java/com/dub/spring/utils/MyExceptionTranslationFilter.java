package com.dub.spring.utils;


/**
 * This custom bean is used to save the correct redirect URI during the authentication handling. 
 * The redirect URI and the query string are saved as a session attribute named redirectURI.
 * */

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

public class MyExceptionTranslationFilter 
						extends ExceptionTranslationFilter {

	
	public MyExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationEntryPoint);
	}

	
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
	
		HttpServletRequest req = (HttpServletRequest) servletRequest;
			
		String requestURI = req.getRequestURI();
		
		String queryStr = req.getQueryString();
				
		HttpSession session = req.getSession();
		
		// save only if not authenticated yet
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth " + auth.getClass());
		System.out.println("requestURI " + requestURI);
		
		String fullRequestURI = requestURI + "?" + queryStr;
		System.out.println("fullRequestURI " + fullRequestURI);
		
		if ((auth.getClass() == AnonymousAuthenticationToken.class) && !"/login".equals(requestURI)) {
			System.out.println("Setting requestURI " + requestURI);
			
			session.setAttribute("requestURI", fullRequestURI);	
		} 
			
		super.doFilter(servletRequest, servletResponse, filterChain);
	}
}
