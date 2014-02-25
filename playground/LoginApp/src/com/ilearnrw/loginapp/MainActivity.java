package com.ilearnrw.loginapp;

import com.ilearnrw.loginapp.network.Authentication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	Authentication authentication = null;
	SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPreferences = getSharedPreferences(ACCOUNT_SERVICE, MODE_PRIVATE);
		
		checkIfLoggedIn();
	}
	
	void checkIfLoggedIn() {
		String auth = sharedPreferences.getString("auth", null);
		String refresh = sharedPreferences.getString("refresh", null);
		if (auth == null || refresh == null)
		{
			//client is not logged in
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		else {
			//client is logged in
			authentication = new Authentication(auth, refresh);
			TextView textView = (TextView) findViewById(R.id.helloworld);
			textView.setText("Tokens are:\nAuth:" + authentication.getAuth() + "\nRefresh:" + authentication.getRefresh());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void logout(View view) {
		sharedPreferences.edit().remove("auth").remove("refresh").commit();
		checkIfLoggedIn();
	}

}
