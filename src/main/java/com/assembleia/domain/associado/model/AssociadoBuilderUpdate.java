package com.assembleia.domain.associado.model;

import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.vo.Nome;
import lombok.Getter;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Getter
public class AssociadoBuilderUpdate {

    private final Consumer<AssociadoBuilderUpdate> action;

    private final AssociadoId id;

    private Nome nome;

    AssociadoBuilderUpdate(AssociadoId id, Consumer<AssociadoBuilderUpdate> action) {
        this.id = requireNonNull(id);
        this.action = requireNonNull(action);
    }

    public void apply() {

        action.accept(this);
    }

    public AssociadoBuilderUpdate nome(Nome nome) {
        this.nome = nome;
        return this;
    }

}
