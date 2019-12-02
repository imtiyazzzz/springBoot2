package com.example.service;

import com.sixsprints.core.service.GenericCrudService;
import com.example.domain.PasswordResetOtp;

public interface PasswordResetOtpService extends GenericCrudService<PasswordResetOtp> {
  PasswordResetOtp findByEmail(String email);
}