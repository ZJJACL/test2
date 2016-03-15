package com.example.webstore;

import java.text.DecimalFormat;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class Mywallet extends Activity {

	TextView textView;
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymoney);
		
		textView = (TextView) findViewById(R.id.money_rest);
		editText=(EditText)this.findViewById(R.id.money_ed_chongzhi);		
		
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/QueryWallet";
		RequestParams params = new RequestParams();
		params.add("account", AppContent.buyeraccount);
		
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					WalletVO vo = JSON.toJavaObject((JSONObject) jsonResp.data,
							WalletVO.class);
					
					String parten = "#.#";
					DecimalFormat decimalFormat = new DecimalFormat(parten);
					String sumprices = decimalFormat.format(vo.getRest());

					
					textView.setText(sumprices);
					
					}
				if (501 == jsonResp.code) {
					Toast.makeText(Mywallet.this, "系统繁忙！", 1).show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Mywallet.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
		
		
	}

	public void chongzhiOnclick(View view) throws Exception {
		float money_rest=Float.parseFloat(textView.getText().toString());
		float chongzhi=Float.parseFloat(editText.getText().toString());
		float restmoney=money_rest+chongzhi;
		
		
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + AppContent.IP + ":8080/webstore/UpdateWallet";
		RequestParams params = new RequestParams();
		params.add("account", AppContent.buyeraccount);
		params.add("restmoney", Float.toString(restmoney));
		
		client.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String data) {
				String jsonStr = new String(data);
				JSONResponce jsonResp = JSON.parseObject(jsonStr,
						JSONResponce.class);
				if (200 == jsonResp.code) {
					Toast.makeText(Mywallet.this, "充值成功", Toast.LENGTH_SHORT).show();
					}
				if (501 == jsonResp.code) {
					Toast.makeText(Mywallet.this, "系统繁忙！", 1).show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable t) {
				t.printStackTrace();
				Toast.makeText(Mywallet.this, "失败!", Toast.LENGTH_SHORT).show();

			}
		});
		
		String parten = "#.#";
		DecimalFormat decimalFormat = new DecimalFormat(parten);
		String sumprices = decimalFormat.format(restmoney);
		textView.setText(sumprices);
		editText.setText("0");
		
	}
	

	public void tixianOnclick(View view) throws Exception {
		

		Toast.makeText(Mywallet.this, "提现成功，以转入绑定银行卡", Toast.LENGTH_SHORT).show();
	}
}
