package com.customer.controller;

/**
 * @author Shawon
 */

import javax.annotation.security.RolesAllowed;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.model.User;
import com.customer.model.UserRepo;
import com.customer.service.CustomerService;
import com.customer.utils.MyGson;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerController {

	private final CustomerService customerService;
	private final UserRepo repo;
	private final PasswordEncoder passwordEncoder;

	public CustomerController(CustomerService customerService, UserRepo repo) {
		this.customerService = customerService;
		this.repo = repo;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@PostMapping("/register")
	private String createUser(@RequestBody String json) {
		/*
		 * this is a public API.. phone number and username should be unique...
		 * passwords will be encoded before saving user info into DB
		 */
		User userInfo = null;

		try {
			userInfo = MyGson.gson.fromJson(json, User.class);
			String encryptedPass = this.passwordEncoder.encode(userInfo.getPass());
			userInfo.setPass(encryptedPass);
			customerService.createUser(userInfo);
		} catch (JsonSyntaxException e) {
			log.info("Json parsing error{}", e);
		}
		if (userInfo.getRole().equalsIgnoreCase("USER")) {
			return "Created USER" + userInfo.toString();
		} else
			return "Created ADMIN" + userInfo.toString();
	}

	@PostMapping("/update")
	@RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
	public String updateUserInfo(@RequestBody String json) {

		try {
			/*
			 * convert json to User search in DB exist or not if exist update info in User
			 * object comes from DB if not exist insert data
			 */
			User updatedUserInfo = MyGson.gson.fromJson(json, User.class);
			User dbInfo = repo.findByUsername(updatedUserInfo.getUsername());
			updatedUserInfo.setID(dbInfo.getID());
			customerService.updateUser(updatedUserInfo);
		} catch (JsonSyntaxException e) {
			log.info("Parsing error {}", e);
		}
		return "Updated UserInfo" + json;
	}

	@PostMapping("/delete")
	@RolesAllowed("ROLE_ADMIN")
	public String deleteUser(@RequestBody String json) {
		User updatedUserInfo = MyGson.gson.fromJson(json, User.class);
		customerService.deleteUser(getUserID(updatedUserInfo.getUsername()));
		return "Deleted user" + json;
	}

	@GetMapping("/admin")
	@RolesAllowed("ROLE_ADMIN")
	public String adminAPI() {
		return "Welcome Admin";
	}

	@GetMapping("/")
	private String homeAPI() {
		return "<h1>Welcome</h1>";
	}

	private User getUserID(String userName) {
		return repo.findByUsername(userName);

	}
}
