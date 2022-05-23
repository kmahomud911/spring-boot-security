package com.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue
	private Integer ID;
	private String email;
	@Column(unique = true)
	private String username;
	private String name;
	private String pass;
	@Column(unique = true)
	private String phoneNumber;
	private String role;
}
