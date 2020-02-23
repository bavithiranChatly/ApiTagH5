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
	String OpenIdValue = "Null";
	String ValueResponse = "Null";
	String AccountName = "Null" ;
	int StatusCode = 0;

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// ReadExcelDetails
		ReadExcelClass.ExcelReadDetails();

	}

	// Close the HttpClient Conn
	protected void close() throws IOException {
		httpClient.close();
	}

	// To Run the Post Value
	protected void sendPost(String AccountNameValue ,String TagIdValue, String OpenIdValue) throws Exception {

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
			//String ValueResponse = EntityUtils.toString(response.getEntity());

			
		    StatusCode = response.getStatusLine().getStatusCode();
			
			System.out.println("StatusCode Value" + "" + "=" +StatusCode);
			
			if(StatusCode == 200)
			{
				ValueResponse = "200 OK: {\"status\": \" Ok \", \"message\" : \" Successfully submitted \"}";
			}
			else if(StatusCode == 400)
			{
				ValueResponse = "400 BadRequest: {\"status\": \" Error\", \"message\" : \" TagId not found/Invalid OpenId/Invalid Accesstoken \"}";
			}
			else if(StatusCode == 200 && StatusCode == 400)
			{
				ValueResponse = "Sorry there is no excepted Error Code";
			}
			else if(StatusCode == 0)
			{
				ValueResponse = "Api Hasn't processed";
			}
			
			// Accessing HaspMapWriteClass to send to data to write in excel sheet
			HaspMapWriteClass.HaspPutData(AccountNameValue,TagIdValue, OpenIdValue,ValueResponse);

		}
		

	}

}
