package com.assembleia.query.pauta.repository;

import com.assembleia.query.pauta.model.PautaQuery;
import com.assembleia.query.pauta.projection.ListagemPauta;
import com.assembleia.query.pauta.projection.Pauta;
import com.assembleia.sk.identifiers.PautaId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface PautaQueryRepository extends Repository<PautaQuery, PautaId>{

    Slice<ListagemPauta> findAllProjectedBy(Pageable pageable);

    Optional<Pauta> findProjectedById(UUID id);

    default Pauta get(UUID id) {
        return findProjectedById(id)
            .orElseThrow(PautaId.PautaIdNaoEncontradoException::new);
    }

}
