package com.crud.domain.pauta.repository;

import com.crud.domain.pauta.model.Pauta;
import com.crud.domain.pauta.model.StatusPauta;
import com.crud.sk.identifiers.PautaId;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PautaDomainRepository extends Repository<Pauta, PautaId> {

    boolean existsById(PautaId id);

    void save(Pauta pauta);

    List<Pauta> findAllByStatus(StatusPauta status);

}
