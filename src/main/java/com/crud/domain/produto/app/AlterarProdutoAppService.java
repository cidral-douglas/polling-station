package com.crud.domain.produto.app;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.domain.produto.model.Produto;
import com.crud.domain.produto.repository.ProdutoDomainRepository;
import com.crud.domain.produto.usecase.AlterarProdutoUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
@Transactional
public class AlterarProdutoAppService implements AlterarProdutoUseCase {

    private final ProdutoDomainRepository repository;

    @Override
    public void handle(AlterarProduto command) {

        Produto produto = repository.get(command.getId());

        produto.alterar()
            .descricao(command.getDescricao())
            .codigoExterno(command.getCodigoExterno())
            .apply();

        repository.save(produto);
    }

    @Override
    @EventListener
    public void on(ProdutoAlterado event) {}

}