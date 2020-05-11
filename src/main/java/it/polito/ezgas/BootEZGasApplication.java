package it.polito.ezgas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.service.impl.UserServiceimpl;

@SpringBootApplication
public class BootEZGasApplication {
	
	//this is used to get the instance of the UserServiceimpl class
	@Autowired
	private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(BootEZGasApplication.class, args);
	}
	
	@PostConstruct
	public void setupDbWithData() throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:h2:./data/memo", "sa", "password");
		conn.close();
			
		//list all the users stored in the database and, if there is no an admin user create it
		UserConverter conv = new UserConverter();
		UserServiceimpl s = appContext.getBean(UserServiceimpl.class);
		Boolean thereIsAdmin = false;
		
		//search whether there is an admin already or not
		for (UserDto uDto : s.getAllUsers()) {
			if (conv.convertFromDto(uDto).getAdmin()) {
				thereIsAdmin = true;
			}
		}
		
		//if there is no admin yet in the database, add one
		if (thereIsAdmin == false) {
			User user = new User("admin", "admin", "admin@ezgas.com", 5);
			user.setAdmin(true);
			UserDto uDto = conv.convert(user);
			s.saveUser(uDto);
		}
	}
}
