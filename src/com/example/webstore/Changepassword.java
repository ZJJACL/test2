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
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Changepassword extends Activity {

	private String buyeraccount;
	private EditText edit_buyeraccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		Bundle bundle = this.getIntent().getExtras();
		buyeraccount = bundle.getString("buyeraccount");
		edit_buyeraccount = (EditText) this
				.findViewById(R.id.update111_buyeraccount);
		edit_buyeraccount.setText(AppContent.buyeraccount);

	}

	public void confirmchangeOnclick(View view) throws Exception {
		EditText oldpassword=(EditText)Changepassword.this.findViewById(R.id.old_buyerpassword);
		EditText newpassword1=(EditText)Changepassword.this.findViewById(R.id.new_buyerpassword1);
		EditText newpassword2=(EditText)Changepassword.this.findViewById(R.id.new_buyerpassword2);
		String password1=newpassword1.getText().toString().trim();
		String password2=newpassword2.getText().toString().trim();
		if(ThisAppUtil.isNone(password1)){
			Toast.makeText(Changepassword.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(ThisAppUtil.isNone(password2)){
			Toast.makeText(Changepassword.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(!oldpassword.getText().toString().trim().equals(AppContent.buyerpassword)){
			Toast.makeText(Changepassword.this, "原密码输入不正确！请重新输入", 1).show();
			oldpassword.setFocusable(true);
			newpassword1.setText("");
			newpassword2.setText("");
			return;
		}
		if(!newpassword1.getText().toString().trim().equals(newpassword2.getText().toString().trim())){
			Toast.makeText(Changepassword.this, "两次新密码输入不一致，请重新输入", 1).show();
			newpassword1.setFocusable(true);
			newpassword1.setText("");
			newpassword2.setText("");
			return;
		}
		
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/UpdatePassword";
		RequestParams params = new RequestParams();
		params.add("account",buyeraccount );
		params.add("password",newpassword1.getText().toString().trim() );
		params.add("type","buyer");

		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					Toast.makeText(Changepassword.this, "修改成功", 1).show();
				}
				if (501 == jsonResp.code) {
					Toast.makeText(Changepassword.this, "此卖家没有商品", 1).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Changepassword.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});


		

	}

}
