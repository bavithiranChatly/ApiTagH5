package ApiPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ApiTagH5Class {

	String TagIdValue;
	String OpenIdValue;

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// ReadExcelDetails
		ExcelReadDetails();

	}

	// Close the HttpClient Conn
	private void close() throws IOException {
		httpClient.close();
	}

	// To Run the Post Value
	private void sendPost(String TagIdValue, String OpenIdValue) throws Exception {

		Properties Apiobj = new Properties();

		FileInputStream Apiobjfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");

		Apiobj.load(Apiobjfile);

		String BaseUrlApi = Apiobj.getProperty("BASEURL");

		// HttpPost post = new
		// HttpPost("https://stgcalls.wechatify.com/api/TagManagement/SendTagDetailByOpenId?access_token=634b1d6f-5675-4925-be31-e282f247ebca&Version=v2");

		HttpPost post = new HttpPost(BaseUrlApi);

		// Excel Read Function.

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();

		urlParameters.add(new BasicNameValuePair("TagId", TagIdValue));
		urlParameters.add(new BasicNameValuePair("OpenId", OpenIdValue));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println(EntityUtils.toString(response.getEntity()));
			
			String ResponseString = response.getEntity().toString();
			
			//System.out.println("Value in String "  +ResponseString);

		}

	}

	// Read the Excel data
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

		// Exce the main function

	}

	// Event Details
	private static void ExcelReadDetails() throws Exception {

		ApiTagH5Class ReadobjFile = new ApiTagH5Class();

		// Prepare the path of excel file

		String filePath = System.getProperty("user.dir");
		
		Properties Apiobj = new Properties();

		FileInputStream Apiobjfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");

		Apiobj.load(Apiobjfile);

		String ExcelFileName = Apiobj.getProperty("ExcelFileName");
		String ExcelSheetName = Apiobj.getProperty("ExcelSheetName");
		
		

		ReadobjFile.ReadExcel(filePath,ExcelFileName,ExcelSheetName);

	}

}
