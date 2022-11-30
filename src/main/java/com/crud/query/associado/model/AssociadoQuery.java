package com.crud.query.associado.model;

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
@Table(name = "associado")
public class AssociadoQuery extends AbstractAnemicEntity<UUID> {

    @Id
    private UUID id;

    private String nome;

    private String cpf;

    @Column(name = "row_updated_at")
    private ZonedDateTime rowUpdatedAt;

    private boolean deleted;

    @JsonIgnore
    public ZonedDateTime getRowUpdatedAt() {
        return rowUpdatedAt;
    }
}