package org.hmcampoverde.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_institution")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Institution extends org.hmcampoverde.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id", nullable = false, length = 4, unique = true)
    private Long id;

    @Column(name = "institution_amie", nullable = false, length = 8)
    private String amie;

    @Column(name = "institution_name", nullable = false, length = 100)
    private String name;

    @Column(name = "institution_phone", nullable = true, length = 16)
    private String phone;

    @Column(name = "institution_email", nullable = false, length = 75)
    private String email;

    @Column(name = "institution_address", nullable = false, columnDefinition = "TEXT")
    private String address;

}
