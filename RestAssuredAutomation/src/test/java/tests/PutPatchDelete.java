package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class PutPatchDelete {
	
//	@Test
	public void Put_Method() {
		
		baseURI = "https://reqres.in/api";
		basePath="/users/2";
		
		JSONObject request = new JSONObject();
		request.put("name", "Palash");
		request.put("Job", "QA Engineer");
		
		System.out.println(request.toJSONString());
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		put(basePath).
		then().
		statusCode(200).
		log().all(); 
	}
	
	@Test
	public void Patch_Method() {
		
		baseURI = "https://reqres.in/api";
		
		JSONObject request = new JSONObject();
		request.put("name", "Palash");
		request.put("Job", "QA Engineer");
		
		System.out.println(request.toJSONString());
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("/users/2").
		then().
		statusCode(200).
		log().all(); 
	}

	@Test
	public void Delete_Method() {
		
		baseURI = "https://reqres.in/api";

		when().
		delete("/users/2").
		then().
		statusCode(204).
		log().all(); 
	}
}
