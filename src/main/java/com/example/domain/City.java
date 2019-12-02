package com.example.domain;

import org.springframework.data.mongodb.core.index.Indexed;

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
@EqualsAndHashCode(callSuper = true)
public class City extends AbstractMongoEntity {

  private static final long serialVersionUID = 6383759485440499086L;

  @Indexed(unique = true)
  private String name;

  @Indexed
  private String countryName;
}