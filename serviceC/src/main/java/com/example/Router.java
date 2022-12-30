package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class Router {

    @Bean
    RouterFunction<ServerResponse>  routes(Handler handler)  {
		return RouterFunctions.route(RequestPredicates.GET("/api/service/{status}"),handler::hello);
	}

    
}
