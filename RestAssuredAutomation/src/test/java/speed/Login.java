package speed;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.LoginResponse;
import utils.ExcelUtils;

public class Login {
	
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@Test
	public void name() throws IOException {
		
		baseURI = "https://appapi.tryspeed.dev";
		String excelPath = "./data/TestData.xlsx";
		String sheetName = "Login";
		
		// Now we are going to access the excel sheet and get the celldata by passing the excelpath and sheetname to ExcelUtils.java
		ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
		
		//Create a JSON object for request and add the data from excel to it
		JSONObject request = new JSONObject();
		request.put("email", excel.getCellData(1, 0));
		request.put("password", excel.getCellData(1, 1));
		
		System.out.println("Request Body: " + request.toJSONString());
		
		// Now we are going to make a request by having the request in the body and response will be assigned to a Response object
		Response httpRequest = given()
		.header("Content-Type", "application/json")
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(request.toJSONString())
	.when()
		.post("/login")
	.then()
		.extract().response();
		
		// Get the response as string and store it in string variable
		String stringToParse = httpRequest.getBody().asString();
		System.out.println(stringToParse);
		
		// Using ObjectMapper, we are going to use readValue Method to deserialize JSON content from given JSON content String
		ObjectMapper objectMapper = new ObjectMapper();
		LoginResponse loginResponse = objectMapper.readValue(stringToParse, LoginResponse.class);
		
		// Pass this userinfo object to the method writeResponse. In this method we are going to write our response to an excel sheet
		writeResponse(loginResponse);
		
	}

	private void writeResponse(LoginResponse loginResponse) throws IOException {
		
		// Create a new workbook and a worksheet
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("LoginResponse");
		
		// Create a two dimensional object array
		Object[][] respData = {
				{"Access Token", "Id Token", "Refresh Token", "Session"},
				{loginResponse.getAccess_token(), loginResponse.getId_token(), loginResponse.getRefresh_token(),loginResponse.getSession()}
				};
		
		// Create a rowcount variable and assign 0 as initial value
		int rowCount = 0;

		for (Object[] rData : respData) {
			Row row = sheet.createRow(++rowCount);
			int columnCount = 0;
			for (Object field : rData) {
				Cell cell = row.createCell(++columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					System.out.println(cell.getStringCellValue());
					}
				else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					System.out.println(cell.getNumericCellValue());

					}
				}
			}
		
		// Finally we are creating a FileOutputStream object providing the excel file path and writing to the workbook
		FileOutputStream outputStream = new FileOutputStream("./data/Response.xlsx");
		workbook.write(outputStream);
		workbook.close();
		}
	}

