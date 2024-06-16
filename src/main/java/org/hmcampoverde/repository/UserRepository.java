package org.hmcampoverde.repository;

import java.util.Optional;

import org.hmcampoverde.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select user from User user left join fetch user.roles left join fetch employee where user.username = :username")
	Optional<User> findByUsername(@Param("username") String username);

	@Transactional
	@Modifying
	@Query("update User u set u.password = :password where u.username = :username")
	void updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}
