package com.example.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import com.sixsprints.core.generic.GenericRepository;
import com.example.domain.PasswordResetOtp;

@Repository
@JaversSpringDataAuditable
public interface PasswordResetOtpRepository extends GenericRepository<PasswordResetOtp> {

  PasswordResetOtp findByEmail(String email);

}
