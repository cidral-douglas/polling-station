package com.crud.domain.produto.usecase;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.crud.domain.produto.model.Produto;
import com.crud.sk.identifiers.ProdutoId;
import com.crud.sk.vo.CodigoExterno;
import com.crud.sk.vo.Descricao;

import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Value;

public interface AlterarProdutoUseCase {

    void handle(AlterarProduto command);

    void on(ProdutoAlterado event);

    @Value
    @Builder
    class AlterarProduto {

        @ApiModelProperty(hidden=true)
        ProdutoId id;

        @Valid
        @NotNull(message = "{Negociacao.Produto.descricao.NotNull}")
        Descricao descricao;

        @Valid
        @NotNull(message = "{Negociacao.Produto.codigoExterno.NotNull}")
        CodigoExterno codigoExterno;


        public AlterarProduto from(ProdutoId id) {
            return AlterarProduto.builder()
                .id(requireNonNull(id))
                .descricao(descricao)
                .codigoExterno(codigoExterno)
                .build();
        }
    }

    @Value
    @Builder(access = PRIVATE)
    class ProdutoAlterado {

        ProdutoId id;

        Descricao descricao;

        CodigoExterno codigoExterno;


        public static ProdutoAlterado from(Produto produto) {
            return ProdutoAlterado.builder()
                .id(produto.getId())
                .descricao(produto.getDescricao())
                .codigoExterno(produto.getCodigoExterno())
                .build();
        }
    }
}
