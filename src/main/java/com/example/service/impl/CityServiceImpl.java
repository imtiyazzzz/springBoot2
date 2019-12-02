package com.example.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.domain.City;
import com.example.dto.CityDTO;
import com.example.repository.CityRepository;
import com.example.service.CityService;
import com.example.service.ConfigService;
import com.example.util.transformer.CityMapper;
import com.sixsprints.core.dto.MetaData;
import com.sixsprints.core.generic.GenericRepository;
import com.sixsprints.core.service.AbstractCrudService;

@Service
public class CityServiceImpl extends AbstractCrudService<City> implements CityService {

  @Resource
  private CityRepository cityRepository;

  @Resource
  private CityMapper cityMapper;

  @Resource
  private ConfigService config;

  @Override
  protected GenericRepository<City> repository() {
    return cityRepository;
  }

  @Override
  public void updateAllFromDto(List<CityDTO> cityDTOs) {
    super.updateAll(cityMapper.toDomain(cityDTOs));
  }

  @Override
  public List<City> findAllCitiesByCountryNameIn(List<String> countryNames) {
    return cityRepository.findAllByCountryNameIn(countryNames);
  }

  @Override
  protected MetaData<City> metaData(City entity) {
    return MetaData.<City>builder().collection("city").prefix("CTY").classType(City.class)
      .dtoClassType(CityDTO.class).fields(config.get().getCityFields()).build();
  }

  @Override
  protected City findDuplicate(City entity) {
    return cityRepository.findByName(entity.getName());
  }
}