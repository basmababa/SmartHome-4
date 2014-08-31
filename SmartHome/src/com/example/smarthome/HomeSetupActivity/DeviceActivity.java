package com.example.smarthome.HomeSetupActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.smarthome.R;

public class DeviceActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener
{
	private SeekBar adjunctSeekbar;
	private TextView adjunctTextView;
	private RelativeLayout adjunctView;
	private DatabaseHelper dbHelper = null;
	private ImageView delImageView;//删除按钮
	private String devID;//设备id
	private String devType;//设备类型
	private boolean devState = false;//设备状态
	private DevView devView = null;
	private EditText nameEditText;//设备名称
	private EditText iDEditText;//设备ID
	private EditText styleEditText;//设备类型
	private ImageView backImageView;//返回图标
	private ImageView stateImageView;//设备状态
	private TextView stateTextView;
	private EditText brandEditText;//设备品牌
	private String devBrand;

	//删除设备
	private void delete()
	{
		HomeControlActivity.deleteDev(this.devView);
		finish();
	}

	//设备对应的图像状态
	private int getImageByState(boolean paramBoolean)
	{
		if (paramBoolean)
			return R.drawable.device_control_on;//开状态
		else
			return R.drawable.device_control_off;//管状态
	}

	/**
	 * 设备状态
	 * @param paramBoolean1 原设备状态
	 * @param paramBoolean2 要修改的设备状态
	 * @return
	 */
	private String getTextByState(boolean paramBoolean1, boolean paramBoolean2)
	{
		String str;
		if (paramBoolean1)
			if (paramBoolean2)
				str = getString(R.string.device_state_on);
			else
				str = getString(R.string.device_state_off);
		else
			str = getString(R.string.device_state_off);
		return str;   
    }

	//初始化状态
	private void initDevViewFrame()
	{
		String str1 = this.devView.getDevName();//设备名称
		String str2 = this.devView.getDevType();//设备类型
		this.devType = str2;//设备类型
		this.nameEditText.setText(str1);
		this.iDEditText.setText(this.devID);
		this.brandEditText.setText(this.devBrand);
		this.styleEditText.setText(this.devType);
		this.stateTextView.setText(getTextByState(this.devState, true));
		this.stateImageView.setImageResource(getImageByState(this.devState));
		if(str2.equals(getString(R.string.dev_name_air)) || str2.equals(getString(R.string.dev_name_thermostat)))
		{
			this.adjunctView.setVisibility(View.VISIBLE);//显示
			this.adjunctSeekbar.setProgress(65);//空调类型?????????????????????????
		}
  }

	//修改后保存信息
  private void save()
  {
    String str = this.nameEditText.getText().toString().trim();
    this.devView.setDevName(str);
    this.devView.setDevID(this.devID);
    this.devView.setDevState(this.devState);
    HomeControlActivity.updateDevName(this.devView, str);//更新设备名称
    HomeControlActivity.updateDevPort(this.devView, this.devID);//更新设备ID
    HomeControlActivity.updateDevState(this.devView, this.devState, getWindow(), new UIThread.UICallback()//更新设备状态
    {
	      public void onSuccess(Context paramAnonymousContext, Object paramAnonymousObject)
	      {
	        if (DeviceActivity.this.devState)
	        {
	          DeviceActivity.this.devState = false;
	          DeviceActivity.this.stateImageView.setImageResource(DeviceActivity.this.getImageByState(DeviceActivity.this.devState));
	          DeviceActivity.this.stateTextView.setText(DeviceActivity.this.getTextByState(DeviceActivity.this.devState, true));
	        }
	        else
	        {
	          DeviceActivity.this.devState = true;
	          DeviceActivity.this.stateImageView.setImageResource(DeviceActivity.this.getImageByState(DeviceActivity.this.devState));
	          DeviceActivity.this.stateTextView.setText(DeviceActivity.this.getTextByState(DeviceActivity.this.devState, true));
	        }
      }
    });
  }

  //判断ID号是否已存在
  private boolean checkPortListValue(DatabaseHelper paramDatabaseHelper, String str)
  {
    final ArrayList localArrayList = new ArrayList();
    List localList;
    int j;
    localList = this.dbHelper.rawQuery("select DeviceId from DEV_INFO;", null);
    j = localList.size();//没有设备
    if (j != 0)
    {
    	for (int k = 0; k < j; k++)
    	{
    		if(str == (String)((Map)localList.get(k)).get("DeviceId"))
    		{
    			return true;
    		}
    	}
    }
    return false;
  }

