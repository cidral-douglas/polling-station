package com.crud.query.pauta.app;

import com.crud.query.pauta.projection.ListagemPauta;
import com.crud.query.pauta.projection.Pauta;
import com.crud.query.pauta.repository.PautaQueryRepository;
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
public class PautaQueryAppService {

    private final PautaQueryRepository pautaQueryRepository;

    public Pauta recuperarPauta(UUID id) {
        return pautaQueryRepository.get(id);
    }

    public Slice<ListagemPauta> listar(Pageable pageable) {
        return pautaQueryRepository
            .findAllProjectedBy(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("rowUpdatedAt")));
    }

}
