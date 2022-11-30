package com.crud.query.votacao.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ResultadoVotacao {

    @JsonIgnore
    Long getVotosNao();

    @JsonIgnore
    Long getVotosSim();

    default String getResultado() {
        int compareValue = getVotosSim().compareTo(getVotosNao());
        if(compareValue > 0) {
            return "Há mais votos SIM";
        } else if (compareValue < 0) {
            return "Há mais votos NÃO";
        } else {
            return "Houve um EMPATE";
        }
    }

}
