package com.ps;

import com.ps.ports.api.OrchestrationServicePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import reactor.core.publisher.Flux;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OrchestrationController {

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/service/orchestrate/{status}", beanClass = OrchestrationServicePort.class, beanMethod = "orchestrate", operation = @Operation(operationId = "orchestrate", summary = "Orchestration success or error ", tags = {
                    "Account" }, parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "status", description = "This will enable api to run in success and failure scenarios.") }, responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            })) })

    public RouterFunction<ServerResponse> route(OrchestrationServicePort orchestrationServicePort) {
        return RouterFunctions
                .route(RequestPredicates.GET("/service/orchestrate/{status}"), req -> {

                    return ServerResponse.ok().body(
                    
                    orchestrationServicePort.orchestrate(req.pathVariable("status")
                    
                    
                    ),
                            Object.class);
                });
    }

}
