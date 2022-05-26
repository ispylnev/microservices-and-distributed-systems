package com.product;

import com.product.command.interceptor.CreateProductCommandInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProductApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductApp.class, args);
    }

    @Autowired
    public void registerCreateCommandInterceptor(ApplicationContext applicationContext, CommandBus commandBus) {
       commandBus.registerDispatchInterceptor(applicationContext.getBean(CreateProductCommandInterceptor.class));
    }

}
