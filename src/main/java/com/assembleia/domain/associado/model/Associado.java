package com.assembleia.domain.associado.model;

import com.assembleia.domain.associado.usecase.AlterarAssociadoUseCase.AssociadoAlterado;
import com.assembleia.domain.associado.usecase.RegistrarAssociadoUseCase.AssociadoRegistrado;
import com.assembleia.domain.associado.usecase.RemoverAssociadoUseCase.AssociadoRemovido;
import com.assembleia.sdk.jpa.AbstractEntitySoftDeleted;
import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.vo.CPF;
import com.assembleia.sk.vo.Nome;
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
public class Associado extends AbstractEntitySoftDeleted<Associado, AssociadoId> {

    @EmbeddedId
    @AttributeOverride(name = AssociadoId.ATTR, column = @Column(name = "id"))
    private AssociadoId id;

    @Embedded
    private Nome nome;

    @Embedded
    private CPF cpf;


    public static AssociadoBuilder builder() {
        return new AssociadoBuilder();
    }

    Associado(AssociadoBuilder builder) {

        this.id = requireNonNull(builder.id);
        this.nome = requireNonNull(builder.nome);
        this.cpf = requireNonNull(builder.cpf);

        registerEvent(AssociadoRegistrado.from(this));
    }

    public AssociadoBuilderUpdate alterar() {

        return new AssociadoBuilderUpdate(id, form -> {
            nome = requireNonNull(form.getNome());

            registerEvent(AssociadoAlterado.from(this));
        });
    }

    public void remover() {
        markAsDeleted();
        registerEvent(AssociadoRemovido.from(this));
    }
}
