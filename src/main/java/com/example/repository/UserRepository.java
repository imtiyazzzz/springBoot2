package com.example.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import com.example.domain.User;
import com.sixsprints.core.generic.GenericRepository;

@Repository
@JaversSpringDataAuditable
public interface UserRepository extends GenericRepository<User> {

  User findByEmail(String email);

  User findByEmailOrMobileNumber(String email, String mobileNumber);

}
