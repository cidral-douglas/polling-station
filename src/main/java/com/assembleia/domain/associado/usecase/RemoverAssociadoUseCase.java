package com.assembleia.domain.associado.usecase;

import com.assembleia.domain.associado.model.Associado;
import com.assembleia.sk.identifiers.AssociadoId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

public interface RemoverAssociadoUseCase {

    void handle(RemoverAssociado command);

    void on(AssociadoRemovido event);

    @Value
    @Builder
    class RemoverAssociado {

        AssociadoId id;

        public static RemoverAssociado from(AssociadoId id){
            return RemoverAssociado.builder()
                .id(id)
                .build();
        }
    }

    @Value
    @Builder(access = AccessLevel.PRIVATE)
    class AssociadoRemovido {

        AssociadoId id;

        public static AssociadoRemovido from(Associado associado){
            return AssociadoRemovido.builder()
                .id(associado.getId())
                .build();
        }
    }
}
