package org.hmcampoverde.entity;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(callSuper = false, onlyExplicitlyIncluded = true)
public class UserPrincipal extends User {

	private static final long serialVersionUID = 1L;

	private org.hmcampoverde.entity.User user;

	public UserPrincipal(org.hmcampoverde.entity.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), !user.isDeleted(), true, true, true, authorities);
		this.user = user;
	}
}
