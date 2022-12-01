package com.assembleia.domain.votacao.model;

import com.assembleia.domain.votacao.usecase.RegistrarVotacaoUseCase.VotacaoRegistrada;
import com.assembleia.sdk.jpa.AbstractEntity;
import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.identifiers.PautaId;
import com.assembleia.sk.identifiers.VotacaoId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("squid:S2160")

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)

@DynamicUpdate
@Entity
public class Votacao extends AbstractEntity<Votacao, VotacaoId> {

    @EmbeddedId
    @AttributeOverride(name = VotacaoId.ATTR, column = @Column(name = "id"))
    private VotacaoId id;

    @AttributeOverride(name = PautaId.ATTR, column = @Column(name = "pauta_id"))
    private PautaId pautaId;

    @AttributeOverride(name = AssociadoId.ATTR, column = @Column(name = "associado_id"))
    private AssociadoId associadoId;

    @Enumerated(EnumType.STRING)
    private Voto voto;

    public static VotacaoBuilder builder() {
        return new VotacaoBuilder();
    }

    Votacao(VotacaoBuilder builder) {

        this.id = requireNonNull(builder.id);
        this.pautaId = requireNonNull(builder.pautaId);
        this.associadoId = requireNonNull(builder.associadoId);
        this.voto = requireNonNull(builder.voto);

        registerEvent(VotacaoRegistrada.from(this));
    }
}
