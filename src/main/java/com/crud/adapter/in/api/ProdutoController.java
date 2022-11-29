package com.crud.adapter.in.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.domain.produto.usecase.AlterarProdutoUseCase;
import com.crud.domain.produto.usecase.AlterarProdutoUseCase.AlterarProduto;
import com.crud.domain.produto.usecase.RegistrarProdutoUseCase;
import com.crud.domain.produto.usecase.RegistrarProdutoUseCase.RegistrarProduto;
import com.crud.domain.produto.usecase.RemoverProdutoUseCase;
import com.crud.domain.produto.usecase.RemoverProdutoUseCase.RemoverProduto;
import com.crud.query.produto.app.ProdutoQueryAppService;
import com.crud.query.produto.projection.ListagemProduto;
import com.crud.query.produto.projection.Produto;
import com.crud.sk.identifiers.ProdutoId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

//@ApiGuideline
@RestController
@RequestMapping(path = "/api/v1/produtos", produces = APPLICATION_JSON_VALUE)
public class ProdutoController {

    private final RegistrarProdutoUseCase registrarProdutoService;
    private final AlterarProdutoUseCase alterarProdutoService;
    private final RemoverProdutoUseCase removerProdutoService;
    private final ProdutoQueryAppService produtoQueryService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrar(@RequestBody RegistrarProduto cmd) {

        ProdutoId id = registrarProdutoService.handle(cmd);

        return ResponseEntity.created(fromCurrentRequest()
            .path("/").path(id.asString()).build().toUri())
            .build();
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void alterar(@PathVariable ProdutoId id, @RequestBody AlterarProduto cmd) {
        alterarProdutoService.handle(cmd.from(id));
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable ProdutoId id){
        removerProdutoService.handle(RemoverProduto.from(id));
    }

    @GetMapping(path = "/{id}")
    public Produto buscaPorId(@PathVariable UUID id) {
        return produtoQueryService.recuperarProduto(id);
    }

    @GetMapping
    public Slice<ListagemProduto> listagem(Pageable pageable) {
        return produtoQueryService.listar(pageable);
    }
}
