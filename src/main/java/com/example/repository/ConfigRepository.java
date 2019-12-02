package com.example.repository;

import org.springframework.stereotype.Repository;

import com.sixsprints.core.generic.GenericRepository;
import com.example.domain.Config;

@Repository
public interface ConfigRepository extends GenericRepository<Config> {
}
