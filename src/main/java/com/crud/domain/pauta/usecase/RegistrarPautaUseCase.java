package com.crud.domain.pauta.usecase;

import com.crud.domain.pauta.model.Pauta;
import com.crud.sk.identifiers.PautaId;
import com.crud.sk.vo.Descricao;
import com.crud.sk.vo.Pergunta;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

public interface RegistrarPautaUseCase {

    PautaId handle(RegistrarPauta command);

    void on(PautaRegistrada event);

    @Value
    @Builder
    class RegistrarPauta {

        @Valid
        @NotNull(message = "Descrição deve ser preenchida")
        Descricao descricao;

        @Valid
        @NotNull(message = "Pergunta deve ser preenchida")
        Pergunta pergunta;

        @Valid
        @NotNull(message = "Data limite deve ser preenchida")
        LocalDateTime dataLimite;

    }

    @Value
    @Builder(access = PRIVATE)
    class PautaRegistrada {

        PautaId id;

        Descricao descricao;

        Pergunta pergunta;

        LocalDateTime dataLimite;


        public static PautaRegistrada from(Pauta pauta) {
            return PautaRegistrada.builder()
                .id(pauta.getId())
                .descricao(pauta.getDescricao())
                .pergunta(pauta.getPergunta())
                .dataLimite(pauta.getDataLimite())
                .build();
        }
    }
}
