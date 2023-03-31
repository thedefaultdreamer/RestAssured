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

import constants.EndPoints;
import constants.Environments;
import constants.Errors;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.SignupResponse;
import utils.ExcelUtils;

public class POST_VerifySignUpEmail {

//	verify Email with ValidLink
//	verify Email with InvalidLink
//	verify Email with EmptyLink
//	verify Email with ExpiredLink
//	verify Email with AlreadyUsedLink

	private final String url = "jdbc:mysql://dev-cluster-instance-0.cuvu8wdg9yht.us-east-1.rds.amazonaws.com:4307/speed?useUnicode=yes&characterEncoding=UTF-8&charSet=UTF-8";
	private final String user = "speed_developer";
	private final String password = "vT28JDLHxzMDcDlsoGe2";
	public static String verification_link = null;

	Environments env = new Environments();
	EndPoints endPoints = new EndPoints();
	Errors errors = new Errors();

	String excelPath = "./src/test/resources/UMSData.xlsx";
	String sheetName = "Signup";

	// Now we are going to access the excel sheet and get the cell data by passing the excel path and sheet name to ExcelUtils.java
	ExcelUtils excel = new ExcelUtils(excelPath, sheetName);

	@Test
	// Method to initialize connection to the database and execute query
	public void connectDataBase() throws SQLException {
		
		Connection conn = DriverManager.getConnection(url, user, password);
		PreparedStatement stm  = conn.prepareStatement("SELECT id FROM tbl_user_link Where new_email = '"+excel.getCellData(5, 2)+"'");
		ResultSet result =  stm.executeQuery();
		try {
			while(result.next()) {
				verification_link = result.getString("id");
				System.out.println("verification_link: " + verification_link);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_ValidLink() {

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link);

		System.out.println(request.toJSONString());

		given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.verifyEmail)
				.then()
				.statusCode(200)
				.log()
				.status();
		}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_InvalidLink() {

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link+"h");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.verifyEmail)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_EmptyLink() {

		JSONObject request = new JSONObject();
		request.put("verification_link", "");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.verifyEmail)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_ExpiredLink() {

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link);

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.verifyEmail)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void verifyEmail_with_AlreadyUsedLink() {

		JSONObject request = new JSONObject();
		request.put("verification_link", verification_link);

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.verifyEmail)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		}
	}


