package com.crud.domain.associado.model;

import com.crud.domain.associado.usecase.AlterarAssociadoUseCase.AssociadoAlterado;
import com.crud.domain.associado.usecase.RegistrarAssociadoUseCase.AssociadoRegistrado;
import com.crud.domain.associado.usecase.RemoverAssociadoUseCase.AssociadoRemovido;
import com.crud.sdk.jpa.AbstractEntitySoftDeleted;
import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.vo.CPF;
import com.crud.sk.vo.Nome;
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
