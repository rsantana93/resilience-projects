package com.sample.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.domain.model.Response;
import com.sample.domain.service.BackendAdapter;

@RestController
public class ResourceController {

  @Autowired
  BackendAdapter backendAdapter;

  @GetMapping("/retryable-operation")
  public ResponseEntity<Response> validateSpringRetryCapability(@RequestParam String param1, @RequestParam String param2) {
    Response apiResponse = backendAdapter.getBackendResponse(param1, param2);
    return ResponseEntity.ok().body(apiResponse);
  }
}