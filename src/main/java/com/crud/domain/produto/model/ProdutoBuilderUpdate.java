package com.crud.domain.produto.model;

import static java.util.Objects.requireNonNull;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

import com.crud.sk.identifiers.ProdutoId;
import com.crud.sk.vo.CodigoExterno;
import com.crud.sk.vo.Descricao;

import lombok.Getter;

@Getter
public class ProdutoBuilderUpdate {

    private final Consumer<ProdutoBuilderUpdate> action;

    private final ProdutoId id;

    private Descricao descricao;

    private CodigoExterno codigoExterno;

    ProdutoBuilderUpdate(ProdutoId id, Consumer<ProdutoBuilderUpdate> action) {
        this.id = requireNonNull(id);
        this.action = requireNonNull(action);
    }

    public void apply() {

        action.accept(this);
    }

    public ProdutoBuilderUpdate descricao(Descricao descricao) {
        this.descricao = descricao;
        return this;
    }
    public ProdutoBuilderUpdate codigoExterno (CodigoExterno codigoExterno) {
        this.codigoExterno = codigoExterno;
        return this;
    }

}
