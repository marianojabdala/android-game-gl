package org.battleship.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 * As a general rule on Android applications we need to set all the fields as public,
 * this is for optimizations
 * 
 * */

public final class ClientRest {

	public String url;

	public static URLConnection urlConnection;

	public static Map<String,String> execute( String url ) throws Exception {
		   
		HttpGet request = new HttpGet(url);

		return executeRequest(request, url);
	}

	private static Map<String,String> executeRequest(HttpUriRequest request, String url) {
		HttpClient client = new DefaultHttpClient();

		HttpResponse httpResponse;
		Map<String,String> responseMap = new HashMap<String, String>();
		
		try {
			httpResponse = client.execute(request);
			responseMap.put("responseCode", httpResponse.getStatusLine().getStatusCode()+"");
			responseMap.put("message", httpResponse.getStatusLine().getReasonPhrase());

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				String response = convertStreamToString(instream);
				responseMap.put("response", response);
				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
		return responseMap;
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
