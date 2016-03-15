package com.example.webstore;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.String.AppContent;
import com.example.util.JSONResponce;
import com.example.vo.WalletVO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class PayOrder extends Activity {

	
	private float money_rest;
	private String store_name;
	private String goods_id;
	private String goods_name;
	private float goods_price;
	private int goods_sum;
	private float order_sum;
	private TextView txtmoney_rest;
	private TextView txtgoods_name;
	private TextView txtgoods_price;
	private TextView txtgoods_sum;
	private TextView txtorder_sum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_pay);
		Bundle bundle=this.getIntent().getExtras();
		store_name=bundle.getString("store_name");
		goods_id=bundle.getString("goods_id");
		goods_name=bundle.getString("goods_name");
		goods_price=bundle.getFloat("goods_price");
		goods_sum=bundle.getInt("goods_sum");
		order_sum=bundle.getFloat("order_sum");
		txtmoney_rest=(TextView)this.findViewById(R.id.pay_money_rest);
		
		txtgoods_name=(TextView)this.findViewById(R.id.pay_goods_name);
		txtgoods_name.setText(goods_name);
		txtgoods_price=(TextView)this.findViewById(R.id.pay_goods_price);
		txtgoods_price.setText(Float.toString(goods_price));
		txtgoods_sum=(TextView)this.findViewById(R.id.pay_goods_sum);
		txtgoods_sum.setText(Integer.toString(goods_sum));
		txtorder_sum=(TextView)this.findViewById(R.id.pay_order_sum);
		txtorder_sum.setText(Float.toString(order_sum));
		querymoney();
		
	}
	public void querymoney(){
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/QueryWallet";
		RequestParams params = new RequestParams();
		params.add("account", AppContent.buyeraccount);
		client.post(url,params,new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					 WalletVO vo = JSON.toJavaObject((JSONObject)jsonResp.data, WalletVO.class);
					 txtmoney_rest.setText(Float.toString(vo.getRest()));
				}
				if (501 == jsonResp.code) {
					Toast.makeText(PayOrder.this, "系统繁忙，请稍后再试", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(PayOrder.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
	}
	public void pay_money(View view)throws Exception{
		money_rest=Float.parseFloat(txtmoney_rest.getText().toString())-Float.parseFloat(txtorder_sum.getText().toString());
		
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/UpdateWallet";
		RequestParams params = new RequestParams();
		params.add("account", AppContent.buyeraccount);
		params.add("restmoney", Float.toString(money_rest));
		client.post(url,params,new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					Toast.makeText(PayOrder.this, "付款成功!", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent();
					intent.setClassName(PayOrder.this, "com.example.webstore.HomePage");
					startActivity(intent);

				}
				if (501 == jsonResp.code) {
					Toast.makeText(PayOrder.this, "此卖家没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(PayOrder.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});updateorder();
	}
	public void updateorder(){
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/UpdateOrder";
		RequestParams params = new RequestParams();
		params.add("account", AppContent.buyeraccount);
		params.add("pay_tag",1+"");
		params.add("goods_id",goods_id);
		params.add("store_name",store_name);
		client.post(url,params,new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					

				}
				if (501 == jsonResp.code) {
					Toast.makeText(PayOrder.this, "此卖家没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(PayOrder.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
		
	}
	
}
