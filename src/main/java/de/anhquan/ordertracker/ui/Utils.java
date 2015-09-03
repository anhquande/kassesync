package de.anhquan.ordertracker.ui;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.anhquan.ordertracker.Constants;

public class Utils {

	static Logger log = Logger.getLogger(Utils.class);

	public static void openWebpage(URL url) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop()
				: null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(url.toURI());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONObject getRemoteSetting(String key) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post;
		try {
			post = new HttpPost(Constants.host + "/bimat?action=get&key=" + key);
			log.debug("getRemoteSetting: from " + post.getURI());
			HttpResponse response = client.execute(post);

			Reader rd = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(rd);
			return (JSONObject) obj;
		} catch (URISyntaxException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		} catch (HttpException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (ParseException e) {
			log.error(e);
		}

		return null;
	}

	public static boolean doPostRequest(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post;
		try {
			post = new HttpPost(url);

			log.debug("doPostRequest to URL = " + url);

			client.execute(post);
			// HttpResponse response = client.execute(post);
			// BufferedReader rd = new BufferedReader(new InputStreamReader(
			// response.getEntity().getContent()));
			// String line = "";
			// while ((line = rd.readLine()) != null) {
			// System.out.println(line);
			// }

		} catch (URISyntaxException e) {
			log.error(e);
			return false;
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			return false;
		} catch (HttpException e) {
			log.error(e);
			return false;
		} catch (IOException e) {
			log.error(e);
			return false;
		}

		return true;
	}

	public static void playSound() {
		String sound = "win.sound.exclamation";
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit()
				.getDesktopProperty(sound);
		if (runnable != null)
			runnable.run();

	}

}
