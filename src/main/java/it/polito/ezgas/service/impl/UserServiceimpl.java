package it.polito.ezgas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.UserService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
@Transactional
public class UserServiceimpl implements UserService {
	
	//instance of UserRepository
	@Autowired
	private UserRepository repo;

	@Override
	public UserDto getUserById(Integer userId) throws InvalidUserException {
		//query the database and throw exception if user not found
		User u = repo.findOne(userId);
	    if (u == null) {
			throw new InvalidUserException("No user found with id: " + userId);
	    }
	    //convert to UserDto and return it
		UserConverter conv = new UserConverter();
		return conv.convert(u);
	}

	@Override
	//saves a user to the databse
	public UserDto saveUser(UserDto userDto) {
		UserConverter conv = new UserConverter();
		
		//converts to User and saves it
		User u = conv.convertFromDto(userDto);
		repo.save(u);
		return conv.convert(u);
	}

	@Override
	public List<UserDto> getAllUsers() {
		UserConverter conv = new UserConverter();
		List<UserDto> dtoList;
		
		//query the database to find all users 
		List<User> userList = repo.findAll();
		
		//converts from List<User> to List<UserDto>
		dtoList = userList.stream().map(u -> conv.convert(u)).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public Boolean deleteUser(Integer userId) throws InvalidUserException {
		//gets all users and if it finds the one with the provided id it deletes it
		for (UserDto uDto : this.getAllUsers()) {
			if (uDto.getUserId() == userId) {
				repo.delete(userId);
				return true;
			}
		}
		
		//if no user with that id is found it throws an exception
		throw new InvalidUserException("Error: no user with id " + userId + " found\n");
	}

	@Override
	//handles the login process
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {
		//gets all users
		for (UserDto uDto : this.getAllUsers()) {
			//looks for the user with the same email as the input
			if (uDto.getEmail().equalsIgnoreCase(credentials.getUser())) {
				//verifies that the password for that user is right
				if (uDto.getPassword().equals(credentials.getPw())) {
					//create loginDto and return it
					LoginDto lDto = new LoginDto();
					lDto.setUserId(uDto.getUserId());
					lDto.setUserName(uDto.getUserName());
					lDto.setEmail(uDto.getEmail());
					lDto.setReputation(uDto.getReputation());
					lDto.setAdmin(uDto.getAdmin());
					return lDto;
				}
			}
		}
		//if either there is no user with that email or the password is wrong, throws an exception
		throw new InvalidLoginDataException("Error: wrong login data with email: " + credentials.getUser() + " and pwd: " + credentials.getPw());
	}

	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
