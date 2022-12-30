package com.example;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class Handler {


     public Mono<ServerResponse> hello(ServerRequest   request)   {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just("Hello "+request.pathVariable("name")),String.class)
        //.delayElement(Duration.ofSeconds(5))
        .log()
        ;
     }



}
