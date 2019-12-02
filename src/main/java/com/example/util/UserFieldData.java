package com.example.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.ImmutableMap;
import com.sixsprints.core.dto.FieldDto;
import com.sixsprints.core.enums.DataType;

public class UserFieldData {

  public static final List<FieldDto> fields() {

    List<FieldDto> fields = new ArrayList<FieldDto>();
    int i = 0;
    fields.add(FieldDto.builder().name("firstName").displayName("First Name").sequence(i++)
      .dataType(DataType.TEXT)
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "First Name")).build());

    fields.add(FieldDto.builder().name("lastName").displayName("Last Name").sequence(i++)
      .dataType(DataType.TEXT)
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "Last Name")).build());

    fields.add(FieldDto.builder().name("email").displayName("Email").sequence(i++)
      .dataType(DataType.EMAIL)
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "Email"))
      .build());



    fields.add(FieldDto.builder().name("countryName").displayName("Country Name").sequence(i++)
      .dataType(DataType.SELECT).collectionName("country").columnName("name")
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "Country Name")).build());

    fields.add(FieldDto.builder().name("cityName").displayName("City Name").sequence(i++)
      .dataType(DataType.SELECT).collectionName("city").columnName("name")
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "City Name")).build());

    fields.add(FieldDto.builder().name("mobileNumber").displayName("Mobile Number").sequence(i++)
      .dataType(DataType.TEXT)
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "Mobile Number")).build());

    fields.add(FieldDto.builder().name("roleName").displayName("Role Name").sequence(i++)
      .dataType(DataType.SELECT).collectionName("role").columnName("name")
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "Role Name")).build());

    fields.add(FieldDto.builder().name("wallet.balance").displayName("Wallet").sequence(i++)
      .dataType(DataType.TEXT).isLocked(true)
      .localizedDisplay(ImmutableMap.<Locale, String>of(Locale.ENGLISH, "Wallet")).build());

    return fields;
  }

}