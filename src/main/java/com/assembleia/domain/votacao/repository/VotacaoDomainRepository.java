package com.assembleia.domain.votacao.repository;

import com.assembleia.domain.votacao.model.Votacao;
import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.identifiers.PautaId;
import com.assembleia.sk.identifiers.VotacaoId;
import org.springframework.data.repository.Repository;

public interface VotacaoDomainRepository extends Repository<Votacao, VotacaoId> {

    boolean existsByPautaIdAndAssociadoId(PautaId pautaId, AssociadoId associadoId);
    void save(Votacao votacao);

}
