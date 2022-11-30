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
import java.util.function.Predicate;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public final class PautaId extends SimpleValueObject<UUID> implements Serializable {

    private static final long serialVersionUID = -7619634321690541293L;

    public static final String ATTR = "value";

    public static final PautaId NAO_INFORMADO = new PautaId();


    @JsonValue
    private final UUID value;

    private PautaId() {
        this.value = null;
    }

    private PautaId(UUID value) {
        this.value = requireNonNull(value);
    }

    public static PautaId generate() {
        return new PautaId(UUID.randomUUID());
    }

    public static PautaId from(UUID uuid) {
        return new PautaId(uuid);
    }

    @JsonCreator
    public static PautaId from(String uuid) {
        return "".equalsIgnoreCase(uuid) ? NAO_INFORMADO : new PautaId(UUID.fromString(uuid));
    }

    public PautaIdNaoEncontradoException notFoundException() {
        return new PautaIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Predicate<PautaId> constraint) {
        if (isValid() && (!constraint.test(this))) {
            throw notFoundException();
        }
    }

    public boolean isValid() {
        return isValid(this) && nonNull(this.value);
    }

    public static boolean isValid(PautaId value) {
        return !PautaId.NAO_INFORMADO.equals(value);
    }

    public static class PautaIdNaoEncontradoException extends BusinessError {
        public PautaIdNaoEncontradoException() {
            super("Pauta não encontrada!");
        }
    }
}
