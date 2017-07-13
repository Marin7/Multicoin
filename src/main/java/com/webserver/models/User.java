package com.webserver.models;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "email", nullable = false, length = 200)
	private String email;

	@Column(name = "dollars", nullable = false)

	private Double dollars;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "multicoin_address", nullable = false)
	private String multicoinAddress;

	public User() {
	}

	public User(Integer id, String username, String email, Double dollars, String password, String multicoinAddress) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.dollars = dollars;
		this.password = password;
		this.multicoinAddress = multicoinAddress;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getDollars() {
		return dollars;
	}

	public void setDollars(Double dollars) {
		this.dollars = dollars;
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

	public String getMulticoinAddress() {
		return multicoinAddress;
	}

	public void setMulticoinAddress(String multicoinAddress) {
		this.multicoinAddress = multicoinAddress;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", dollars=" + dollars +
				", password='" + password + '\'' +
				", multicoinAddress='" + multicoinAddress + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (email != null ? !email.equals(user.email) : user.email != null) return false;
		if (dollars != null ? !dollars.equals(user.dollars) : user.dollars != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		return multicoinAddress != null ? multicoinAddress.equals(user.multicoinAddress) : user.multicoinAddress == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (dollars != null ? dollars.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (multicoinAddress != null ? multicoinAddress.hashCode() : 0);
		return result;
	}
}
