package com.crud.adapter.in.api;

import com.crud.domain.votacao.usecase.RegistrarVotacaoUseCase;
import com.crud.domain.votacao.usecase.RegistrarVotacaoUseCase.RegistrarVotacao;
import com.crud.sk.identifiers.VotacaoId;
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
@RequestMapping(path = "/api/v1/votacoes", produces = APPLICATION_JSON_VALUE)
public class VotacaoController {

    private final RegistrarVotacaoUseCase registrarVotacaoUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@RequestBody RegistrarVotacao cmd) {

        VotacaoId id = registrarVotacaoUseCase.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
            .path("/").path(id.asString()).build().toUri())
            .build();
    }

}
