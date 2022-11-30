package com.crud.domain.pauta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class FecharPautaPublisher {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void enviarMensagem(String message) {

    ListenableFuture<SendResult<String, String>> future =
        kafkaTemplate.send("pautas", message);

    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        System.out.println("Mensagem enviada=[" + message + "]");
      }
      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Falha no envia da mensagem=["
            + message + "] por causa de: " + ex.getMessage());
      }
    });
  }
}
