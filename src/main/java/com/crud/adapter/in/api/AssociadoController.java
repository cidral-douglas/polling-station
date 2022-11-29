package com.crud.adapter.in.api;

import com.crud.domain.associado.usecase.AlterarAssociadoUseCase;
import com.crud.domain.associado.usecase.AlterarAssociadoUseCase.AlterarAssociado;
import com.crud.domain.associado.usecase.RegistrarAssociadoUseCase;
import com.crud.domain.associado.usecase.RegistrarAssociadoUseCase.RegistrarAssociado;
import com.crud.domain.associado.usecase.RemoverAssociadoUseCase;
import com.crud.domain.associado.usecase.RemoverAssociadoUseCase.RemoverAssociado;
import com.crud.sk.identifiers.AssociadoId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequiredArgsConstructor

//@ApiGuideline
@RestController
@RequestMapping(path = "/api/v1/associados", produces = APPLICATION_JSON_VALUE)
public class AssociadoController {

    private final RegistrarAssociadoUseCase registrarAssociadoUseCase;
    private final AlterarAssociadoUseCase alterarAssociadoUseCase;
    private final RemoverAssociadoUseCase removerAssociadoUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@RequestBody RegistrarAssociado cmd) {

        AssociadoId id = registrarAssociadoUseCase.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
            .path("/").path(id.asString()).build().toUri())
            .build();
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void alterar(@PathVariable AssociadoId id, @RequestBody AlterarAssociado cmd) {
        alterarAssociadoUseCase.handle(cmd.from(id));
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable AssociadoId id){
        removerAssociadoUseCase.handle(RemoverAssociado.from(id));
    }

}
