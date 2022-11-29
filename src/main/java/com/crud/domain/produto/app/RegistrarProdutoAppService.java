package com.crud.domain.produto.app;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.domain.produto.model.Produto;
import com.crud.domain.produto.repository.ProdutoDomainRepository;
import com.crud.domain.produto.usecase.RegistrarProdutoUseCase;
import com.crud.sk.identifiers.ProdutoId;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
@Transactional
public class RegistrarProdutoAppService implements RegistrarProdutoUseCase {

    private final ProdutoDomainRepository produtoDomainRepository;

    @Override
    public ProdutoId handle(RegistrarProduto command) {

        Produto produto = Produto.builder()
            .codigoExterno(command.getCodigoExterno())
            .codigoExternoDuplicatedConstraint(produtoDomainRepository::existsByCodigoExterno)
            .descricao(command.getDescricao())
            .build();

        produtoDomainRepository.save(produto);

        return produto.getId();
    }

    @Override
    @EventListener
    public void on(ProdutoRegistrado event) {}

}
