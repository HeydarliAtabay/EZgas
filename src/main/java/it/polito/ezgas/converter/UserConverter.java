package it.polito.ezgas.converter;

import org.springframework.core.convert.converter.Converter;

import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

public class UserConverter implements Converter<User, UserDto>{

	@Override
	//converts from User to UserDto
	public UserDto convert(User source) {
		//creates the UserDto and returns it
		UserDto dto = new UserDto(
				source.getUserId(),
				source.getUserName(),
				source.getPassword(),
				source.getEmail(),
				source.getReputation());
		
		dto.setAdmin(source.getAdmin());
		
		return dto;
	}
	
	//converts from UserDto to User
	public User convertFromDto(UserDto source) {
		//creates the User and returns it
		User u = new User(
				source.getUserName(),
				source.getPassword(),
				source.getEmail(),
				source.getReputation());
		
		u.setUserId(source.getUserId());
		u.setAdmin(source.getAdmin());
		
		return u;
	}
}
