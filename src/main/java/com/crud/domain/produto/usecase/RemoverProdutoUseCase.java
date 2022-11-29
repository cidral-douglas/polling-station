package com.crud.domain.produto.usecase;

import com.crud.domain.produto.model.Produto;
import com.crud.sk.identifiers.ProdutoId;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

public interface RemoverProdutoUseCase {

    void handle(RemoverProduto command);

    void on(ProdutoRemovido event);

    @Value
    @Builder
    class RemoverProduto {

        ProdutoId id;

        public static RemoverProduto from(ProdutoId id){
            return RemoverProduto.builder()
                .id(id)
                .build();
        }
    }

    @Value
    @Builder(access = AccessLevel.PRIVATE)
    class ProdutoRemovido {

        ProdutoId id;

        public static ProdutoRemovido from(Produto produto){
            return ProdutoRemovido.builder()
                .id(produto.getId())
                .build();
        }
    }
}
