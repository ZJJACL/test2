package com.example.vo;

public class BuyerVO {
	public String buyeraccount;
	public String buyerpassword;
	
	
	public BuyerVO() {
		
	}
	public BuyerVO(String buyeraccount, String buyerpassword) {
		super();
		this.buyeraccount = buyeraccount;
		this.buyerpassword = buyerpassword;
	}
	public String getBuyeraccount() {
		return buyeraccount;
	}
	public void setBuyeraccount(String buyeraccount) {
		this.buyeraccount = buyeraccount;
	}
	public String getBuyerpassword() {
		return buyerpassword;
	}
	public void setBuyerpassword(String buyerpassword) {
		this.buyerpassword = buyerpassword;
	}
	@Override
	public String toString() {
		return "BuyerVO [buyeraccount=" + buyeraccount + ", buyerpassword="
				+ buyerpassword + "]";
	}
	
	

}
