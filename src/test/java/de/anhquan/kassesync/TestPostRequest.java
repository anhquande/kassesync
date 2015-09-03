package de.anhquan.kassesync;

import java.io.*;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.parser.JSONParser;

public class TestPostRequest {
	public static void main(String[] args) {
		try {
			HttpClient client = new DefaultHttpClient();
			String orderListURL = "http://localhost:8080/bimat/control";
			String getSettingURL = "http://localhost:8080/bimat?action=get&key=store.mode.busy";
			String restoreURL = "http://localhost:8080/restore?id=6e50d1d22279641216c93208d75e7bd806c&status=0";
			HttpPost method = new HttpPost(restoreURL );
			//HttpPost method = new HttpPost("http://2.daiduongservice.appspot.com/ordercreation");
			method.setHeader("Accept", "application/json");
	        HttpResponse response = client.execute(method);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}			
			
		} catch (Exception e) {
			System.err.println("Fatal error: " + e.getMessage());
			e.printStackTrace();
		} finally {
		}
	}
}