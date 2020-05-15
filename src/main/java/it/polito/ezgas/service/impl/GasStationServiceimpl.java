package it.polito.ezgas.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.service.GasStationService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class GasStationServiceimpl implements GasStationService {
	
	//instance of the GasStationRepository
	@Autowired
	private GasStationRepository repo;

	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		if (gasStationId < 0) {
			throw new InvalidGasStationException("Error: impossible id for gas station (id < 0)");
		}
		
		//query the database
		GasStation gs = repo.findOne(gasStationId);
		
		//gs is null if there is no gas station with the provided id
		if (gs == null) {
			return null;
		}
		
		//create dto and return it
		GasStationConverter conv = new GasStationConverter();
		return conv.convert(gs);
	}

	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
		double d = -1.0;
		
		//if the prices are -1, set a default value for the price
		if (gasStationDto.getHasDiesel()) {
			if( Math.abs(gasStationDto.getDieselPrice() - d) < 0.0000001) {
				gasStationDto.setDieselPrice(1.2);
			}
		}
		
		if (gasStationDto.getHasSuper()) {
			if( Math.abs(gasStationDto.getSuperPrice() - d) < 0.0000001) {
				gasStationDto.setSuperPrice(1.2);
			}
		}
		
		if (gasStationDto.getHasSuperPlus()) {
			if( Math.abs(gasStationDto.getSuperPlusPrice() - d) < 0.0000001) {
				gasStationDto.setSuperPlusPrice(1.4);
			}
		}
		
		if (gasStationDto.getHasGas()) {
			if( Math.abs(gasStationDto.getGasPrice() - d) < 0.0000001) {
				gasStationDto.setGasPrice(1.5);
			}
		}
		
		if (gasStationDto.getHasMethane()) {
			if( Math.abs(gasStationDto.getMethanePrice() - d) < 0.0000001) {
				gasStationDto.setMethanePrice(1.1);
			}
		}
		
		//throws priceException if prices are negative
		if (gasStationDto.getHasDiesel()) {
			if(gasStationDto.getDieselPrice() < 0) {
				throw new PriceException("Error: diesel price negative");
			}
		}
		
		if (gasStationDto.getHasSuper()) {
			if(gasStationDto.getSuperPrice() < 0) {
				throw new PriceException("Error: super price negative");
			}
		}
		
		if (gasStationDto.getHasSuperPlus()) {
			if(gasStationDto.getSuperPlusPrice() < 0) {
				throw new PriceException("Error: super plus price negative");
			}
		}
		
		if (gasStationDto.getHasGas()) {
			if(gasStationDto.getGasPrice() < 0) {
				throw new PriceException("Error: gas price negative");
			}
		}
		
		if (gasStationDto.getHasMethane()) {
			if(gasStationDto.getMethanePrice() < 0) {
				throw new PriceException("Error: methane price negative");
			}
		}
		
		//latitude must be from 0 to 90; otherwise throw GPSDataException
		if(gasStationDto.getLat() < 0 || gasStationDto.getLat() > 90) {
			throw new GPSDataException("Error: impossible latitude coordinate");
		}
		
		//longitude must be from -180 to 180; otherwise throw GPSDataException
		if(gasStationDto.getLon() < -180 || gasStationDto.getLon() > 180) {
			throw new GPSDataException("Error: impossible longitude coordinate");
		}
		
		GasStationConverter conv = new GasStationConverter();
		repo.save(conv.convertFromDto(gasStationDto));
		
		return gasStationDto;
	}

	@Override
	public List<GasStationDto> getAllGasStations() {
		GasStationConverter conv = new GasStationConverter();
		//query database for all gas stations
		List<GasStation> gsList = repo.findAll();
		List<GasStationDto> dtoList;
		
		//convert from List<GasStation> to List<GasStationDto>
		dtoList = gsList.stream().map(gs -> conv.convert(gs)).collect(Collectors.toList());
				
		return dtoList;
	}

	@Override
	public Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException {
		if (gasStationId < 0) {
			throw new InvalidGasStationException("Error: impossible id for gas station (id < 0)");
		}
		
		if (repo.findOne(gasStationId) == null) {
			return null;
		}
		
		repo.delete(gasStationId);
		return true;
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		if (gasolinetype == null || gasolinetype == "") {
			throw new InvalidGasTypeException("Error: no gasoline type provided (null or empty string)");
		}
		
		//res is the list that is going to be returned
		List <GasStationDto> res = new ArrayList<GasStationDto>();
		GasStationConverter conv = new GasStationConverter();
		
		//for each gas station, if it has the gasoline type passed as argument it adds it to res
		for (GasStation gs : repo.findAll()) {
			if (gasolinetype.equalsIgnoreCase("Diesel")) {
				if(gs.getHasDiesel()) {
					res.add(conv.convert(gs));
				}
			}
			else if (gasolinetype.equalsIgnoreCase("Gasoline") || gasolinetype.equalsIgnoreCase("Super")) {
				if(gs.getHasSuper()) {
					res.add(conv.convert(gs));
				}
			}
			else if (gasolinetype.equalsIgnoreCase("Premium Gasoline") || gasolinetype.equalsIgnoreCase("Super Plus")) {
				if(gs.getHasSuperPlus()) {
					res.add(conv.convert(gs));
				}
			}
			else if (gasolinetype.equalsIgnoreCase("LPG") || gasolinetype.equalsIgnoreCase("Gas")) { //hasGas
				if(gs.getHasGas()) {
					res.add(conv.convert(gs));
				}
			}
			else if (gasolinetype.equalsIgnoreCase("Methane")) { //hasMethane
				if(gs.getHasMethane()) {
					res.add(conv.convert(gs));
				}
			}
		}
		
		//it sorts res based on the price of the gasoline type
		if (gasolinetype.equalsIgnoreCase("Diesel")) {
			Collections.sort(res, Comparator.comparingDouble(GasStationDto::getDieselPrice));
		}
		else if (gasolinetype.equalsIgnoreCase("Gasoline") || gasolinetype.equalsIgnoreCase("Super")) {
			Collections.sort(res, Comparator.comparingDouble(GasStationDto::getSuperPrice));
		}
		else if (gasolinetype.equalsIgnoreCase("Premium Gasoline") || gasolinetype.equalsIgnoreCase("Super Plus")) {
			Collections.sort(res, Comparator.comparingDouble(GasStationDto::getSuperPlusPrice));
		}
		else if (gasolinetype.equalsIgnoreCase("LPG") || gasolinetype.equalsIgnoreCase("Gas")) {
			Collections.sort(res, Comparator.comparingDouble(GasStationDto::getGasPrice));
		}
		else if (gasolinetype.equalsIgnoreCase("Methane")) {
			Collections.sort(res, Comparator.comparingDouble(GasStationDto::getMethanePrice));
		}
		
		return res;
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		//latitude must be from 0 to 90; otherwise throw GPSDataException
		if(lat < 0 || lat > 90) {
			throw new GPSDataException("Error: impossible latitude coordinate");
		}
		
		//longitude must be from -180 to 180; otherwise throw GPSDataException
		if(lon < -180 || lon > 180) {
			throw new GPSDataException("Error: impossible longitude coordinate");
		}
		
		List<GasStationDto> closeGs = new ArrayList<GasStationDto>();
		
		//for each gas station in the database, if its distance is less than 1km to the provided point, it adds it to closeGs
		for (GasStationDto gs : this.getAllGasStations()) {
			if (distance(lat, lon, gs.getLat(), gs.getLon()) <= 1000) {
				closeGs.add(gs);
			}
		}
		
		return closeGs;
	}
	
	//computes the distance between two points each specified with latitude and longitude
	private double distance(double lat1, double lon1, double lat2, double lon2) {
		Integer r = 6371000; //radius of earth
		double p1 = lat1 * Math.PI/180;  //lat1 in radians
		double p2 = lat2 * Math.PI/180;  //lat 2 in radians
		double dp = (lat2 - lat1)*Math.PI/180;  //delta in radians
		double dl = (lon2 - lon1)*Math.PI/180;  //delta lon in radians
		
		double a = Math.sin(dp/2)*Math.sin(dp/2) + Math.cos(p1)*Math.cos(p2) * Math.sin(dl/2)*Math.sin(dl/2);
		
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return r * c; //in meters
	}

	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolinetype,
			String carsharing) throws InvalidGasTypeException, GPSDataException {
		if(gasolinetype != null) {
			if (gasolinetype.equalsIgnoreCase("")) {
				throw new InvalidGasTypeException("Error: gasoline type is the empty string");
			}
		}
		
		//latitude must be from 0 to 90; otherwise throw GPSDataException
		if(lat < 0 || lat > 90) {
			throw new GPSDataException("Error: impossible latitude coordinate");
		}
		
		//longitude must be from -180 to 180; otherwise throw GPSDataException
		if(lon < -180 || lon > 180) {
			throw new GPSDataException("Error: impossible longitude coordinate");
		}
		
		//closeGs is the list of all stations closer than 1km
		List<GasStationDto> closeGs = this.getGasStationsByProximity(lat, lon);
		
		//if carsharing parameter was specified, it filters closeGs to keep only the stations with that carsharing
		if (carsharing != null) {
			closeGs = closeGs.stream().filter(g -> g.getCarSharing().equalsIgnoreCase(carsharing)).collect(Collectors.toList());
		}
		
		//if gasolinetype was specified, it filters closeGs with only the stations with that gasoline type
		if(gasolinetype != null) {
			//res is the one that is going to be returned
			List<GasStationDto> res = new ArrayList<GasStationDto>();
			//in gasTypegs there are the stations which provide the right gasoline type
			List<GasStationDto> gasTypeGs = this.getGasStationsByGasolineType(gasolinetype);
			
			//for each station in closeGs, it adds it to res if that station is also in gasTypeGs
			for(GasStationDto closeG : closeGs) {
				if(this.gasStationIsInList(closeG, gasTypeGs)) {
					res.add(closeG);
				}
			}
			
			return res;
		}
		
		return closeGs;
	}
	
	//true if gasStation is in gsList, false otherwise
	private Boolean gasStationIsInList(GasStationDto gasStation, List<GasStationDto> gsList) {
		for (GasStationDto gs : gsList) {
			if (gasStation.getGasStationId() == gs.getGasStationId()) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
			throws InvalidGasTypeException {
		if (gasolinetype.equalsIgnoreCase("")) {
			throw new InvalidGasTypeException("Error: gas type is empty string");
		}
		
		//res is the list that is going to be returned
		List<GasStationDto> res = new ArrayList<GasStationDto>();
		
		//gasTypeList is the list of stations with the right gasoline type
		List<GasStationDto> gasTypeList = this.getGasStationsByGasolineType(gasolinetype);
		
		//carSharingList is the list of stations with the right carSharing
		List<GasStationDto> carSharingList = this.getGasStationByCarSharing(carsharing);
		
		//a station is added to res if it is present in both gasTypeList and carSharingList
		for(GasStationDto gs : gasTypeList) {
			if (this.gasStationIsInList(gs, carSharingList)) {
				res.add(gs);
			}
		}
		
		return res;
	}

	@Override
	public void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice,
			double gasPrice, double methanePrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		if (userId < 0) {
			throw new InvalidUserException("Error: negative value for user id");
		}
		
		GasStationDto gs = this.getGasStationById(gasStationId);
		if (gs == null) {
			return;
		}
		
		gs.setReportUser(userId);
		
		if(gs.getHasDiesel()) {
			if(dieselPrice < 0) {
				throw new PriceException("Error: price for diesel is negative");
			}
			gs.setDieselPrice(dieselPrice);
		}
		
		if(gs.getHasSuper()) {
			if(superPrice < 0) {
				throw new PriceException("Error: price for super is negative");
			}
			gs.setSuperPrice(superPrice);
		}
		
		if(gs.getHasSuperPlus()) {
			if(superPlusPrice < 0) {
				throw new PriceException("Error: price for super Plus is negative");
			}
			gs.setSuperPlusPrice(superPlusPrice);
		}
		
		if(gs.getHasGas()) {
			if(gasPrice < 0) {
				throw new PriceException("Error: price for Gas is negative");
			}
			gs.setGasPrice(gasPrice);
		}
		
		if(gs.getHasMethane()) {
			if(methanePrice < 0) {
				throw new PriceException("Error: price for Methane is negative");
			}
			gs.setMethanePrice(methanePrice);
		}
		
		try {
			this.saveGasStation(gs);
		} catch (PriceException e) {
			e.printStackTrace();
		} catch (GPSDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		List<GasStationDto> stations = new ArrayList<GasStationDto>();
		
		for (GasStationDto gs : this.getAllGasStations()) {
			if (gs.getCarSharing() != null) {
				if (gs.getCarSharing().equalsIgnoreCase(carSharing)) {
					stations.add(gs);
				}
			}
		}
			
		return stations;
	}
	
}
