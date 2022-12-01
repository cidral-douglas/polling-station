package com.assembleia.adapter.in.api;

import com.assembleia.domain.votacao.usecase.RegistrarVotacaoUseCase;
import com.assembleia.domain.votacao.usecase.RegistrarVotacaoUseCase.RegistrarVotacao;
import com.assembleia.query.votacao.app.VotacaoQueryAppService;
import com.assembleia.query.votacao.projection.ContagemVotos;
import com.assembleia.query.votacao.projection.ResultadoVotacao;
import com.assembleia.sk.identifiers.VotacaoId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/votacoes", produces = APPLICATION_JSON_VALUE)
public class VotacaoController {

    private final RegistrarVotacaoUseCase registrarVotacaoUseCase;
    private final VotacaoQueryAppService votacaoQueryAppService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@Valid  @RequestBody RegistrarVotacao cmd) {

        VotacaoId id = registrarVotacaoUseCase.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
            .path("/").path(id.asString()).build().toUri())
            .build();
    }

    @GetMapping(path = "/contagem-de-votos/{pautaId}")
    public ContagemVotos getContagemVotosByPauta(@PathVariable UUID pautaId) {
        return votacaoQueryAppService.recuperarContagemVotosByPauta(pautaId);
    }

    @GetMapping(path = "/resultado-votacao/{pautaId}")
    public ResultadoVotacao recuperarResultadoVotacaoByPauta(@PathVariable UUID pautaId) {
        return votacaoQueryAppService.recuperarResultadoVotacaoByPauta(pautaId);
    }

}
