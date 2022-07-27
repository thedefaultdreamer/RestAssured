package tests;

import static io.restassured.RestAssured.*;

import java.io.FileOutputStream;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.result.Row;

import groovyjarjarpicocli.CommandLine.Help.TextTable.Cell;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.LoginResponse;
import utils.ExcelUtils;

public class DataDriven {
	
	private static Response response;
	private static final String baseURI = "https://appapi.tryspeed.dev";
	
	@Test
	public void post() throws JsonMappingException, JsonProcessingException {
		
		String excelPath = "./data/TestData.xlsx";
		String sheetName = "Login";
		
		ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
		
		// POST method
		JSONObject request = new JSONObject();
		request.put("email", excel.getCellData(1,0));
		request.put("email", excel.getCellData(1,1));
		
		response = given().baseUri(baseURI).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(request.toJSONString()).when()
				.post("/login")
				.then()
				.extract()
				.response();
		
		String stringToParse = response.getBody().asString();
		ObjectMapper objectMapper = new ObjectMapper();
		LoginResponse loginResponse = objectMapper.readValue(stringToParse, LoginResponse.class);
		writeResponse(loginResponse);
	}
	
	private void writeResponse(LoginResponse loginResponse) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("LoginResponse");
		
		Object[][] respData = {
				{"User ID", "User Name", "Job", "CreatedAt"},
				{loginResponse.getAccess_token(), loginResponse.getId_token(), loginResponse.getRefresh_token(),loginResponse.getSession()}
				};
		
		// Using foreach loop, we are iterating through the object array and creating row and then column and setting the cellvalue

		int rowCount = 0;

		for (Object[] rData : respData) {
			Row row = (Row) sheet.createRow(++rowCount);
			int columnCount = 0;
			for (Object field : rData) {
				XSSFCell cell = ((XSSFRow) row).createCell(++columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					}
				else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					}
				}
			}
		
		try {
			FileOutputStream outputStream = new FileOutputStream("./data/Response.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
 