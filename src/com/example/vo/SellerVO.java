package com.example.vo;

public class SellerVO {
	private String seller_account;
	private String seller_password;

	public String getSeller_account() {
		return seller_account;
	}

	public void setSeller_account(String seller_account) {
		this.seller_account = seller_account;
	}

	public String getSeller_password() {
		return seller_password;
	}

	public void setSeller_password(String seller_password) {
		this.seller_password = seller_password;
	}

	public SellerVO() {

	}

	@Override
	public String toString() {
		return "SellerVO [seller_account=" + seller_account
				+ ", seller_password=" + seller_password + "]";
	}

}
