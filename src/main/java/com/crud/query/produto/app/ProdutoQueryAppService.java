package com.crud.query.produto.app;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.query.produto.projection.ListagemProduto;
import com.crud.query.produto.projection.Produto;
import com.crud.query.produto.repository.ProdutoQueryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ProdutoQueryAppService {

    private final ProdutoQueryRepository produtoQueryRepository;

    public Produto recuperarProduto(UUID id) {
        return produtoQueryRepository.get(id);
    }

    public Slice<ListagemProduto> listar(Pageable pageable) {
        return produtoQueryRepository
            .findAllProjectedBy(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("rowUpdatedAt")));
    }

}
