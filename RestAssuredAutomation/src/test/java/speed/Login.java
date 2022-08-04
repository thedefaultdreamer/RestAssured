package speed;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import constants.EndPoints;
import constants.Environments;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.LoginResponse;
import utils.ExcelUtils;
import utils.WriteResponse;

public class Login {
	
	Environments env = new Environments();
	EndPoints endPoints = new EndPoints();
	
	String excelPath = "./src/test/resources/TestData.xlsx";
	String sheetName = "Login";
	
	// Now we are going to access the excel sheet and get the celldata by passing the excelpath and sheetname to ExcelUtils.java
	ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void name() throws IOException {
		
		//Create a JSON object for request and add the data from excel to it
		JSONObject request = new JSONObject();
		request.put("email", excel.getCellData(1, 0));
		request.put("password", excel.getCellData(1, 1));
		
		System.out.println("Request Body: " + request.toJSONString());
		
		// Now we are going to make a request by having the request in the body and response will be assigned to a Response object
		Response httpRequest = given().baseUri(env.dev)
		.header("Content-Type", "application/json")
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(request.toJSONString())
	.when()
		.post(endPoints.login)
	.then()
		.extract().response();
		
		// Get the response as string and store it in string variable
		String stringToParse = httpRequest.getBody().asString();
		System.out.println(stringToParse);
		
		// Using ObjectMapper, we are going to use readValue Method to deserialize JSON content from given JSON content String
		ObjectMapper objectMapper = new ObjectMapper();
		LoginResponse loginResponse = objectMapper.readValue(stringToParse, LoginResponse.class);
		
		// Pass this userinfo object to the method writeResponse. In this method we are going to write our response to an excel sheet
		WriteResponse writeresponse = new WriteResponse();
		writeresponse.writeResponse(loginResponse);
		
	}
}

