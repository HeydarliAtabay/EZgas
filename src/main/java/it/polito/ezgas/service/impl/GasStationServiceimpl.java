package it.polito.ezgas.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
	private GasStationRepository repo;
	
	@Autowired
	private UserServiceimpl userService;
	
	public GasStationServiceimpl(GasStationRepository gsrepo) {
		this.repo = gsrepo;
	}

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
		//if the prices are null, set a default value for the price
		if (gasStationDto.getHasDiesel()) {
			if( gasStationDto.getDieselPrice() == null) {
				gasStationDto.setDieselPrice(1.2);
			}
		}
		
		if (gasStationDto.getHasSuper()) {
			if( gasStationDto.getSuperPrice() == null) {
				gasStationDto.setSuperPrice(1.2);
			}
		}
		
		if (gasStationDto.getHasSuperPlus()) {
			if(gasStationDto.getSuperPlusPrice() == null) {
				gasStationDto.setSuperPlusPrice(1.4);
			}
		}
		
		if (gasStationDto.getHasGas()) {
			if(gasStationDto.getGasPrice() == null) {
				gasStationDto.setGasPrice(1.5);
			}
		}
		
		if (gasStationDto.getHasMethane()) {
			if(gasStationDto.getMethanePrice() == null) {
				gasStationDto.setMethanePrice(1.1);
			}
		}
		
		if (gasStationDto.getHasPremiumDiesel()) {
			if(gasStationDto.getPremiumDieselPrice() == null) {
				gasStationDto.setPremiumDieselPrice(1.1);
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
		
		if (gasStationDto.getHasPremiumDiesel()) {
			if(gasStationDto.getPremiumDieselPrice() < 0) {
				throw new PriceException("Error: premium diesel price negative");
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
		
		if (gasStationDto.getCarSharing() != null && gasStationDto.getCarSharing().equals("null")) {
			gasStationDto.setCarSharing(null);
		}
		
		GasStation savedGs = repo.save(conv.convertFromDto(gasStationDto));
		
		return conv.convert(savedGs);
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
			return false;
		}
		
		repo.delete(gasStationId);
		return true;
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		if (gasolinetype == null || gasolinetype.equalsIgnoreCase("")) {
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
			else if (gasolinetype.equalsIgnoreCase("Premium Diesel")) { 
				if(gs.getHasPremiumDiesel()) {
					res.add(conv.convert(gs));
				}
			}
			else { //if gasolinetype provided does not match any known gasollinetype it throws the exception 
				throw new InvalidGasTypeException("Error: gasolinetype provided does not match any known gasollinetype");
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
		else if (gasolinetype.equalsIgnoreCase("Premium Diesel")) {
			Collections.sort(res, Comparator.comparingDouble(GasStationDto::getPremiumDieselPrice));
		}
		else { //if gasolinetype provided does not match any known gasollinetype it throws the exception 
			throw new InvalidGasTypeException("Error: gasolinetype provided does not match any known gasollinetype");
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
	
	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon, int radius) throws GPSDataException {
		//latitude must be from 0 to 90; otherwise throw GPSDataException
		if(lat < 0 || lat > 90) {
			throw new GPSDataException("Error: impossible latitude coordinate");
		}
		
		//longitude must be from -180 to 180; otherwise throw GPSDataException
		if(lon < -180 || lon > 180) {
			throw new GPSDataException("Error: impossible longitude coordinate");
		}
		
		if(radius <= 0) {
			radius = 1;
		}
		
		List<GasStationDto> closeGs = new ArrayList<GasStationDto>();
		
		//for each gas station in the database, if its distance is less than 1km to the provided point, it adds it to closeGs
		for (GasStationDto gs : this.getAllGasStations()) {
			if (distance(lat, lon, gs.getLat(), gs.getLon()) <= radius*1000) {
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
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, int radius, String gasolinetype,
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
		List<GasStationDto> closeGs = this.getGasStationsByProximity(lat, lon, radius);
		
		//if carsharing parameter was specified, it filters closeGs to keep only the stations with that carsharing
		if (!carsharing.equalsIgnoreCase("null")) {
			closeGs = closeGs.stream().filter(g -> g.getCarSharing().equalsIgnoreCase(carsharing)).collect(Collectors.toList());
		}
		
		//if gasolinetype was specified, it filters closeGs with only the stations with that gasoline type
		if(!gasolinetype.equalsIgnoreCase("null")) {
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
			if (gasStation.getGasStationId().intValue() == gs.getGasStationId().intValue()) {
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
		List<GasStationDto> gasTypeList;
		
		//if gasolineType is not specified, gasTypeList is the list of all gas stations
		if (gasolinetype.equalsIgnoreCase("null")) {
			gasTypeList = this.getAllGasStations();
		}
		else {
			gasTypeList = this.getGasStationsByGasolineType(gasolinetype);
		}
		
		//carSharingList is the list of stations with the right carSharing
		List<GasStationDto> carSharingList;
		
		//if carsharing is not specified, carSharingList is the list of all gas stations
		if (carsharing.equalsIgnoreCase("null")) {
			carSharingList = this.getAllGasStations();
		}
		else {
			carSharingList = this.getGasStationByCarSharing(carsharing);
		}
		
		//a station is added to res if it is present in both gasTypeList and carSharingList
		for(GasStationDto gs : gasTypeList) {
			if (this.gasStationIsInList(gs, carSharingList)) {
				res.add(gs);
			}
		}
		
		return res;
	}

	@Override
	public void setReport(Integer gasStationId, Double dieselPrice, Double superPrice, Double superPlusPrice,
			Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		if (userId < 0) {
			throw new InvalidUserException("Error: negative value for user id");
		}
		
		GasStationDto gs = this.getGasStationById(gasStationId);
		if (gs == null) {
			return;
		}
		
		
		Integer userRep;
		
		if(gs.getReportUser() != null && gs.getReportUser() >= 0) { //if there is already a price report
			Integer oldUserRep = this.userService.getUserById(gs.getReportUser()).getReputation();
			Integer newUserRep = this.userService.getUserById(userId).getReputation();
			if(newUserRep >= oldUserRep) {  //if the new user has acceptable reputation
				gs.setReportUser(userId);    //set new report user
				gs.setUserDto(this.userService.getUserById(userId));    //set new userDto
				userRep = newUserRep;
			}
			else if (this.isReportRecent(gs.getReportTimestamp())){  //if (today - p.time_tag) <= 4 days  and the new reputation is bad don't set the new report
				return;
			}
			else { //if the new user has a worse reputation but the setreport is old, change it anyway
				userRep = oldUserRep;
			}
		}
		else {  //if there was no price report in the gas station
			
			userRep = this.userService.getUserById(userId).getReputation();
			gs.setReportUser(userId);    //set new report user
			gs.setUserDto(this.userService.getUserById(userId));    //set new userDto
		}
		
		String timestamp = new SimpleDateFormat("MM-dd-YYYY").format(new Date());
		gs.setReportDependability(this.calculateDependability(userRep, timestamp));
		gs.setReportTimestamp(timestamp);
		
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
		
		if(gs.getHasPremiumDiesel()) {
			if(premiumDieselPrice < 0) {
				throw new PriceException("Error: price for Premium diesel is negative");
			}
			gs.setPremiumDieselPrice(premiumDieselPrice);
		}
		
		try {
			this.saveGasStation(gs);
		} catch (PriceException e) {
			e.printStackTrace();
		} catch (GPSDataException e) {
			e.printStackTrace();
		}
	}
	
	//return true if (today - p.time_tag) <= 4 days 
	private boolean isReportRecent(String reportTimeStamp) {
		SimpleDateFormat dateF = new SimpleDateFormat("MM-dd-YYYY");
		Date today = new Date();
		try {
			Date ts = dateF.parse(reportTimeStamp);
			
			long diff = today.getTime() - ts.getTime();
			long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			
			if(diffDays <= 4) {
				return true;
			}
			return false;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private double calculateDependability(Integer userTrust, String reportTimestamp){
		double obs = 0;
		
		SimpleDateFormat dateF = new SimpleDateFormat("MM-dd-YYYY");
		Date parsedDate;
		try {
			parsedDate = dateF.parse(reportTimestamp);
			long reportTSMillis = parsedDate.getTime();
			long nowMillis = System.currentTimeMillis();
			
			if (nowMillis - reportTSMillis > 7*24*3600*1000) {
				obs = 0;
			}
			else {
				obs = 1 - ( (nowMillis - reportTSMillis)/(1000*3600*24) )/7;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 50 * (userTrust + 5)/10 + 50 * obs;
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
