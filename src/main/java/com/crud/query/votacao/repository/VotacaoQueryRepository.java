package com.crud.query.votacao.repository;

import com.crud.query.votacao.model.VotacaoQuery;
import com.crud.query.votacao.projection.ContagemVotos;
import com.crud.sk.identifiers.VotacaoId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface VotacaoQueryRepository extends Repository<VotacaoQuery, VotacaoId>{

    @Query(value = "SELECT " +
                    "COUNT(CASE WHEN voto = 'NAO' THEN 1 ELSE NULL END) as votosNao, " +
                    "COUNT(CASE WHEN voto = 'SIM' THEN 1 ELSE NULL END) as votosSim " +
                    "FROM votacao " +
                    "WHERE pauta_id = ?1", nativeQuery = true)
    ContagemVotos getCotagemVotosByPautaId(UUID ID);

}
