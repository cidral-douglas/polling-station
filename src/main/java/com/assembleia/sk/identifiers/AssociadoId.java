package com.assembleia.sk.identifiers;

import com.assembleia.sdk.stereotype.BusinessError;
import com.assembleia.sdk.stereotype.SimpleValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public final class AssociadoId extends SimpleValueObject<UUID> implements Serializable {

    private static final long serialVersionUID = -7619634321690541293L;

    public static final String ATTR = "value";

    public static final AssociadoId NAO_INFORMADO = new AssociadoId();


    @JsonValue
    private final UUID value;

    private AssociadoId() {
        this.value = null;
    }

    private AssociadoId(UUID value) {
        this.value = requireNonNull(value);
    }

    public static AssociadoId generate() {
        return new AssociadoId(UUID.randomUUID());
    }

    public static AssociadoId from(UUID uuid) {
        return new AssociadoId(uuid);
    }

    @JsonCreator
    public static AssociadoId from(String uuid) {
        return "".equalsIgnoreCase(uuid) ? NAO_INFORMADO : new AssociadoId(UUID.fromString(uuid));
    }

    public AssociadoIdNaoEncontradoException notFoundException() {
        return new AssociadoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Predicate<AssociadoId> constraint) {
        if (isValid() && (!constraint.test(this))) {
            throw notFoundException();
        }
    }

    public boolean isValid() {
        return isValid(this) && nonNull(this.value);
    }

    public static boolean isValid(AssociadoId value) {
        return !AssociadoId.NAO_INFORMADO.equals(value);
    }


    public static class AssociadoIdNaoEncontradoException extends BusinessError {
        public AssociadoIdNaoEncontradoException() {
            super("Associado não encontrado!");
        }
    }
}
