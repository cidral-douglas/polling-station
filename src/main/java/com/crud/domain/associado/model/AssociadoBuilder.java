package com.crud.domain.associado.model;

import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.vo.CPF;
import com.crud.sk.vo.Nome;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class AssociadoBuilder {

    private Predicate<CPF> cpfConstraint;

    protected AssociadoId id;

    protected Nome nome;

    protected CPF cpf;



    public AssociadoBuilder nome(Nome nome) {
        this.nome = nome;
        return this;
    }

    public AssociadoBuilder cpf(CPF cpf) {
        this.cpf = cpf;
        return this;
    }


    public AssociadoBuilder cpfDuplicatedConstraint(Predicate<CPF> cpfConstraint) {
        this.cpfConstraint = cpfConstraint;
        return this;
    }


    public Associado build() {
        id = AssociadoId.generate();

        requireNonNull(cpf).applyDuplicateConstraint(cpfConstraint);

        return new Associado(this);
    }

}
