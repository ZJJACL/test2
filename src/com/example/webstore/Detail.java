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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends Activity {

	private String goodname;
	private String goods_id;
	private TextView txtgoodname;
	private TextView txtgood_price, txtstorename;
	private String storename;
	private float good_price;
	private EditText detail_num;
	private Button btn_down;
	private Button btn_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		Bundle bundle = this.getIntent().getExtras();
		goodname = bundle.getString("goodname");
		goods_id = bundle.getString("goods_id");
		good_price = bundle.getFloat("good_price");
		storename = bundle.getString("storename");
		txtgoodname = (TextView) this.findViewById(R.id.good_name_detail);
		txtgood_price = (TextView) this.findViewById(R.id.good_price_detail);
		txtstorename = (TextView) this.findViewById(R.id.detail_store_name);
		txtstorename.setText("卖家店铺：" + storename);
		txtgoodname.setText(goodname);
		txtgood_price.setText(Float.toString(good_price));

		detail_num = (EditText) Detail.this.findViewById(R.id.detail_num);

		btn_down = (Button) this.findViewById(R.id.btn_detail_down);
		btn_down.setTag(detail_num);
		btn_add = (Button) this.findViewById(R.id.btn_detail_add);
		btn_add.setTag(detail_num);

	}

	public void detail_downOnclick(View view) throws Exception {

		EditText et = (EditText) view.getTag();

		if (Integer.parseInt(et.getText().toString()) <=1) {
			et.setText(1 + "");
		}
		else {
			et.setText(Integer.parseInt(et.getText().toString()) - 1 + "");
		}

	}

	public void detail_addOnclick(View view) throws Exception {

		EditText et = (EditText) view.getTag();
		et.setText(Integer.parseInt(et.getText().toString()) + 1 + "");

	}

	public void buynowOnclick(View view) throws Exception {

		int goods_sum = Integer.parseInt(detail_num.getText().toString());
		Intent intent = new Intent();
		intent.setClassName(Detail.this, "com.example.webstore.ConfirmOrder");
		Bundle bundle = new Bundle();
		bundle.putString("goods_id", goods_id);
		bundle.putString("goods_name", goodname);
		bundle.putFloat("goods_price", good_price);
		bundle.putString("store_name", storename);
		bundle.putInt("goods_sum", goods_sum);

		intent.putExtras(bundle);
		startActivity(intent);

	}

	public void addshoppingcart(View view) throws Exception {

		add(goods_id, AppContent.buyeraccount);

	}

	public void add(String goodid, String buyeraccount) {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP
				+ ":8080/webstore/AddShoppingCart";
		RequestParams params = new RequestParams();
		params.add("goodid", goodid);
		params.add("buyeraccount", buyeraccount);
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {

					Toast.makeText(Detail.this, "添加成功", 1).show();

				}
				if (501 == jsonResp.code) {
					Toast.makeText(Detail.this, "此商品在购物车中已有", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Detail.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
	}

}
