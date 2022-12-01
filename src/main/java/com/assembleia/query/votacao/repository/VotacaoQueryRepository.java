package com.assembleia.query.votacao.repository;

import com.assembleia.query.votacao.model.VotacaoQuery;
import com.assembleia.query.votacao.projection.ContagemVotos;
import com.assembleia.query.votacao.projection.ResultadoVotacao;
import com.assembleia.sk.identifiers.VotacaoId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface VotacaoQueryRepository extends Repository<VotacaoQuery, VotacaoId>{

    @Query(value = "SELECT " +
                    "COUNT(CASE WHEN voto = 'NAO' THEN 1 ELSE NULL END) as votosNao, " +
                    "COUNT(CASE WHEN voto = 'SIM' THEN 1 ELSE NULL END) as votosSim " +
                    "FROM votacao " +
                    "WHERE pauta_id = ?1", nativeQuery = true)
    ContagemVotos getCotagemVotosByPautaId(UUID id);

    @Query(value = "SELECT " +
                    "COUNT(CASE WHEN votacao.voto = 'NAO' THEN 1 ELSE NULL END) as votosNao, " +
                    "COUNT(CASE WHEN votacao.voto = 'SIM' THEN 1 ELSE NULL END) as votosSim, " +
                    "p.status as status," +
                    "p.data_limite as dataLimite " +
                    "FROM votacao " +
                    "INNER JOIN pauta p ON p.id = votacao.pauta_id " +
                    "WHERE votacao.pauta_id = ?1 " +
                    "GROUP BY p.status, p.data_limite", nativeQuery = true)
    ResultadoVotacao recuperarResultadoVotacaoByPautaId(UUID id);

}
