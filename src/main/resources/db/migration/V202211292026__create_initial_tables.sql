CREATE TABLE associado (

    id UUID NOT NULL,

    row_version INT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted BOOL NOT NULL DEFAULT false,

    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,

    CONSTRAINT associado_pkey PRIMARY KEY (id)
);

CREATE TABLE pauta (

   id UUID NOT NULL,

   row_version INT NOT NULL DEFAULT 0,
   row_created_at TIMESTAMP NOT NULL DEFAULT NOW(),
   row_updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

   descricao VARCHAR(255) NOT NULL,
   pergunta VARCHAR(255) NOT NULL,
   data_limite TIMESTAMP NOT NULL,
   status VARCHAR(7) NOT NULL,

   CONSTRAINT pauta_pkey PRIMARY KEY (id)
);

CREATE TABLE votacao (

   id UUID NOT NULL,

   row_version INT NOT NULL DEFAULT 0,
   row_created_at TIMESTAMP NOT NULL DEFAULT NOW(),
   row_updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

   associado_id UUID NOT NULL,
   pauta_id UUID NOT NULL,
   voto VARCHAR(3) NOT NULL,

   CONSTRAINT votacao_pkey PRIMARY KEY (id),
   CONSTRAINT votacao_associado_id_pkey FOREIGN KEY (associado_id) REFERENCES associado(id),
   CONSTRAINT votacao_pauta_id_pkey FOREIGN KEY (pauta_id) REFERENCES pauta(id)
);