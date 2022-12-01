package com.assembleia.domain.associado.repository;

import com.assembleia.domain.associado.model.Associado;
import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.vo.CPF;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AssociadoDomainRepository extends Repository<Associado, AssociadoId> {

    boolean existsById(AssociadoId id);

    boolean existsByCpf(CPF cpf);

    void save(Associado associado);

    Optional<Associado> findById(AssociadoId id);

    default Associado get(AssociadoId id) {
        return findById(id)
            .orElseThrow(id::notFoundException);
    }

}
