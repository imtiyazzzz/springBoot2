package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.dto.AuthDTO;
import com.example.dto.UserDTO;
import com.sixsprints.auth.dto.AuthResponseDTO;
import com.sixsprints.auth.service.AuthService;
import com.sixsprints.core.exception.EntityAlreadyExistsException;
import com.sixsprints.core.exception.EntityInvalidException;
import com.sixsprints.core.exception.EntityNotFoundException;
import com.sixsprints.core.exception.NotAuthenticatedException;
import com.sixsprints.core.exception.NotAuthorizedException;

public interface UserService extends AuthService<User> {

  AuthResponseDTO<UserDTO> signup(UserDTO userDTO) throws EntityAlreadyExistsException, EntityInvalidException;

  AuthResponseDTO<UserDTO> signin(AuthDTO authDTO) throws NotAuthenticatedException, EntityNotFoundException;

  AuthResponseDTO<UserDTO> adminSignin(AuthDTO authDTO)
    throws NotAuthenticatedException, EntityNotFoundException, NotAuthorizedException;

  UserDTO tokenValidation(User user);

  void signout(User domain, String token);

  void updateAllFromDto(List<UserDTO> userDTOs);

  User findByEmail(String email);

}
