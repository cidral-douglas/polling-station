package com.crud.domain.produto.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.crud.domain.produto.model.Produto;
import com.crud.sk.identifiers.ProdutoId;
import com.crud.sk.vo.CodigoExterno;

public interface ProdutoDomainRepository extends Repository<Produto, ProdutoId> {

    boolean existsById(ProdutoId id);

    boolean existsByCodigoExterno(CodigoExterno codigoExterno);

    void save(Produto produto);

    Optional<Produto> findById(ProdutoId id);

    default Produto get(ProdutoId id) {
        return findById(id)
            .orElseThrow(id::notFoundException);
    }

}
