package org.hmcampoverde.repository;

import java.util.List;

import org.hmcampoverde.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	@Query("from Menu m left join fetch m.roles r left join fetch m.parent where m.deleted = false and r.name IN (:roles)")
	List<Menu> findByRolesNameIn(@Param("roles") List<String> roles, Sort sort);
}
