package com.example.webstore;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.String.AppContent;
import com.example.util.JSONResponce;
import com.example.vo.GoodVO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Store extends Activity {
	private ImageView image;
	private ListView listview;
	JSONArray array = new JSONArray();
	private String goods_id;
	private float good_price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.store);

		Bundle bundle = this.getIntent().getExtras();

		String goodname = bundle.getString("goodname");

		showgood(goodname);

		listview = (ListView) this.findViewById(R.id.store_info);

	}

	public void detail_onclick(View view) throws Exception {
		ViewGroup parentview = (ViewGroup) view.getParent();
		TextView textviewstorename = (TextView) parentview
				.findViewById(R.id.store_name111);

		TextView textviewname = (TextView) parentview
				.findViewById(R.id.store_good_name);
		TextView textviewprice = (TextView) parentview
				.findViewById(R.id.store_good_price);
		TextView textviewid = (TextView) parentview
				.findViewById(R.id.store_good_id);

		String name = textviewname.getText().toString();
		float price = Float.parseFloat(textviewprice.getText().toString());
		String id = textviewid.getText().toString();
		String storename = textviewstorename.getText().toString();

		Intent intent = new Intent();
		intent.setClassName(Store.this, "com.example.webstore.Detail");
		Bundle bundle = new Bundle();
		bundle.putString("goodname", name);
		bundle.putString("goods_id", id);
		bundle.putFloat("good_price", price);
		bundle.putString("storename", storename);
		intent.putExtras(bundle);
		startActivity(intent);
		
		
	}

	public void showgood(String type) {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/QueryGood";
		RequestParams params = new RequestParams();
		params.add("goodtype", type);

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
										R.layout.storedetail, null);
							}
							GoodVO vo = new GoodVO();
							vo = JSON.toJavaObject(
									(JSONObject) array.get(position),
									GoodVO.class);

							TextView textView = (TextView) convertView
									.findViewById(R.id.store_good_name);
							textView.setText(vo.getGoods_name());
							TextView textview1 = (TextView) convertView
									.findViewById(R.id.store_good_price);
							textview1.setText(Float.toString(vo
									.getGoods_price()));
							TextView textview2 = (TextView) convertView
									.findViewById(R.id.store_good_id);
							textview2.setText(vo.getGoods_id());
							TextView textview3 = (TextView) convertView
									.findViewById(R.id.store_name111);
							textview3.setText(vo.getStore_name());

							image = (ImageView) convertView
									.findViewById(R.id.imagebutton);
							image.setBackgroundResource(R.drawable.ic_launcher);

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
							// TODO Auto-generated method stub
							return array.size();
						}
					});

					Toast.makeText(Store.this, "正在加载.....", Toast.LENGTH_SHORT)
							.show();

				}
				if (501 == jsonResp.code) {
					Toast.makeText(Store.this, "此卖家没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Store.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});

	}

}
