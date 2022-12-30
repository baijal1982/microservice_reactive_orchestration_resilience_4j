package com.ps.service;

import com.ps.ports.api.OrchestrationServicePort;
import com.ps.ports.spi.OrchestrationAdapterPort;

import reactor.core.publisher.Flux;


public class OrchestrationServiceImpl implements OrchestrationServicePort {

//    @Autowired
    private OrchestrationAdapterPort  adapterPort;

    public OrchestrationServiceImpl(OrchestrationAdapterPort adapterPort){
        this.adapterPort = adapterPort;
    }

    @Override
    public Flux<String> orchestrate(String status) {
        // TODO Auto-generated method stub
        return adapterPort.orchestrate(status);
    }

   
}
