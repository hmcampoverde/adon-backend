package org.hmcampoverde.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @CreatedDate
    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP", insertable = true, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = true, columnDefinition = "TIMESTAMP", insertable = false, updatable = true)
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void createdEntity() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void updatedEntity() {
        lastModifiedDate = LocalDateTime.now();
    }
}
