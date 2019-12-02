package com.example.domain;

import java.util.List;
import java.util.Locale;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sixsprints.core.domain.AbstractMongoEntity;
import com.sixsprints.core.dto.FieldDto;

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
public class Config extends AbstractMongoEntity {

  private static final long serialVersionUID = -3144821735162802309L;

  private List<FieldDto> userFields;

  private List<FieldDto> countryFields;

  private List<FieldDto> cityFields;

  private List<FieldDto> categoryFields;

  private List<FieldDto> voucherFields;

  private List<FieldDto> merchantFields;

  private List<FieldDto> membershipFields;

  private List<FieldDto> membershipPlanFields;

  private List<FieldDto> membershipCodeFields;

  private List<FieldDto> termsOfServiceFields;

  private List<FieldDto> privacyPolicyFields;

  private List<String> entityPermissions;

  private Locale selectedLocale;

}