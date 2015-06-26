/**
 * 
 */
package com.pasm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Peter
 * 
 */
@Controller
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@RequestMapping(value =  "/", method = RequestMethod.GET)
	public ModelAndView welcomePage(HttpServletRequest request,HttpServletResponse resonse) {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is welcome page!");
		model.setViewName("welcome");
		return model;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}

	
	
	@RequestMapping(value = "/customLogin", method = RequestMethod.GET)
	public ModelAndView customLogin(String code,HttpServletRequest request,HttpServletResponse resonse){
		ModelAndView model = new ModelAndView();
		String username="admin";
		String password="123";
		performLogin(username, password,request, resonse);
		model.setViewName("admin");
		return model;
    }
	
	protected void performLogin(String username, String password, HttpServletRequest request,HttpServletResponse response) {
	    if (username == null) username = "";
	    if (password == null) password = "";
	    username = username.trim();
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
	    authRequest.setDetails(new WebAuthenticationDetails(request));
	    Authentication authResult = null;
	    try {
	    	
	        authResult = authenticationManager.authenticate(authRequest);
	    } catch (AuthenticationException failed) {
	    }
	    SecurityContextHolder.getContext().setAuthentication(authResult);
	}
	
/*	@RequestMapping(value = "/j_spring_security_logout", method = RequestMethod.GET)

	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          if (auth != null){    
             new SecurityContextLogoutHandler().logout(request, response, auth);
          }
        SecurityContextHolder.getContext().setAuthentication(null);
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is welcome page!");
		model.setViewName("welcome");
		return model;
    }*/
}
