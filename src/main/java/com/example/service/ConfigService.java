package com.example.service;

import java.util.Locale;

import com.sixsprints.core.service.GenericCrudService;
import com.example.domain.Config;

public interface ConfigService extends GenericCrudService<Config> {
  Config get();

  Config get(Locale locale);
}