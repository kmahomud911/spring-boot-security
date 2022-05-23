package com.customer.service;

import org.springframework.stereotype.Service;

import com.customer.model.User;
import com.customer.model.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private UserRepo repo;

	public CustomerServiceImpl(UserRepo repo) {
		this.repo = repo;
	}

	@Override
	public User createUser(User user) {

		return repo.save(user);
	}

	@Override
	public void deleteUser(User user) {
		log.info("Deleting user {}", repo.findById(user.getID()));
		repo.deleteById(user.getID());
	}

	@Override
	public User updateUser(User user) {
		return repo.save(user);
	}
}
