package com.mymark.mymarkshoppingcart;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.mymark.mymarkshoppingcart.service.CustomerWebService;
import com.mymark.mymarkshoppingcart.service.ProductWebService;
import com.mymark.mymarkshoppingcart.service.ShoppingCartService;
import com.mymark.mymarkshoppingcart.service.impl.CustomerWebServiceImpl;
import com.mymark.mymarkshoppingcart.service.impl.ProductWebServiceImpl;
import com.mymark.mymarkshoppingcart.service.impl.ShoppingCartServiceImpl;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource({"classpath:application.properties"})
public class MyMarkShoppingCartApplication {

	@Resource
	private Environment environment;

	
	@Bean
	public ShoppingCartService cartService() {
		return new ShoppingCartServiceImpl();
	}

	@Bean
	public ProductWebService productWebService() {
		return new ProductWebServiceImpl();
	}
	
	@Bean
	public CustomerWebService customerWebService() {
		return new CustomerWebServiceImpl();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MyMarkShoppingCartApplication.class, args);
	}
}
