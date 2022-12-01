package com.assembleia.query.associado.repository;

import com.assembleia.query.associado.model.AssociadoQuery;
import com.assembleia.query.associado.projection.Associado;
import com.assembleia.query.associado.projection.ListagemAssociado;
import com.assembleia.sk.identifiers.AssociadoId;
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
