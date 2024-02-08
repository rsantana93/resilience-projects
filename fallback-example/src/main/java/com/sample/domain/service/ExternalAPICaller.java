package com.sample.domain.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sample.domain.model.Response;

@Component
public class ExternalAPICaller {
    private final RestTemplate restTemplate;

    public ExternalAPICaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Response callApi() {
        return restTemplate.getForObject("/api/external", Response.class);
    }

    public Response callApiWithDelay() {
    	Response result = restTemplate.getForObject("/api/external", Response.class);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignore) {
        }
        return result;
    }
}