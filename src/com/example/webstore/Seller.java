package com.example.webstore;

import java.util.ArrayList;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.String.AppContent;
import com.example.util.JSONResponce;
import com.example.vo.GoodVO;
import com.example.vo.OrderVO;
import com.example.vo.SellerVO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Seller extends Activity {

	private ListView listview;
	private ListView orderlistview;
	ArrayList<GoodVO> list = new ArrayList<GoodVO>();
	JSONArray array = new JSONArray();
	JSONArray array1 = new JSONArray();
	private TabHost host;
	OrderVO vo = new OrderVO();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.seller);
		
		

		host = (TabHost) this.findViewById(R.id.seller_tabhost);
		host.setup();

		this.listview = (ListView) this.findViewById(R.id.sellerhupjia_listview);

		TabSpec firstTab = host.newTabSpec("货架");
		firstTab.setIndicator("货架",
				getResources().getDrawable(android.R.drawable.btn_star));
		firstTab.setContent(R.id.sellerhuojia);
		host.addTab(firstTab);
		showorder();
        orderlistview=(ListView) this.findViewById(R.id.seller_order_list);
 
		TabSpec secondTab = host.newTabSpec("订单");
		secondTab.setIndicator("订单",
				getResources().getDrawable(android.R.drawable.btn_star));
		secondTab.setContent(R.id.seller_order_listview);
		host.addTab(secondTab);

		TabSpec thirdTab = host.newTabSpec("管理");
		thirdTab.setIndicator("管理",
				getResources().getDrawable(android.R.drawable.btn_star));
		thirdTab.setContent(R.id.seller_management);
		host.addTab(thirdTab);
		showgood();

	}
   public void	refreshorderOnclick(View view)throws Exception{
	   showorder();
   }
	
	public void showorder(){
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/QueryOrder";
		RequestParams params = new RequestParams();
		params.add("account","二号店");
		params.add("type", "seller");

		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					array1 = (JSONArray) jsonResp.getData();
					orderlistview.setAdapter(new BaseAdapter() {

						@Override
						public View getView(int position, View convertView,
								ViewGroup parent) {
							if (convertView == null) {
								convertView = getLayoutInflater().inflate(
										R.layout.seller_orderlistview_detail, null);
							}

							
							vo = JSON.toJavaObject(
									(JSONObject) array1.get(position),
									OrderVO.class);

							TextView textView2 = (TextView) convertView.findViewById(R.id.seller_order_goods_name);
							textView2.setText(vo.getGoods_name());
							TextView textView3 = (TextView) convertView.findViewById(R.id.seller_order_goods_price);
							textView3.setText(Float.toString(vo.getGoods_price()));
							
							Button button=(Button)convertView.findViewById(R.id.searchorder);
							button.setText("查看订单");
							button.setTag(textView2);
							

							return convertView;
						}

						@Override
						public long getItemId(int position) {

							return 0;
						}

						@Override
						public Object getItem(int position) {

							return null;
						}

						@Override
						public int getCount() {

							return array1.size();
						}
					});

				}
				if (501 == jsonResp.code) {
					Toast.makeText(Seller.this, "此卖家没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Seller.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});

		
	}
	
	public void sellerorderOnclick(View view)throws Exception{
	  	
		TextView txtgoods_name=(TextView) view.getTag();
		
		String goods_name=txtgoods_name.getText().toString();
		
		Intent intent=new Intent();
		intent.setClassName(Seller.this, "com.example.webstore.OrderDetail");
		Bundle bundle=new Bundle();
		bundle.putString("account", "二号店");
		bundle.putString("goods_name", goods_name);
		
		intent.putExtras(bundle);
		startActivity(intent);
		

		
		
	}
	

	 public void showgood() {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/QueryGood";
		RequestParams params = new RequestParams();
		params.add("goodtype", "allgoods");

		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					array = (JSONArray) jsonResp.getData();
					listview.setAdapter(new BaseAdapter() {

						@Override
						public View getView(int position, View convertView,
								ViewGroup parent) {
							if (convertView == null) {
								convertView = getLayoutInflater().inflate(
										R.layout.sellergood, null);
							}

							GoodVO vo = new GoodVO();
							vo = JSON.toJavaObject(
									(JSONObject) array.get(position),
									GoodVO.class);

							TextView textView2 = (TextView) convertView
									.findViewById(R.id.goods_name);
							textView2.setText(vo.getGoods_name());
							TextView textView3 = (TextView) convertView
									.findViewById(R.id.goods_price);
							textView3.setText(Float.toString(vo
									.getGoods_price()));
							ImageView imageview = (ImageView) convertView
									.findViewById(R.id.imageview);
							imageview.setBackgroundResource(R.drawable.twos);

							return convertView;
						}

						@Override
						public long getItemId(int position) {

							return 0;
						}

						@Override
						public Object getItem(int position) {

							return null;
						}

						@Override
						public int getCount() {

							return array.size();
						}
					});

				}
				if (501 == jsonResp.code) {
					Toast.makeText(Seller.this, "此卖家没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Seller.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});

	}

	public void sversionOnclcik(View view) throws Exception {

		Intent intent = new Intent();
		intent.setClassName(Seller.this, "com.example.webstore.Version");
		startActivity(intent);

	}

	public void smywalletOnclick(View view) throws Exception {

		Intent intent = new Intent();
		intent.setClassName(Seller.this, "com.example.webstore.Mywallet");
		startActivity(intent);

	}

	public void changepasswordOnclick(View view) throws Exception {

		Toast.makeText(Seller.this, "正在开发。。。。。。。", Toast.LENGTH_SHORT).show();
	}

}