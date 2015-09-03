package de.anhquan.kassesync;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.parser.JSONParser;

public class TestWebHook {
	public static void main(String[] args) {
		try {
			HttpClient client = new DefaultHttpClient();
			String host = "localhost:8080";
			//host="bienhoadev.appspot.com";
			HttpPost method = new HttpPost("http://"+host +"/ordercreation");
			//HttpPost method = new HttpPost("http://2.daiduongservice.appspot.com/ordercreation");
	        BufferedReader in = new BufferedReader(new FileReader("payload.json"));
	        String s;
	        String str="";
	        while( (s = in.readLine()) != null) {
	        	str =str+ new String(s.getBytes(),"UTF-8");
	        }

	        HttpEntity entity = new ByteArrayEntity(str.getBytes("UTF-8"));
	        method.setEntity(entity);

	        HttpResponse response = client.execute(method);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

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