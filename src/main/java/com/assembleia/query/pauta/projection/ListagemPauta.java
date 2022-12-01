package com.assembleia.query.pauta.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ListagemPauta {

    UUID getId();

    String getDescricao();

    String getPergunta();

    LocalDateTime getDataLimite();

}
