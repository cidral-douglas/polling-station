package com.crud.domain.produto.model;

import static java.util.Objects.requireNonNull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.crud.sk.identifiers.ProdutoId;
import com.crud.sk.vo.CodigoExterno;
import com.crud.sk.vo.Descricao;

public class ProdutoBuilder {

    private Predicate<CodigoExterno> codigoExternoConstraint;

    protected ProdutoId id;

    protected Descricao descricao;

    protected CodigoExterno codigoExterno;


    public ProdutoBuilder descricao(Descricao descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoBuilder codigoExterno(CodigoExterno codigoExterno) {
        this.codigoExterno = codigoExterno;
        return this;
    }

    public ProdutoBuilder codigoExternoDuplicatedConstraint(Predicate<CodigoExterno> constraint) {
        this.codigoExternoConstraint = constraint;
        return this;
    }


    public Produto build() {
        id = ProdutoId.generate();

        requireNonNull(codigoExterno).applyDuplicateConstraint(codigoExternoConstraint);

        return new Produto(this);
    }

}
