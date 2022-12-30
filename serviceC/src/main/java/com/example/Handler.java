package com.example;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class Handler {


     public Mono<ServerResponse> hello(ServerRequest   request)   {
        if(request.pathVariable("status")!=null && !Boolean.valueOf(request.pathVariable("status"))){
                return Mono.error( new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"mocking the error state"));
        }
        else {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(Boolean.valueOf(request.pathVariable("status"))),Boolean.class)
        //.delayElement(Duration.ofSeconds(5))
        .log();
        }
     }



}
