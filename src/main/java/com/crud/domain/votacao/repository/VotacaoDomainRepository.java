package com.crud.domain.votacao.repository;

import com.crud.domain.votacao.model.Votacao;
import com.crud.sk.identifiers.VotacaoId;
import org.springframework.data.repository.Repository;

public interface VotacaoDomainRepository extends Repository<Votacao, VotacaoId> {

    void save(Votacao votacao);

}
