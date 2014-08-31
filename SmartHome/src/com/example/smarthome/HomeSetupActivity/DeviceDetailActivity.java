package com.example.smarthome.HomeSetupActivity;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.R;
import com.example.smarthome.SmartHomebaseApp;

public class DeviceDetailActivity extends Activity implements OnClickListener
{

	UtilDataForDeviceDetail mydata;
	private Context context;
	private EditText deviceidID;
	private EditText devicename01;
	private EditText devicebrand01;
	private EditText devicestyle01;// ����
	private ImageView devicestate01;// ״̬
	private TextView deviceStateDisp;// ״̬��ʾTextView06
	private ImageView imageViewback;// back
	private ImageView imageViewDelete;// delete
	private TextView TextView06_on_of;

	private Map<String, String> mapForDelete;
	private String idid;
	private static boolean state_on_off = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_detaile);
		context = this;
		initUI(context);
		getData(context);

	}

	private void initUI(Context context)
	{
		deviceidID = (EditText) findViewById(R.id.deviceidID);
		devicename01 = (EditText) findViewById(R.id.devicename01);
		devicebrand01 = (EditText) findViewById(R.id.devicebrand01);
		devicestyle01 = (EditText) findViewById(R.id.devicestyle01);
		devicestate01 = (ImageView) findViewById(R.id.devicestate01);
		deviceStateDisp = (TextView) findViewById(R.id.TextView06_on_of);
		// back
		imageViewback = (ImageView) findViewById(R.id.ImageView05back);
		imageViewback.setOnClickListener(this);
		// ɾ��
		imageViewDelete = (ImageView) findViewById(R.id.ImageView03delete);
		imageViewDelete.setOnClickListener(this);
		devicestate01.setOnClickListener(this);

	}

	private void getData(Context context)
	{
		mydata = (UtilDataForDeviceDetail) ((Activity) context).getIntent()
				.getSerializableExtra("MY_DATA");

		String id = mydata.getID();
		idid = id;
		String name = mydata.getDeviceName();
		String brand = mydata.getBrandName();
		String type = mydata.getDeviceType();
		mapForDelete = mydata.getMap();

		deviceidID.setText(id);
		devicename01.setText(name);
		devicebrand01.setText(brand);
		devicestyle01.setText(type);

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if (v.equals(imageViewback))// back
		{
			finish();
		}
		else if (v.equals(imageViewDelete))
		{
			// �����������delete���� ��������֮�� ��ʾɾ���ɹ� Ȼ��ˢ��һ�� �����б�ˢ��һ�� Ȼ��finish
			// .......
			HomeControlActivity.deleteDev(mapForDelete, idid, 1);
			finish();
		}
		// �������������δʵ��
		else if (v.equals(devicestate01))
		{
			if (state_on_off)// ��ǰ״̬�Ǵ򿪵� �Ǿ͹ر�
			{
				new Thread(new Runnable()
				{

					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						try
						{
							// SocketControl.getSocketConnection("");
							SmartHomebaseApp app = (SmartHomebaseApp) getApplication();

							if (app.socket != null)// �ǿ�, �õ���Socket
							{
								//
								app.printWriter
										.println("WebSetAction_Idu_Output "
												+ idid + 0);
								app.printWriter.flush();
								// jsonFromSocket =
								// NetworkUtil.dataInputStream.readLine();
								Message msg = new Message();
								msg.what = 1;
								closeSuccess.sendMessage(msg);
							}
							else
							{
								Message msg = new Message();
								msg.what = 0;
								openSuccess.sendMessage(msg);
							}
							// SocketControl.closeSocketConnection("");

						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
			else
			// ��ǰ״̬�ǹرյ� �Ǿʹ�
			{
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						try
						{
							// SocketControl.getSocketConnection("");
							SmartHomebaseApp app = (SmartHomebaseApp) getApplication();

							if (app.socket != null)// �ǿ�, �õ���Socket
							{
								//
								app.printWriter
										.println("WebSetAction_Idu_Output "
												+ idid + 1);
								app.printWriter.flush();
								// jsonFromSocket =
								// NetworkUtil.dataInputStream.readLine();
								Message msg = new Message();
								msg.what = 1;
								openSuccess.sendMessage(msg);
							}
							else
							{
								Message msg = new Message();
								msg.what = 0;
								openSuccess.sendMessage(msg);

							}
							// SocketControl.closeSocketConnection("");

						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}

	private Handler closeSuccess = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				showToast("�رճɹ�");
				state_on_off = false;
				devicestate01.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.device_control_off));
				deviceStateDisp.setText("�ر�");
				break;
			case 0:
				ToastWindow toastWindow = new ToastWindow(context);
				toastWindow.showToast("����ʧ��");
				break;
			default:
				break;
			}
		}
	};

	private Handler openSuccess = new Handler()
	{
		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				showToast("�򿪳ɹ�");
				state_on_off = true;// ����on
				devicestate01.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.device_control_on));
				deviceStateDisp.setText("��");
				break;
			case 0:
				ToastWindow toastWindow = new ToastWindow(context);
				toastWindow.showToast("����ʧ��");
			default:
				break;
			}
		}
	};

	private void showToast(String msg)
	{
		Toast.makeText(context, msg, 0).show();
	}
}
