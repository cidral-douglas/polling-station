package com.crud.sdk.jpa;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@Where(clause = "deleted=false")
public abstract class AbstractEntitySoftDeleted<A extends AbstractAggregateRoot<A>, K>
    extends AbstractAggregateRoot<A> implements Persistable<K> {

    @Transient
    private transient int hash = 0;

    @JsonIgnore
    @Version
    @Column(name = "row_version")
    private int entityVersion;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "row_created_at")
    private LocalDateTime entityCreatedAt;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "row_updated_at")
    private LocalDateTime entityUpdatedAt;

    @JsonIgnore
    protected boolean deleted;

    @Override
    public boolean isNew() {
        return entityVersion == 0;
    }

    protected void markAsDeleted() {
        deleted = true;
    }

    @Override
    public int hashCode() {
        if (hash == 0)
            hash = Objects.hash(getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || !getClass().equals(obj.getClass()))
            return false;

        @SuppressWarnings("unchecked")
        AbstractEntitySoftDeleted<A, K> other = (AbstractEntitySoftDeleted<A, K>) obj;

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + getId() +
            ", isNew=" + isNew() +
            ", entityVersion=" + entityVersion +
            ", entityCreatedAt=" + entityCreatedAt +
            ", entityUpdatedAt=" + entityUpdatedAt +
            "]";
    }

}
