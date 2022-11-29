package com.crud.sk.identifiers;

import com.crud.sdk.stereotype.BusinessError;
import com.crud.sdk.stereotype.SimpleValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public final class VotacaoId extends SimpleValueObject<UUID> implements Serializable {

    private static final long serialVersionUID = -7619634321690541293L;

    public static final String ATTR = "value";

    public static final VotacaoId NAO_INFORMADO = new VotacaoId();


    @JsonValue
    private final UUID value;

    private VotacaoId() {
        this.value = null;
    }

    private VotacaoId(UUID value) {
        this.value = requireNonNull(value);
    }

    public static VotacaoId generate() {
        return new VotacaoId(UUID.randomUUID());
    }

    public static VotacaoId from(UUID uuid) {
        return new VotacaoId(uuid);
    }

    @JsonCreator
    public static VotacaoId from(String uuid) {
        return "".equalsIgnoreCase(uuid) ? NAO_INFORMADO : new VotacaoId(UUID.fromString(uuid));
    }

    public VotacaoIdNaoEncontradoException notFoundException() {
        return new VotacaoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<VotacaoId, Boolean> constraint) {
        if (isValid() && (!constraint.apply(this))) {
            throw notFoundException();
        }
    }

    public boolean isValid() {
        return isValid(this) && nonNull(this.value);
    }

    public static boolean isValid(VotacaoId value) {
        return !VotacaoId.NAO_INFORMADO.equals(value);
    }

//    @ApiNotFound(value = "ProdutoIdNaoEncontradoException")
    public static class VotacaoIdNaoEncontradoException extends BusinessError {

        private static final long serialVersionUID = 991370116545156345L;

    }
}
