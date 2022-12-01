package com.crud.domain.associado.usecase;

import com.crud.domain.associado.model.Associado;
import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.vo.CPF;
import com.crud.sk.vo.Nome;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

public interface RegistrarAssociadoUseCase {

    AssociadoId handle(RegistrarAssociado command);

    void on(AssociadoRegistrado event);

    @Value
    @Builder
    class RegistrarAssociado {

        @Valid
        @NotNull(message = "Nome deve ser preenchido")
        Nome nome;

        @Valid
        @NotNull(message = "Cpf deve ser preenchido")
        CPF cpf;

    }

    @Value
    @Builder(access = PRIVATE)
    class AssociadoRegistrado {

        AssociadoId id;

        Nome nome;

        CPF cpf;

        public static AssociadoRegistrado from(Associado associado) {
            return AssociadoRegistrado.builder()
                .id(associado.getId())
                .nome(associado.getNome())
                .cpf(associado.getCpf())
                .build();
        }
    }
}