  //进度条数值计算
  private String setProgressValue(int paramInt, String paramString)
  {
    String str = "";
    if ("air_condition".equals(paramString))
    {
      str = String.valueOf((int)(16.0D + 14.5D * paramInt / 100.0D)) + " 'C";
      this.adjunctTextView.setText(str);
    }
    return str;
  }

	public void onClick(View paramView)
	{
		switch (paramView.getId())
		{
    	case R.id.ImageView03://删除按钮响应
    	{
    		DialogManager.showAlertDialog(this, getString(R.string.dev_delete_title), getString(R.string.dev_delete__msg)
    				, getString(R.string.alertdialog_positivebutton), new DialogInterface.OnClickListener()
    					{ 
    						public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
    						{
    							Log.i("1", "确定删除1"); //INFO 
    							DeviceActivity.this.delete();
    						}
    					}, getString(R.string.alertdialog_negativebutton), null);
    		break;
    	}
		case R.id.ImageView05://返回按钮响应
		{
			finish();
			break;
		}
		case R.id.devicestate01:
		{
			DevView localDevView = this.devView;//当前的状态
			boolean bool;
			if(this.devState)
			{
				bool = false;//新的状态
			}
			else
			{
				bool = true;
			}
			HomeControlActivity.updateDevState(localDevView, bool, getWindow(), new UIThread.UICallback()
	        {
	          public void onSuccess(Context paramAnonymousContext, Object paramAnonymousObject)
	          {
	            if (DeviceActivity.this.devState)
	            {
	              DeviceActivity.this.devState = false;
	              DeviceActivity.this.stateImageView.setImageResource(DeviceActivity.this.getImageByState(DeviceActivity.this.devState));
	              DeviceActivity.this.stateTextView.setText(DeviceActivity.this.getTextByState(DeviceActivity.this.devState, true));
	            }
	            else
	            {
	              DeviceActivity.this.devState = true;
	              DeviceActivity.this.stateImageView.setImageResource(DeviceActivity.this.getImageByState(DeviceActivity.this.devState));
	              DeviceActivity.this.stateTextView.setText(DeviceActivity.this.getTextByState(DeviceActivity.this.devState, true));
	            }
	          }
	        });
			break;
		}
		default:
    }
  }

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_device);//设置布局
		this.devView = HomeControlActivity.getDevView(getIntent().getStringExtra("DEV_VIEW_SIGN"));//获得对应的设备
		this.devID = this.devView.getDevId();//获得ID
		this.devBrand = this.devView.getDevBrand();//设备品牌
		this.devState = this.devView.getDevState();//获得设备状态
		this.dbHelper = new DatabaseHelper(this);//获得数据库
		this.delImageView = ((ImageView)findViewById(R.id.ImageView03));//删除设备按钮
		this.delImageView.setOnClickListener(this);
		this.backImageView = ((ImageView)findViewById(R.id.ImageView05));//返回设备按钮
		this.backImageView.setOnClickListener(this);
		this.nameEditText = ((EditText)findViewById(R.id.devicename01));//设备名称
		this.nameEditText.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable paramAnonymousEditable)
			{
			}

			public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
			{
			}

			public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
			{
				String str = DeviceActivity.this.nameEditText.getText().toString().trim();//获得更改设备的名称
				HomeControlActivity.updateDevName(DeviceActivity.this.devView, str);//更新设备的名称
			}
		});
		this.iDEditText = ((EditText)findViewById(R.id.deviceid01));//设备id显示,
		this.styleEditText = ((EditText)findViewById(R.id.devicestyle01));//设备style显示,
		this.brandEditText = ((EditText)findViewById(R.id.devicebrand01));//设备品牌
		this.stateImageView = ((ImageView)findViewById(R.id.devicestate01));//设备状态按钮
		this.stateImageView.setOnClickListener(this);
		this.stateTextView = ((TextView)findViewById(R.id.TextView06));//状态显示
		this.adjunctView = ((RelativeLayout)findViewById(R.id.RelativeLayout10));
		this.adjunctView.setVisibility(View.GONE);//不显示
		this.adjunctSeekbar = ((SeekBar)findViewById(R.id.SeekBar12));//温度控制导航
		this.adjunctSeekbar.setOnSeekBarChangeListener(this);
		this.adjunctTextView = ((TextView)findViewById(R.id.TextView08));//温度显示
		initDevViewFrame();//初始化设备个参数
  }

  public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean)
  {
	  setProgressValue(paramInt, "air_condition");
  }

  public void onStartTrackingTouch(SeekBar paramSeekBar)
  {
  }

  public void onStopTrackingTouch(SeekBar paramSeekBar)
  {
  }
}