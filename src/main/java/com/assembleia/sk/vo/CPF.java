package com.assembleia.sk.vo;

import com.assembleia.sdk.stereotype.BusinessError;
import com.assembleia.sdk.stereotype.SimpleValueObject;
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
import java.util.InputMismatchException;
import java.util.function.Predicate;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Getter
@NoArgsConstructor(force = true)

@Access(AccessType.FIELD)

@Embeddable
public final class CPF extends SimpleValueObject<String> implements Serializable {

    private static final long serialVersionUID = -2970706941893707612L;
    public static final CPF NAO_INFORMADO = new CPF("");
    public static final String ATTR = "value";

    @Size(max = 11, min = 11, message = "Cpf deve ter 11 dígitos")
    @NotBlank(message = "Cpf não pode ser vazio")
    @JsonValue
    @Column(name = "cpf")
    private final String value;

    private CPF(String value) {
        this.value = requireNonNull(value).trim();
        verificaSeCpfTemApenasDigitos();
        verificaSeCpfValido();
    }

    private CPF(Long value) {
        this.value = requireNonNull(value).toString();
        verificaSeCpfTemApenasDigitos();
        verificaSeCpfValido();
    }

    private boolean isValid() {
        return !isNull(value) && !value.equals("");
    }

    private void verificaSeCpfTemApenasDigitos() {
        if(isValid()) {
            if(!value.matches("[0-9]+")) {
                throw new CPFDeveConterApenasDigitosException();
            }
        }
    }

    private void verificaSeCpfValido() {
        if(isValid()) {
            if (value.equals("00000000000") ||
                value.equals("11111111111") ||
                value.equals("22222222222") || value.equals("33333333333") ||
                value.equals("44444444444") || value.equals("55555555555") ||
                value.equals("66666666666") || value.equals("77777777777") ||
                value.equals("88888888888") || value.equals("99999999999") ||
                (value.length() != 11))
                throw new CPFInvalidoException();

            char dig10, dig11;
            int sm, i, r, num, peso;

            try {
                // Calculo do 1o. Digito Verificador
                sm = 0;
                peso = 10;
                for (i=0; i<9; i++) {
                    num = (int)(value.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig10 = '0';
                else dig10 = (char)(r + 48);

                // Calculo do 2o. Digito Verificador
                sm = 0;
                peso = 11;
                for(i=0; i<10; i++) {
                    num = (int)(value.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig11 = '0';
                else dig11 = (char)(r + 48);

                // Verifica se os digitos calculados conferem com os digitos informados.
                if (!(dig10 == value.charAt(9)) || !(dig11 == value.charAt(10)))
                    throw new CPFInvalidoException();
            } catch (InputMismatchException erro) {
                throw new CPFInvalidoException();
            }
        }
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

    public static class CPFInvalidoException extends BusinessError {
        public CPFInvalidoException() {
            super("Cpf inválido!");
        }
    }

    public static class CPFDeveConterApenasDigitosException extends BusinessError {
        public CPFDeveConterApenasDigitosException() {
            super("Cpf deve conter apenas dígitos!");
        }
    }
}