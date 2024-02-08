package com.sample.infrastructure.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.sample.api.exceptionhandler.RemoteServiceNotAvailableException;
import com.sample.domain.model.Response;
import com.sample.domain.service.BackendAdapter;

@Service
public class BackendAdapterImpl implements BackendAdapter {

  @Override
  public Response getBackendResponse(String param1, String param2) {
	Response resp = new Response("Retorno da API");
    int random = new Random().nextInt(4);
    if (random % 2 == 0) {
      throw new RemoteServiceNotAvailableException("Retorno not available");
    }

    return resp;
  }

  @Override
  public String getBackendResponseFallback(RemoteServiceNotAvailableException e, String param1, String param2) {
    return "Retorno fallback metodo!!!";
  }
}