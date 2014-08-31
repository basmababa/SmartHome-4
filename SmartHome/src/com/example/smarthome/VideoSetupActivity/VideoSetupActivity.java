package com.example.smarthome.VideoSetupActivity;

import com.example.smarthome.R;
import com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity.SystemAboutSubActivity;
import com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity.SystemInternetSubActivity;
import com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity.TestActivity;
import com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity.VideoPinDaoYiSubActivity;

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

public class VideoSetupActivity extends ActivityGroup implements View.OnClickListener{

	private RelativeLayout shezhineirong;
	private Button btn001;
	private Button btn004;
	private Button btn003;
	private Button btn006;
	private Button btn005;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//全屏显示，隐藏标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_setup);
		this.shezhineirong = ((RelativeLayout)findViewById(R.id.shezhineirong));//设置内容
	    this.btn001 = ((Button)findViewById(R.id.btn001_video));
	    this.btn003 = ((Button)findViewById(R.id.btn003_video));
	    this.btn004 = ((Button)findViewById(R.id.btn004_video));
	    this.btn005 = ((Button)findViewById(R.id.btn005_video));
	    this.btn006 = ((Button)findViewById(R.id.btn006_video));
	    this.btn001.setOnClickListener(this);
	    this.btn003.setOnClickListener(this);
	    this.btn004.setOnClickListener(this);
	    this.btn005.setOnClickListener(this);
	    this.btn006.setOnClickListener(this);
	    xuanzeduiyingdeneirong(0);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId())
	    {
	    	case R.id.btn001_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(0);
	  	      	break;
	    	}
	    	case R.id.btn003_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(1);
	  	      break;
	    	}
	    	case R.id.btn004_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	    		this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(3);
	  	      	break;
	    	}
	    	case R.id.btn005_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	    		this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	xuanzeduiyingdeneirong(2);
	  	      	break;
	    	}
	    	case R.id.btn006_video:
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
	    		localIntent = new Intent(this, VideoPinDaoYiSubActivity.class);//频道一
	    		localIntent.putExtra("pindao", "1");
	    		break;
	    	}
	    	case 1:
	    	{
	    		localIntent = new Intent(this, VideoPinDaoYiSubActivity.class);//频道一
	    		localIntent.putExtra("pindao", "2");
	    		break;
	    	}
	    	case 2:
	    	{
	    		localIntent = new Intent(this, VideoPinDaoYiSubActivity.class);//频道一
	    		localIntent.putExtra("pindao", "3");
	    		break;
	    	}
	    	case 3:
	    	{
	    		localIntent = new Intent(this, VideoPinDaoYiSubActivity.class);//频道一
	    		localIntent.putExtra("pindao", "4");
	    		break;
	    	}
	    	case 4:
	    	{
	    		localIntent = new Intent(this, VideoPinDaoYiSubActivity.class);//频道一
	    		localIntent.putExtra("pindao", "5");
	    		break;
	    	}
	    	default:
	    }
	    this.shezhineirong.removeAllViews();
	    localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window localWindow = VideoSetupActivity.this.getLocalActivityManager().startActivity("subActivity", localIntent);
		this.shezhineirong.addView(localWindow.getDecorView(), -1, -1);
	}
}
