package it.polito.ezgas;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GasStationConverterTests {
	@Test
	public void gasStationToDtoTest() {
		GasStation gs = new GasStation(
				"gsNameTest",
				"gsAddTest",
				true,
				true,
				true,
				true,
				false,
				"car2go",
				47.568,
				7.568,
				2,
				2,
				2,
				2,
				-1,
				15,
				"timestamp",
				1.6);
		
		User u = new User();
		u.setUserName("userInGs");
		gs.setUser(u);
		
		GasStationConverter conv = new GasStationConverter();
		GasStationDto gsDto = conv.convert(gs);
		
		assertTrue(gsDto.getGasStationName().equals("gsNameTest"));
		assertTrue(gsDto.getGasStationAddress().equals("gsAddTest"));
		assertTrue(gsDto.getHasDiesel());
		assertTrue(gsDto.getHasSuper());
		assertTrue(gsDto.getHasSuperPlus());
		assertTrue(gsDto.getHasGas());
		assertTrue(!gsDto.getHasMethane());
		assertTrue(gsDto.getCarSharing().equals("car2go"));
		assertTrue(gsDto.getLat() == 47.568);
		assertTrue(gsDto.getLon() == 7.568);
		assertTrue(gsDto.getDieselPrice() == 2);
		assertTrue(gsDto.getSuperPrice() == 2);
		assertTrue(gsDto.getGasPrice() == 2);
		assertTrue(gsDto.getReportUser() == 15);
		assertTrue(gsDto.getReportTimestamp().equals("timestamp"));
		assertTrue(gsDto.getReportDependability() == 1.6);
		assertTrue(gsDto.getUserDto().getUserName().equals("userInGs"));
	}
	
	@Test
	public void dtoToGasStationTest() {
		GasStationDto gsDto = new GasStationDto(
				16,
				"gsName",
				"gsAddr",
				true,
				true,
				true,
				true,
				false, //methane
				"enjoy",
				45.89,
				8.89,
				2,
				2,
				2,
				2,
				-1,
				15,
				"timestamp",
				1.5);
		
		UserDto uDto = new UserDto();
		uDto.setUserName("userNameGS");
		gsDto.setUserDto(uDto);
		GasStationConverter conv = new GasStationConverter();
		GasStation gs = conv.convertFromDto(gsDto);
		
		assertTrue(gs.getGasStationId() == 16);
		assertTrue(gs.getGasStationName().equals("gsName"));
		assertTrue(gs.getGasStationAddress().equals("gsAddr"));
		assertTrue(gs.getHasDiesel());
		assertTrue(gs.getHasSuper());
		assertTrue(gs.getHasSuperPlus());
		assertTrue(gs.getHasGas());
		assertTrue(!gs.getHasMethane());
		assertTrue(gs.getCarSharing().equals("enjoy"));
		assertTrue(gs.getLat() == 45.89);
		assertTrue(gs.getLon() == 8.89);
		assertTrue(gs.getDieselPrice() == 2);
		assertTrue(gs.getSuperPrice() == 2);
		assertTrue(gs.getSuperPlusPrice() == 2);
		assertTrue(gs.getGasPrice() == 2);
		assertTrue(gs.getReportUser() == 15);
		assertTrue(gs.getReportTimestamp().equals("timestamp"));
		assertTrue(gs.getReportDependability() == 1.5);
		assertTrue(gs.getUser().getUserName().equals("userNameGS"));
	}
}
