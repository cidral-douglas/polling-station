package com.crud.domain.associado.usecase;

import com.crud.domain.associado.model.Associado;
import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.vo.Nome;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

public interface AlterarAssociadoUseCase {

    void handle(AlterarAssociado command);

    void on(AssociadoAlterado event);

    @Value
    @Builder
    class AlterarAssociado {

        @ApiModelProperty(hidden=true)
        AssociadoId id;

        @Valid
        @NotNull(message = "Nome deve ser preenchido")
        Nome nome;


        public AlterarAssociado from(AssociadoId id) {
            return AlterarAssociado.builder()
                .id(requireNonNull(id))
                .nome(nome)
                .build();
        }
    }

    @Value
    @Builder(access = PRIVATE)
    class AssociadoAlterado {

        AssociadoId id;

        Nome nome;

        public static AssociadoAlterado from(Associado associado) {
            return AssociadoAlterado.builder()
                .id(associado.getId())
                .nome(associado.getNome())
                .build();
        }
    }
}
