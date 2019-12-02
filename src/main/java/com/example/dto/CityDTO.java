package com.example.dto;

import javax.validation.constraints.NotBlank;

import com.example.dto.CityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public class CityDTO {

	  private String id;

	  private String slug;

	  @NotBlank
	  private String name;

	  @NotBlank
	  private String countryName;
}
