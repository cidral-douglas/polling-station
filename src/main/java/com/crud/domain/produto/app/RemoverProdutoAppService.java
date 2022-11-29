package com.crud.domain.produto.app;

import javax.transaction.Transactional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.crud.domain.produto.repository.ProdutoDomainRepository;
import com.crud.domain.produto.usecase.RemoverProdutoUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RemoverProdutoAppService implements RemoverProdutoUseCase {

    private final ProdutoDomainRepository produtoDomainRepository;

    @Override
    public void handle(RemoverProduto comando) {
        produtoDomainRepository.findById(comando.getId())
            .ifPresent(produto -> {
                produto.remover();
                produtoDomainRepository.save(produto);
            });
    }

    @Override
    @EventListener
    public void on(ProdutoRemovido event) {}
}

