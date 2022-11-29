package com.crud.query.produto.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.Repository;

import com.crud.query.produto.model.ProdutoQuery;
import com.crud.query.produto.projection.ListagemProduto;
import com.crud.query.produto.projection.Produto;

public interface ProdutoQueryRepository extends Repository<ProdutoQuery, UUID>{

    Slice<ListagemProduto> findAllProjectedBy(Pageable pageable);

    Optional<Produto> findProjectedById(UUID id);

    default Produto get(UUID id) {
        return findProjectedById(id)
            .orElseThrow(ProdutoQuery.ProdutoIdNaoEncontradoException::new);
    }

}
