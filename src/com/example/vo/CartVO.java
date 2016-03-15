package com.example.vo;

public class CartVO {
	private String goods_id;
	private String goods_name;
	private float goods_price;
	private int goods_sum;
	private String buyeraccount;
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
	public float getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(float goods_price) {
		this.goods_price = goods_price;
	}
	public int getGoods_sum() {
		return goods_sum;
	}
	public void setGoods_sum(int goods_sum) {
		this.goods_sum = goods_sum;
	}
	public String getBuyeraccount() {
		return buyeraccount;
	}
	public void setBuyeraccount(String buyeraccount) {
		this.buyeraccount = buyeraccount;
	}
	@Override
	public String toString() {
		return "CartVO [goods_id=" + goods_id + ", goods_name=" + goods_name
				+ ", goods_price=" + goods_price + ", goods_sum=" + goods_sum
				+ ", buyeraccount=" + buyeraccount + "]";
	}
	

}
