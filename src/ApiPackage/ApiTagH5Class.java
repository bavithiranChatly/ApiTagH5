package ApiPackage;

import java.io.FileInputStream;
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

public class ApiTagH5Class {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ApiTagH5Class obj = new ApiTagH5Class();

		try {
			// System.out.println("Testing 1 - Send Http GET request");
			// obj.sendGet();

			System.out.println("Testing 2 - Send Http POST request");
			obj.sendPost();
		} finally {
			obj.close();
		}

	}

	private void close() throws IOException {
		httpClient.close();
	}

	private void sendPost() throws Exception {
		
		Properties Apiobj = new Properties();
		
		FileInputStream Apiobjfile = new FileInputStream(System.getProperty("user.dir")+"\\application.properties");
		
		Apiobj.load(Apiobjfile);
		
		String BaseUrlApi = Apiobj.getProperty("BASEURL");



		//HttpPost post = new HttpPost("https://stgcalls.wechatify.com/api/TagManagement/SendTagDetailByOpenId?access_token=634b1d6f-5675-4925-be31-e282f247ebca&Version=v2");

		HttpPost post = new HttpPost(BaseUrlApi);
		
		//Excel Read Function.
				
		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();

		urlParameters.add(new BasicNameValuePair("TagId", "2df1415f-d08d-4e3c-8e0b-cb79ded0d432"));
		urlParameters.add(new BasicNameValuePair("OpenId", "oo0EUvyzsTKwC3pQoF4tTfIHQ8z8"));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println(EntityUtils.toString(response.getEntity()));
			
			
		}

	}

}
