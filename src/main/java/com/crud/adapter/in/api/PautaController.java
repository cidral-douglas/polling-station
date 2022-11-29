package com.crud.adapter.in.api;

import com.crud.domain.pauta.usecase.RegistrarPautaUseCase;
import com.crud.domain.pauta.usecase.RegistrarPautaUseCase.RegistrarPauta;
import com.crud.sk.identifiers.PautaId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequiredArgsConstructor

//@ApiGuideline
@RestController
@RequestMapping(path = "/api/v1/pautas", produces = APPLICATION_JSON_VALUE)
public class PautaController {

    private final RegistrarPautaUseCase registrarPautaUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@RequestBody RegistrarPauta cmd) {

        PautaId id = registrarPautaUseCase.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
            .path("/").path(id.asString()).build().toUri())
            .build();
    }

}
