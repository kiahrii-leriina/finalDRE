package org.jsp.DA.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.DA.entity.User;
import org.jsp.DA.reposiroty.UserRepository;
import org.jsp.DA.util.UserGender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	@Autowired
	private UserRepository repo;

	public Optional<User> findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public User saveUser(User user) {
		return repo.save(user);
	}

	public Optional<User> findById(int id) {
		return repo.findById(id);
	}

	public void deleteUser(int id) {
		repo.deleteById(id);
	}

	public List<User> findAll() {
		return repo.findAll();
	}

	public List<User> findByGender(UserGender opositeGender) {
		return repo.findByGender(opositeGender);
	}

	public List<User> findByNameLike(String name) {
		return repo.findByNameLike(name);
	}

	public Optional<User> findByNameAndPhone(String name, long phone) {
		return repo.findByNameAndPhone(name,phone);
	}

	


}
