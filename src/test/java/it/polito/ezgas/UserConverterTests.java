package it.polito.ezgas;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConverterTests {
	@Test
	public void adminUserToDtoTest() {
		User u = new User(
				"userNameTest",
				"pwdTest",
				"testEmail",
				4);
		u.setAdmin(true);
		
		UserConverter conv = new UserConverter();
		UserDto uDto = conv.convert(u);
		
		assertTrue(uDto.getUserName().equals("userNameTest"));
		assertTrue(uDto.getPassword().equals("pwdTest"));
		assertTrue(uDto.getEmail().equals("testEmail"));
		assertTrue(uDto.getReputation() == 4);
		assertTrue(uDto.getAdmin());
	}
	
	@Test
	public void normalUserToDtoTest() {
		User u = new User(
				"userNameTest",
				"pwdTest",
				"testEmail",
				4);
		u.setAdmin(false);
		
		UserConverter conv = new UserConverter();
		UserDto uDto = conv.convert(u);
		
		assertTrue(uDto.getUserName().equals("userNameTest"));
		assertTrue(uDto.getPassword().equals("pwdTest"));
		assertTrue(uDto.getEmail().equals("testEmail"));
		assertTrue(uDto.getReputation() == 4);
		assertTrue(!uDto.getAdmin());
	}
	
	@Test
	//UserDto --> User
	public void adminUserDtoToUserTest() {
		UserDto uDto = new UserDto(
				6,
				"userNameTest",
				"testPwd",
				"testEmail",
				5,
				true);
		
		UserConverter conv = new UserConverter();
		User u = conv.convertFromDto(uDto);
		
		assertTrue(u.getUserId() == 6);
		assertTrue(u.getUserName().equals("userNameTest"));
		assertTrue(u.getPassword().equals("testPwd"));
		assertTrue(u.getEmail().equals("testEmail"));
		assertTrue(u.getReputation() == 5);
		assertTrue(u.getAdmin());
	}
	
	@Test
	//UserDto --> User
	public void normalUserDtoToUserTest() {
		UserDto uDto = new UserDto(
				6,
				"userNameTest",
				"testPwd",
				"testEmail",
				5);
		
		UserConverter conv = new UserConverter();
		User u = conv.convertFromDto(uDto);
		
		assertTrue(u.getUserId() == 6);
		assertTrue(u.getUserName().equals("userNameTest"));
		assertTrue(u.getPassword().equals("testPwd"));
		assertTrue(u.getEmail().equals("testEmail"));
		assertTrue(u.getReputation() == 5);
		assertTrue(!u.getAdmin());
	}
	//NFR Tests
		@Test
		public void NFR2_normalUserDtoToUserTest() {
			UserDto uDto = new UserDto(
					6,
					"userNameTest",
					"testPwd",
					"testEmail",
					5);
			
			UserConverter conv = new UserConverter();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			User u = conv.convertFromDto(uDto);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
		}
		@Test
		public void NFR2_adminUserDtoToUserTest() {
			UserDto uDto = new UserDto(
					6,
					"userNameTest",
					"testPwd",
					"testEmail",
					5,
					true);
			
			UserConverter conv = new UserConverter();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			User u = conv.convertFromDto(uDto);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
		}
		@Test
		public void NFR2_normalUserToDtoTest() {
			User u = new User(
					"userNameTest",
					"pwdTest",
					"testEmail",
					4);
			u.setAdmin(false);
			
			UserConverter conv = new UserConverter();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			UserDto uDto = conv.convert(u);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
		}
		@Test
		public void NFR2_adminUserToDtoTest() {
			User u = new User(
					"userNameTest",
					"pwdTest",
					"testEmail",
					4);
			u.setAdmin(true);
			
			UserConverter conv = new UserConverter();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			UserDto uDto = conv.convert(u);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
			
		}
}
