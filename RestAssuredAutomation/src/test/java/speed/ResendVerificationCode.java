package speed;

import static io.restassured.RestAssured.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.SignupResponse;

public class ResendVerificationCode {

	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void resendVerificationCode_ValidRequest() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("email", "palash+114@tryspeed.com");

		System.out.println(request.toJSONString());

		given().baseUri(baseURI).header("Content-Type", "application/json").contentType(ContentType.JSON)
				.accept(ContentType.JSON).body(request.toJSONString()).when().post("/resend-verification-code").then()
				.statusCode(200).log().status();
	}

	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void resendVerificationCode_EmailAlreadyVerify() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("email", "palash@tryspeed.com");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/resend-verification-code")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void resendVerificationCode_UserDoesNotExist() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("email", "palashh@tryspeed.com");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/resend-verification-code")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void resendVerificationCode_InvalidRequest() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("email", "");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/resend-verification-code")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

}
