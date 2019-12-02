package com.example.util;

import java.util.Locale;

public interface Constants {

  int OTP_VALIDITY_IN_MINUTES = 10;

  String DEFAULT_ADMIN_ROLE = "ADMIN";

  String DEFAULT_USER_ROLE = "USER";

  String AUTH_TOKEN = "X-AUTH-TOKEN";

  String API_PREFIX = "/api/v1";

  String CSV_IMPORT_MESSAGE = "CSV file import processing is complete. Number of rows successfully processed: %d (number of error cells: %d), number of rows failed in processing: %d. See the CSV error log for details.";

  Locale DEFAULT_LOCALE = Locale.ENGLISH;

  // "(+<2 to 4 digit>)<4 to 12 digit>"
  String DEFAULT_MOBILE_NUMBER_REGEX = "^[(][+]\\d{2,4}[)]\\d{4,12}$";

  String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
}
