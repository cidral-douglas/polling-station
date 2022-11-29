package com.crud.sk.vo;

import static java.util.Objects.requireNonNull;
import static javax.persistence.AccessType.FIELD;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.crud.sdk.stereotype.SimpleValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Embeddable
@Access(FIELD)
public final class Descricao extends SimpleValueObject<String> implements Serializable {

    private static final long serialVersionUID = -8547562992121602274L;

    public static final Descricao NAO_INFORMADA = new Descricao("");
    public static final String ATTR = "value";

    @Size(max = 255, message = "{Descricao.Size}")
    @NotBlank(message = "{Descricao.NotBlank}")
    @JsonValue
    @Column(name = "descricao")
    private final String value;

    private Descricao(String value) {
        this.value = requireNonNull(value).trim();
    }

    @JsonCreator
    public static Descricao from(String value) {
        return new Descricao(value);
    }

}
