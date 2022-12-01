package com.assembleia.domain.pauta.usecase;

import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.sk.identifiers.PautaId;
import com.assembleia.sk.vo.Descricao;
import com.assembleia.sk.vo.Pergunta;
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
