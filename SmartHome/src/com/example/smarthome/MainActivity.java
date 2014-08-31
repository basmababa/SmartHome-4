package com.example.smarthome;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.HomeModeActivity.HomeModeActivity;
import com.example.smarthome.HomeSetupActivity.HomeControlActivity;
import com.example.smarthome.SystemSetupActivity.SystemSetupActivity;
import com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity.SystemSetupPreference;
import com.example.smarthome.VideoSetupActivity.Test1Activity;

public class MainActivity extends Activity implements View.OnClickListener
{

	private Button btn_app_exit;// 退出按钮
	private Button btn_devicecontrol;// 智能家居控制
	private Button btn_homedefence;//
	private Button btn_homesetup;// 家居设置
	// private Button btn_homeEntertainment;//家居娱乐
	private Button btn_homemode;// 模式控制
	private TextView datetimelable;// 日期时间
	private TextView minutelable;// 分钟
	private TextView hourslable;// 时钟

	// 建立广播接收时间
	private BroadcastReceiver mIntentReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context arg0, Intent arg1)
		{
			// TODO Auto-generated method stub
			MainActivity.this.gengxinTime();
		}
	};
	private String hour;// 时
	private String minute;// 分

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.btn_devicecontrol = ((Button) findViewById(R.id.btn_devicecontrol));// 设备控制
		this.btn_app_exit = ((Button) findViewById(R.id.btn_ExitApp));// 退出按钮
		this.btn_homedefence = ((Button) findViewById(R.id.btn_homevideo));// 家庭安防
		this.btn_homemode = ((Button) findViewById(R.id.btn_homemode));// 模式控制
		// this.btn_homeEntertainment =
		// ((Button)findViewById(R.id.btn_homeEntertainment));//家居娱乐
		this.btn_homesetup = ((Button) findViewById(R.id.btn_homesetup));// 家居设置
		this.datetimelable = ((TextView) findViewById(R.id.dataTimeLabel));
		this.hourslable = ((TextView) findViewById(R.id.hours));
		this.minutelable = ((TextView) findViewById(R.id.minutes));
		this.btn_devicecontrol.setOnClickListener(this);// 智能家居监听
		this.btn_app_exit.setOnClickListener(this);// 退出监听
		this.btn_homedefence.setOnClickListener(this);// 家庭安防监听
		this.btn_homemode.setOnClickListener(this);// 模式监听
		// this.btn_homeEntertainment.setOnClickListener(this);//家居娱乐监听
		this.btn_homesetup.setOnClickListener(this);// 家居设置监听
		SystemSetupPreference.init(this);
		gengxinTime();// 更新当前时间
	}

	private void gengxinTime()
	{
		// TODO Auto-generated method stub
		Calendar localCalendar = Calendar.getInstance();// 获得日历
		String str = new SimpleDateFormat("yyyy年MM月dd日  E").format(new Date())
				.replace("周", "星期");// 时间格式
		this.datetimelable.setText(str);// 设置时间
		if (localCalendar.get(11) >= 10)
		{
			this.hour = String.valueOf(localCalendar.get(11));// 获取显示显示格式
		} else
		{
			this.hour = ("0" + String.valueOf(localCalendar.get(11)));
		}
		if (localCalendar.get(12) >= 10)// 获取分钟的显示格式
		{
			this.minute = String.valueOf(localCalendar.get(12));
		} else
		{
			this.minute = ("0" + String.valueOf(localCalendar.get(12)));
		}
		this.hourslable.setText(this.hour);
		this.minutelable.setText(this.minute);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{

		case R.id.btn_devicecontrol:// 设备控制
			{
				Toast.makeText(this, "家居控制", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, HomeControlActivity.class));
				break;
			}
		case R.id.btn_ExitApp:// 退出按钮
			{
				isExitDiaolog(getResources().getString(R.string.exit_question));// 退出提示按钮
				break;
			}
		case R.id.btn_homevideo:// 家庭监控
			{
				Toast.makeText(this, "家居视频", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, Test1Activity.class));
				break;
			}
		case R.id.btn_homemode:// 模式控制
			{
				Toast.makeText(this, "模式控制", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, HomeModeActivity.class));
				break;
			}
		// case R.id.btn_homeEntertainment://家居娱乐
		// {
		// Toast.makeText(this, "家居娱乐", Toast.LENGTH_LONG).show();
		// startActivity(new Intent(this, MediaPlaybackActivity.class));
		// break;
		// }
		case R.id.btn_homesetup:// 家居设置
			{
				Toast.makeText(this, "家居设置", Toast.LENGTH_LONG).show();
				startActivity(new Intent(this, SystemSetupActivity.class));
				break;
			}
		default:
			startActivity(new Intent(this, MainActivity.class));
		}
	}

	private void isExitDiaolog(String string)
	{
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage(string);
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						// MainActivity.this.finish();
						// System.exit(1);
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(this.mIntentReceiver);
		SmartHomebaseApp app = (SmartHomebaseApp) getApplication();
		app.closeSocket();

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		IntentFilter localIntentFilter1 = new IntentFilter();
		localIntentFilter1.addAction("android.intent.action.TIME_TICK");
		localIntentFilter1.addAction("android.intent.action.TIME_SET");
		localIntentFilter1.addAction("android.intent.action.DATE_CHANGED");
		registerReceiver(this.mIntentReceiver, localIntentFilter1, null, null);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			isExitDiaolog(getResources().getString(R.string.exit_question));
			return true;
		}
		return true;
	}
}
