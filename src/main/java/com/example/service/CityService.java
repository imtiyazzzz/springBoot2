package com.example.service;

import java.util.List;

import com.example.domain.City;
import com.sixsprints.core.service.GenericCrudService;
import com.example.dto.CityDTO;

public interface CityService extends GenericCrudService<City>{
	 void updateAllFromDto(List<CityDTO> cityDTOs);

	  List<City> findAllCitiesByCountryNameIn(List<String> countryNames);
}
