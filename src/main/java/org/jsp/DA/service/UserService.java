package org.jsp.DA.service;

import java.util.List;
import java.util.Optional;

import org.jsp.DA.dao.UserDao;
import org.jsp.DA.entity.User;
import org.jsp.DA.exceptionClasses.DuplicateEntryException;
import org.jsp.DA.exceptionClasses.NOUserFoundException;
import org.jsp.DA.util.ResponseStructure;
import org.jsp.DA.util.UserGender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private EmailService emailService;

	@Transactional
	public ResponseEntity<ResponseStructure<User>> saveUser(User user) {

		Optional<User> optional = dao.findByEmail(user.getEmail());
		if (optional.isPresent()) {
			throw new DuplicateEntryException("Duplicate Entry");
		}

		User savedUser = dao.saveUser(user);
		emailService.sendEmail(savedUser);

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value())
				.message("USER SAVED SUCCESSFULLY").body(savedUser).build();

		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(rs);

		return re;
	}

	public ResponseEntity<ResponseStructure<User>> deleteUser(int id) {

		Optional<User> optional = dao.findById(id);
		if (optional.isEmpty()) {
			throw new NOUserFoundException("No User Found, unable to delete user");
		}

		dao.deleteUser(id);

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value())
				.message("USER DELETED SUCCESSFULLY").body(null).build();

		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(rs);

		return re;
	}

	public ResponseEntity<ResponseStructure<List<User>>> findAll() {

		List<User> users = dao.findAll();

		if (users.isEmpty()) {
			throw new NOUserFoundException("No User Found In The List/Database");
		}

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("List of users")
				.body(users).build();

		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(rs);

		return re;
	}

	public ResponseEntity<ResponseStructure<List<User>>> findMatch(int id, int top) {

		Optional<User> byId = dao.findById(id);

		if (byId.isEmpty()) {
			throw new NOUserFoundException("No User Found with the given id: " + id);
		}

		User user = byId.get();

		UserGender opositeGender = user.getGender() == UserGender.MALE ? UserGender.FEMALE : UserGender.MALE;

		List<User> opositeGenderUsers = dao.findByGender(opositeGender);

		opositeGenderUsers.sort((u1, u2) -> {
			int agediff1 = Math.abs(u1.getAge() - user.getAge());
			int agediff2 = Math.abs(u2.getAge() - user.getAge());

			if (agediff1 != agediff2) {
				return Integer.compare(agediff1, agediff2);
			}

			int matchCount1 = getMatchCount(user.getInterests(), u1.getInterests());
			int matchCount2 = getMatchCount(user.getInterests(), u2.getInterests());

			return Integer.compare(matchCount2, matchCount1);
		});

		List<User> topMatches = opositeGenderUsers.subList(0, Math.min(top, opositeGenderUsers.size()));

//		🛡️ Why Math.min(top, opositeGenderUsers.size())?
//				Let’s say:
//				You ask for top 5 matches (top = 5)
//				But there are only 3 opposite-gender users in the list
//
//				If you write:
//				subList(0, 5)
//				☠️ It would throw an IndexOutOfBoundsException.
//
//				But this:
//
//				subList(0, Math.min(5, 3)) → subList(0, 3)
//				✅ Safely gives you 3 users.
		emailService.matchUsers(user, opositeGenderUsers);

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value()).message("TOP MATCHING USERS")
				.body(topMatches).build();

		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(rs);

		return re;

	}

	private int getMatchCount(List<String> userInterest, List<String> condidateInterest) {

		if (userInterest == null || condidateInterest == null)
			return 0;
		int count = 0;
		for (String i : userInterest) {
			if (condidateInterest.contains(i))
				count++;
		}
		return count;
	}

	public ResponseEntity<ResponseStructure<User>> findByNameLike(String name) {

		List<User> users = dao.findByNameLike(name);
		if (users.isEmpty()) {
			throw new NOUserFoundException("No User Found with name like " + name);
		}

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value())
				.message("List of user with name like: " + name).body(users).build();

		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(rs);
		return re;
	}

	public ResponseEntity<ResponseStructure<User>> updateUser(User user) {

		Optional<User> byId = dao.findById(user.getId());
		if (byId.isEmpty()) {
			throw new NOUserFoundException("No User Found With The Given ID '" + user.getId() + "' Not Able To Update");
		}

		User saveUser = dao.saveUser(user);

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value())
				.message("User Updated successfully").body(saveUser).build();
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(rs);

		return re;
	}

	public ResponseEntity<ResponseStructure<User>> updateEmail(String name, long phone, String email) {

		Optional<User> optional = dao.findByNameAndPhone(name, phone);
		if (optional.isEmpty()) {
			throw new NOUserFoundException("NO user found with the given name and email: " + name + "," + email);
		}

		User user = optional.get();
		user.setEmail(email);

		User saveUser = dao.saveUser(user);
		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.OK.value())
				.message("User updated successfully").body(saveUser).build();
		
		ResponseEntity re = ResponseEntity.status(HttpStatus.OK).body(rs);

		return re;
	}

}
