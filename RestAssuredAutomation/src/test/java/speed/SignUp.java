package speed;

import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.SignupResponse;

public class SignUp {
	
	@SuppressWarnings("unchecked")
	@Test
	public void SignUp_With_AlreadyRegisteredEmail() {
		
		baseURI = "https://appapi.tryspeed.dev/signup";
	//	basePath = "/signup";
		
		JSONObject request = new JSONObject();
		request.put("country", "India");
		request.put("first_name", "Alexander");
		request.put("last_name", "Supertramp");
		request.put("password", "Admin@123");
		request.put("email", "palash@tryspeed.com");
		
		System.out.println(request.toJSONString());

		Response httpRequest = 
				((Response) given()
			.header("Content-Type", "application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post(baseURI))
			.andReturn();
		
		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);
		
		
		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		
	}

}
