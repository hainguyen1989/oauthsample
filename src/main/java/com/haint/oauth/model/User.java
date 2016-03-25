/**
 * 
 */
package com.haint.oauth.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author HAINT
 *
 */
public class User {

	@SerializedName(value = "userId")
	private Long id;
	@SerializedName(value = "email")
	private String username;
	private String password;
	@SerializedName(value = "name")
	private String fullname;
	private String address;
	private String mobile;
	@SerializedName(value = "id")
	private String socialId;
	@SerializedName(value = "link")
	private String socialLink;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String facebookId) {
		this.socialId = facebookId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSocialLink() {
		return socialLink;
	}

	public void setSocialLink(String socialLink) {
		this.socialLink = socialLink;
	}

}
