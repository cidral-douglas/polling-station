package com.crud.domain.votacao.repository;

import com.crud.domain.votacao.model.Votacao;
import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.identifiers.PautaId;
import com.crud.sk.identifiers.VotacaoId;
import org.springframework.data.repository.Repository;

public interface VotacaoDomainRepository extends Repository<Votacao, VotacaoId> {

    boolean existsByPautaIdAndAssociadoId(PautaId pautaId, AssociadoId associadoId);
    void save(Votacao votacao);

}
