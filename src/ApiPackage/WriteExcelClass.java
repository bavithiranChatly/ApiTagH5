package ApiPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelClass {

	public static void ExcelWriteDetails(String TagIdValue, String OpenIdValue, String ValueResponse)
			throws IOException {
		String[] valueToWrite = { TagIdValue, TagIdValue, ValueResponse };
		System.out.println("Entered the write function");
		// Create an object of current class function

		WriteExcelClass objWriteExcelFile = new WriteExcelClass();

		String filePath = System.getProperty("user.dir");

		Properties Apiobj = new Properties();

		FileInputStream Apiobjfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");

		Apiobj.load(Apiobjfile);

		String ExcelFileName = Apiobj.getProperty("ExcelFileName");
		String ExcelSheetName = Apiobj.getProperty("WriteExcelSheetName");

		WriteExcelClass.WriteExcel(filePath, ExcelFileName, ExcelSheetName, valueToWrite);

	}

	public static void WriteExcel(String filePath, String ExcelFileName, String ExcelSheetName, String[] valueToWrite)
			throws IOException {

		// Create an object of File class to open xlsx file

		File file = new File(filePath + "\\" + ExcelFileName);
		System.out.println("Entered the file function");

		// Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);
		System.out.println("Entered the inputStream function");

		HSSFWorkbook WriteWorkbook = new HSSFWorkbook(inputStream);

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		// String fileExtensionName =
		// ExcelFileName.substring(ExcelFileName.indexOf("."));
		String fileExtensionName = "success";
		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			// WriteWorkbook = new XSSFWorkbook(inputStream);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			// WriteWorkbook = new HSSFWorkbook(inputStream);

		}

		// Read excel sheet by sheet name

		Sheet sheet = WriteWorkbook.getSheet(ExcelSheetName);
		System.out.println("Entered the sheet function");

		// Get the current count of rows in excel file

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Get the first row from the sheet

		Row row = sheet.getRow(0);

		// Create a new row and append it at last of sheet

		Row newRow = sheet.createRow(rowCount + 1);

		// Create a loop over the cell of newly created Row

		for (int j = 0; j < valueToWrite.length; j++) {

			// Fill data in row
			System.out.print("wer");
			Cell cell = newRow.createCell(j);

			cell.setCellValue(valueToWrite[j]);

		}
		// Close input stream

		inputStream.close();

		// Create an object of FileOutputStream class to create write data in excel file

		FileOutputStream outputStream = new FileOutputStream(file);

		// write data in the excel file

		WriteWorkbook.write(outputStream);

		// close output stream

		outputStream.close();

	}
}
