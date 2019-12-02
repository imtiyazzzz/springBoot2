package com.example.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Config;
import com.example.enums.EntityPermission;
import com.example.repository.ConfigRepository;
import com.example.service.ConfigService;
import com.example.util.Constants;
import com.example.util.UserFieldData;
import com.sixsprints.core.dto.FieldDto;
import com.sixsprints.core.dto.MetaData;
import com.sixsprints.core.generic.GenericRepository;
import com.sixsprints.core.service.AbstractCrudService;

@Service
public class ConfigServiceImpl extends AbstractCrudService<Config> implements ConfigService {
  @Autowired
  private ConfigRepository configRepository;

  @Override
  protected GenericRepository<Config> repository() {
    return configRepository;
  }

  @Override
  public Config get() {
    return get(Constants.DEFAULT_LOCALE);
  }

  @Override
  public Config get(Locale locale) {
    Config config = getConfigFromDb();
    return localize(config, locale);
  }

  private Config getConfigFromDb() {
    List<Config> configs = findAll();
    if (configs.isEmpty()) {
      return init();
    }
    return configs.get(0);
  }

  private Config localize(Config config, Locale locale) {
    config.setSelectedLocale(locale);
    localiseStrings(locale, config.getUserFields());
    localiseStrings(locale, config.getCountryFields());
    localiseStrings(locale, config.getCityFields());
    localiseStrings(locale, config.getCategoryFields());
    localiseStrings(locale, config.getMerchantFields());
    localiseStrings(locale, config.getVoucherFields());
    localiseStrings(locale, config.getMembershipFields());
    localiseStrings(locale, config.getMembershipPlanFields());
    localiseStrings(locale, config.getMembershipCodeFields());
    localiseStrings(locale, config.getTermsOfServiceFields());
    localiseStrings(locale, config.getPrivacyPolicyFields());
    return config;
  }

  private void localiseStrings(Locale locale, List<FieldDto> fields) {
    if (fields != null && !fields.isEmpty()) {
      for (FieldDto dto : fields) {
        String displayName = dto.getLocalizedDisplay().get(locale);
        if (StringUtils.isNotBlank(displayName)) {
          dto.setDisplayName(displayName);
        } else {
          dto.setDisplayName(dto.getLocalizedDisplay().get(Locale.ENGLISH));
        }
        dto.setLocalizedDisplay(null);
      }
    }
  }

  private Config init() {
    List<FieldDto> userFields = userFields();

    List<String> permissions = allPermissions();
    Config config = Config.builder()
      .userFields(userFields).build();
    return save(config);
  }

  private List<String> allPermissions() {
    return Arrays.asList(EntityPermission.values()).stream().filter(perm -> !EntityPermission.ANY.equals(perm))
      .map(perm -> perm.toString()).collect(Collectors.toList());
  }

  private List<FieldDto> userFields() {
    return UserFieldData.fields();
  }


  @Override
  protected MetaData<Config> metaData(Config entity) {
    return MetaData.<Config>builder().collection("config").prefix("CFG").classType(Config.class).build();
  }

  @Override
  protected Config findDuplicate(Config entity) {
    return configRepository.findBySlug(entity.getSlug());
  }
}