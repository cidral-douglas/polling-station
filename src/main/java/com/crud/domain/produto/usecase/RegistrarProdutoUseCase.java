package com.crud.domain.produto.usecase;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.crud.domain.produto.model.Produto;
import com.crud.sk.identifiers.ProdutoId;
import com.crud.sk.vo.CodigoExterno;
import com.crud.sk.vo.Descricao;

import lombok.Builder;
import lombok.Value;

public interface RegistrarProdutoUseCase {

    ProdutoId handle(RegistrarProduto command);

    void on(ProdutoRegistrado event);

    @Value
    @Builder
    class RegistrarProduto {

        @Valid
        @NotNull(message = "{Produto.descricao.NotNull}")
        Descricao descricao;

        @Valid
        @NotNull(message = "{Produto.codigoExterno.NotNull}")
        CodigoExterno codigoExterno;

    }

    @Value
    @Builder(access = PRIVATE)
    class ProdutoRegistrado {

        ProdutoId id;

        Descricao descricao;

        CodigoExterno codigoExterno;

        public static ProdutoRegistrado from(Produto produto) {
            return ProdutoRegistrado.builder()
                .id(produto.getId())
                .descricao(produto.getDescricao())
                .codigoExterno(produto.getCodigoExterno())
                .build();
        }
    }
}
