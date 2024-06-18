package org.hmcampoverde.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ticket extends org.hmcampoverde.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false, length = 4, unique = true)
    private Long id;

    @Column(name = "ticket_number", nullable = false, length = 30)
    private String number;

    @CreatedBy
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_created_by", foreignKey = @ForeignKey(name = "FK_TICKET_EMPLOYEE_CREATE_BY"), nullable = false)
    private Employee createdBy;

    @Column(name = "ticket_description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_status", foreignKey = @ForeignKey(name = "FK_TICKET_STATUS"), nullable = false)
    private Status status;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_priority", foreignKey = @ForeignKey(name = "FK_TICKET_PRIORITY"), nullable = false)
    private Priority priority;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_categority", foreignKey = @ForeignKey(name = "FK_TICKET_CATEGORITY"), nullable = false)
    private Category category;

    @CreatedBy
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_assigned_to", foreignKey = @ForeignKey(name = "FK_TICKET_EMPLOYEE_ASSIGNED_TO"), nullable = true)
    private Employee assignedTo;

    @Column(name = "ticket_solution", nullable = true, columnDefinition = "TEXT")
    private String solution;

    @Column(name = "ticket_solved_date", nullable = true, columnDefinition = "TIMESTAMP", insertable = false, updatable = true)
    private LocalDateTime solvedDate;

    @Column(name = "ticket_archived_date", nullable = true, columnDefinition = "TIMESTAMP", insertable = false, updatable = true)
    private LocalDateTime archivedDate;

}
