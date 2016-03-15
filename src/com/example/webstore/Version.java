package com.example.webstore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Version extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.version_about);
	}

	public void checkversionOnclik(View view) {

		Toast.makeText(Version.this, "baga yalu", Toast.LENGTH_LONG).show();

	}

}
