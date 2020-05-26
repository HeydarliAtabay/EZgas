package it.polito.ezgas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.UserServiceimpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceimplTests {
	@Test
	//tests exception if negative userId in getUserById()
	public void getUserByIdExceptionTest() {
		UserRepository repo = mock(UserRepository.class);
		UserServiceimpl s = new UserServiceimpl(repo);
		try {
			s.getUserById(-1);
			fail();
		} catch (InvalidUserException e) {
			assertTrue(true);
		}
	}
	
	@Test
	//tests return null if no user found in getUserById()
	public void getUserByIdNullTest() {
		Integer userId = 15;
		UserRepository repo = mock(UserRepository.class);
		when(repo.findOne(userId)).thenReturn(null);
		UserServiceimpl s = new UserServiceimpl(repo);
		try {
			UserDto u = s.getUserById(userId);
			assertTrue(u == null);
		} catch (InvalidUserException e) {
			fail(); //it should only throw the exception if userId is negative
		}
	}
	
	@Test
	//getUserById() successful test
	public void getUserByIdTest() {
		User u = new User();
		u.setUserId(5);
		u.setUserName("testUN");
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findOne(5)).thenReturn(u);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			UserDto uDto = s.getUserById(5);
			assertTrue(uDto.getUserId() == 5);
			assertTrue(uDto.getUserName().equals("testUN"));
		} catch (InvalidUserException e) {
			fail();
		}
	}
	
	@Test
	//tests with a mock repo with two users getAllUsers()
	public void getAllUsersTest() {
		List<User> uList = new ArrayList<User>();
		
		User u1 = new User();
		u1.setUserName("user1");
		User u2 = new User();
		u2.setUserName("user2");
		
		uList.add(u1);
		uList.add(u2);
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findAll()).thenReturn(uList);
		
		UserServiceimpl s = new UserServiceimpl(repo);
		List<UserDto> ulDto = s.getAllUsers();
		
		
		assertTrue(ulDto.size() == 2);
		//with the two conditions in OR because order of the users shouldn't matter
		assertTrue(ulDto.get(0).getUserName().equals("user1") || ulDto.get(0).getUserName().equals("user2"));
		assertTrue(ulDto.get(1).getUserName().equals("user1") || ulDto.get(1).getUserName().equals("user2"));
	}
	
	@Test
	//tests, with an empty mock repo, getAllUsers()
	public void getAllUsersEmptyTest() {
		UserRepository repo = mock(UserRepository.class);
		when(repo.findAll()).thenReturn(new ArrayList<User>());
		
		UserServiceimpl s = new UserServiceimpl(repo);
		List<UserDto> ulDto = s.getAllUsers();
		assertTrue(ulDto.size() == 0);
	}
	
	@Test
	//tests if it throws the exception with a negative userId
	public void deleteUserExceptionTest() {
		UserRepository repo = mock(UserRepository.class);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			s.deleteUser(-5);
			fail(); //it should throw the exception
		} catch (InvalidUserException e) {
			assertTrue(true);
		}
	}
	
	@Test
	//tests (with mock repo) the deletion of a user
	public void deleteUserTest() {
		List<User> uList = new ArrayList<User>();
		User u = new User();
		u.setUserId(5);
		uList.add(u);
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findAll()).thenReturn(uList);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			Boolean b = s.deleteUser(5);
			assertTrue(b);
		} catch (InvalidUserException e) {
			fail();
		}
	}
	
	@Test
	//tests (with mock repo) the deletion of a user wich does not exist
	public void deleteNonExistingUserTest() {
		List<User> uList = new ArrayList<User>();
		User u = new User();
		u.setUserId(5);
		uList.add(u);
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findAll()).thenReturn(uList);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			Boolean b = s.deleteUser(10);
			assertTrue(!b);
		} catch (InvalidUserException e) {
			fail();
		}
	}
	
	@Test
	//performs a successful login (with a mock database)
	public void loginSuccessfulTest() {
		List<User> uList = new ArrayList<User>();
		User u = new User();
		u.setUserId(5);
		u.setEmail("testEmail");
		u.setPassword("testPwd");
		uList.add(u);
		
		IdPw cr = new IdPw("testEmail", "testPwd");
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findAll()).thenReturn(uList);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			LoginDto lDto = s.login(cr);
			assertTrue(lDto.getEmail().equals("testEmail"));
			assertTrue(lDto.getUserId() == 5);
		} catch (InvalidLoginDataException e) {
			fail(); //email and password are correct, so it shouldn't throw the exception
		}
	}
	
	@Test
	//performs an unsuccessful login with wrong password (with a mock database)
	public void loginWrongPwdTest() {
		List<User> uList = new ArrayList<User>();
		User u = new User();
		u.setUserId(5);
		u.setEmail("testEmail");
		u.setPassword("testPwd");
		uList.add(u);
		
		IdPw cr = new IdPw("testEmail", "wrongPwd");
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findAll()).thenReturn(uList);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			s.login(cr);
			fail();
		} catch (InvalidLoginDataException e) {
			assertTrue(true);
		}
	}
	
	@Test
	//performs an unsuccessful login with an email not on the database (with a mock database)
	public void loginWrongEmailTest() {
		List<User> uList = new ArrayList<User>();
		User u = new User();
		u.setUserId(5);
		u.setEmail("testEmail");
		u.setPassword("testPwd");
		uList.add(u);
		
		IdPw cr = new IdPw("wrongEmail", "testPwd");
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findAll()).thenReturn(uList);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			s.login(cr);
			fail();
		} catch (InvalidLoginDataException e) {
			assertTrue(true);
		}
	}
	
	@Test
	//tests successful increaseUserReputation()
	public void increaseReputationTest() {
		Integer userId = 5;
		Integer rep = 3;
		
		User u = new User();
		u.setReputation(rep);
		u.setUserId(userId);
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findOne(userId)).thenReturn(u);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			Integer newRep = s.increaseUserReputation(userId);
			assertTrue(newRep == rep + 1);
		} catch (InvalidUserException e) {
			fail();
		}
	}
	
	@Test
	//tests increaseUserReputation() with user rep at 5
	public void increaseReputationMaximumTest() {
		Integer userId = 5;
		Integer rep = 5;
		
		User u = new User();
		u.setReputation(rep);
		u.setUserId(userId);
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findOne(userId)).thenReturn(u);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			Integer newRep = s.increaseUserReputation(userId);
			assertTrue(newRep == rep);
		} catch (InvalidUserException e) {
			fail();
		}
	}
	
	@Test
	//tests successful decreaseUserReputation()
	public void decreaseReputationTest() {
		Integer userId = 5;
		Integer rep = 3;
		
		User u = new User();
		u.setReputation(rep);
		u.setUserId(userId);
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findOne(userId)).thenReturn(u);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			Integer newRep = s.decreaseUserReputation(userId);
			assertTrue(newRep == rep - 1);
		} catch (InvalidUserException e) {
			fail();
		}
	}
	
	@Test
	//tests decreaseUserReputation() with user rep at -5 (minimum)
	public void decreaseReputationMinimumTest() {
		Integer userId = 5;
		Integer rep = -5;
		
		User u = new User();
		u.setReputation(rep);
		u.setUserId(userId);
		
		UserRepository repo = mock(UserRepository.class);
		when(repo.findOne(userId)).thenReturn(u);
		UserServiceimpl s = new UserServiceimpl(repo);
		
		try {
			Integer newRep = s.decreaseUserReputation(userId);
			assertTrue(newRep == rep);
		} catch (InvalidUserException e) {
			fail();
		}
	}
}
