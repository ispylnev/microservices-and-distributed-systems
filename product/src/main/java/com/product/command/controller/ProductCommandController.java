package com.product.command.controller;

import com.product.command.CreateProductCommand;
import com.product.core.dto.CreateProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("create")
    public void createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        CreateProductCommand createProductCommand = new CreateProductCommand(
                UUID.randomUUID().toString(),
                createProductDto.title(),
                createProductDto.price(),
                createProductDto.quantity());
        try {
            commandGateway.sendAndWait(createProductCommand);
        } catch (Exception ex) {
            log.info(ex.getLocalizedMessage());
        }
    }
}
