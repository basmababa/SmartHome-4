package com.example.smarthome.HomeSetupActivity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smarthome.R;

public class AddDeviceActivity extends Activity implements View.OnClickListener
{

	private Spinner deviceStyleSpinner;// 设备类型
	private Context context;
	private ImageView yesImageView;// 确认添加
	private ImageView backImageView;// 返回按钮
	private ImageButton typeImageButton;// 设备类型下拉按钮
	private EditText devicetypeEditText;// 设备类型
	private EditText deviceidEditText;// 设备id
	private EditText devicenameEditText;// 设备名称
	private EditText devicebrandEditText;// 设备品牌

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_device);

		context = this;// 当前上下文
		this.deviceidEditText = (EditText) findViewById(R.id.deviceid);// 设备ID输入
		this.devicenameEditText = (EditText) findViewById(R.id.devicename);// 设备名称输入框
		this.devicebrandEditText = (EditText) findViewById(R.id.devicebrand);// 设备名称输入框
		this.devicetypeEditText = (EditText) findViewById(R.id.devicestyle);// 设备类型输入框
		this.typeImageButton = (ImageButton) findViewById(R.id.devicestylebutton);// 设备类型选择按钮
		this.yesImageView = ((ImageView) findViewById(R.id.yesImageView));// 删除设备按钮
		this.yesImageView.setOnClickListener(this);
		this.backImageView = ((ImageView) findViewById(R.id.backImageView));// 删除设备按钮
		this.backImageView.setOnClickListener(this);
		initlistview();
	}

	private void initlistview()
	{
		// TODO Auto-generated method stub
		// 设备类型下拉
		final PopupWindow typePopupWindow = new PopupWindow(this);// 创建设备类型下拉对话框
		typePopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.device_middle_bg));// 设置设备类型下拉对话框的背景
		typePopupWindow.setWidth(220);// 设置宽度
		typePopupWindow.setHeight(200);// 设置高度
		typePopupWindow.setFocusable(true);
		ListView typeListView = new ListView(this);
		typeListView.setAlwaysDrawnWithCacheEnabled(true);
		typeListView.setCacheColorHint(Color.TRANSPARENT);
		final String[] arrayOfString = new String[7];// 设备类型数组
		arrayOfString[0] = context.getString(R.string.dev_name_air);
		arrayOfString[1] = context.getString(R.string.dev_name_gated);
		arrayOfString[2] = context.getString(R.string.dev_name_fan);
		arrayOfString[3] = context.getString(R.string.dev_name_thermostat);
		arrayOfString[4] = context.getString(R.string.dev_name_light);
		arrayOfString[5] = context.getString(R.string.dev_name_socket);
		arrayOfString[6] = context.getString(R.string.dev_name_other);
		ArrayAdapter typeArrayAdapter = new ArrayAdapter(this,
				R.layout.simple_list_item, arrayOfString);// 设备类型适配器
		typeListView.setAdapter(typeArrayAdapter);// 为typeListView关联数据
		typeListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()// 为设备类型里面的子项添加监听
				{
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong)
					{
						devicetypeEditText
								.setText(arrayOfString[paramAnonymousInt]);// 获得点击的设备类型，设置设备类型输入为点击的设备类型
						typePopupWindow.dismiss();// 设备类型列表消失
					}
				});
		typePopupWindow.setContentView(typeListView);// 把设备类型列表添加到typePopupWindow对话框中去
		View.OnClickListener typeImageButtonOnClickListener = new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				devicetypeEditText.requestFocus();
				typePopupWindow.showAsDropDown(devicetypeEditText);// 类型选择框显示在类型输入框的下方
			}
		};
		typeImageButton.setOnClickListener(typeImageButtonOnClickListener);// 为设备类型下拉按钮添加监听
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_device, menu);
		return true;
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.backImageView:// 返回
			{
				finish();
				break;
			}
		case R.id.yesImageView:// 确定添加 调用addDevice
			{
				adddevice();
				break;
			}
		default:
		}
	}

	private void adddevice()
	{

		/**
		 * get input
		 */
		String str1 = deviceidEditText.getText().toString();// ID
		String str2 = devicenameEditText.getText().toString();// 设备名字
		String str3 = devicetypeEditText.getText().toString();// 设备类型
		String str4 = devicebrandEditText.getText().toString();// 设备品牌
		if ((str1.equals("")) || (str2.equals(""))
				|| (str3.equals("") || str4.equals("")))
		{// 有得输入为空
			Log.i("1", "添加设备不能为空"); // INFO
			Toast toast = Toast
					.makeText(
							getApplicationContext(),
							AddDeviceActivity.this
									.getString(R.string.alertdialog_error_adddev_noempty),
							Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) toast.getView();
			ImageView imageCodeProject = new ImageView(getApplicationContext());
			imageCodeProject
					.setImageResource(R.drawable.customdialog_default_icon);
			toastView.addView(imageCodeProject, 0);
			toast.show();
			return;
		} else
		// 都不为空
		{
			if (isNum(str1) != true)// 设备ID不符合要求
			{
				Toast toast = Toast.makeText(getApplicationContext(),
						"设备ID不符合要求!!!!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView imageCodeProject = new ImageView(
						getApplicationContext());
				imageCodeProject
						.setImageResource(R.drawable.customdialog_default_icon);
				toastView.addView(imageCodeProject, 0);
				toast.show();
				return;
			}
			Log.i("1", "添加设备对话框结束"); // INFO
			try
			{
				// MainActivity.addDevSQLite(str1, str2, str3, str4, true);
				/**
				 * 这里也许只要更改主界面的addDev函数就行了 就是一个Socket的连接 发送json的操作 应该很简单
				 */
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", str1);
				map.put("DeviceName", str2);
				map.put("DeviceType", str3);
				map.put("BrandName", str4);
				map.put("ModelName", "");
				map.put("X", "");
				map.put("Y", "");
				HomeControlActivity.addDev(map, str1, 1);
				// HomeControlActivity.addDev(new DevView(context, str1, str2,
				// str3, str4, true));
			} catch (IllegalArgumentException e)
			{
				Toast toast = Toast.makeText(getApplicationContext(),
						"设备ID重复!!!!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView imageCodeProject = new ImageView(
						getApplicationContext());
				imageCodeProject
						.setImageResource(R.drawable.customdialog_default_icon);
				toastView.addView(imageCodeProject, 0);
				toast.show();
				return;
			}
			Toast toast = Toast.makeText(getApplicationContext(), "设备添加成功!!!!",
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) toast.getView();
			ImageView imageCodeProject = new ImageView(getApplicationContext());
			imageCodeProject
					.setImageResource(R.drawable.customdialog_default_icon);
			toastView.addView(imageCodeProject, 0);
			toast.show();
		}
	}

	private boolean isNum(String str3)
	{
		// TODO Auto-generated method stub
		// 判断ID号是否符合要求
		return str3.matches("[0-9]+");
	}
}
