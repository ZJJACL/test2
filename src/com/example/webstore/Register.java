package com.example.webstore;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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

public class Register extends Activity {

	private EditText registeraccount;
	private EditText registerpassword1;
	private EditText registerpassword2;
	private RadioButton user_buyer;
	private RadioButton user_seller;
	private RadioGroup group;
	private String usertype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		registeraccount = (EditText) this.findViewById(R.id.registeraccount);
		registerpassword1 = (EditText) this.findViewById(R.id.registerpassword);
		registerpassword2 = (EditText) this.findViewById(R.id.registerconfirmpassword);
		group = (RadioGroup)this.findViewById(R.id.user_group);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId)
				{
				case R.id.user_buyer:
					usertype="买家";
					break;
				case R.id.user_seller:
					usertype="卖家";
					break;
				
				
				}
				
			}
		 
		});

	}

	/**
	 * 注册按钮
	 * 
	 * @param view
	 * @throws Exception
	 */
	public void confirmregisterOnclick(View view) throws Exception {

		String account = registeraccount.getText().toString().trim();
		String password1 = registerpassword1.getText().toString().trim();
		String password2 = registerpassword2.getText().toString().trim();

	

		if (ThisAppUtil.isNone(account)) {
			Toast.makeText(Register.this, "账户不能为空！", 1).show();
			return;
		}

		if (ThisAppUtil.isNone(password1)) {
			Toast.makeText(Register.this, "密码不能为空！", 1).show();
			return;
		}

		if (ThisAppUtil.isNone(password2)) {
			Toast.makeText(Register.this, "密码不能为空！", 1).show();
			return;
		}

		if (!password1.equals(password2)) {
			Toast.makeText(Register.this, "两次输入密码不一致！", 1).show();
			return;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/Register";
		RequestParams params = new RequestParams();
		params.add("registeraccount", account);
		params.add("registerpassword1", password1);
        params.add("usertype", usertype);
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
					intent.setClassName(Register.this,
							"com.example.webstore.MainActivity");
					startActivity(intent);
					Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT)
							.show();

				}
				if (501 == jsonResp.code) {
					Toast.makeText(Register.this, "账号已存在", Toast.LENGTH_SHORT)
							.show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Register.this, "系统繁忙", 1).show();

			}
		});
		
		registerwallet();

	}

	public void registerwallet(){
		
		String account1 = registeraccount.getText().toString().trim();
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/RegisterWallet";
		RequestParams params = new RequestParams();
		params.add("buyeraccount", account1);
		
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					

					Intent intent = new Intent();
					intent.setClassName(Register.this,
							"com.example.webstore.HomePage");
					startActivity(intent);
					

				}
				if (501 == jsonResp.code) {
					Toast.makeText(Register.this, "账号已存在", Toast.LENGTH_SHORT)
							.show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Register.this, "系统繁忙", 1).show();

			}
		});
	}
	
}
