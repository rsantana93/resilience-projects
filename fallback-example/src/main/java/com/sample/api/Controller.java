package com.sample.api;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.domain.model.Response;
import com.sample.domain.service.ExternalAPICaller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/")
public class Controller {
	
    private final ExternalAPICaller externalAPICaller;

    public Controller(ExternalAPICaller externalApi) {
        this.externalAPICaller = externalApi;
    }

    @GetMapping("/circuit-breaker")
    @CircuitBreaker(name = "CircuitBreakerService")
    public ResponseEntity<Response> circuitBreakerApi() {
    	Response response = externalAPICaller.callApi();
    	return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    
    @GetMapping("/fallback")
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod="fallback")
    public ResponseEntity<Response> fallback(){
    	Response response = externalAPICaller.callApi();
    	return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    
    public ResponseEntity<Response> fallback(Exception ex) {
    	return new ResponseEntity<Response>(new Response("serviço indisponivel"), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/retry")
    @Retry(name = "retryApi", fallbackMethod = "fallbackAfterRetry")
    public ResponseEntity<Response> retryApi() {
    	Response response = externalAPICaller.callApi();
    	return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/time-limiter")
    @TimeLimiter(name = "timeLimiterApi")
    public CompletableFuture<Response> timeLimiterApi() {
        return CompletableFuture.supplyAsync(externalAPICaller::callApiWithDelay);
    }

    @GetMapping("/bulkhead")
    @Bulkhead(name = "bulkheadApi")
    public ResponseEntity<Response> bulkheadApi() {
    	Response response = externalAPICaller.callApi();
    	return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/rate-limiter")
    @RateLimiter(name = "rateLimiterApi")
    public ResponseEntity<Response> rateLimitApi() {
    	Response response = externalAPICaller.callApi();
    	return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> fallbackAfterRetry(Exception ex) {
    	String response = "todas as tentativas foram esgotadas";
    	return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}