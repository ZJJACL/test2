package com.example.vo;

public class OrderVO {
	private String goods_id;
	private String goods_name;
	private int goods_sum;
	private float goods_price;
	private String buyeraccount;
	private String store_name;
	private String address;
	private String phone;
	private String kuaidi;
	private float order_sum;
	private String isshouhuo;
	

	public String getIsshouhuo() {
		return isshouhuo;
	}

	public void setIsshouhuo(String isshouhuo) {
		this.isshouhuo = isshouhuo;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public int getGoods_sum() {
		return goods_sum;
	}

	public void setGoods_sum(int goods_sum) {
		this.goods_sum = goods_sum;
	}

	public float getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(float goods_price) {
		this.goods_price = goods_price;
	}

	public String getBuyeraccount() {
		return buyeraccount;
	}

	public void setBuyeraccount(String buyeraccount) {
		this.buyeraccount = buyeraccount;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getKuaidi() {
		return kuaidi;
	}

	public void setKuaidi(String kuaidi) {
		this.kuaidi = kuaidi;
	}

	public float getOrder_sum() {
		return order_sum;
	}

	public void setOrder_sum(float order_sum) {
		this.order_sum = order_sum;
	}

	public int getPaytag() {
		return paytag;
	}

	public void setPaytag(int paytag) {
		this.paytag = paytag;
	}

	private int paytag;

}
