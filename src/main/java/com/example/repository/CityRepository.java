package com.example.repository;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import com.example.domain.City;
import com.sixsprints.core.generic.GenericRepository;
@Repository
@JaversSpringDataAuditable
public interface CityRepository extends GenericRepository<City>{
	  City findByName(String name);

	  List<City> findAllByCountryNameIn(List<String> countryNames);
}
