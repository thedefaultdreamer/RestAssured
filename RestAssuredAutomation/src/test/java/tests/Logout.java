package tests;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import org.testng.ITestContext;

public class Logout {

	@Test
	public void SpeedLogout(ITestContext context) {

		System.out.println("Logout running");
		baseURI = "https://appapi.tryspeed.dev/logout";

		String accecc_Token = (String) context.getAttribute("AccessToken");
		String id_Token = (String) context.getAttribute("IdToken");
		String session_id = (String) context.getAttribute("Session");

		JSONObject request = new JSONObject();
		request.put("session", session_id);

		given()
			.baseUri(baseURI)
			.headers("Authorization", id_Token)
			.headers("Access", accecc_Token)
			.headers("Content-Type", "application/json")
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post()
		.then()
		.statusCode(200);

	}
}
