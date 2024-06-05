package com.example.demo.core;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.demo.enums.EnumsUtil.UserStatus;
import com.example.demo.enums.EnumsUtil.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

public class User {

	@JsonProperty
	private long id;

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	@JsonProperty
	private String firstName;

	@JsonProperty
	private String lastName;

	@JsonProperty
	private String email;

	@Enumerated(EnumType.STRING)
	@JsonProperty
	private UserType userType;

	@Enumerated(EnumType.STRING)
	@JsonProperty
	private UserStatus userStatus;

	public User() {
	}

	public User(long id, String username, String password, String firstName, String lastName, String email,
			UserType userType, UserStatus userStatus) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userType = userType;
		this.userStatus = userStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
