/**
 * 
 */
package com.haint.oauth.net;

import com.haint.oauth.model.User;

/**
 * @author HAINT
 *
 */
public interface SocialNetworkAPIWrapper {

	String getDialogLink();

	String getAccessToken(String code);

	User getUser();
	
	void setAccessToken(String token);
}
