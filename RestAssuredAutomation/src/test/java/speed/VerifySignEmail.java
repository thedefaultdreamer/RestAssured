package speed;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.SignupResponse;

public class VerifySignEmail {
	
 private final String url = "jdbc:mysql://dev-cluster-instance-0.cuvu8wdg9yht.us-east-1.rds.amazonaws.com:4307/speed?useUnicode=yes&characterEncoding=UTF-8&charSet=UTF-8";
 private final String user = "speed_developer";
 private final String password = "vT28JDLHxzMDcDlsoGe2";
 public static String verification_link = null;
	
	@Test
	// Method to initialize connection to the database and execute query
	public void connectDataBase() throws SQLException {
	
		Connection conn = DriverManager.getConnection(url, user, password);
		PreparedStatement stm  = conn.prepareStatement("SELECT id FROM tbl_user_link Where new_email = 'palash+119@tryspeed.com'");
		ResultSet result =  stm.executeQuery();
		while(result.next()) {
			verification_link = result.getString("id");
			System.out.println("verification_link: " + verification_link);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_ValidLink() {
		
		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link);

		System.out.println(request.toJSONString());

		given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/verify-email")
				.then()
				.statusCode(200)
				.log()
				.status();
		}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_Invalidlink() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link+"h");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/verify-email")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_EmptyLink() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("verification_link", "");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/verify-email")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_ExpiredLink() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link);

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/verify-email")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	
	@SuppressWarnings("unchecked")
	@Test
	public void verifyEmail_with_AlreadyUsedLink() {

		baseURI = "https://appapi.tryspeed.dev";

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link);

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/verify-email")).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	}


