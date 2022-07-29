package speed;

import static io.restassured.RestAssured.*;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import constants.EndPoints;
import constants.Environments;
import constants.Errors;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.SignupResponse;

public class SignUp {

	Environments Env = new Environments();
	EndPoints endPoints = new EndPoints();
	Errors errors = new Errors();
	
	@SuppressWarnings("unchecked")
	@Test
	public void signup_with_AlreadyRegisteredEmail() {

		JSONObject request = new JSONObject();
		request.put("country", "India");
		request.put("first_name", "Alexander");
		request.put("last_name", "Supertramp");
		request.put("password", "Admin@123");
		request.put("email", "palash@tryspeed.com");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(Env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.signup)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		
		Assert.assertEquals(message, errors.signup_AlreadyRegisteredEmail);
	}

	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void signup_with_EmptyDataField() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("country", "");
		request.put("first_name", "");
		request.put("last_name", "");
		request.put("password", "");
		request.put("email", "");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/signup")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		response.getErrors().forEach(data -> {
			System.out.println("Error message: " + data.getMessage());
		});
//		String message = response.getErrors().get(0).getMessage();
//		System.out.println("Error message: " + message);
	}

	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void signup_with_InvalidEmailFormat() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("country", "India");
		request.put("first_name", "Palash");
		request.put("last_name", "Patidar");
		request.put("password", "Admin@123");
		request.put("email", "palash@tryspeed.com.");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/signup")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

	@SuppressWarnings("unchecked")
	@Test (enabled = false)
	public void signup_with_ExceedPassword() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("country", "India");
		request.put("first_name", "Palash");
		request.put("last_name", "Patidar");
		request.put("password", "Open@123456231235645645644564564564565645645645645654212323122313");
		request.put("email", "palash@tryspeed.com.");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/signup")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

	@SuppressWarnings("unchecked")
	@Test (enabled = false)
	public void signup_with_ValidData() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("country", "India");
		request.put("first_name", "Palash");
		request.put("last_name", "Patidar");
		request.put("password", "Open@123");
		request.put("email", "palash+122@tryspeed.com");

		System.out.println(request.toJSONString());

		given().baseUri(baseURI).header("Content-Type", "application/json").contentType(ContentType.JSON)
				.accept(ContentType.JSON).body(request.toJSONString()).when().post("/signup").then().statusCode(200)
				.log().status();
	}
	
}
