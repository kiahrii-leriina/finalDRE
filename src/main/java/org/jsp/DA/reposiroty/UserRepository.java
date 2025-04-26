package org.jsp.DA.reposiroty;

import java.util.List;
import java.util.Optional;

import org.jsp.DA.entity.User;
import org.jsp.DA.util.UserGender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);

	List<User> findByGender(UserGender opositeGender);

	List<User> findByNameLike(String name);

	Optional<User> findByNameAndPhone(String name, long phone);


}
