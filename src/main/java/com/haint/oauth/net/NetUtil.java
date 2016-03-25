/**
 * 
 */
package com.haint.oauth.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HAINT
 *
 */
public class NetUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(NetUtil.class);

	public static String getResult(String url) {
		try {
			HttpURLConnection conn = getConnection(url, true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			StringBuilder returnStr = new StringBuilder();
			while((line = br.readLine()) != null){
				returnStr.append(line);
			}

			LOGGER.debug(">> returnStr =" + returnStr.toString());
			return returnStr.toString();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getResult(String url, Map<String, String> params) {
		try {
			HttpURLConnection conn = getConnection(url, true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			String urlParameters = "";

			for (Map.Entry<String, String> entry : params.entrySet()) {
				urlParameters += entry.getKey() + "=" + entry.getValue() + "&";
			}

			if (!urlParameters.isEmpty()) {
				urlParameters = urlParameters.substring(0, urlParameters.length() - 1);
			}
			
			conn.setDoOutput(true);
			
			//send POST request
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(urlParameters);
			dos.flush();
			dos.close();
			
			int responseCode = conn.getResponseCode();
			
			LOGGER.debug(">>> Sending POST request to URL: " + url);
			LOGGER.debug(">>> POST Parameters: " + urlParameters);
			LOGGER.debug(">>> Response code: " + responseCode);

			if (responseCode != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + responseCode);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			StringBuilder returnStr = new StringBuilder();
			while((line = br.readLine()) != null){
				returnStr.append(line);
			}

			LOGGER.debug(">> returnStr =" + returnStr.toString());
			return returnStr.toString();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static HttpURLConnection getConnection(String urlStr, boolean useProxy) throws IOException {
		
		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("fis\\haint51", "qwer4321!".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.fis.vn", 8080));

		if(!useProxy){
			proxy = Proxy.NO_PROXY;
		}
		
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);

		return conn;
	}

}
