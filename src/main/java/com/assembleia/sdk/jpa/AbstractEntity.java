package com.assembleia.sdk.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity<A extends AbstractAggregateRoot<A>, K>
    extends AbstractAggregateRoot<A> implements Persistable<K> {

    @Transient
    private transient int hash = 0;

    @JsonIgnore
    @Version
    @Column(name = "row_version")
    protected int entityVersion;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "row_created_at")
    protected LocalDateTime entityCreatedAt;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "row_updated_at")
    protected LocalDateTime entityUpdatedAt;

    @Override
    public boolean isNew() {
        return entityVersion == 0;
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
        AbstractEntity<A, K> other = (AbstractEntity<A, K>) obj;

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
