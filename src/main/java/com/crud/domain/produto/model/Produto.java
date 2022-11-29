package com.crud.domain.produto.model;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;

import com.crud.domain.produto.usecase.AlterarProdutoUseCase.ProdutoAlterado;
import com.crud.domain.produto.usecase.RegistrarProdutoUseCase.ProdutoRegistrado;
import com.crud.domain.produto.usecase.RemoverProdutoUseCase.ProdutoRemovido;
import com.crud.sdk.jpa.AbstractEntitySoftDeleted;
import com.crud.sk.identifiers.ProdutoId;
import com.crud.sk.vo.CodigoExterno;
import com.crud.sk.vo.Descricao;

import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("squid:S2160")

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)

@DynamicUpdate
@Entity
public class Produto extends AbstractEntitySoftDeleted<Produto, ProdutoId> {

    @EmbeddedId
    @AttributeOverride(name = ProdutoId.ATTR, column = @Column(name = "id"))
    private ProdutoId id;

    @Embedded
    private Descricao descricao;

    @Embedded
    private CodigoExterno codigoExterno;

    public static ProdutoBuilder builder() {
        return new ProdutoBuilder();
    }

    Produto(ProdutoBuilder builder) {

        this.id = requireNonNull(builder.id);
        this.descricao = requireNonNull(builder.descricao);
        this.codigoExterno = requireNonNull(builder.codigoExterno);

        registerEvent(ProdutoRegistrado.from(this));
    }

    public ProdutoBuilderUpdate alterar() {

        return new ProdutoBuilderUpdate(id, form -> {
            descricao = requireNonNull(form.getDescricao());
            codigoExterno = requireNonNull(form.getCodigoExterno());

            registerEvent(ProdutoAlterado.from(this));
        });
    }

    public void remover() {
        markAsDeleted();
        registerEvent(ProdutoRemovido.from(this));
    }
}
