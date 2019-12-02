package com.example.util.transformer;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.sixsprints.core.dto.PageDto;

@Mapper(componentModel = "spring")
public abstract class PageMapper<T> {

  public PageDto<T> toPageDto(Page<T> page) {
    if (page == null) {
      return null;
    }
    return PageDto.<T>builder()
      .currentPageSize(page.getNumberOfElements())
      .currentPage(page.getNumber())
      .totalElements(page.getTotalElements())
      .totalPages(page.getTotalPages())
      .content(page.getContent())
      .build();
  }

}
