package com.example.webstore;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.String.AppContent;
import com.example.util.JSONResponce;
import com.example.vo.CartVO;
import com.example.vo.OrderVO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class HomePage extends Activity {

	
	private String regoods_name;
	private String regoods_id;
	private String regoods_sum;
	private String regoods_price;
	private String restore_name;


	private ListView lv;
	private ListView lv2;
	private TabHost host;
	private TextView sumprice, buyeraccount;
	private float sum = 0;
	JSONArray array = new JSONArray();

	JSONArray array2 = new JSONArray();

	private CheckBox checkall;
	private ArrayList<CheckBox> arrayList = new ArrayList<CheckBox>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		buyeraccount = (TextView) this.findViewById(R.id.manage_buyeraccount);
		buyeraccount.setText(AppContent.buyeraccount);

		checkall = (CheckBox) this.findViewById(R.id.checkAll);
		sumprice = (TextView) this.findViewById(R.id.sum_price);

		/**
		 * 加载Tabhost
		 */
		host = (TabHost) this.findViewById(R.id.main_tab);
		host.setup();

		TabSpec firstTab = host.newTabSpec("首页");
		firstTab.setIndicator("首页",
				getResources().getDrawable(R.drawable.abc_btn_radio_material));

		firstTab.setContent(R.id.HomePage);

		host.addTab(firstTab);

		/**
		 * 购物车
		 */
		this.lv = (ListView) this.findViewById(R.id.main_list);

		lv.setAdapter(new BaseAdapter() {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return array.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getLayoutInflater().inflate(
							R.layout.shoppingcartadd, null);
				}
				CartVO vo = new CartVO();
				vo = JSON.toJavaObject((JSONObject) array.get(position),
						CartVO.class);

				TextView textView = (TextView) convertView
						.findViewById(R.id.main_test1);
				textView.setText(vo.getGoods_name());
				TextView textView1 = (TextView) convertView
						.findViewById(R.id.main_test2);
				TextView textView2 = (TextView) convertView
						.findViewById(R.id.car_price);
				textView.setText(Float.toString(vo.getGoods_price()));
				EditText textView3 = (EditText) convertView
						.findViewById(R.id.car_quantity);
				Button button1 = (Button) convertView
						.findViewById(R.id.car_down);
				Button button2 = (Button) convertView
						.findViewById(R.id.car_add);
				TextView textView5 = (TextView) convertView
						.findViewById(R.id.car_goods_id);
				textView5.setText(vo.getGoods_id());
				
				button2.setTag(textView3);
				button1.setTag(textView3);

				CheckBox checkBox = (CheckBox) convertView
						.findViewById(R.id.check_one);
				arrayList.add(checkBox);

				return convertView;
			}

		});

		TabSpec sencondTab = host.newTabSpec("购物车");
		sencondTab.setIndicator("购物车",
				getResources().getDrawable(R.drawable.ic_launcher));
		sencondTab.setContent(R.id.shoppingcart);
		host.addTab(sencondTab);

		/**
		 * 订单视图
		 */
		this.lv2 = (ListView) this.findViewById(R.id.order_list);
		lv2.setAdapter(new BaseAdapter() {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return array2.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int position, View convertView2,
					ViewGroup parent) {
				if (convertView2 == null) {
					convertView2 = getLayoutInflater().inflate(
							R.layout.order_bbbbb, null);
				}
				OrderVO vo = new OrderVO();
				vo = JSON.toJavaObject((JSONObject) array2.get(position),
						OrderVO.class);

				TextView textView2 = (TextView) convertView2
						.findViewById(R.id.order_one);
				textView2.setText(vo.getGoods_name());
				TextView textView3 = (TextView) convertView2
						.findViewById(R.id.order_two);
				textView3.setText(vo.getStore_name());

				TextView textView4 = (TextView) convertView2
						.findViewById(R.id.nouse_renmingbi);
				TextView textView44 = (TextView) convertView2
						.findViewById(R.id.check_order_sum);
				textView44.setText(Float.toString(vo.getOrder_sum()));

				TextView textView5 = (TextView) convertView2
						.findViewById(R.id.nouser_price);
				TextView textView55 = (TextView) convertView2
						.findViewById(R.id.check_order_price);
				textView55.setText(Float.toString(vo.getGoods_price()));

				TextView textView6 = (TextView) convertView2
						.findViewById(R.id.nouse_quantity);
				TextView textView66 = (TextView) convertView2
						.findViewById(R.id.check_order_quantity);
				textView66.setText(Integer.toString(vo.getGoods_sum()));

				TextView textView7 = (TextView) convertView2
						.findViewById(R.id.nouse_phone);
				TextView textView77 = (TextView) convertView2
						.findViewById(R.id.check_order_phone);
				textView77.setText(vo.getPhone());

				TextView textView8 = (TextView) convertView2
						.findViewById(R.id.nouse_address);
				TextView textView88 = (TextView) convertView2
						.findViewById(R.id.check_order_address);
				textView88.setText(vo.getAddress());

				TextView textView9 = (TextView) convertView2
						.findViewById(R.id.nouse_wuliu);
				TextView textView99 = (TextView) convertView2
						.findViewById(R.id.check_order_wuliu);
				if (vo.getIsshouhuo().equals("0")) {
					textView99.setText("未收货");

				} else {
					textView99.setText("已确认收货");
				}

				return convertView2;
			}

		});

		TabSpec thirdTab = host.newTabSpec("订单");
		thirdTab.setIndicator("订单",
				getResources().getDrawable(R.drawable.ic_launcher));
		thirdTab.setContent(R.id.order_lister);
		host.addTab(thirdTab);

		TabSpec fourthTab = host.newTabSpec("管理");
		fourthTab.setIndicator("管理",
				getResources().getDrawable(R.drawable.ic_launcher));
		fourthTab.setContent(R.id.management);
		host.addTab(fourthTab);

	}

	public void CartRefresh(View view) throws Exception {

		showcart();
	}

	/**
	 * 购物车 勾选all 按钮
	 */
	double m = 1;

	public void checkallOnclick(View view) {

		CheckBox ch = (CheckBox) view.getTag();

		if (m % 2 == 0) {
			for (int i = arrayList.size() - 1; i >= 0; i--) {

				CheckBox box = arrayList.get(i);
				if (box.isChecked()) {
					box.performClick();
				}

			}
			sumprice.setText("0.00");
		} else {
			for (int i = arrayList.size() - 1; i >= 0; i--) {

				CheckBox box = arrayList.get(i);
				if (!box.isChecked()) {
					box.performClick();
				}

			}

		}
		m++;

	}

	/**
	 * 版本信息按钮
	 */
	public void versionOnclick(View view) throws Exception {
		Intent intent = new Intent();
		intent.setClassName(HomePage.this, "com.example.webstore.Version");
		startActivity(intent);
	}

	/**
	 * 菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_cancel:
			System.exit(0);
			break;
		case R.id.menu_exit:
			break;
		}
		return true;
	}

	/**
	 * 我的钱包按钮
	 */
	public void mywalletOnclick(View view) throws Exception {
		Intent intent = new Intent();
		intent.setClassName(HomePage.this, "com.example.webstore.Mywallet");
		startActivity(intent);

	}

	/**
	 * 结算按钮
	 */
	public void jiesuanOnclick(View view) throws Exception {

		

		
		Intent intent = new Intent();
		intent.setClassName(HomePage.this, "com.example.webstore.ConfirmOrder");
		Bundle bundle = new Bundle();

		 bundle.putString("goods_name", regoods_name);
		 bundle.putFloat("goods_price",Float.parseFloat(regoods_price));
		 bundle.putString("goods_id", regoods_id);
		 bundle.putInt("goods_sum",Integer.parseInt(regoods_sum));
		 bundle.putString("store_name", restore_name);
		 intent.putExtras(bundle);
		 startActivity(intent);

	}

	/**
	 * 购物车勾选
	 */

	public void checkoneOnclick(View view) throws Exception {
		ViewGroup parentView = (ViewGroup) view.getParent();

		TextView te = (TextView) parentView.findViewById(R.id.car_price);
		
		TextView rregoods_name = (TextView) parentView.findViewById(R.id.main_test1);
		TextView rregoods_sum = (TextView) parentView.findViewById(R.id.car_quantity);
		TextView rregoods_id = (TextView) parentView.findViewById(R.id.car_goods_id);
		
		regoods_id=rregoods_id.getText().toString();
		regoods_name=rregoods_name.getText().toString();
		regoods_price=te.getText().toString();
		regoods_sum=rregoods_sum.getText().toString();
		restore_name="二号店";
		
		EditText ed = (EditText) parentView.findViewById(R.id.car_quantity);

		CheckBox box = (CheckBox) view;
		if (box.isChecked()) {
			sum += Float.parseFloat(te.getText().toString())
					* Integer.parseInt(ed.getText().toString());
		} else {
			sum -= Float.parseFloat(te.getText().toString())
					* Integer.parseInt(ed.getText().toString());
		}

		String parten = "#.##";
		DecimalFormat decimalFormat = new DecimalFormat(parten);
		String sumprices = decimalFormat.format(sum);

		sumprice.setText(sumprices);

	}

	public void showorderOnclick(View view) throws Exception {

		showorder();

	}

	public void showorder() {

		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/QueryOrder";
		RequestParams params = new RequestParams();
		params.add("account", AppContent.buyeraccount);
		params.add("type", "buyer");
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					array2 = (JSONArray) jsonResp.getData();

					lv2.setAdapter(new BaseAdapter() {

						@Override
						public int getCount() {
							// TODO Auto-generated method stub
							return array2.size();
						}

						@Override
						public Object getItem(int position) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public long getItemId(int position) {
							// TODO Auto-generated method stub
							return 0;
						}

						@Override
						public View getView(int position, View convertView2,
								ViewGroup parent) {
							if (convertView2 == null) {
								convertView2 = getLayoutInflater().inflate(
										R.layout.order_bbbbb, null);
							}
							OrderVO vo = new OrderVO();
							vo = JSON.toJavaObject(
									(JSONObject) array2.get(position),
									OrderVO.class);
							TextView textView2 = (TextView) convertView2
									.findViewById(R.id.order_one);
							textView2.setText(vo.getGoods_name());
							TextView textView3 = (TextView) convertView2
									.findViewById(R.id.order_two);
							textView3.setText(vo.getStore_name());

							TextView textView4 = (TextView) convertView2
									.findViewById(R.id.nouse_renmingbi);
							TextView textView44 = (TextView) convertView2
									.findViewById(R.id.check_order_sum);
							textView44.setText(Float.toString(vo.getOrder_sum()));

							TextView textView5 = (TextView) convertView2
									.findViewById(R.id.nouser_price);
							TextView textView55 = (TextView) convertView2
									.findViewById(R.id.check_order_price);
							textView55.setText(Float.toString(vo
									.getGoods_price()));

							TextView textView6 = (TextView) convertView2
									.findViewById(R.id.nouse_quantity);
							TextView textView66 = (TextView) convertView2
									.findViewById(R.id.check_order_quantity);
							textView66.setText(Integer.toString(vo
									.getGoods_sum()));

							TextView textView7 = (TextView) convertView2
									.findViewById(R.id.nouse_phone);
							TextView textView77 = (TextView) convertView2
									.findViewById(R.id.check_order_phone);
							textView77.setText(vo.getPhone());

							TextView textView8 = (TextView) convertView2
									.findViewById(R.id.nouse_address);
							TextView textView88 = (TextView) convertView2
									.findViewById(R.id.check_order_address);
							textView88.setText(vo.getAddress());

							TextView textView9 = (TextView) convertView2
									.findViewById(R.id.nouse_wuliu);
							TextView textView99 = (TextView) convertView2
									.findViewById(R.id.check_order_wuliu);
							if (vo.getIsshouhuo().equals("0")) {
								textView99.setText("未收货");

							} else {
								textView99.setText("已确认收货");
							}

							return convertView2;
						}

					});
				

				}
				if (501 == jsonResp.code) {
					Toast.makeText(HomePage.this, "系统繁忙！", 1).show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(HomePage.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});

	}

	public void showcart() {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP
				+ ":8080/webstore/QueryShoppingCart";
		RequestParams params = new RequestParams();
		params.add("buyeraccount", AppContent.buyeraccount);
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					array = (JSONArray) jsonResp.getData();

					lv.setAdapter(new BaseAdapter() {

						@Override
						public int getCount() {
							// TODO Auto-generated method stub
							return array.size();
						}

						@Override
						public Object getItem(int position) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public long getItemId(int position) {
							// TODO Auto-generated method stub
							return 0;
						}

						@Override
						public View getView(int position, View convertView,
								ViewGroup parent) {
							if (convertView == null) {
								convertView = getLayoutInflater().inflate(
										R.layout.shoppingcartadd, null);
							}
							CartVO vo = new CartVO();
							vo = JSON.toJavaObject(
									(JSONObject) array.get(position),
									CartVO.class);

							TextView textView = (TextView) convertView
									.findViewById(R.id.main_test1);
							textView.setText(vo.getGoods_name());
							TextView textView1 = (TextView) convertView
									.findViewById(R.id.main_test2);
							TextView textView2 = (TextView) convertView
									.findViewById(R.id.car_price);
							textView2.setText(Float.toString(vo
									.getGoods_price()));
							EditText textView3 = (EditText) convertView
									.findViewById(R.id.car_quantity);
							Button button1 = (Button) convertView
									.findViewById(R.id.car_down);
							Button button2 = (Button) convertView
									.findViewById(R.id.car_add);
							TextView textView5 = (TextView) convertView
									.findViewById(R.id.car_goods_id);
							textView5.setText(vo.getGoods_id());
							button2.setTag(textView3);
							button1.setTag(textView3);

							CheckBox checkBox = (CheckBox) convertView
									.findViewById(R.id.check_one);
							arrayList.add(checkBox);

							return convertView;
						}

					});
				

				}
				if (501 == jsonResp.code) {
					Toast.makeText(HomePage.this, "购物车中没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(HomePage.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
	}

	/**
	 * 点击computer图片
	 */
	public void computerOnclick(View view) throws Exception {
		String type = "computer";
		Intent intent = new Intent();
		intent.setClassName(HomePage.this, "com.example.webstore.Store");
		Bundle bundle = new Bundle();
		bundle.putString("goodname", type);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void iceOnclick(View view) throws Exception {
		String type = "bingxiang";
		Intent intent = new Intent();
		intent.setClassName(HomePage.this, "com.example.webstore.Store");
		Bundle bundle = new Bundle();
		bundle.putString("goodname", type);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	public void washOnclick(View view) throws Exception {
		String type = "xiyiji";
		Intent intent = new Intent();
		intent.setClassName(HomePage.this, "com.example.webstore.Store");
		Bundle bundle = new Bundle();
		bundle.putString("goodname", type);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	int i;

	/**
	 * 购物车 “-”
	 */
	public void cardownOnclick(View view) throws Exception {
		EditText et = (EditText) view.getTag();
		if (Integer.parseInt(et.getText().toString().trim()) >= 1) {
			et.setText((Integer.parseInt(et.getText().toString().trim()) - 1)
					+ "");
		} else {
			Toast.makeText(HomePage.this, "购物车的数量不能低于1件", 1).show();

		}

	}

	/**
	 * 购物车 “+”
	 */
	public void caraddOnclick(View view) throws Exception {
		EditText et = (EditText) view.getTag();
		et.setText((Integer.parseInt(et.getText().toString().trim()) + 1) + "");

	}

	/**
	 * 账户管理按钮
	 */
	public void changepasswordOnclick(View view) throws Exception {

		Intent intent = new Intent();
		intent.setClassName(HomePage.this,
				"com.example.webstore.Changepassword");
		Bundle bundle = new Bundle();
		bundle.putString("buyeraccount", AppContent.buyeraccount);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	/**
	 * 收货地址按钮
	 */
	public void addressOnclick(View view) throws Exception {

		Intent intent = new Intent();
		intent.setClassName(HomePage.this, "com.example.webstore.Address");

		startActivity(intent);
	}

}
