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

	private Spinner deviceStyleSpinner;// �豸����
	private Context context;
	private ImageView yesImageView;// ȷ�����
	private ImageView backImageView;// ���ذ�ť
	private ImageButton typeImageButton;// �豸����������ť
	private EditText devicetypeEditText;// �豸����
	private EditText deviceidEditText;// �豸id
	private EditText devicenameEditText;// �豸����
	private EditText devicebrandEditText;// �豸Ʒ��

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_device);

		context = this;// ��ǰ������
		this.deviceidEditText = (EditText) findViewById(R.id.deviceid);// �豸ID����
		this.devicenameEditText = (EditText) findViewById(R.id.devicename);// �豸���������
		this.devicebrandEditText = (EditText) findViewById(R.id.devicebrand);// �豸���������
		this.devicetypeEditText = (EditText) findViewById(R.id.devicestyle);// �豸���������
		this.typeImageButton = (ImageButton) findViewById(R.id.devicestylebutton);// �豸����ѡ��ť
		this.yesImageView = ((ImageView) findViewById(R.id.yesImageView));// ɾ���豸��ť
		this.yesImageView.setOnClickListener(this);
		this.backImageView = ((ImageView) findViewById(R.id.backImageView));// ɾ���豸��ť
		this.backImageView.setOnClickListener(this);
		initlistview();
	}

	private void initlistview()
	{
		// TODO Auto-generated method stub
		// �豸��������
		final PopupWindow typePopupWindow = new PopupWindow(this);// �����豸���������Ի���
		typePopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.device_middle_bg));// �����豸���������Ի���ı���
		typePopupWindow.setWidth(220);// ���ÿ��
		typePopupWindow.setHeight(200);// ���ø߶�
		typePopupWindow.setFocusable(true);
		ListView typeListView = new ListView(this);
		typeListView.setAlwaysDrawnWithCacheEnabled(true);
		typeListView.setCacheColorHint(Color.TRANSPARENT);
		final String[] arrayOfString = new String[7];// �豸��������
		arrayOfString[0] = context.getString(R.string.dev_name_air);
		arrayOfString[1] = context.getString(R.string.dev_name_gated);
		arrayOfString[2] = context.getString(R.string.dev_name_fan);
		arrayOfString[3] = context.getString(R.string.dev_name_thermostat);
		arrayOfString[4] = context.getString(R.string.dev_name_light);
		arrayOfString[5] = context.getString(R.string.dev_name_socket);
		arrayOfString[6] = context.getString(R.string.dev_name_other);
		ArrayAdapter typeArrayAdapter = new ArrayAdapter(this,
				R.layout.simple_list_item, arrayOfString);// �豸����������
		typeListView.setAdapter(typeArrayAdapter);// ΪtypeListView��������
		typeListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()// Ϊ�豸���������������Ӽ���
				{
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong)
					{
						devicetypeEditText
								.setText(arrayOfString[paramAnonymousInt]);// ��õ�����豸���ͣ������豸��������Ϊ������豸����
						typePopupWindow.dismiss();// �豸�����б���ʧ
					}
				});
		typePopupWindow.setContentView(typeListView);// ���豸�����б���ӵ�typePopupWindow�Ի�����ȥ
		View.OnClickListener typeImageButtonOnClickListener = new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				devicetypeEditText.requestFocus();
				typePopupWindow.showAsDropDown(devicetypeEditText);// ����ѡ�����ʾ�������������·�
			}
		};
		typeImageButton.setOnClickListener(typeImageButtonOnClickListener);// Ϊ�豸����������ť��Ӽ���
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
		case R.id.backImageView:// ����
			{
				finish();
				break;
			}
		case R.id.yesImageView:// ȷ����� ����addDevice
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
		String str2 = devicenameEditText.getText().toString();// �豸����
		String str3 = devicetypeEditText.getText().toString();// �豸����
		String str4 = devicebrandEditText.getText().toString();// �豸Ʒ��
		if ((str1.equals("")) || (str2.equals(""))
				|| (str3.equals("") || str4.equals("")))
		{// �е�����Ϊ��
			Log.i("1", "����豸����Ϊ��"); // INFO
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
		// ����Ϊ��
		{
			if (isNum(str1) != true)// �豸ID������Ҫ��
			{
				Toast toast = Toast.makeText(getApplicationContext(),
						"�豸ID������Ҫ��!!!!", Toast.LENGTH_LONG);
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
			Log.i("1", "����豸�Ի������"); // INFO
			try
			{
				// MainActivity.addDevSQLite(str1, str2, str3, str4, true);
				/**
				 * ����Ҳ��ֻҪ�����������addDev���������� ����һ��Socket������ ����json�Ĳ��� Ӧ�úܼ�
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
						"�豸ID�ظ�!!!!", Toast.LENGTH_LONG);
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
			Toast toast = Toast.makeText(getApplicationContext(), "�豸��ӳɹ�!!!!",
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
		// �ж�ID���Ƿ����Ҫ��
		return str3.matches("[0-9]+");
	}
}
