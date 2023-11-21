package it.dedagroup.venditabiglietti.principal.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public interface GeneralCallService {

	private ObjectMapper getMapper() {
		ObjectMapper mapper=new ObjectMapper();
		JavaTimeModule module=new JavaTimeModule();
		module.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
		module.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
		module.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
		module.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
		mapper.registerModule(module);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	
	private<DTORequest> DTORequest convertFromJson(String json,Class<DTORequest> value) {
		try {
			return getMapper().readValue(json,value);
		}catch (JsonProcessingException e) {
			return null;
		}
	}
	
	default<DTORequest> String convertToJson(DTORequest e) {
		try {
			return getMapper().writeValueAsString(e);
		} catch (JsonProcessingException e1) {
			return null;
		}
	}
	
	private<DTORequest> HttpEntity<DTORequest> createEntity(String auth,DTORequest e){
		HttpHeaders headers=new HttpHeaders();
		if(auth!=null)headers.add("Authorization", "Bearer "+auth);
		if(e==null) {
			return new HttpEntity<>(headers);
		}else {
			headers.setContentType(MediaType.APPLICATION_JSON);
			return new HttpEntity<>(e,headers);
		}
	}
	
	private <DTORequest,DTOResponse> DTOResponse call(HttpMethod method,String path,String auth,DTORequest e,Class<DTOResponse> classe) {
		HttpEntity<DTORequest> requestBody=createEntity(auth, e);
		RestTemplate template=new RestTemplate();
		try {
			ResponseEntity<DTOResponse> response=template.exchange(path,method,requestBody,classe);
			if(response.getStatusCode().is2xxSuccessful()) {
				return response.getBody();
			}else {
				throw new ResponseStatusException(HttpStatus.valueOf(response.getStatusCode().value()));
			}
		}catch (Exception ex) {
			if(ex instanceof ResponseStatusException) {
				throw ex;
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}
	}
		
	default <DTORequest,DTOResponse> DTOResponse callGet(String path,String auth,DTORequest e,Class<DTOResponse> classe) {
		return call(HttpMethod.GET, path, auth, e, classe);
	}
	
	default <DTORequest,DTOResponse> DTOResponse callPost(String path,String auth,DTORequest e,Class<DTOResponse> classe) {
		return call(HttpMethod.POST, path, auth, e, classe);
	}
	
	default <DTORequest,DTOResponse> DTOResponse callPut(String path,String auth,DTORequest e,Class<DTOResponse> classe) {
		return call(HttpMethod.PUT, path, auth, e, classe);
	}

	default <DTORequest, DTOResponse> List<DTOResponse> callGetForList(String path, String auth, DTORequest e, Class<DTOResponse[]> classe) {
		return List.of(callGet(path, auth, e, classe));
	}
	default <DTORequest, DTOResponse> List<DTOResponse> callPostForList(String path, String auth, DTORequest e, Class<DTOResponse[]> classe) {
		return List.of(callPost(path, auth, e, classe));
	}
	default <DTORequest, DTOResponse> List<DTOResponse> callPutForList(String path, String auth, DTORequest e, Class<DTOResponse[]> classe) {
		return List.of(callPut(path, auth, e, classe));
	}
}
