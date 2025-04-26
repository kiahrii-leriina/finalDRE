package org.jsp.DA.dto;

import java.util.List;

import org.jsp.DA.util.UserGender;
import org.jsp.DA.util.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchingUser {

	private int id;
	private String name;
	private int age;
	private String email;
	private long phone;
	private String password;
	private UserGender gender;
	private UserStatus status;
	private List<String> interests;
	private int agediff;
	private int matchingInterestCount;
	
}
