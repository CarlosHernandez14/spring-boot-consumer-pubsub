package com.charly.springbootconsumerpubsub.controllers;

import com.charly.springbootconsumerpubsub.models.Body;
import com.charly.springbootconsumerpubsub.models.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/v1")
public class PubSubController {

    // Bean ObjectMapper para convertir de JSON a POJO
    private final ObjectMapper objectMapper;

    // Inyeccion de dependencias por constructor del ObjectMapper
    @Autowired
    public PubSubController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/consume")
    public Mono<ResponseEntity<String>> receiveMessage(@RequestBody Body body) {

        // Obtenemos el PubSub message del request body
        Body.Message message = body.getMessage();
        // Validamos si existe el mensaje
        if (message == null) {
            // Si no existe, retornamos un bad request
            String msg = "Bad request: invalid PubSub message format";
            // Imprimimos en los logs el mensaje de error
            log.error(msg);
            return Mono.just(new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST));
        }

        // En caso de que si exista el mensaje
        String data = message.getData();
        UserMessage userMessage;

        try {
            // Convertimos el mensaje de JSON a POJO
            userMessage = objectMapper.readValue(new String(Base64.getDecoder().decode(data)), UserMessage.class);

        } catch (JsonProcessingException ex ) {
            // Imprimos el error en los logs
            log.error(ex.getMessage());
            return Mono.just(new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }

        // En caso de que se haya convertido correctamente, imprimimos el mensaje en los logs
        log.info("UserMessage info: {}", userMessage);
        // Retornamos un response de que se recibio el mensaje
        return Mono.just(new ResponseEntity<>(userMessage.getBody(), HttpStatus.OK));
    }

}
