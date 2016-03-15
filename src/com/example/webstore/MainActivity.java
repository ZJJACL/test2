package com.example.webstore;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.String.AppContent;
import com.example.util.JSONResponce;
import com.example.util.ThisAppUtil;
import com.example.vo.BuyerVO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends ActionBarActivity {

	private EditText buyeraccount;
	private EditText buyerpassword;
	private EditText selleraccount;
	private EditText sellerpassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getSupportActionBar().hide();
		buyeraccount = (EditText) this.findViewById(R.id.text_main_accountedit);
		buyerpassword = (EditText) this
				.findViewById(R.id.text_main_passwardedit);
		selleraccount = (EditText) this
				.findViewById(R.id.text_main_accountedit);
		sellerpassword = (EditText) this
				.findViewById(R.id.text_main_passwardedit);

	}

	public void userRegister(View view) throws Exception {

		Intent intent = new Intent();
		intent.setClassName(MainActivity.this, "com.example.webstore.Register");
		startActivity(intent);

	}

	/**
	 * 涔板鐧婚檰
	 * 
	 * @param view
	 * @throws Exception
	 */
	public void buyerLoginOnclick(View view) throws Exception {

		String account = buyeraccount.getText().toString().trim();
		String password = buyerpassword.getText().toString().trim();
		AppContent.buyeraccount = account;
		if (ThisAppUtil.isNone(account)) {
			Toast.makeText(MainActivity.this, "璐︽埛涓嶈兘涓虹┖锛�", 1).show();
			return;
		}
		if (ThisAppUtil.isNone(password)) {
			Toast.makeText(MainActivity.this, "瀵嗙爜涓嶈兘涓虹┖锛�", 1).show();
			return;
		}

		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/LoginBuyer";
		RequestParams params = new RequestParams();
		params.add("buyeraccount", account);
		params.add("buyerpassword", password);
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					BuyerVO vo = JSON.toJavaObject((JSONObject) jsonResp.data,
							BuyerVO.class);

					Intent intent = new Intent();
					intent.setClassName(MainActivity.this,
							"com.example.webstore.HomePage");
					startActivity(intent);
					Toast.makeText(MainActivity.this, "鐧婚檰鎴愬姛",
							Toast.LENGTH_SHORT).show();

				}
				if (501 == jsonResp.code) {
					Toast.makeText(MainActivity.this, "瀵嗙爜閿欒",
							Toast.LENGTH_SHORT).show();
					return;
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(MainActivity.this, "澶辫触!", 1).show();

			}
		});

	}

	/**
	 * 鍗栧鐧婚檰
	 * 
	 * @param view
	 * @throws Exception
	 */

	public void sellerLoginOnclick(View view) throws Exception {

		Intent intent = new Intent();
		intent.setClassName(MainActivity.this, "com.example.webstore.Seller");
		startActivity(intent);
		Toast.makeText(MainActivity.this, "鐧婚檰鎴愬姛", Toast.LENGTH_SHORT).show();

		String account = selleraccount.getText().toString().trim();
		String password = sellerpassword.getText().toString().trim();

		if (ThisAppUtil.isNone(account)) {
			Toast.makeText(MainActivity.this, "璐︽埛涓嶈兘涓虹┖锛�", 1).show();
			return;
		}
		if (ThisAppUtil.isNone(account)) {
			Toast.makeText(MainActivity.this, "瀵嗙爜涓嶈兘涓虹┖锛�", 1).show();
			return;
		}

		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/LoginSeller";
		RequestParams params = new RequestParams();
		params.add("selleraccount", account);
		params.add("sellerpassword", password);
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					BuyerVO vo = JSON.toJavaObject((JSONObject) jsonResp.data,
							BuyerVO.class);

					Intent intent = new Intent();
					intent.setClassName(MainActivity.this,
							"com.example.webstore.Seller");
					startActivity(intent);
					Toast.makeText(MainActivity.this, "鐧婚檰鎴愬姛",
							Toast.LENGTH_SHORT).show();

				}
				if (501 == jsonResp.code) {
					Toast.makeText(MainActivity.this, "瀵嗙爜閿欒",
							Toast.LENGTH_SHORT).show();
					return;
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(MainActivity.this, "澶辫触!", 1).show();

			}
		});

	}
}
