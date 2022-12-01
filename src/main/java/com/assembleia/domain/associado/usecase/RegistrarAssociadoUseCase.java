package com.assembleia.domain.associado.usecase;

import com.assembleia.domain.associado.model.Associado;
import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.vo.CPF;
import com.assembleia.sk.vo.Nome;
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

        public RegistrarAssociado from() {
            return RegistrarAssociado.builder()
                .nome(nome)
                .cpf(cpf)
                .build();
        }

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
