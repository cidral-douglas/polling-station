package com.crud.sk.identifiers;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.UUID;
import java.util.function.Function;

import javax.persistence.Embeddable;

import com.crud.sdk.stereotype.BusinessError;
import com.crud.sdk.stereotype.SimpleValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
@Embeddable
public final class ProdutoId extends SimpleValueObject<UUID> implements Serializable {

    private static final long serialVersionUID = -7619634321690541293L;

    public static final String ATTR = "value";

    public static final ProdutoId NAO_INFORMADO = new ProdutoId();


    @JsonValue
    private final UUID value;

    private ProdutoId() {
        this.value = null;
    }

    private ProdutoId(UUID value) {
        this.value = requireNonNull(value);
    }

    public static ProdutoId generate() {
        return new ProdutoId(UUID.randomUUID());
    }

    public static ProdutoId from(UUID uuid) {
        return new ProdutoId(uuid);
    }

    @JsonCreator
    public static ProdutoId from(String uuid) {
        return "".equalsIgnoreCase(uuid) ? NAO_INFORMADO : new ProdutoId(UUID.fromString(uuid));
    }

    public ProdutoIdNaoEncontradoException notFoundException() {
        return new ProdutoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<ProdutoId, Boolean> constraint) {
        if (isValid() && (!constraint.apply(this))) {
            throw notFoundException();
        }
    }

    public boolean isValid() {
        return isValid(this) && nonNull(this.value);
    }

    public static boolean isValid(ProdutoId value) {
        return !ProdutoId.NAO_INFORMADO.equals(value);
    }

//    @ApiNotFound(value = "ProdutoIdNaoEncontradoException")
    public static class ProdutoIdNaoEncontradoException extends BusinessError {

        private static final long serialVersionUID = 991370116545156345L;

    }
}
