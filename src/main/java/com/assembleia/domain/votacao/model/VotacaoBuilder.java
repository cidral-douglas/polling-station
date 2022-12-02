package com.assembleia.domain.votacao.model;

import com.assembleia.sdk.stereotype.BusinessError;
import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.identifiers.PautaId;
import com.assembleia.sk.identifiers.VotacaoId;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class VotacaoBuilder {

    protected VotacaoId id;

    protected PautaId pautaId;

    protected AssociadoId associadoId;

    protected Voto voto;

    private Predicate<PautaId> pautaIdExistsConstraint;

    private Predicate<AssociadoId> associadoIdExistsConstraint;

    private BiPredicate<PautaId, AssociadoId> pautaIdAndAssociadoIdExistsConstraint;


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

    public VotacaoBuilder pautaIdAndAssociadoIdExistsConstraint(BiPredicate<PautaId, AssociadoId> pautaIdAndAssociadoIdExistsConstraint) {
        this.pautaIdAndAssociadoIdExistsConstraint = pautaIdAndAssociadoIdExistsConstraint;
        return this;
    }

    public Votacao build() {
        id = VotacaoId.generate();

        requireNonNull(pautaId).applyExistsConstraint(pautaIdExistsConstraint);
        requireNonNull(associadoId).applyExistsConstraint(associadoIdExistsConstraint);
        applyPautaIdAndAssociadoIdExistsConstraint(pautaIdAndAssociadoIdExistsConstraint);

        return new Votacao(this);
    }

    private void applyPautaIdAndAssociadoIdExistsConstraint(BiPredicate<PautaId, AssociadoId> constraint) {
        if ((constraint.test(pautaId, associadoId))) {
            throw new AssociadoJaVotouNessaPautaException();
        }
    }

    public static class AssociadoJaVotouNessaPautaException extends BusinessError {
        private AssociadoJaVotouNessaPautaException() {
            super("Associado já votou nessa pauta!");
        }
    }

}
