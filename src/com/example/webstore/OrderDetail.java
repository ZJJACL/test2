package com.example.webstore;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.String.AppContent;
import com.example.util.JSONResponce;
import com.example.vo.CartVO;
import com.example.vo.GoodVO;
import com.example.vo.OrderVO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetail extends Activity {
	private TextView order_goods_name;
	private TextView order_store_name;
	private TextView order_goods_price;
	private TextView order_goods_sum;
	private TextView order_buyeraccount;
	private TextView order_address;
	private TextView order_phone;
	private TextView order_order_sum;
	private TextView order_kuaidi;
	private TextView order_isshouhuo;
	private Button button1;
	
	
	
	private String goods_name;
	private String store_name;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		
		order_goods_name=(TextView)this.findViewById(R.id.seller_order_goods_name);
		order_store_name=(TextView)this.findViewById(R.id.seller_order_store_name);
		order_goods_price=(TextView)this.findViewById(R.id.seller_order_goods_price);
		order_buyeraccount=(TextView)this.findViewById(R.id.seller_order_buyeraccount);
		order_goods_sum=(TextView)this.findViewById(R.id.seller_order_goods_sum);
		order_order_sum=(TextView)this.findViewById(R.id.seller_order_order_sum);
		order_address=(TextView)this.findViewById(R.id.seller_order_address);
		
		order_phone=(TextView)this.findViewById(R.id.seller_order_phone);
		order_kuaidi=(TextView)this.findViewById(R.id.seller_order_kuaidi);
		order_isshouhuo=(TextView)this.findViewById(R.id.seller_order_isshouhuo);
		button1=(Button)this.findViewById(R.id.seller_querenshouhuo);
		
		Bundle bundle=new Bundle();
		bundle=this.getIntent().getExtras();
		goods_name=bundle.getString("goods_name");
		store_name=bundle.getString("account");
		order_goods_name.setText("商品名称："+bundle.getString("goods_name"));
		order_store_name.setText("店铺名称"+bundle.getString("account"));
		
		show();
		
	}
	
	public void querenshouhuoOnclick(View view){
		
		
		
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/Querenshouhuo";
		RequestParams params = new RequestParams();
		params.add("goods_name", goods_name);
		params.add("store_name", store_name);
		params.add("isshouhuo", "1");

		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					
					Toast.makeText(OrderDetail.this, "确认成功", 1).show();
					

				}
				if (501 == jsonResp.code) {
					Toast.makeText(OrderDetail.this, "系统繁忙", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(OrderDetail.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
	}
	
	public void show(){
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/QueryOrder1";
		RequestParams params = new RequestParams();
		params.add("goods_name", goods_name);
		params.add("store_name", store_name);

		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					OrderVO vo = new OrderVO();
					vo = JSON.toJavaObject((JSONObject)jsonResp.data,OrderVO.class);
					order_goods_price.setText("商品单价："+Float.toString(vo.getGoods_price()));
					order_goods_sum.setText("商品数量："+vo.getGoods_sum());
					order_buyeraccount.setText("买家账户："+vo.getBuyeraccount());
					order_address.setText("买家地址："+vo.getAddress());
					order_phone.setText("买家联系方式："+vo.getPhone());
					order_kuaidi.setText("使用快递："+vo.getKuaidi());
					order_order_sum.setText("总价：      "+vo.getOrder_sum());
					if(vo.getIsshouhuo().equals("0")){
						order_isshouhuo.setText("你还没收货");
						
					}
					if(vo.getIsshouhuo().equals("1"))
					{
						order_isshouhuo.setText("已收货");
						button1.setVisibility(4);
						
					}
					

				}
				if (501 == jsonResp.code) {
					Toast.makeText(OrderDetail.this, "此卖家没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(OrderDetail.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
	}
	
	
	
	
	

	
}
