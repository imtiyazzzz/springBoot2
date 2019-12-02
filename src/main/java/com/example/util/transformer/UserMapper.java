package com.example.util.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.domain.User;
import com.example.dto.UserDTO;
import com.sixsprints.core.transformer.GenericTransformer;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends GenericTransformer<User, UserDTO> {

	public abstract User toEntity(UserDTO dto);

	@Mapping(target = "password", source = "user.email")
	public abstract UserDTO toDto(User user);

	@Override
	public User toDomain(UserDTO dto) {
		return toEntity(dto);
	}

}
