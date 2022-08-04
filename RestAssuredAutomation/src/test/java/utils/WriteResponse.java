package utils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pojo.LoginResponse;

public class WriteResponse {
	
	public void writeResponse(LoginResponse loginResponse) throws IOException {
		
		// Create a new workbook and a worksheet
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		
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
		FileOutputStream outputStream = new FileOutputStream("./src/test/resources/Response.xlsx");
		workbook.write(outputStream);
		workbook.close();
		}
}
