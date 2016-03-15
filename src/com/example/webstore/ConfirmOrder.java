package com.example.webstore;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.String.AppContent;
import com.example.util.JSONResponce;
import com.example.util.ThisAppUtil;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmOrder extends Activity {
	private String goods_id;
	private String goods_name;
	private float goods_price;
	private String store_name;
	private int goods_sum;
	private TextView txtvgoods_name;
	private TextView txtvgoods_information;
	private EditText order_address;
	private EditText order_phone;
	private TextView ordersum;
	private Spinner order_kuaidi;
	private float order_sum;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comfirmorder);
		Bundle bundle=this.getIntent().getExtras();
		goods_name=bundle.getString("goods_name");
		goods_price=bundle.getFloat("goods_price");
		goods_id=bundle.getString("goods_id");
		store_name=bundle.getString("store_name");
		goods_sum=bundle.getInt("goods_sum");
		txtvgoods_name=(TextView) this.findViewById(R.id.order_goods_name);
		txtvgoods_information=(TextView) this.findViewById(R.id.order_goods_information);
		txtvgoods_name.setText(goods_name);
		txtvgoods_information.setText("单价 ￥："+goods_price+"\n 数量："+goods_sum);
		order_kuaidi=(Spinner)this.findViewById(R.id.spinner);
		ordersum=(TextView)this.findViewById(R.id.ordersum);
		ordersum.setText(Float.toString(goods_price*goods_sum));
	}
	public void btn_submit_order(View view)throws Exception{
		order_address=(EditText)ConfirmOrder.this.findViewById(R.id.order_address);
		order_phone=(EditText)ConfirmOrder.this.findViewById(R.id.order_phone);
		String orderkuaidi=order_kuaidi.getSelectedItem().toString();
		String address=order_address.getText().toString();
		String phone=order_phone.getText().toString();
		
		
		 order_sum =goods_price*goods_sum;
		if(ThisAppUtil.isNone(address)){
			Toast.makeText(ConfirmOrder.this, "请输入地址", Toast.LENGTH_SHORT).show();
			return;
		}
		if(ThisAppUtil.isNone(phone)){
			Toast.makeText(ConfirmOrder.this, "请输入联系方式", Toast.LENGTH_SHORT).show();
			return;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/SubmitOrder";
		RequestParams params = new RequestParams();
		params.add("goods_id", goods_id);
		params.add("goods_name", goods_name);
		params.add("goods_sum", Integer.toString(goods_sum));
		params.add("goods_price", Float.toString(goods_price));
		params.add("buyeraccount", AppContent.buyeraccount);
		params.add("store_name", store_name);
		params.add("address", address);
		params.add("phone", phone);
		params.add("kuaidi", orderkuaidi);
		params.add("order_sum", Float.toString(order_sum));
		params.add("pay_tag", 0+"");
		params.add("isshouhuo","0");
		client.post(url,params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					Toast.makeText(ConfirmOrder.this, "订单提交成功", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent();
					intent.setClassName(ConfirmOrder.this, "com.example.webstore.PayOrder");
					Bundle bundle=new Bundle();
					bundle.putString("goods_name",goods_name );
					bundle.putString("goods_id",goods_id );
					bundle.putFloat("goods_price",goods_price );
					bundle.putInt("goods_sum",goods_sum );
					bundle.putFloat("order_sum",order_sum );
					bundle.putString("store_name",store_name );
					intent.putExtras(bundle);
				    startActivity(intent);
					

				}
				if (501 == jsonResp.code) {
					Toast.makeText(ConfirmOrder.this, "订单表中已有此商品", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(ConfirmOrder.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});

		
	}

}
