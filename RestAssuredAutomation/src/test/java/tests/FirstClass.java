package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;

public class FirstClass {
	
	@Test
	public void Get_Method1() {
		
		Response response = get("https://reqres.in/api/users?page=2");
		
		System.out.println("=================First test====================");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.getTime());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.getHeader("Content-type"));
	}
	
	@Test 
	public void Get_Method2() {
		
		System.out.println("=================Second test====================");
		
		baseURI="https://reqres.in/api";
		
		given().
		baseUri(baseURI).
		get("/users?page=2").
		then().
		statusCode(200).
		body("data[1].id", equalTo(8)).
		log().all();
			
	}
}
