package com.crud.query.produto.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.crud.sdk.jpa.AbstractAnemicEntity;
import com.crud.sdk.stereotype.BusinessError;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Immutable
@Entity
@Table(name = "produto")
public class ProdutoQuery extends AbstractAnemicEntity<UUID> {

    @Id
    private UUID id;

    private String descricao;

    @Column(name = "codigo_externo")
    private String codigoExterno;

    @Column(name = "row_updated_at")
    private ZonedDateTime rowUpdatedAt;

    private boolean deleted;

    @JsonIgnore
    public ZonedDateTime getRowUpdatedAt() {
        return rowUpdatedAt;
    }

//    @ApiNotFound(value = "ProdutoIdNaoEncontradoException")
    public static class ProdutoIdNaoEncontradoException extends BusinessError {

        private static final long serialVersionUID = 991370116545156345L;

    }
}