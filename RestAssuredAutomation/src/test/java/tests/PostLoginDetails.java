package tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

public class PostLoginDetails {

	RequestSpecification httpRequest;

	@Test
	public void SpeedLogin(ITestContext context) {

		// Creating Pojo class object
		LoginRequest LoginDetails = new LoginRequest("palash@tryspeed.com", "Admin@123");

		baseURI = "https://appapi.tryspeed.dev/login";

		System.out.println("============================================");
		System.out.println(LoginDetails.toString());

		Response httpRequest = 
				(Response) given()
					.baseUri(baseURI)
					.header("Content-Type", "application/json")
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(LoginDetails)
				.when()
					.post()
					.andReturn();

		LoginResponse response = httpRequest.getBody().as(LoginResponse.class);

//		System.out.println("============================================");
		String accessToken = response.getAccess_token();
		System.out.println("Access Token: " + accessToken);
//		System.out.println("============================================");

		String idToken = response.getId_token();
		System.out.println("Id Token: " + idToken);
//		System.out.println("============================================");

		String refreshToken = response.getRefresh_token();
		System.out.println("Refresh Token: " + refreshToken);
//		System.out.println("============================================");

		String session = response.getSession();
		System.out.println("Session: " + session);
		System.out.println("============================================");

		context.setAttribute("AccessToken", accessToken);
		context.setAttribute("IdToken", idToken);
		context.setAttribute("Session", session);

	}
}
