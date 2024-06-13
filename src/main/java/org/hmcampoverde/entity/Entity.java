package org.hmcampoverde.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@MappedSuperclass
@Data
public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT 'FALSE'", insertable = true)
    private boolean deleted = Boolean.FALSE;

    @Column(name = "create_at", nullable = false, columnDefinition = "TIMESTAMP", insertable = true, updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = true, columnDefinition = "TIMESTAMP", insertable = false, updatable = true)
    private LocalDateTime updateAt;

    @PrePersist
    public void createEntity() {
        createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updateEntity() {
        updateAt = LocalDateTime.now();
    }
}
