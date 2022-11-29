package com.crud.domain.pauta.repository;

import com.crud.domain.pauta.model.Pauta;
import com.crud.sk.identifiers.PautaId;
import org.springframework.data.repository.Repository;

public interface PautaDomainRepository extends Repository<Pauta, PautaId> {

    void save(Pauta pauta);

}
