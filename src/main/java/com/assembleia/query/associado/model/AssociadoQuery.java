package com.assembleia.query.associado.model;

import com.assembleia.sdk.jpa.AbstractAnemicEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;

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
@Where(clause = "deleted=false")
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