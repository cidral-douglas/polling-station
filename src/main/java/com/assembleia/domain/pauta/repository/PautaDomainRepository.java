package com.assembleia.domain.pauta.repository;

import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.domain.pauta.model.StatusPauta;
import com.assembleia.sk.identifiers.PautaId;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PautaDomainRepository extends Repository<Pauta, PautaId> {

    boolean existsById(PautaId id);

    void save(Pauta pauta);

    Optional<Pauta> findById(PautaId id);

    default Pauta get(PautaId id) {
        return findById(id)
            .orElseThrow(id::notFoundException);
    }

    List<Pauta> findAllByStatus(StatusPauta status);

}
