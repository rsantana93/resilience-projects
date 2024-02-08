package com.sample.domain.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import com.sample.api.exceptionhandler.RemoteServiceNotAvailableException;
import com.sample.domain.model.Response;

public interface BackendAdapter {

  @Retryable(retryFor = {RemoteServiceNotAvailableException.class},
      maxAttempts = 3,
      backoff = @Backoff(delay = 1000))
  public Response getBackendResponse(String param1, String param2);

  @Recover
  public String getBackendResponseFallback(RemoteServiceNotAvailableException e, String param1, String param2);
}