package com.ps.ports.api;


import reactor.core.publisher.Flux;


public interface OrchestrationServicePort {

    Flux<String> orchestrate(String status);
}
