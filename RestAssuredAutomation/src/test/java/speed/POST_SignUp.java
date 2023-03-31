package speed;

import static io.restassured.RestAssured.*;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import constants.EndPoints;
import constants.Environments;
import constants.Errors;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.SignupRequest;
import pojo.SignupResponse;
import utils.ExcelUtils;

public class POST_SignUp {
	
//	SignUp with already registered email address
//	SignUp with empty data field
//	SignUp with invalid email format
//	SignUp with exceed password
//	SignUp with valid data 

	Environments env = new Environments();
	EndPoints endPoints = new EndPoints();
	Errors errors = new Errors();
	
	String excelPath = "./src/test/resources/UMSData.xlsx";
	String sheetName = "Signup";
	
	// Now we are going to access the excel sheet and get the celldata by passing the excelpath and sheetname to ExcelUtils.java
	ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
	
	@SuppressWarnings({ "unchecked" })
	@Test (enabled = false)
	public void signup_with_AlreadyRegisteredEmail() {
		
		System.out.println("=======signup_with_AlreadyRegisteredEmail=======");

		JSONObject request = new JSONObject();
		request.put("first_name", excel.getCellData(1, 0));
		request.put("last_name", excel.getCellData(1, 1));
		request.put("email", excel.getCellData(1, 2));
		request.put("country", excel.getCellData(1, 3));
		request.put("password", excel.getCellData(1, 4));
		
		System.out.println("Request Body: " + request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.signup)).andReturn();
		
		httpRequest.getStatusLine();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		
		String statusLine = httpRequest.getStatusLine();
		System.out.println("Status line: " + statusLine);
		
		Assert.assertEquals(message, errors.signupError_AlreadyRegisteredEmail);
		
	}

	@SuppressWarnings("unchecked")
	@Test (enabled = false)
	public void signup_with_EmptyDataField() {
		
		System.out.println("=======signup_with_EmptyDataField=======");

		JSONObject request = new JSONObject();
		request.put("country", "");
		request.put("first_name", "");
		request.put("last_name", "");
		request.put("password", "");
		request.put("email", "");

		System.out.println("Request Body: " + request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.signup)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		response.getErrors().forEach(data -> {
			System.out.println("Error message: " + data.getMessage());
		});
		
		String statusLine = httpRequest.getStatusLine();
		System.out.println("Status line: " + statusLine);
		
//		String message = response.getErrors().get(0).getMessage();
//		System.out.println("Error message: " + message);
	}

	@SuppressWarnings({ "unchecked" })
	@Test (enabled = false)
	public void signup_with_InvalidEmailFormat() {

		System.out.println("=======signup_with_InvalidEmailFormat========");

		JSONObject request = new JSONObject();
		request.put("first_name", excel.getCellData(3, 0));
		request.put("last_name", excel.getCellData(3, 1));
		request.put("email", excel.getCellData(3, 2));
		request.put("country", excel.getCellData(3, 3));
		request.put("password", excel.getCellData(3, 4));

		System.out.println("Request Body: " + request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.signup)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);

		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		
		Assert.assertEquals(message, errors.signupError_InvalidEmailFormat);
		
		String statusLine = httpRequest.getStatusLine();
		System.out.println("Status line: " + statusLine);
	}

	@SuppressWarnings("unchecked")
	@Test (enabled = false)
	public void signup_with_ExceedPassword() {

		System.out.println("========signup_with_ExceedPassword========");

		JSONObject request = new JSONObject();
		request.put("first_name", excel.getCellData(4, 0));
		request.put("last_name", excel.getCellData(4, 1));
		request.put("email", excel.getCellData(4, 2));
		request.put("country", excel.getCellData(4, 3));
		request.put("password", excel.getCellData(4, 4));

		System.out.println("Request Body: " + request.toJSONString());

		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post(endPoints.signup)).andReturn();

		SignupResponse response = httpRequest.getBody().as(SignupResponse.class);
		
		String message = response.getErrors().get(0).getMessage();
		System.out.println("Error message: " + message);
		
		Assert.assertEquals(message, errors.signupError_ExcceExceedPassword);
		
		String statusLine = httpRequest.getStatusLine();
		System.out.println("Status line: " + statusLine);
	}

	@SuppressWarnings("unchecked")
	@Test (enabled = true)
	public void signup_with_ValidData() {

		System.out.println("=======signup_with_ValidData=======");
		
		JSONObject request = new JSONObject();
		request.put("first_name", excel.getCellData(5, 0));
		request.put("last_name", excel.getCellData(5, 1));
		request.put("email", excel.getCellData(5, 2) );
		request.put("country", excel.getCellData(5, 3));
		request.put("password", excel.getCellData(5, 4));

		System.out.println("Request Body: " + request.toJSONString());
		
		
		Response httpRequest = ((Response) given().baseUri(env.dev).header("Content-Type", "application/json").contentType(ContentType.JSON)
				.accept(ContentType.JSON).body(request.toJSONString()).when().post(endPoints.signup)).andReturn();
		
		Assert.assertEquals(httpRequest.getStatusCode(), 200);
		
	}
	
}
