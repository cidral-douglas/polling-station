package com.crud.sk.vo;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.function.Predicate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.crud.sdk.stereotype.BusinessError;
import com.crud.sdk.stereotype.SimpleValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)

@Access(AccessType.FIELD)

@Embeddable
public final class CodigoExterno extends SimpleValueObject<String> implements Serializable {

    private static final long serialVersionUID = -2970706941893707612L;
    public static final CodigoExterno NAO_INFORMADO = new CodigoExterno("");
    public static final String ATTR = "value";

    @Size(max = 16, message = "{CodigoExterno.Size}")
    @NotBlank(message = "{CodigoExterno.NotBlank}")
    @JsonValue
    @Column(name = "codigo_externo")
    private final String value;

    private CodigoExterno(String value) {
        this.value = requireNonNull(value).trim();
    }

    private CodigoExterno(Long value) {
        this.value = requireNonNull(value).toString();
    }

    @JsonCreator
    public static CodigoExterno from(String value) {
        return new CodigoExterno(value);
    }

    public static CodigoExterno from(Long value) {
        return new CodigoExterno(value);
    }

    public CodigoExternoDuplicadoException duplicatedException() {
        return new CodigoExternoDuplicadoException();
    }

    public void applyDuplicateConstraint(Predicate<CodigoExterno> constraint) {
        if (constraint.test(this))
            throw duplicatedException();
    }

//    @ApiBadRequest(value = "CodigoExternoDuplicadoException")
    public static class CodigoExternoDuplicadoException extends BusinessError {

        private static final long serialVersionUID = 991370116545156345L;

    }
}