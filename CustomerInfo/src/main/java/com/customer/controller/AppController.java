package com.customer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customer.model.UserRepo;
import com.customer.service.CustomerService;
import com.google.gson.Gson;

@RestController
@CrossOrigin("*")
public class AppController {
	private static Logger log = LogManager.getLogger(AppController.class);

	static Gson gson;
	static {
		gson = new Gson();
	}

	private final CustomerService customerService;
	private final UserRepo repo;

	public AppController(CustomerService customerService, UserRepo repo) {
		this.customerService = customerService;
		this.repo = repo;
	}

	@RequestMapping(value = "/test/api", method = RequestMethod.GET)
	public String testApi() {
		return customerService.toString();
	}

}