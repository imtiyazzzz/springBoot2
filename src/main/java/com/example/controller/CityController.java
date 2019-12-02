package com.example.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.Authenticated;
import com.example.domain.User;
import com.example.dto.CityDTO;
import com.example.enums.AccessPermission;
import com.example.enums.EntityPermission;
import com.example.service.CityService;
import com.example.util.transformer.CityMapper;
import com.sixsprints.core.exception.EntityAlreadyExistsException;
import com.sixsprints.core.exception.EntityInvalidException;
import com.sixsprints.core.utils.RestResponse;
import com.sixsprints.core.utils.RestUtil;
import com.example.util.Constants;
@RestController
public class CityController {
	  @Resource
	  private CityService cityService;

//	  @Resource
//	  private PageMapper<City> pageMapper;

	  @Resource
	  private CityMapper cityMapper;

	  @PostMapping("city/add")
	  public ResponseEntity<RestResponse<CityDTO>> add(
	    @Authenticated(entity = EntityPermission.CITY, access = AccessPermission.CREATE) User user,
	    @RequestBody @Valid CityDTO countryDTO) throws EntityAlreadyExistsException, EntityInvalidException {
		  System.out.println(countryDTO);
	    return RestUtil.successResponse(cityMapper.toDto(cityService.create(cityMapper.toEntity(countryDTO))));
	  }
}
