package com.crud.adapter.in.api;

import com.crud.domain.pauta.usecase.RegistrarPautaUseCase;
import com.crud.domain.pauta.usecase.RegistrarPautaUseCase.RegistrarPauta;
import com.crud.query.pauta.app.PautaQueryAppService;
import com.crud.query.pauta.projection.ListagemPauta;
import com.crud.query.pauta.projection.Pauta;
import com.crud.sk.identifiers.PautaId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/pautas", produces = APPLICATION_JSON_VALUE)
public class PautaController {

    private final RegistrarPautaUseCase registrarPautaUseCase;
    private final PautaQueryAppService pautaQueryAppService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@Valid @RequestBody RegistrarPauta cmd) {

        PautaId id = registrarPautaUseCase.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
            .path("/").path(id.asString()).build().toUri())
            .build();
    }

    @GetMapping(path = "/{id}")
    public Pauta buscaPorId(@PathVariable UUID id) {
        return pautaQueryAppService.recuperarPauta(id);
    }

    @GetMapping
    public List<ListagemPauta> listagem(Pageable pageable) {
        return pautaQueryAppService.listar(pageable).getContent();
    }

}
