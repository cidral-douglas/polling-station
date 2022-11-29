package com.crud.domain.votacao.model;

import com.crud.sk.identifiers.AssociadoId;
import com.crud.sk.identifiers.PautaId;
import com.crud.sk.identifiers.VotacaoId;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class VotacaoBuilder {

    protected VotacaoId id;

    protected PautaId pautaId;

    protected AssociadoId associadoId;

    protected Voto voto;

    private Predicate<PautaId> pautaIdExistsConstraint;

    private Predicate<AssociadoId> associadoIdExistsConstraint;


    public VotacaoBuilder pautaId(PautaId pautaId) {
        this.pautaId = pautaId;
        return this;
    }

    public VotacaoBuilder associadoId(AssociadoId associadoId) {
        this.associadoId = associadoId;
        return this;
    }

    public VotacaoBuilder voto(Voto voto) {
        this.voto = voto;
        return this;
    }

    public VotacaoBuilder pautaIdExistsConstraint(Predicate<PautaId> pautaIdExistsConstraint) {
        this.pautaIdExistsConstraint = pautaIdExistsConstraint;
        return this;
    }

    public VotacaoBuilder associadoIdExistsConstraint(Predicate<AssociadoId> associadoIdExistsConstraint) {
        this.associadoIdExistsConstraint = associadoIdExistsConstraint;
        return this;
    }

    public Votacao build() {
        id = VotacaoId.generate();

        requireNonNull(pautaId).applyExistsConstraint(pautaIdExistsConstraint);
        requireNonNull(associadoId).applyExistsConstraint(associadoIdExistsConstraint);

        return new Votacao(this);
    }

}
