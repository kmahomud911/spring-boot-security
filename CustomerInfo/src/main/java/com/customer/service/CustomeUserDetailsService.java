package com.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.customer.model.User;
import com.customer.model.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found..");
		}
		log.info(user.toString());
		return new CustomUserDetails(user);
	}

}
