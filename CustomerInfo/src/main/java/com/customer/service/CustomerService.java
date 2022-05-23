package com.customer.service;

import com.customer.model.User;

public interface CustomerService {

	User createUser(User user);

	void deleteUser(User user);

	User updateUser(User user);
}
