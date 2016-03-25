/**
 * 
 */
package com.haint.oauth.net;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haint.oauth.model.User;

/**
 * @author HAINT
 *
 */
public class GoogleAPIWrapper implements SocialNetworkAPIWrapper {

	private final static Logger LOGGER = LoggerFactory.getLogger(GoogleAPIWrapper.class);
	private static String appId = "198883335558-svc27j3m8lpe6nsj68oj05c88enmle0k.apps.googleusercontent.com";
	private static String appSecret = "woafrWFIEy349ZCWEVzRWhfI";
	private static String redirectUrl = "http://localhost:8080/OAuthSample/snlogin/google";
	private String accessToken;
	private Gson gson;

	public GoogleAPIWrapper() {
		gson = new Gson();
	}

	@Override
	public String getDialogLink() {
		String dialogLink = "https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=%s&response_type=code&client_id=%s&approval_prompt=force";

		return String.format(dialogLink, redirectUrl, appId);
	}

	@Override
	public String getAccessToken(String code) {
		String accessTokenLink = "https://accounts.google.com/o/oauth2/token";

		Map<String, String> urlParameter = new HashMap<>();
		urlParameter.put("code", code);
		urlParameter.put("client_id", appId);
		urlParameter.put("client_secret", appSecret);
		urlParameter.put("redirect_uri", redirectUrl);
		urlParameter.put("grant_type", "authorization_code");

		String result = NetUtil.getResult(accessTokenLink, urlParameter);
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		String accessToken = json.get("access_token").getAsString();
		LOGGER.debug(">>> Access token: " + accessToken);

		return accessToken;
	}

	@Override
	public User getUser() {
		String url = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=%s";
		url = String.format(url, this.accessToken);
		String result = NetUtil.getResult(url);
		User user = gson.fromJson(result, User.class);
		user.setFullname(user.getFullname());
		return user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public static void main(String[] args) {
		String json = "{\"id\": \"116121476620757823879\","
				+ "\"email\": \"thehai1911@gmail.com\","
				+ "\"verified_email\": true,"
				+ "\"name\": \"Nguyễn Thế Hải\","
				+ "\"given_name\": \"Nguyễn Thế\", "
				+ "\"family_name\": \"Hải\", "
				+ "\"link\": \"https://plus.google.com/116121476620757823879\", "
				+ "\"picture\": \"https://lh5.googleusercontent.com/-GhE3YJ6ELQI/AAAAAAAAAAI/AAAAAAAAHQI/ylHxH_yx058/photo.jpg\", "
				+ "\"gender\": \"male\"}";
		
		Gson gson = new Gson();
		User user = gson.fromJson(json, User.class);
		System.out.println(user.getFullname());
				
	}
}
