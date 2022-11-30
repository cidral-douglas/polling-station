package com.crud.query.votacao.model;

import com.crud.sdk.jpa.AbstractAnemicEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Immutable
@Entity
@Table(name = "votacao")
public class VotacaoQuery extends AbstractAnemicEntity<UUID> {

    @Id
    private UUID id;

    @Column(name = "pauta_id")
    private UUID pautaId;

    @Column(name = "associado_id")
    private UUID associadoId;

    private String voto;

    @Column(name = "row_updated_at")
    private ZonedDateTime rowUpdatedAt;

    @JsonIgnore
    public ZonedDateTime getRowUpdatedAt() {
        return rowUpdatedAt;
    }
}