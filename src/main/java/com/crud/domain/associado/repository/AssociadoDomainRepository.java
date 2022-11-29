package com.crud.domain.associado.repository;

import com.crud.domain.associado.model.Associado;
import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.vo.CPF;
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
