package speed;

import static io.restassured.RestAssured.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import constants.EndPoints;
import constants.Environments;
import constants.Errors;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.SignupResponse;
import utils.ExcelUtils;

public class POST_ResendVerificationCode {
	
//	Verify resendVerificationCode Valid Request
//	Verify resendVerificationCode Email Already Verify
//	Verify resendVerificationCode User Does Not Exist
//	Verify resendVerificationCode Invalid Request
	
	Environments env = new Environments();
	EndPoints endPoints = new EndPoints();
	Errors errors = new Errors();
	
	String excelPath = "./src/test/resources/UMSData.xlsx";
	String sheetName = "ResendVerificationCode";
	
	// Now we are going to access the excel sheet and get the celldata by passing the excelpath and sheetname to ExcelUtils.java
	ExcelUtils excel = new ExcelUtils(excelPath, sheetName);

	@SuppressWarnings("unchecked")
	@Test(enabled = true)
	public void resendVerificationCode_ValidRequest() {

		JSONObject request = new JSONObject();
		request.put("email", excel.getCellData(1, 0));

		System.out.println(request.toJSONString());

		given().baseUri(env.dev).header("Content-Type", "application/json").contentType(ContentType.JSON)
				.accept(ContentType.JSON).body(request.toJSONString()).when().post(endPoints.resendVerificationCode).then()
				.statusCode(200).log().status();
	}

	@SuppressWarnings("unchecked")
	@Test(enabled = true)
	public void resendVerificationCode_EmailAlreadyVerify() {

		JSONObject request = new JSONObject();
		request.put("email", excel.getCellData(2, 0));

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.resendVerificationCode)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

	@SuppressWarnings("unchecked")
	@Test(enabled = true)
	public void resendVerificationCode_UserDoesNotExist() {

		JSONObject request = new JSONObject();
		request.put("email", excel.getCellData(3, 0));

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.resendVerificationCode)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

	@SuppressWarnings("unchecked")
	@Test (enabled = true)
	public void resendVerificationCode_InvalidRequest() {

		JSONObject request = new JSONObject();
		request.put("email", "");

		System.out.println(request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.resendVerificationCode)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
	}

}
