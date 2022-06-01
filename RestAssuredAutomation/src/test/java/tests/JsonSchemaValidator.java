package tests;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

public class JsonSchemaValidator {
	
	@Test
	public void Get_Method() {
		
		baseURI="https://reqres.in/api";
		basePath="/users?page=2";
		given().
		    get(basePath).
		then().
		    assertThat().body(matchesJsonSchemaInClasspath("schema.json"));
			
	}

}
