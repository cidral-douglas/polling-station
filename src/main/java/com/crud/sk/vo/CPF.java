package com.crud.sk.vo;

import com.crud.sdk.stereotype.BusinessError;
import com.crud.sdk.stereotype.SimpleValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

@Getter
@NoArgsConstructor(force = true)

@Access(AccessType.FIELD)

@Embeddable
public final class CPF extends SimpleValueObject<String> implements Serializable {

    private static final long serialVersionUID = -2970706941893707612L;
    public static final CPF NAO_INFORMADO = new CPF("");
    public static final String ATTR = "value";

    @Size(max = 11, message = "{CPF.Size}")
    @NotBlank(message = "{CPF.NotBlank}")
    @JsonValue
    @Column(name = "cpf")
    private final String value;

    private CPF(String value) {
        this.value = requireNonNull(value).trim();
    }

    private CPF(Long value) {
        this.value = requireNonNull(value).toString();
    }

    @JsonCreator
    public static CPF from(String value) {
        return new CPF(value);
    }

    public static CPF from(Long value) {
        return new CPF(value);
    }

    public CPFDuplicadoException duplicatedException() {
        return new CPFDuplicadoException();
    }

    public void applyDuplicateConstraint(Predicate<CPF> constraint) {
        if (constraint.test(this))
            throw duplicatedException();
    }

    public static class CPFDuplicadoException extends BusinessError {
        public CPFDuplicadoException() {
            super("Já existe um associado com esse cpf!");
        }
    }
}