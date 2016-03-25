/**
 * 
 */
package com.haint.oauth.net;

/**
 * @author HAINT
 *
 */
public enum SocialNetworkType {
	FACEBOOK("facebook"), TWITTER("twitter"), GOOGLE("google");

	String name;

	private SocialNetworkType(String name) {
		this.name = name;
	}

	public static SocialNetworkType fromString(String type) {
		if (type != null) {
			for (SocialNetworkType snType : SocialNetworkType.values()) {
				if (snType.name.equalsIgnoreCase(type)) {
					return snType;
				}
			}
		}

		return null;
	}
}
