package com.example.util.transformer;
import com.sixsprints.core.transformer.GenericTransformer;
import com.example.dto.CityDTO;
import org.mapstruct.Mapper;
import com.example.domain.City;
@Mapper(componentModel = "spring")
public abstract class CityMapper extends GenericTransformer<City, CityDTO>{
	  public abstract City toEntity(CityDTO dto);

	  public abstract CityDTO toDto(City entity);

	  @Override
	  public City toDomain(CityDTO dto) {
	    return toEntity(dto);
	  }
}
