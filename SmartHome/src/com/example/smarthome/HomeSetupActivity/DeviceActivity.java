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
	private ImageView delImageView;//ɾ����ť
	private String devID;//�豸id
	private String devType;//�豸����
	private boolean devState = false;//�豸״̬
	private DevView devView = null;
	private EditText nameEditText;//�豸����
	private EditText iDEditText;//�豸ID
	private EditText styleEditText;//�豸����
	private ImageView backImageView;//����ͼ��
	private ImageView stateImageView;//�豸״̬
	private TextView stateTextView;
	private EditText brandEditText;//�豸Ʒ��
	private String devBrand;

	//ɾ���豸
	private void delete()
	{
		HomeControlActivity.deleteDev(this.devView);
		finish();
	}

	//�豸��Ӧ��ͼ��״̬
	private int getImageByState(boolean paramBoolean)
	{
		if (paramBoolean)
			return R.drawable.device_control_on;//��״̬
		else
			return R.drawable.device_control_off;//��״̬
	}

	/**
	 * �豸״̬
	 * @param paramBoolean1 ԭ�豸״̬
	 * @param paramBoolean2 Ҫ�޸ĵ��豸״̬
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

	//��ʼ��״̬
	private void initDevViewFrame()
	{
		String str1 = this.devView.getDevName();//�豸����
		String str2 = this.devView.getDevType();//�豸����
		this.devType = str2;//�豸����
		this.nameEditText.setText(str1);
		this.iDEditText.setText(this.devID);
		this.brandEditText.setText(this.devBrand);
		this.styleEditText.setText(this.devType);
		this.stateTextView.setText(getTextByState(this.devState, true));
		this.stateImageView.setImageResource(getImageByState(this.devState));
		if(str2.equals(getString(R.string.dev_name_air)) || str2.equals(getString(R.string.dev_name_thermostat)))
		{
			this.adjunctView.setVisibility(View.VISIBLE);//��ʾ
			this.adjunctSeekbar.setProgress(65);//�յ�����?????????????????????????
		}
  }

	//�޸ĺ󱣴���Ϣ
  private void save()
  {
    String str = this.nameEditText.getText().toString().trim();
    this.devView.setDevName(str);
    this.devView.setDevID(this.devID);
    this.devView.setDevState(this.devState);
    HomeControlActivity.updateDevName(this.devView, str);//�����豸����
    HomeControlActivity.updateDevPort(this.devView, this.devID);//�����豸ID
    HomeControlActivity.updateDevState(this.devView, this.devState, getWindow(), new UIThread.UICallback()//�����豸״̬
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

  //�ж�ID���Ƿ��Ѵ���
  private boolean checkPortListValue(DatabaseHelper paramDatabaseHelper, String str)
  {
    final ArrayList localArrayList = new ArrayList();
    List localList;
    int j;
    localList = this.dbHelper.rawQuery("select DeviceId from DEV_INFO;", null);
    j = localList.size();//û���豸
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

  //��������ֵ����
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
    	case R.id.ImageView03://ɾ����ť��Ӧ
    	{
    		DialogManager.showAlertDialog(this, getString(R.string.dev_delete_title), getString(R.string.dev_delete__msg)
    				, getString(R.string.alertdialog_positivebutton), new DialogInterface.OnClickListener()
    					{ 
    						public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
    						{
    							Log.i("1", "ȷ��ɾ��1"); //INFO 
    							DeviceActivity.this.delete();
    						}
    					}, getString(R.string.alertdialog_negativebutton), null);
    		break;
    	}
		case R.id.ImageView05://���ذ�ť��Ӧ
		{
			finish();
			break;
		}
		case R.id.devicestate01:
		{
			DevView localDevView = this.devView;//��ǰ��״̬
			boolean bool;
			if(this.devState)
			{
				bool = false;//�µ�״̬
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
		setContentView(R.layout.activity_device);//���ò���
		this.devView = HomeControlActivity.getDevView(getIntent().getStringExtra("DEV_VIEW_SIGN"));//��ö�Ӧ���豸
		this.devID = this.devView.getDevId();//���ID
		this.devBrand = this.devView.getDevBrand();//�豸Ʒ��
		this.devState = this.devView.getDevState();//����豸״̬
		this.dbHelper = new DatabaseHelper(this);//������ݿ�
		this.delImageView = ((ImageView)findViewById(R.id.ImageView03));//ɾ���豸��ť
		this.delImageView.setOnClickListener(this);
		this.backImageView = ((ImageView)findViewById(R.id.ImageView05));//�����豸��ť
		this.backImageView.setOnClickListener(this);
		this.nameEditText = ((EditText)findViewById(R.id.devicename01));//�豸����
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
				String str = DeviceActivity.this.nameEditText.getText().toString().trim();//��ø����豸������
				HomeControlActivity.updateDevName(DeviceActivity.this.devView, str);//�����豸������
			}
		});
		this.iDEditText = ((EditText)findViewById(R.id.deviceid01));//�豸id��ʾ,
		this.styleEditText = ((EditText)findViewById(R.id.devicestyle01));//�豸style��ʾ,
		this.brandEditText = ((EditText)findViewById(R.id.devicebrand01));//�豸Ʒ��
		this.stateImageView = ((ImageView)findViewById(R.id.devicestate01));//�豸״̬��ť
		this.stateImageView.setOnClickListener(this);
		this.stateTextView = ((TextView)findViewById(R.id.TextView06));//״̬��ʾ
		this.adjunctView = ((RelativeLayout)findViewById(R.id.RelativeLayout10));
		this.adjunctView.setVisibility(View.GONE);//����ʾ
		this.adjunctSeekbar = ((SeekBar)findViewById(R.id.SeekBar12));//�¶ȿ��Ƶ���
		this.adjunctSeekbar.setOnSeekBarChangeListener(this);
		this.adjunctTextView = ((TextView)findViewById(R.id.TextView08));//�¶���ʾ
		initDevViewFrame();//��ʼ���豸������
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