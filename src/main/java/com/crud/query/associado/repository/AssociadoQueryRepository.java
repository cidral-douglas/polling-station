package com.crud.query.associado.repository;

import com.crud.query.associado.model.AssociadoQuery;
import com.crud.query.associado.projection.Associado;
import com.crud.query.associado.projection.ListagemAssociado;
import com.crud.sk.identifiers.AssociadoId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface AssociadoQueryRepository extends Repository<AssociadoQuery, AssociadoId>{

    Slice<ListagemAssociado> findAllProjectedBy(Pageable pageable);

    Optional<Associado> findProjectedById(UUID id);

    default Associado get(UUID id) {
        return findProjectedById(id)
            .orElseThrow(AssociadoId.AssociadoIdNaoEncontradoException::new);
    }

}
