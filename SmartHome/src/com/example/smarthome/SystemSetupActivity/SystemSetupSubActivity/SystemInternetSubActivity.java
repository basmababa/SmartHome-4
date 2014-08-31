package com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity;

import java.util.regex.Pattern;

import com.example.smarthome.R;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SystemInternetSubActivity extends Activity {

	private TextView bendiIP;
	private EditText edit_serverip;
	private EditText edit_serverport;
	private Button btn_ok;
	private Button btn_cancel;
	protected String str_serverip;//IP变量
	protected int str_serverport;//PORT变量

	private boolean checkIP(String paramString)
	  {
	    return Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$").matcher(paramString).matches();
	  }
	protected boolean checkPORT(int paramInt) {
		// TODO Auto-generated method stub
		if(paramInt <= 0 || paramInt >= 20000)
		return false;
		else
		return true;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_internet_sub);
		this.edit_serverip = ((EditText)findViewById(R.id.home_serverip_edittext));
		this.edit_serverport = ((EditText)findViewById(R.id.home_serverport_edittext));
		getConfigPreference();
	    this.btn_ok = ((Button)findViewById(R.id.ok_button));//确定按钮
	    this.btn_cancel = ((Button)findViewById(R.id.cancel_button));//取消按钮
	    this.btn_ok.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View paramAnonymousView)
	    	{
	    		SystemInternetSubActivity.this.str_serverip = SystemInternetSubActivity.this.edit_serverip.getText().toString();
	    		SystemInternetSubActivity.this.str_serverport = Integer.valueOf(SystemInternetSubActivity.this.edit_serverport.getText().toString().trim());
	       		if ((SystemInternetSubActivity.this.str_serverip == null) || (SystemInternetSubActivity.this.str_serverip.equals("")))
	       			SystemInternetSubActivity.this.isShowDiaolog("IP输入不能为空！！");
	       		else
	       		{
	       			if(!SystemInternetSubActivity.this.checkIP(SystemInternetSubActivity.this.str_serverip))
	       			{
	       				SystemInternetSubActivity.this.isShowDiaolog("IP地址输入有错！！");
	       			}
	       			else 
	       			{
	       				if (!SystemInternetSubActivity.this.checkPORT(SystemInternetSubActivity.this.str_serverport))
	       					SystemInternetSubActivity.this.isShowDiaolog("PORT地址输入有错！！");
		       			else
		       			{
		       				SystemInternetSubActivity.this.saveConfigPreference();
		       				SystemInternetSubActivity.this.isShowDiaolog("输入正确！！");
		       			}
	       			}
	       		}
	    	}
	    });
	    this.btn_cancel.setOnClickListener(new View.OnClickListener()
	    {
	    	public void onClick(View paramAnonymousView)
	    	{
	    		finish();
	    	}
	    });
	}
	
	private void isShowDiaolog(String string){
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(SystemInternetSubActivity.this);  
        builder.setMessage(string);  
        builder.setPositiveButton("知道了",  
                new android.content.DialogInterface.OnClickListener() {  
                    @Override  
                    public void onClick(DialogInterface dialog, int which) {  
                        dialog.dismiss();   
                    }  
                });  
        builder.setTitle("提示"); 
        builder.create().show();  
	}

	protected void saveConfigPreference() {
		// TODO Auto-generated method stub
		SystemSetupPreference.setServiceIp(this.str_serverip);
		SystemSetupPreference.setServicePort(this.str_serverport);
	}
	
	protected void getConfigPreference() {
		// TODO Auto-generated method stub
		this.str_serverip = SystemSetupPreference.getServiceIp();
		this.str_serverport = SystemSetupPreference.getServicePort();
		this.edit_serverip.setText(this.str_serverip);
		this.edit_serverport.setText(String.valueOf(this.str_serverport));
	} 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.system_internet_sub, menu);
		return true;
	}
}
