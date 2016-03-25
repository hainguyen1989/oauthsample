/**
 * 
 */
package com.haint.oauth.net;

/**
 * @author HAINT
 *
 */
public class SocialNetworkAPIWrapperFactory {

	public SocialNetworkAPIWrapper getSocialNetworkAPIWrapper(SocialNetworkType type) {

		if (type == null) {
			return null;
		}

		SocialNetworkAPIWrapper socialNetworkAPIWrapper = null;

		switch (type) {
		case FACEBOOK:
			socialNetworkAPIWrapper = new FacebookAPIWrapper();
			break;
		case GOOGLE:
			socialNetworkAPIWrapper = new GoogleAPIWrapper();
			break;
		case TWITTER:
			break;
		default:
			break;
		}

		return socialNetworkAPIWrapper;
	}
}
