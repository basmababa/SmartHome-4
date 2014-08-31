package com.example.smarthome.SystemSetupActivity;

import com.example.smarthome.R;
import com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity.SystemAboutSubActivity;
import com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity.SystemInternetSubActivity;
import com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity.TestActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SystemSetupActivity extends ActivityGroup implements View.OnClickListener{

	private RelativeLayout shezhineirong;
	private Button btn001;
	private Button btn004;
	private Button btn003;
	private Button btn006;
	private Button btn005;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_setup);
		this.shezhineirong = ((RelativeLayout)findViewById(R.id.shezhineirong));//设置内容
	    this.btn001 = ((Button)findViewById(R.id.btn001));
	    this.btn003 = ((Button)findViewById(R.id.btn003));
	    this.btn004 = ((Button)findViewById(R.id.btn004));
	    this.btn005 = ((Button)findViewById(R.id.btn005));
	    this.btn006 = ((Button)findViewById(R.id.btn006));
	    this.btn001.setOnClickListener(this);
	    this.btn003.setOnClickListener(this);
	    this.btn004.setOnClickListener(this);
	    this.btn005.setOnClickListener(this);
	    this.btn006.setOnClickListener(this);
	    xuanzeduiyingdeneirong(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.system_setup, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId())
	    {
	    	case R.id.btn001:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(0);
	  	      	break;
	    	}
	    	case R.id.btn003:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(1);
	  	      break;
	    	}
	    	case R.id.btn004:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	    		this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(2);
	  	      	break;
	    	}
	    	case R.id.btn005:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	    		this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(3);
	  	      	break;
	    	}
	    	case R.id.btn006:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	xuanzeduiyingdeneirong(4);
	  	      	break;	
	    	}
	    	default:
	    }
	}

	private void xuanzeduiyingdeneirong(int i){
		// TODO Auto-generated method stub
	    Intent localIntent = null;
	    switch (i)
	    {
	    	case 0:
	    	{
	    		localIntent = new Intent(this, SystemInternetSubActivity.class);//网络设置
	    		break;
	    	}
	    	case 1:
	    	{
	    		Toast.makeText(this, "扩展选项", Toast.LENGTH_LONG).show();//扩展选项
	    		localIntent = new Intent(this, TestActivity.class);
	    		break;
	    	}
	    	case 2:
	    	{
	    		Toast.makeText(this, "扩展选项", Toast.LENGTH_LONG).show();
	    		localIntent = new Intent(this, TestActivity.class);
	    		break;
	    	}
	    	case 3:
	    	{
	    		Toast.makeText(this, "扩展选项", Toast.LENGTH_LONG).show();
	    		localIntent = new Intent(this, TestActivity.class);
	    		break;
	    	}
	    	case 4:
	    	{
	    		localIntent = new Intent(this, SystemAboutSubActivity.class);//关于信息
	    		break;
	    	}
	    	default:
	    }
	    this.shezhineirong.removeAllViews();
	    localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window localWindow = SystemSetupActivity.this.getLocalActivityManager().startActivity("subActivity", localIntent);
		this.shezhineirong.addView(localWindow.getDecorView(), -1, -1);
	}
}
