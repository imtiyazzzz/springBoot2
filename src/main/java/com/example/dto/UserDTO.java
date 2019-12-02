package com.example.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.sixsprints.auth.dto.Authenticable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class UserDTO implements Authenticable {

	private String id;

	private String slug;

	@NotBlank
	private String firstName;

	private String lastName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String countryName;

	@NotBlank
	private String cityName;

	@NotBlank
	private String mobileNumber;

	private String roleName;

	private String walletSlug;

	private String bookletSlug;

	@Override
	public String authId() {
		return getEmail();
	}

	@Override
	public String passcode() {
		return getPassword();
	}
}