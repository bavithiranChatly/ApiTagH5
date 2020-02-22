package ApiPackage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelClass extends ApiTagH5Class {

	public static void ExcelReadDetails() throws Exception {

		ReadExcelClass ReadobjFile = new ReadExcelClass();

		// Prepare the path of excel file

		String filePath = System.getProperty("user.dir");

		Properties Apiobj = new Properties();

		FileInputStream Apiobjfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");

		Apiobj.load(Apiobjfile);

		String ExcelFileName = Apiobj.getProperty("ExcelFileName");
		String ExcelSheetName = Apiobj.getProperty("ReadExcelSheetName");

		ReadobjFile.ReadExcel(filePath, ExcelFileName, ExcelSheetName);

	}

	public void ReadExcel(String filePath, String fileName, String sheetName) throws Exception {

		// Create an object of File class to open xlsx file

		File file = new File(filePath + "\\" + fileName);

		// Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook ReadWorkbook = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			ReadWorkbook = new XSSFWorkbook(inputStream);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class

			ReadWorkbook = new HSSFWorkbook(inputStream);

		}

		// Read sheet inside the workbook by its name

		Sheet ReadSheet = ReadWorkbook.getSheet(sheetName);

		// Find number of rows in excel file

		int rowCount = ReadSheet.getLastRowNum() - ReadSheet.getFirstRowNum();

		// Create a loop over all the rows of excel file to read it

		for (int i = 0; i < rowCount + 1; i++) {

			Row row = ReadSheet.getRow(i);

			// Create a loop to print cell values in a row

			for (int j = 0; j < row.getLastCellNum(); j++) {

				// Print Excel data in console

				System.out.print(row.getCell(j).getStringCellValue() + "|| ");

				if (j == 0) {
					System.out.println("Tag");
					TagIdValue = row.getCell(j).getStringCellValue().toString();
				}
				if (j == 1) {
					System.out.println("OpenId");
					OpenIdValue = row.getCell(j).getStringCellValue().toString();
				}

			}

			ApiTagH5Class obj = new ApiTagH5Class();

			try {

				System.out.println("Testing 2 - Send Http POST request");

				obj.sendPost(TagIdValue, OpenIdValue);

			} finally {

				obj.close();

			}
			System.out.println();
		}

		// Write the code for write data after haspmap get whole data response

		HaspMapWriteClass.HaspMapExcelWrite();

		// Exce the main function

	}

}
