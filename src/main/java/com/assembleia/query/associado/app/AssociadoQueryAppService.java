package com.assembleia.query.associado.app;

import com.assembleia.query.associado.projection.Associado;
import com.assembleia.query.associado.projection.ListagemAssociado;
import com.assembleia.query.associado.repository.AssociadoQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class AssociadoQueryAppService {

    private final AssociadoQueryRepository associadoQueryRepository;

    public Associado recuperarAssociado(UUID id) {
        return associadoQueryRepository.get(id);
    }

    public Slice<ListagemAssociado> listar(Pageable pageable) {
        return associadoQueryRepository
            .findAllProjectedBy(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("rowUpdatedAt")));
    }

}
