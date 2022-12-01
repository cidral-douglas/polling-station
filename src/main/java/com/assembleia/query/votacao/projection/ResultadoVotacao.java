package com.assembleia.query.votacao.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface ResultadoVotacao {

    @JsonIgnore
    String getStatus();

    @JsonIgnore
    LocalDateTime getDataLimite();

    @JsonIgnore
    Long getVotosNao();

    @JsonIgnore
    Long getVotosSim();

    default String getResultado() {
        if(getStatus().equals("ABERTA") || LocalDateTime.now().isBefore(getDataLimite())) {
            return "A votação ainda está aberta";
        } else {
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

}
