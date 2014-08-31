package com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity;

import com.example.smarthome.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SystemAboutSubActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_about_sub);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.system_about_sub, menu);
		return true;
	}

}
