package org.hmcampoverde.entity;

import org.hmcampoverde.enums.Profile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_employee")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Employee extends org.hmcampoverde.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false, length = 4, unique = true)
    private Long id;

    @Column(name = "employee_firstname", nullable = false, length = 75)
    private String firstname;

    @Column(name = "employee_lastname", nullable = false, length = 75)
    private String lastname;

    @Column(name = "employee_identification", nullable = false, length = 11)
    private String identification;

    @Column(name = "employee_mobil", nullable = true, length = 18)
    private String mobil;

    @Column(name = "employee_email_personal", nullable = true, length = 75)
    private String emailPersonal;

    @Column(name = "employee_email_institutional", nullable = false, length = 75)
    private String emailInstitutional;

    @Column(name = "employee_address", nullable = true, columnDefinition = "TEXT")
    private String address;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", foreignKey = @ForeignKey(name = "FK_EMPLOYEE_INSTITUTION"), nullable = true)
    private Institution institution;

    @Column(name = "employee_manager", nullable = false, columnDefinition = "BOOLEAN DEFAULT 'FALSE'", insertable = true)
    private boolean manager;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_profile", nullable = false, length = 30)
    private Profile profile;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_EMPLOYEE_USER"), nullable = false)
    private User user;

    @Transient
    private String fullname;

    public String getFullname() {
        return this.firstname.concat(" ").concat(this.lastname);
    }

}
