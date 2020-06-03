package it.polito.ezgas.controllertests;

import static org.junit.Assert.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.utils.Constants;

public class TestController {
	@Test
	//test /gasstation/getGasStation/{id}
	public void getGasStationByIdApiTest() throws ClientProtocolException, IOException {
		Integer id = 64;
		
		HttpUriRequest req = new HttpGet("http://localhost:8080/gasstation/getGasStation/" + id);
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		String jsonFromResponse = EntityUtils.toString(res.getEntity());
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GasStationDto gs = mapper.readValue(jsonFromResponse, GasStationDto.class);
		
		assertTrue(gs.getGasStationId() == id);
	}
	
	@Test
	// /gasstation/getallgasstations
	public void getAllGasStationsApiTest() throws ClientProtocolException, IOException {
		HttpUriRequest req = new HttpGet("http://localhost:8080/gasstation" + Constants.GET_ALL_GASSTATIONS);
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		String jsonFromResponse = EntityUtils.toString(res.getEntity());
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
		
		assertTrue(gsArray.length == 8);
	}
	
	@Test
	public void saveGasStationApiTest() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost req = new HttpPost("http://localhost:8080/gasstation" + Constants.SAVE_GASSTATION);
		
		String json = "{ \"gasStationId\": 150, \"gasStationName\": \"testGSname\" }";
		StringEntity entity = new StringEntity(json);
		req.setEntity(entity);
		req.setHeader("Accept", "application/json");
		req.setHeader("Content-type", "application/json");
		
		CloseableHttpResponse res = client.execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
		client.close();
	}
	
	@Test
	public void deleteGasStationApiTest() throws ClientProtocolException, IOException {
		HttpUriRequest req = new HttpDelete("http://localhost:8080/gasstation/deleteGasStation/35");
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
	}
	
	@Test
	public void getGasStationsByGasolineApiTest() throws ClientProtocolException, IOException{
		String gasolineType = "Diesel";
		
		HttpUriRequest req = new HttpGet("http://localhost:8080/gasstation/searchGasStationByGasolineType/" + gasolineType);
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		String jsonFromResponse = EntityUtils.toString(res.getEntity());
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
		
		assertTrue(gsArray.length == 2);
	}
	
	@Test
	public void getGasStationsByProximityApiTest() throws ClientProtocolException, IOException {
		Double lat = 43.7938506	;
		Double lon = 7.6175733;
		
		HttpUriRequest req = new HttpGet("http://localhost:8080/gasstation/searchGasStationByProximity/" + lat + "/" + lon + "/");
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		String jsonFromResponse = EntityUtils.toString(res.getEntity());
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
		
		assertTrue(gsArray.length == 1);
	}
	
	@Test
	public void getGasStationsWithCoordinates() throws ClientProtocolException, IOException {
		Double lat = 43.7938506	;
		Double lon = 7.6175733;
		String gasolineType = "Diesel";
		String carSharing = "Enjoy";
		
		HttpUriRequest req = new HttpGet("http://localhost:8080/gasstation/getGasStationsWithCoordinates/" + lat + "/" + lon + "/" + gasolineType + "/" + carSharing + "/");
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		String jsonFromResponse = EntityUtils.toString(res.getEntity());
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
		
		assertTrue(gsArray.length == 1);
	}
	
	@Test
	public void setGasStationReportApiTest() throws ClientProtocolException, IOException {
		Integer id = 64;
		Double dieselPrice = 1.2;
		Double superPrice = 1.3;
		Double superPlusPrice = 1.4;
		Double gasPrice = 1.1;
		Double methanePrice = 1.5;
		Integer userId = 16;
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost req = new HttpPost("http://localhost:8080/gasstation/setGasStationReport/"
		+ id + "/"
		+ dieselPrice + "/"
		+ superPrice + "/"
		+ superPlusPrice + "/"
		+ gasPrice + "/"
		+ methanePrice + "/"
		+ userId + "/"
		);
		
		
		CloseableHttpResponse res = client.execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
		client.close();
	}
	
	@Test
	public void getUserByIdApiTest() throws ClientProtocolException, IOException {
		Integer id = 25;
		
		HttpUriRequest req = new HttpGet("http://localhost:8080/user/getUser/" + id);
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		String jsonFromResponse = EntityUtils.toString(res.getEntity());
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		UserDto u = mapper.readValue(jsonFromResponse, UserDto.class);
		
		assertTrue(u.getUserId() == id);
	}
	
	@Test
	public void getAllUsersApiTest() throws ClientProtocolException, IOException {
		HttpUriRequest req = new HttpGet("http://localhost:8080/user/getAllUsers");
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		String jsonFromResponse = EntityUtils.toString(res.getEntity());
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		UserDto[] uArray = mapper.readValue(jsonFromResponse, UserDto[].class);
		
		assertTrue(uArray.length == 3);
	}
	
	@Test
	public void saveUserApiTest() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost req = new HttpPost("http://localhost:8080/user" + Constants.SAVE_USER);
		
		String json = "{ \"userName\": \"testUserName\" }";
		StringEntity entity = new StringEntity(json);
		req.setEntity(entity);
		req.setHeader("Accept", "application/json");
		req.setHeader("Content-type", "application/json");
		
		CloseableHttpResponse res = client.execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
		client.close();
	}
	
	@Test
	public void deleteUserApiTest() throws ClientProtocolException, IOException {
		Integer id = 46;
		
		HttpUriRequest req = new HttpDelete("http://localhost:8080/user/deleteUser/" + id);
		HttpResponse res = HttpClientBuilder.create().build().execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
	}
	
	@Test
	public void increaseUserReputationApiTest() throws ClientProtocolException, IOException {
		Integer id = 26;
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost req = new HttpPost("http://localhost:8080/user/increaseUserReputation/" + id);
		
		CloseableHttpResponse res = client.execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
		client.close();
	}
	
	@Test
	public void decreaseUserReputationApiTest() throws ClientProtocolException, IOException {
		Integer id = 26;
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost req = new HttpPost("http://localhost:8080/user/decreaseUserReputation/" + id);
		
		CloseableHttpResponse res = client.execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
		client.close();
	}
	
	@Test
	public void loginApiTest() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost req = new HttpPost("http://localhost:8080/user" + Constants.LOGIN);
		
		String json = "{ \"user\": \"admin\", \"pw\": \"admin\" }";
		StringEntity entity = new StringEntity(json);
		req.setEntity(entity);
		req.setHeader("Accept", "application/json");
		req.setHeader("Content-type", "application/json");
		
		CloseableHttpResponse res = client.execute(req);
		
		assertTrue(res.getStatusLine().getStatusCode() == 200);
		client.close();
	}
}
