package com.crud.domain.votacao.usecase;

import com.crud.domain.votacao.model.Votacao;
import com.crud.domain.votacao.model.Voto;
import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.identifiers.PautaId;
import com.crud.sk.identifiers.VotacaoId;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

public interface RegistrarVotacaoUseCase {

    VotacaoId handle(RegistrarVotacao command);

    void on(VotacaoRegistrada event);

    @Value
    @Builder
    class RegistrarVotacao {

        @Valid
        @NotNull(message = "Id da Pauta deve ser preenchida")
        PautaId pautaId;

        @Valid
        @NotNull(message = "Id do Associado deve ser preenchida")
        AssociadoId associadoId;

        @Valid
        @NotNull(message = "Voto deve ser preenchido")
        Voto voto;

    }

    @Value
    @Builder(access = PRIVATE)
    class VotacaoRegistrada {

        VotacaoId id;

        PautaId pautaId;

        AssociadoId associadoId;

        Voto voto;


        public static VotacaoRegistrada from(Votacao votacao) {
            return VotacaoRegistrada.builder()
                .id(votacao.getId())
                .pautaId(votacao.getPautaId())
                .associadoId(votacao.getAssociadoId())
                .voto(votacao.getVoto())
                .build();
        }
    }
}
