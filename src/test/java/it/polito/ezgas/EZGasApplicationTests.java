package it.polito.ezgas;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EZGasApplicationTests {
//Unit tests for gasStation class
	@Test
	public void TestgetGasStationId1() {
		GasStation gasStation=new GasStation();
	
		int gasStationID=12345;
		
		gasStation.setGasStationId(gasStationID);
		assert(gasStation.getGasStationId()==gasStationID);
		
	}
	@Test
	public void TestgetGasStationId2() {
		GasStation gasStation=new GasStation();
		
		int gasStationID=-12345;
		
		gasStation.setGasStationId(gasStationID);
		assert(gasStation.getGasStationId()==gasStationID);
		
	}
	
	
	@Test
	public void TestgetDieselPrice1() {
		GasStation gasStation=new GasStation();
		double price=5.0;
		gasStation.setDieselPrice(price);
		assert(gasStation.getDieselPrice()==price);
		
	}
	
	@Test
	public void TestgetDieselPrice2() {
		GasStation gasStation=new GasStation();
		double price=-5.0;
		gasStation.setDieselPrice(price);
		assertTrue(gasStation.getDieselPrice()==price);
	}
	@Test
	public void TestgetDieselPrice3() {
		GasStation gasStation=new GasStation();
		double price=4.46;
		gasStation.setDieselPrice(price);
		assertTrue(gasStation.getDieselPrice()==price);
		
	}
	
	@Test
	public void TestgetDieselPrice4() {
		GasStation gasStation=new GasStation();
		double price=-4.46;
		gasStation.setDieselPrice(price);
		assertTrue(gasStation.getDieselPrice()==price);
	}
	
	@Test 
	public void TestsetGasPrice1() {
		GasStation gasStation=new GasStation();
		gasStation.setGasPrice(3.0);
		double price = gasStation.getGasPrice();
		assertTrue(price==3);		
	}
	@Test 
	public void TestsetGasPrice2() {
		GasStation gasStation=new GasStation();
		gasStation.setGasPrice(2.99);
		double price = gasStation.getGasPrice();
		assertTrue(price==2.99);		
	}
	@Test 
	public void TestsetGasPrice3() {
		GasStation gasStation=new GasStation();
		gasStation.setGasPrice(-3.0);
		double price = gasStation.getGasPrice();
		assertTrue(price==-3);		
	}
	@Test 
	public void TestsetGasPrice4() {
		GasStation gasStation=new GasStation();
		gasStation.setGasPrice(-2.99);
		double price = gasStation.getGasPrice();
		assertTrue(price==-2.99);		
	}
	@Test 
	public void TestsetGasPrice5() {
		GasStation gasStation=new GasStation();
		gasStation.setGasPrice(0.0);
		double price = gasStation.getGasPrice();
		assertTrue(price==0);		
	}
//Unit tests for class User
	@Test
	public void TestgetUserId1() {
		User user=new User();
		user.setUserId(-100099);
		assertTrue(user.getUserId()==-100099);
	}
	@Test
	public void TestgetUserId2() {
		User user=new User();
		user.setUserId(100099);
		assertTrue(user.getUserId()==100099);
	}
	@Test
	public void TestgetReputation1() {
		User user=new User();
		user.setReputation(-111111);
		assertTrue(user.getReputation()==-111111);
	}
	
	@Test
	public void TestgetReputation2() {
		User user=new User();
		user.setReputation(111111);
		assertTrue(user.getReputation()==111111);
	}
	
	@Test
	public void TestgetAdmin1() {
		User user=new User();
		user.setAdmin(true);
		assertTrue(user.getAdmin()==true);
	}
	
	@Test
	public void TestgetAdmin2() {
		User user=new User();
		user.setAdmin(false);
		assertTrue(user.getAdmin()==false);
	}
	//NFR Tests
		@Test
		public void NFR2_TestgetGasStationId(){
			GasStation gasStation=new GasStation();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			gasStation.setGasStationId(12345);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
			}
		@Test
		public void NFR2_TestgetDieselPrice(){
			GasStation gasStation=new GasStation();	
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			gasStation.setDieselPrice(4.5);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
			}
		@Test
		public void NFR2_TestsetGasPrice(){
			GasStation gasStation=new GasStation();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			gasStation.setGasPrice(4.5);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
			}
			@Test
			public void NFR2_TestgetUserId(){
			User user = new User();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			user.setUserId(100099);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
			}
			@Test
			public void NFR2_TestgetReputation(){
			User user = new User();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			user.setReputation(-111111);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
			}
			@Test
			public void NFR2_TestgetAdmin(){
			User user = new User();
			Timestamp timeS = new Timestamp(System.currentTimeMillis());
			user.setAdmin(true);
			Timestamp timeF = new Timestamp(System.currentTimeMillis());
			long exec = timeF.getTime()-timeS.getTime();
			assertTrue(exec<=500);
			}
}
