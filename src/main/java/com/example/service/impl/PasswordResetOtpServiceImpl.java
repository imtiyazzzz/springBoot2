package com.example.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixsprints.core.dto.MetaData;
import com.sixsprints.core.generic.GenericRepository;
import com.sixsprints.core.service.AbstractCrudService;
import com.example.domain.PasswordResetOtp;
import com.example.repository.PasswordResetOtpRepository;
import com.example.service.PasswordResetOtpService;

@Service
public class PasswordResetOtpServiceImpl extends AbstractCrudService<PasswordResetOtp>
  implements PasswordResetOtpService {

  @Autowired
  private PasswordResetOtpRepository passwordResetOtpRepository;

  @Override
  protected GenericRepository<PasswordResetOtp> repository() {
    return passwordResetOtpRepository;
  }

  @Override
  protected MetaData<PasswordResetOtp> metaData(PasswordResetOtp entity) {
    return MetaData.<PasswordResetOtp>builder().collection("PasswordResetOtp").prefix("PRO").build();
  }

  @Override
  protected PasswordResetOtp findDuplicate(PasswordResetOtp passwordResetOtp) {
    return passwordResetOtpRepository.findByEmail(passwordResetOtp.getEmail());
  }

  @Override
  protected boolean isInvalid(PasswordResetOtp passwordResetOtp) {
    return passwordResetOtp == null || StringUtils.isBlank(passwordResetOtp.getEmail());
  }

  @Override
  public PasswordResetOtp findByEmail(String email) {
    return passwordResetOtpRepository.findByEmail(email);
  }

}
