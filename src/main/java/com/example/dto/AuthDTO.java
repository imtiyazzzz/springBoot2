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
public class AuthDTO implements Authenticable {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

  @Override
  public String authId() {
    return email;
  }

  @Override
  public String passcode() {
    return password;
  }
}
