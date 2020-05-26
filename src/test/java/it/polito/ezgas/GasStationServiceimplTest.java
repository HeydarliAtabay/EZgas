package it.polito.ezgas;

import exception.*;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.GasStationService;
import it.polito.ezgas.service.UserService;
import it.polito.ezgas.service.impl.GasStationServiceimpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GasStationServiceimplTest { 
	@Autowired
	GasStationService gasStationService;

	@Autowired
	UserService userService;

	@Autowired
	GasStationRepository gasStationRepository;

	@Autowired
	UserRepository userRepository;

	@Before
	public void removeRepositoryContent() {
		// clear db content
		List<User> allUsers = userRepository.findAll();
		List<GasStation> allStations = gasStationRepository.findAll();

		allUsers.forEach(user -> userRepository.delete(user.getUserId()));
		allStations.forEach(gasStation -> gasStationRepository.delete(gasStation.getGasStationId()));
	}

	
	
	@Test
	public void getCarSharingTest() throws PriceException, GPSDataException {
		//  try to create a station with a non empty car sharing
		GasStationDto gasStationDto = new GasStationDto(0, "GasStation1", "Via Roma 1", true, false, true, false, true,
				"Car2Go", 10, 30, -1, -1, -1, -1, -1, null, "", 0);

		GasStationDto gasStationDtoReceived = gasStationService.saveGasStation(gasStationDto);

		assert gasStationDtoReceived.getCarSharing().equals(gasStationDto.getCarSharing());
	}


	@Test
	public void saveGasStationTest1() throws PriceException, GPSDataException {
		//  try to create a station with invalid longitudes
		GasStationDto gasStationDto = new GasStationDto(-1, "GasStation1", "Via Roma 1", true, false, true, false, true,
				"", 10, -190, 13, -1, 2, -1, 19, null, "", 0);


		Assertions.assertThrows(GPSDataException.class, () -> gasStationService.saveGasStation(gasStationDto));

		gasStationDto.setLon(200.0);
		Assertions.assertThrows(GPSDataException.class, () -> gasStationService.saveGasStation(gasStationDto));

		// test values on boundaries
		gasStationDto.setLon(-180.0);
		assert gasStationService.saveGasStation(gasStationDto) != null;

		gasStationDto.setLon(180.0);
		assert gasStationService.saveGasStation(gasStationDto) != null;
	}

	@Test
	public void getGasStationByIdTest() {
		//  try to get a station with a negative id
		Assertions.assertThrows(InvalidGasStationException.class, () -> gasStationService.getGasStationById(-1));
	}

	@Test
	public void deleteGasStationTest() {
		//  try to delete a station with a negative id
		Assertions.assertThrows(InvalidGasStationException.class, () -> gasStationService.deleteGasStation(-1));
	}

	
	//
	// tests on searches
	//

	

	
	@Test
	public void getGasStationByGasolineTypeTest1() {
		// try to get an empty fuel
		Assertions.assertThrows(InvalidGasTypeException.class, () -> gasStationService.getGasStationsByGasolineType(""));
	}
	@Test
	public void getGasStationByGasolineTypeTest2() {
		// try to get an null fuel
		Assertions.assertThrows(InvalidGasTypeException.class, () -> gasStationService.getGasStationsByGasolineType(null));
	}

	@Test
	public void GasStationInvalidLatTest() {
		// try to get stations with an invalid lat
		Assertions.assertThrows(GPSDataException.class, () -> gasStationService.getGasStationsByProximity(-100, 23));
	}

	@Test
	public void GasStationInvalidLonTest() {
		// try to get stations with an invalid lon
		Assertions.assertThrows(GPSDataException.class, () -> gasStationService.getGasStationsByProximity(87, 207));
	}

	@Test
	public void getGasStationByProximityTest() throws GPSDataException, PriceException {//  try to get all gas stations with a "null" fuel
		GasStationDto gasStationDto1 = new GasStationDto(null, "GasStation1", "Via Roma 1", true, true, true, false, false,
				"", 10, 30, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDto2 = new GasStationDto(null, "GasStation2", "Via Roma 13", true, false, true, false, true,
				"Car2Go", 3, 30.004, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDto3 = new GasStationDto(null, "GasStation3", "Via Garibaldi 21", false, false, true, true, true,
				"Car2Go", 67.3, 30.0, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDto4 = new GasStationDto(null, "GasStation4", "Corso Vittorio Emanuele 1", false, false, false, false, true,
				"Enjoy", 10, 55, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDtoReceived1 = gasStationService.saveGasStation(gasStationDto1);
		GasStationDto gasStationDtoReceived2 = gasStationService.saveGasStation(gasStationDto2);
		GasStationDto gasStationDtoReceived3 = gasStationService.saveGasStation(gasStationDto3);
		GasStationDto gasStationDtoReceived4 = gasStationService.saveGasStation(gasStationDto4);

		assert gasStationDtoReceived1 != null;
		assert gasStationDtoReceived2 != null;
		assert gasStationDtoReceived3 != null;
		assert gasStationDtoReceived4 != null;

		List<GasStationDto> gasStationDtos = gasStationService.getGasStationsByProximity(9, 30.0004);

		assert (gasStationDtos.size() == 0);
	}

	@Test
	public void getGasStationWithoutCoordinatesTest() {
		//  try to get all gas stations with an invalid fuel
		Assertions.assertThrows(InvalidGasTypeException.class, () -> gasStationService
				.getGasStationsWithoutCoordinates("", ""));
	}




	@Test
	public void getGasStationsWithCoordinatesTest1() throws PriceException, GPSDataException, InvalidGasTypeException {
		//  try to get some station parameters by WithCoordinates with some parameters which should return nothing
		GasStationDto gasStationDto1 = new GasStationDto(null, "GasStation1", "Via Roma 1", true, true, true, false, false,
				"", 10, 30, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDto2 = new GasStationDto(null, "GasStation2", "Via Roma 13", true, false, true, false, true,
				"Car2Go", 3, 30.004, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDto3 = new GasStationDto(null, "GasStation3", "Via Garibaldi 21", false, false, true, true, true,
				"Car2Go", 67.3, 30.0, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDto4 = new GasStationDto(null, "GasStation4", "Corso Vittorio Emanuele 1", false, false, false, false, true,
				"Enjoy", 10, 55, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDtoReceived1 = gasStationService.saveGasStation(gasStationDto1);
		GasStationDto gasStationDtoReceived2 = gasStationService.saveGasStation(gasStationDto2);
		GasStationDto gasStationDtoReceived3 = gasStationService.saveGasStation(gasStationDto3);
		GasStationDto gasStationDtoReceived4 = gasStationService.saveGasStation(gasStationDto4);

		assert gasStationDtoReceived1 != null;
		assert gasStationDtoReceived2 != null;
		assert gasStationDtoReceived3 != null;
		assert gasStationDtoReceived4 != null;

		List<GasStationDto> gasStationDtos = gasStationService.getGasStationsWithCoordinates(3, 40, "Super", "null");

		assert (gasStationDtos.size() == 0);
	}

	@Test
	public void getGasStationsWithCoordinatesTest2(){
		// test with invalid latitude
		Assertions.assertThrows(GPSDataException.class, () -> gasStationService.getGasStationsWithCoordinates(-100, 30, "null", "null"));
	}

	@Test
	public void getGasStationsWithCoordinatesTest3(){
		// test with invalid latitude
		Assertions.assertThrows(GPSDataException.class, () -> gasStationService.getGasStationsWithCoordinates(20, 300, "null", "null"));
	}
	

	@Test
	public void setReportTest1() {
		// try to create a report with invalid gas station id
		Assertions.assertThrows(InvalidGasStationException.class, () -> gasStationService.setReport(-1, 3, 4, 5, -1, -1, 3));
	}

	@Test
	public void setReportTest2() throws PriceException, GPSDataException{
		// try to create a report with invalid user id
		GasStationDto gasStationDto1 = new GasStationDto(null, "GasStation1", "Via Roma 1", true, true, true, false, false,
				"", 10, 30, -1, -1, -1, -1, -1, -1, null, 0);

		GasStationDto gasStationDtoReceived1 = gasStationService.saveGasStation(gasStationDto1);
		assert gasStationDtoReceived1 != null;

		Assertions.assertThrows(InvalidUserException.class, () -> gasStationService.setReport(gasStationDtoReceived1.getGasStationId(), 3, 4, 5, -1, -1, -1));
	}
	@Test
	public void getGasStationByIdNullTest() {
		Integer gasStationId=101;
	 	GasStationRepository repo = mock(GasStationRepository.class);
	 	when(repo.findOne(gasStationId)).thenReturn(null);
	 	GasStationServiceimpl s = new GasStationServiceimpl(repo);
		try {
			GasStationDto gsd=s.getGasStationById(gasStationId);
			assertTrue(gsd==null);
		} catch (InvalidGasStationException e) {
			fail(); //it should only throw the exception if gasStationId is negative
		}
		
	}

	@Test
	//tests exception if negative GasStationId in getGasStationById()
		public void getGasStationByIdExceptionTest() {
			GasStationRepository repo = mock(GasStationRepository.class);
			GasStationServiceimpl s = new GasStationServiceimpl(repo);
			try {
				s.getGasStationById(-10);
				fail();
			} catch (InvalidGasStationException e) {
				assertTrue(true);
			}
		}
	 public void getAllGasStationsEmptyTest() {
			GasStationRepository repo = mock(GasStationRepository.class);
			when(repo.findAll()).thenReturn(new ArrayList<GasStation>());
			
			GasStationServiceimpl s = new GasStationServiceimpl(repo);
			List<GasStationDto> GasDto = s.getAllGasStations();
			assertTrue(GasDto.size() == 0);
		}
	 @Test
	 public void getAllGasStationsTest() throws PriceException, GPSDataException {
	  
	 List<GasStation> gList = new ArrayList<GasStation>();
		
		GasStation g1 = new GasStation();
		g1.setGasStationName("Q8");
		GasStation g2 = new GasStation();
		g2.setGasStationName("BP");
		
		gList.add(g1);
		gList.add(g2);
		
		GasStationRepository repo = mock(GasStationRepository.class);
		when(repo.findAll()).thenReturn(gList);
		
		GasStationServiceimpl s = new GasStationServiceimpl(repo);
		List<GasStationDto> GasDto = s.getAllGasStations();
		
		
		assertTrue(GasDto.size() == 2);
		//with the two conditions in OR because order of the users shouldn't matter
		assertTrue(GasDto.get(0).getGasStationName().equals("Q8") || GasDto.get(0).getGasStationName().equals("BP"));
		assertTrue(GasDto.get(1).getGasStationName().equals("Q8") || GasDto.get(1).getGasStationName().equals("BP"));

	 }
	 
	// NFR Tests
	 @Test
		public void testNFRAddModifyStation() throws PriceException, GPSDataException {
			GasStationDto gasStationDto = new GasStationDto(0, "GasStation1", "Via Pietro Cossa 2", true, false, true, false, true,
					"", 10, 30, -1, -1, -1, -1, -1, -1, "", 0);

			long start = System.currentTimeMillis();

			GasStationDto gasStationDtoReceived = gasStationService.saveGasStation(gasStationDto);

			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;

			assert (double)timeElapsed / 1000 < 0.5;
		}

		@Test
		public void testNFRDeleteGasStation() throws PriceException, GPSDataException, InvalidGasStationException {
			GasStationDto gasStationDto = new GasStationDto(0, "GasStation1", "Via Pietro Cossa 2", true, false, true, false, true,
					"", 10, 30, -1, -1, -1, -1, -1, -1, "", 0);

			GasStationDto gasStationDtoReceived = gasStationService.saveGasStation(gasStationDto);

			long start = System.currentTimeMillis();

			gasStationService.deleteGasStation(gasStationDtoReceived.getGasStationId());

			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;

			assert (double)timeElapsed / 1000 < 0.5;
		}

		@Test
		public void testNFRListGasStations() throws PriceException, GPSDataException {
			GasStationDto gasStationDto1 = new GasStationDto(null, "GasStation1", "Via Pietro cossa 1", true, true, true, false, false,
					"", 10, 30, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto2 = new GasStationDto(null, "GasStation2", "Via Roma 13", true, false, true, false, true,
					"Car2Go", 3, 30.004, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto3 = new GasStationDto(null, "GasStation3", "Via Garibaldi 21", false, false, true, true, true,
					"Car2Go", 67.3, 30.0, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto4 = new GasStationDto(null, "GasStation4", "Corso Vittorio Emanuele 1", false, false, false, false, true,
					"Enjoy", 10, 55, -1, -1, -1, -1, -1, -1, null, 0);
			GasStationDto gasStationDtoReceived1 = gasStationService.saveGasStation(gasStationDto1);
			GasStationDto gasStationDtoReceived2 = gasStationService.saveGasStation(gasStationDto2);
			GasStationDto gasStationDtoReceived3 = gasStationService.saveGasStation(gasStationDto3);
			GasStationDto gasStationDtoReceived4 = gasStationService.saveGasStation(gasStationDto4);

			long start = System.currentTimeMillis();

			gasStationService.getAllGasStations();

			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;

			assert (double)timeElapsed / 1000 < 0.5;
		}

		@Test
		public void testNFRSearchGasStation() throws PriceException, GPSDataException {
			GasStationDto gasStationDto1 = new GasStationDto(null, "GasStation1", "Via Roma 1", true, true, true, false, false,
					"", 10, 30, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto2 = new GasStationDto(null, "GasStation2", "Via Roma 13", true, false, true, false, true,
					"Car2Go", 3, 30.004, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto3 = new GasStationDto(null, "GasStation3", "Via Garibaldi 21", false, false, true, true, true,
					"Car2Go", 67.3, 30.0, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto4 = new GasStationDto(null, "GasStation4", "Corso Vittorio Emanuele 1", false, false, false, false, true,
					"Enjoy", 10, 55, -1, -1, -1, -1, -1, -1, null, 0);
			GasStationDto gasStationDtoReceived1 = gasStationService.saveGasStation(gasStationDto1);
			GasStationDto gasStationDtoReceived2 = gasStationService.saveGasStation(gasStationDto2);
			GasStationDto gasStationDtoReceived3 = gasStationService.saveGasStation(gasStationDto3);
			GasStationDto gasStationDtoReceived4 = gasStationService.saveGasStation(gasStationDto4);

			long start = System.currentTimeMillis();

			gasStationService.getGasStationsByProximity(30.0, 4.0);

			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;

			assert (double)timeElapsed / 1000 < 0.5;
		}

		@Test
		public void testNFRSearchGasStation2() throws InvalidGasTypeException, PriceException, GPSDataException {
			GasStationDto gasStationDto1 = new GasStationDto(null, "GasStation1", "Via Roma 1", true, true, true, false, false,
					"", 10, 30, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto2 = new GasStationDto(null, "GasStation2", "Via Roma 13", true, false, true, false, true,
					"Car2Go", 3, 30.004, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto3 = new GasStationDto(null, "GasStation3", "Via Garibaldi 21", false, false, true, true, true,
					"Car2Go", 67.3, 30.0, -1, -1, -1, -1, -1, -1, null, 0);

			GasStationDto gasStationDto4 = new GasStationDto(null, "GasStation4", "Corso Vittorio Emanuele 1", false, false, false, false, true,
					"Enjoy", 10, 55, -1, -1, -1, -1, -1, -1, null, 0);
			GasStationDto gasStationDtoReceived1 = gasStationService.saveGasStation(gasStationDto1);
			GasStationDto gasStationDtoReceived2 = gasStationService.saveGasStation(gasStationDto2);
			GasStationDto gasStationDtoReceived3 = gasStationService.saveGasStation(gasStationDto3);
			GasStationDto gasStationDtoReceived4 = gasStationService.saveGasStation(gasStationDto4);

			long start = System.currentTimeMillis();

			gasStationService.getGasStationsWithoutCoordinates("Diesel", "Car2Go");

			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;

			assert (double)timeElapsed / 1000 < 0.5;
		}

		@Test
		public void testNFRAddReport() throws PriceException, GPSDataException, InvalidGasStationException, InvalidUserException {
			GasStationDto gasStationDto = new GasStationDto(0, "GasStation1", "Via Roma 1", true, false, true, false, true,
					"", 10, 30, -1, -1, -1, -1, -1, -1, "", 0);

			GasStationDto gasStationDtoReceived = gasStationService.saveGasStation(gasStationDto);
			UserDto newUser = new UserDto(0, "maumorisio", "se20", "mor@gmail.com", 0);
			newUser.setAdmin(false);

			UserDto saveUserResult = userService.saveUser(newUser);

			long start = System.currentTimeMillis();

			gasStationService.setReport(gasStationDtoReceived.getGasStationId(), 3, 4, 5, 3, 2, saveUserResult.getUserId());

			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;

			assert (double)timeElapsed / 1000 < 0.5;
		}

		@Test
		public void testNFREvaluateReport() throws PriceException, InvalidUserException, InvalidGasStationException, GPSDataException {
			GasStationDto gasStationDto = new GasStationDto(0, "GasStation1", "Via Roma 1", true, false, true, false, true,
					"", 10, 30, -1, -1, -1, -1, -1, -1, "", 0);

			GasStationDto gasStationDtoReceived = gasStationService.saveGasStation(gasStationDto);
			UserDto newUser = new UserDto(0, "maumorisio", "se20", "mor@gmail.com", 0);
			newUser.setAdmin(false);
			UserDto saveUserResult = userService.saveUser(newUser);

			gasStationService.setReport(gasStationDtoReceived.getGasStationId(), 3, 4, 5, 4, 5, saveUserResult.getUserId());

			long start = System.currentTimeMillis();

			userService.increaseUserReputation(saveUserResult.getUserId());

			long finish = System.currentTimeMillis();
			long timeElapsed = finish - start;

			assert (double)timeElapsed / 1000 < 0.5;
		}
	}


