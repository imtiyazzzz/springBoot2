package com.example.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.Authenticated;
import com.example.domain.User;
import com.example.dto.UserDTO;
import com.example.enums.AccessPermission;
import com.example.enums.EntityPermission;
import com.example.service.UserService;
import com.example.util.transformer.PageMapper;
import com.example.util.transformer.UserMapper;
import com.sixsprints.core.dto.FilterRequestDto;
import com.sixsprints.core.dto.PageDto;
import com.sixsprints.core.exception.EntityAlreadyExistsException;
import com.sixsprints.core.exception.EntityInvalidException;
import com.sixsprints.core.exception.EntityNotFoundException;
import com.sixsprints.core.utils.RestResponse;
import com.sixsprints.core.utils.RestUtil;

@RestController
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private PageMapper<User> pageMapper;

	@Resource
	private UserMapper userMapper;

	@PostMapping("add")
	ResponseEntity<RestResponse<UserDTO>> add(
			@Authenticated(entity = EntityPermission.USER, access = AccessPermission.CREATE) User user,
			@RequestBody @Valid UserDTO userDTO) throws EntityAlreadyExistsException, EntityInvalidException {
		//System.out.println(userDTO);
		return RestUtil.successResponse(userMapper.toDto(userService.create(userMapper.toEntity(userDTO))));
	}
	
	@PutMapping("update")
	  public ResponseEntity<RestResponse<UserDTO>> update(
	    @Authenticated(entity = EntityPermission.USER, access = AccessPermission.UPDATE) User user,
	    @RequestBody UserDTO userDTO, @RequestParam String propChanged)
	    throws EntityNotFoundException, EntityAlreadyExistsException {
		//System.out.println(userDTO);
	    return RestUtil.successResponse(userMapper.toDto(userService.patchUpdate(userDTO.getId(), userMapper.toEntity(userDTO), propChanged)));
	  }
	
	@PostMapping("/search")
	  public ResponseEntity<RestResponse<PageDto<UserDTO>>> filter(
	    @Authenticated(entity = EntityPermission.USER, access = AccessPermission.READ) User user,
	    @RequestBody FilterRequestDto filterRequestDto) {
	    return RestUtil.successResponse(userMapper.pageEntityToPageDtoDto(userService.filter(filterRequestDto)),"success");
	  }
}
