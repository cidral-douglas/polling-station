package com.assembleia.domain.pauta.model;

import com.assembleia.sk.identifiers.PautaId;
import com.assembleia.sk.vo.Descricao;
import com.assembleia.sk.vo.Pergunta;

import java.time.LocalDateTime;

public class PautaBuilder {

    protected PautaId id;

    protected Descricao descricao;

    protected Pergunta pergunta;

    protected LocalDateTime dataLimite;


    public PautaBuilder descricao(Descricao descricao) {
        this.descricao = descricao;
        return this;
    }

    public PautaBuilder pergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
        return this;
    }

    public PautaBuilder dataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
        return this;
    }

    public Pauta build() {
        id = PautaId.generate();

        return new Pauta(this);
    }

}
