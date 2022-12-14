package com.assembleia.query.pauta.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Pauta {

    UUID getId();

    String getDescricao();

    String getPergunta();

    LocalDateTime getDataLimite();

    String getStatus();

}
