package com.example.vo;

public class GoodVO {
	private String goods_id;
	private String goods_name;
	private int goods_sum;
	private float goods_price;
	private String store_name;
	
	
	
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
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
	@Override
	public String toString() {
		return "GoodVO [goods_id=" + goods_id + ", goods_name=" + goods_name
				+ ", goods_sum=" + goods_sum + ", goods_price=" + goods_price
				+ ", store_name=" + store_name + "]";
	}
	

}
