package com.example.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;

@SpringBootApplication
@Log4j2
public class OrchestrationApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrchestrationApplication.class, args);
		Flux<Tuple3<String, String, Boolean>> results = Flux.zip(getServerResponse(webClient("http://localhost:8080"),"serviceA"),
		getServerResponse(webClient("http://localhost:8081"),"serviceB"),
		getServerResponse(webClient("http://localhost:8082"),true)
		);
		results.subscribe(tuple -> {
	    if(tuple.getT3())   {
			log.info("invoking Service D and E");
              Flux<String> response = Flux.merge(getServerResponse(webClient("http://localhost:8083"),"serviceD"),
		getServerResponse(webClient("http://localhost:8084"),"serviceE")
		);
		response.subscribe(resp -> log.info(resp));
		}
	}
		);
	



	}

	private static Mono<String> getServerResponse(WebClient webClient ,String name) {
		return webClient
			.get()
			.uri(uri-> uri.path("/api/service/").path(name).build())
		.retrieve()
		.bodyToMono(String.class).log();
		
	  }

	  private static Mono<Boolean> getServerResponse(WebClient webClient ,boolean status) {
		return webClient
			.get()
			.uri(uri-> uri.path("/api/service/").path(String.valueOf(status)).build())
		.retrieve()
		.bodyToMono(Boolean.class).log();
		
	  }
	  
	  
	  private static WebClient webClient(String baseUrl) {
		return WebClient.create(baseUrl);
	  }


	

}
