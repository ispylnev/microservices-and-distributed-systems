package com.appsdeveloperblog.estore.OrdersService;

import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configuration;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


//@EnableDiscoveryClient
@SpringBootApplication
public class OrdersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

    @Bean
    public DeadlineManager deadlineManager(Configuration configuration,
                                           TransactionManager transactionManager) {
		return SimpleDeadlineManager.builder()
                .transactionManager(transactionManager)
                .scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
                .build();
    }

}
