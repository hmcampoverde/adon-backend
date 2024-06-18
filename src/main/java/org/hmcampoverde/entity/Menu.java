package org.hmcampoverde.entity;

import java.util.Set;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Menu extends org.hmcampoverde.entity.Entity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id", nullable = false, length = 2)
	private Integer id;

	@Column(name = "menu_name", nullable = false, length = 75)
	private String name;

	@Column(name = "menu_url", nullable = true, columnDefinition = "TEXT")
	private String url;

	@Column(name = "menu_icon", nullable = true, length = 30)
	private String icon;

	@Column(name = "menu_left", nullable = false, columnDefinition = "BOOLEAN DEFAULT 'TRUE'", insertable = true)
	private boolean left;

	@ManyToOne
	@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "FK_MENU_SUBMENU"), nullable = true)
	private Menu parent;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_rol_menu", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"), foreignKey = @ForeignKey(name = "FK_MENU_ROL"), inverseForeignKey = @ForeignKey(name = "FK_ROL_MENU"))
	private Set<Rol> roles;
}
