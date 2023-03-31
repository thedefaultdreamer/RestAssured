package tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class GetAndPostExamples {

	@Test (enabled = false)
	public void Get_Method() {

		baseURI = "https://reqres.in/api";

		given()
			.get("/users?page=2")
			.then().statusCode(200)
			.body("data[4].first_name", equalTo("George"))
			.body("data.first_name", hasItems("George", "Rachel"));

	}

	@Test
	public void Post_Method() {

		baseURI = "https://reqres.in/api";

		JSONObject request = new JSONObject();
		request.put("name", "Palash");
		request.put("Job", "QA Engineer");

		System.out.println(request.toJSONString());

		given()
			.header("Content-Type", "application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.log()
			.all();
	}

}
