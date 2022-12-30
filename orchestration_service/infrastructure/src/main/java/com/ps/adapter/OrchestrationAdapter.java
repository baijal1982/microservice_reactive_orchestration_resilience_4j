package com.ps.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import com.ps.ports.spi.OrchestrationAdapterPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrchestrationAdapter implements OrchestrationAdapterPort {

    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @Override
    public Flux<String> orchestrate(String status) {
        // TODO Auto-generated method stub
        Flux<Tuple3<String, String, Boolean>> results = Flux.zip(
                getServerResponse("http://localhost:8080", "serviceA"),
                getServerResponse("http://localhost:8081", "serviceB"),
                getServerResponse("http://localhost:8082", status!=null?Boolean.valueOf(status):false));

        return results.flatMap(tuple -> {
            if (tuple.getT3().booleanValue()) {
                log.info("invoking Service D and E");
                return Flux.merge(getServerResponse("http://localhost:8083", "serviceD"),
                        getServerResponse("http://localhost:8084", "serviceE"));
            }
            return Flux.empty();
        })
                .onErrorResume(e -> Flux.error(e));
    }

    private Mono<String> getServerResponse(String baseurl, String name) {
        return WebClient.create(baseurl)
                .get()
                .uri(uri -> uri.path("/api/service/").path(name).build())
                .retrieve()
                .bodyToMono(String.class).log();
    }

    private Mono<Boolean> getServerResponse(String baseurl, boolean name) {
        return WebClient.create(baseurl)
                .get()
                .uri(uri -> uri.path("/api/service/").path(String.valueOf(name)).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .log()
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("orchestration-service");
                    return rcb.run(it, throwable -> Mono.error(throwable));
                });
    }

}
