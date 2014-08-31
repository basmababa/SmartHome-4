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
	private EditText devicestyle01;// 类型
	private ImageView devicestate01;// 状态
	private TextView deviceStateDisp;// 状态显示TextView06
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
		// 删除
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
			// 调用主界面的delete函数 调用完了之后 提示删除成功 然后刷新一下 就是列表刷新一下 然后finish
			// .......
			HomeControlActivity.deleteDev(mapForDelete, idid, 1);
			finish();
		}
		// 这里黄文那里尚未实现
		else if (v.equals(devicestate01))
		{
			if (state_on_off)// 当前状态是打开的 那就关闭
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

							if (app.socket != null)// 非空, 得到了Socket
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
			// 当前状态是关闭的 那就打开
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

							if (app.socket != null)// 非空, 得到了Socket
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
				showToast("关闭成功");
				state_on_off = false;
				devicestate01.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.device_control_off));
				deviceStateDisp.setText("关闭");
				break;
			case 0:
				ToastWindow toastWindow = new ToastWindow(context);
				toastWindow.showToast("联网失败");
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
				showToast("打开成功");
				state_on_off = true;// 代表on
				devicestate01.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.device_control_on));
				deviceStateDisp.setText("打开");
				break;
			case 0:
				ToastWindow toastWindow = new ToastWindow(context);
				toastWindow.showToast("联网失败");
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
