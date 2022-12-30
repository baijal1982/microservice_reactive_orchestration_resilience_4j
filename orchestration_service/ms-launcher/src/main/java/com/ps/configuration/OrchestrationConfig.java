package com.ps.configuration;
import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ps.adapter.OrchestrationAdapter;
import com.ps.ports.api.OrchestrationServicePort;
import com.ps.ports.spi.OrchestrationAdapterPort;
import com.ps.service.OrchestrationServiceImpl;



@Configuration
public class OrchestrationConfig {

    @Bean
    public OrchestrationServicePort orchestrationServicePort(ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory){
        return new OrchestrationServiceImpl(orchestrationAdapterPort(reactiveCircuitBreakerFactory));
    }

    @Bean
    public OrchestrationAdapterPort orchestrationAdapterPort(ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory){
        return new OrchestrationAdapter(reactiveCircuitBreakerFactory);
    }



}
