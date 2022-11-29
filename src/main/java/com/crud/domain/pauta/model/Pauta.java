package com.crud.domain.pauta.model;

import com.crud.domain.pauta.usecase.RegistrarPautaUseCase.PautaRegistrada;
import com.crud.sdk.jpa.AbstractEntity;
import com.crud.sk.identifiers.PautaId;
import com.crud.sk.vo.Descricao;
import com.crud.sk.vo.Pergunta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("squid:S2160")

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)

@DynamicUpdate
@Entity
public class Pauta extends AbstractEntity<Pauta, PautaId> {

    @EmbeddedId
    @AttributeOverride(name = PautaId.ATTR, column = @Column(name = "id"))
    private PautaId id;

    @Embedded
    private Descricao descricao;

    @Embedded
    private Pergunta pergunta;

    @Column(name = "data_limite")
    private LocalDateTime dataLimite;


    public static PautaBuilder builder() {
        return new PautaBuilder();
    }

    Pauta(PautaBuilder builder) {

        this.id = requireNonNull(builder.id);
        this.descricao = requireNonNull(builder.descricao);
        this.pergunta = requireNonNull(builder.pergunta);
        this.dataLimite = requireNonNullElse(builder.dataLimite, LocalDateTime.now().plusMinutes(1));

        registerEvent(PautaRegistrada.from(this));
    }
}
