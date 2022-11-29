package com.crud.sk.vo;

import com.crud.sdk.stereotype.SimpleValueObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static java.util.Objects.requireNonNull;
import static javax.persistence.AccessType.FIELD;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Embeddable
@Access(FIELD)
public final class Pergunta extends SimpleValueObject<String> implements Serializable {

    private static final long serialVersionUID = -8547562992121602274L;

    public static final Pergunta NAO_INFORMADA = new Pergunta("");
    public static final String ATTR = "value";

    @Size(max = 255, message = "{Pergunta.Size}")
    @NotBlank(message = "{Pergunta.NotBlank}")
    @JsonValue
    @Column(name = "pergunta")
    private final String value;

    private Pergunta(String value) {
        this.value = requireNonNull(value).trim();
    }

    @JsonCreator
    public static Pergunta from(String value) {
        return new Pergunta(value);
    }

}
