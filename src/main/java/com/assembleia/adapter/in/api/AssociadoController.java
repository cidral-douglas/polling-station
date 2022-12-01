package com.assembleia.adapter.in.api;

import com.assembleia.domain.associado.usecase.AlterarAssociadoUseCase;
import com.assembleia.domain.associado.usecase.AlterarAssociadoUseCase.AlterarAssociado;
import com.assembleia.domain.associado.usecase.RegistrarAssociadoUseCase;
import com.assembleia.domain.associado.usecase.RegistrarAssociadoUseCase.RegistrarAssociado;
import com.assembleia.domain.associado.usecase.RemoverAssociadoUseCase;
import com.assembleia.domain.associado.usecase.RemoverAssociadoUseCase.RemoverAssociado;
import com.assembleia.query.associado.app.AssociadoQueryAppService;
import com.assembleia.query.associado.projection.Associado;
import com.assembleia.query.associado.projection.ListagemAssociado;
import com.assembleia.sk.identifiers.AssociadoId;
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
@RequestMapping(path = "/api/v1/associados", produces = APPLICATION_JSON_VALUE)
public class AssociadoController {

    private final RegistrarAssociadoUseCase registrarAssociadoUseCase;
    private final AlterarAssociadoUseCase alterarAssociadoUseCase;
    private final RemoverAssociadoUseCase removerAssociadoUseCase;
    private final AssociadoQueryAppService associadoQueryAppService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@Valid @RequestBody RegistrarAssociado cmd) {

        AssociadoId id = registrarAssociadoUseCase.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
            .path("/").path(id.asString()).build().toUri())
            .build();
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void alterar(@PathVariable AssociadoId id, @Valid @RequestBody AlterarAssociado cmd) {
        alterarAssociadoUseCase.handle(cmd.from(id));
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable AssociadoId id){
        removerAssociadoUseCase.handle(RemoverAssociado.from(id));
    }

    @GetMapping(path = "/{id}")
    public Associado buscaPorId(@PathVariable UUID id) {
        return associadoQueryAppService.recuperarAssociado(id);
    }

    @GetMapping
    public List<ListagemAssociado> listagem(Pageable pageable) {
        return associadoQueryAppService.listar(pageable).getContent();
    }

}
