package com.example.smarthome.HomeSetupActivity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarthome.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class lightoff extends Activity {
	private ImageView imageView=null;
	private ToggleButton toggleButton=null;
	private Connect ctrl=new Connect();//控制器
	private String infoToSend="";//发送给电饭煲控制信息
	private ImageView imageView_close;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lightoffpage);		
        
		imageView_close = (ImageView)findViewById(R.id.ImageView_off);
		imageView_close.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		imageView=(ImageView) findViewById(R.id.imageView2);
		toggleButton=(ToggleButton)findViewById(R.id.toggleButton2);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				toggleButton.setChecked(isChecked);
				imageView.setImageResource(isChecked?R.drawable.bulb_off:R.drawable.bulb_on);				
			}

		});
	}
}
