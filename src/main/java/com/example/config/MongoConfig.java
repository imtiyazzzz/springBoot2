package com.example.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.mongodb.MongoClient;
import com.sixsprints.core.config.ClassToStringConverter;
import com.sixsprints.core.config.StringToClassConverter;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

  @Value(value = "${spring.data.mongodb.host}")
  private String host;

  @Value(value = "${spring.data.mongodb.database}")
  private String database;

  @Value(value = "${spring.data.mongodb.port}")
  private int port;

  @Bean
  public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
  }

  @Override
  protected String getDatabaseName() {
    return database;
  }

  @Bean
  @Override
  public MongoClient mongoClient() {
    return new MongoClient(host, port);
  }

  @Override
  public CustomConversions customConversions() {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(new ClassToStringConverter());
    converters.add(new StringToClassConverter());
    return new MongoCustomConversions(converters);
  }

}