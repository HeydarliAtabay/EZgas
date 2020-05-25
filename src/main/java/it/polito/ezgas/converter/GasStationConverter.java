package it.polito.ezgas.converter;

import org.springframework.core.convert.converter.Converter;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;

public class GasStationConverter implements Converter<GasStation, GasStationDto>{

	@Override
	//converts from GasStation to GasStationDto
	public GasStationDto convert(GasStation source) {
		//creates the GasStationDto and returns it
		GasStationDto dto = new GasStationDto(
				source.getGasStationId(),
				source.getGasStationName(),
				source.getGasStationAddress(),
				source.getHasDiesel(),
				source.getHasSuper(),
				source.getHasSuperPlus(),
				source.getHasGas(),
				source.getHasMethane(),
				source.getCarSharing(),
				source.getLat(),
				source.getLon(),
				source.getDieselPrice(),
				source.getSuperPrice(),
				source.getSuperPlusPrice(),
				source.getGasPrice(),
				source.getMethanePrice(),
				source.getReportUser(),
				source.getReportTimestamp(),
				source.getReportDependability());
		
		if (source.getUser() != null) {
			UserConverter uConv = new UserConverter();
			dto.setUserDto(uConv.convert(source.getUser()));
		}
		
		return dto;
	}
	
	public GasStation convertFromDto(GasStationDto source) {
		GasStation gs = new GasStation(
				source.getGasStationName(),
				source.getGasStationAddress(),
				source.getHasDiesel(),
				source.getHasSuper(),
				source.getHasSuperPlus(),
				source.getHasGas(),
				source.getHasMethane(),
				source.getCarSharing(),
				source.getLat(),
				source.getLon(),
				source.getDieselPrice(),
				source.getSuperPrice(),
				source.getSuperPlusPrice(),
				source.getGasPrice(),
				source.getMethanePrice(),
				source.getReportUser(),
				source.getReportTimestamp(),
				source.getReportDependability());
		
		gs.setGasStationId(source.getGasStationId());
		
		UserConverter uConv = new UserConverter();
		if(source.getUserDto() != null) {
			gs.setUser(uConv.convertFromDto(source.getUserDto()));
		}
		
		return gs;
	}
}
