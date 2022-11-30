package com.crud.query.pauta.model;

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
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Immutable
@Entity
@Table(name = "pauta")
public class PautaQuery extends AbstractAnemicEntity<UUID> {

    @Id
    private UUID id;

    private String descricao;

    private String pergunta;

    @Column(name = "data_limite")
    private LocalDateTime dataLimite;

    @Column(name = "row_updated_at")
    private ZonedDateTime rowUpdatedAt;

    @JsonIgnore
    public ZonedDateTime getRowUpdatedAt() {
        return rowUpdatedAt;
    }
}