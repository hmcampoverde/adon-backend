package org.hmcampoverde.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends org.hmcampoverde.entity.Entity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, length = 4, unique = true)
	private Long id;

	@Column(name = "user_username", nullable = true, length = 11)
	private String username;

	@Column(name = "user_password", columnDefinition = "TEXT", nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "tbl_user_rol", joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ROL")), inverseJoinColumns = @JoinColumn(name = "rol_id", foreignKey = @ForeignKey(name = "FK_ROL_USER")))
	private Set<Rol> roles;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Employee employee;
}
