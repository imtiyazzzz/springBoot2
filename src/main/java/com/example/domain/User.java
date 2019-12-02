package com.example.domain;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sixsprints.core.domain.AbstractMongoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document
public class User extends AbstractMongoEntity {

	private static final long serialVersionUID = -7130091497772656114L;

	private String firstName;

	private String lastName;

	@Indexed(unique = true)
	private String email;

	private String password;

	private List<String> invalidTokens;

	@Indexed
	private String countryName;

	@Indexed
	private String cityName;

	@Indexed(unique = true)
	private String mobileNumber;

	@Indexed
	private String roleName;

}
