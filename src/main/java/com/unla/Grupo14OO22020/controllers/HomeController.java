package com.unla.Grupo14OO22020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.unla.Grupo14OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo14OO22020.repositories.IUserRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	
	
	@Autowired
	@Qualifier("userRepository")
	private IUserRepository userRepository;
	
	
	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelpers.HOME);
		
		/*********************************************************************************************************/
		
		String currentUserName = "";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}
		
		System.out.println("--------------------------------------------------------------------------");
		System.out.println(userRepository.findByUsername(currentUserName).get());
		System.out.println(userRepository.findByUsername(currentUserName).get().getId());
		System.out.println(userRepository.findByUsername(currentUserName).get().getIdLocal());
		System.out.println("--------------------------------------------------------------------------");
		

	

		/*********************************************************************************************************/
		
		return modelAndView;
	}
	
	
	
	@GetMapping("/homeEmpleado")
	public ModelAndView homeEmpleado() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelpers.HOME_EMPLEADO);
		
		
		return modelAndView;
	}
	
	
	

}



