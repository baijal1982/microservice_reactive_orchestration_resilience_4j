package com.ps.ports.spi;

import reactor.core.publisher.Flux;


public interface OrchestrationAdapterPort {
    Flux<String> orchestrate(String status);
  
}
