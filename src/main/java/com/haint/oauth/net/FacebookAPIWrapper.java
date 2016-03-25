/**
 * 
 */
package com.haint.oauth.net;

import com.google.gson.Gson;
import com.haint.oauth.model.User;

/**
 * @author HAINT
 *
 */
public class FacebookAPIWrapper implements SocialNetworkAPIWrapper {

	private static String appId = "1073915749317112";
	private static String appSecret = "564196e1d37be0f707d13bdbbe6eb54c";
	private static String redirectUrl = "http://social-login-sample.herokuapp.com/snlogin/facebook";
	private String accessToken;
	private Gson gson;

	public FacebookAPIWrapper() {
		gson = new Gson();
	}

	@Override
	public String getDialogLink() {
		String dialogLink = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s";
		return String.format(dialogLink, appId, redirectUrl);
	}

	@Override
	public String getAccessToken(String code) {
		String accessTokenLink = "https://graph.facebook.com/oauth/access_token?" + "client_id=%s" + "&client_secret=%s" + "&redirect_uri=%s" + "&code=%s";

		accessTokenLink = String.format(accessTokenLink, appId, appSecret, redirectUrl, code);
		String result = NetUtil.getResult(accessTokenLink);
		String token = result.substring(result.indexOf("=") + 1, result.indexOf("&"));
		return token;
	}

	@Override
	public User getUser() {
		String url = "https://graph.facebook.com/v2.5/me?fields=id,name,link&access_token=%s";
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

}
