package com.example.smarthome.HomeSetupActivity;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.smarthome.R;
import com.example.smarthome.SmartHomebaseApp;

public class lighton extends Activity
{
	private ImageView imageView = null;
	private ToggleButton toggleButton = null;
	private Connect ctrl = new Connect();// 控制器
	private String infoToSend = "";// 发送给电饭煲控制信息
	private ImageView imageView_close;
	String addressString = "";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lightonpage);
		final SmartHomebaseApp app = (SmartHomebaseApp) getApplication();
		imageView_close = (ImageView) findViewById(R.id.ImageView_on);
		imageView_close.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});

		imageView = (ImageView) findViewById(R.id.imageView1);
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				toggleButton.setChecked(isChecked);
				imageView.setImageResource(isChecked ? R.drawable.bulb_on
						: R.drawable.bulb_off);

				if (isChecked)// 打开
				{
					new Thread()
					{

						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							super.run();
							if (app.socket != null)
							{
								app.printWriter.println("zigbee getall");
								app.printWriter.flush();
								try
								{
									String commadnString = app.dataInputStream
											.readLine();
									addressString = commadnString.substring(14,
											18);
								}
								catch (IOException e)
								{
									// TODO Auto-generated catch block
									Log.i("light on", e.toString());
									e.printStackTrace();
								}

								app.printWriter.println("zigbee setdev "
										+ addressString + "on");
								app.printWriter.flush();

							}
						}

					}.start();

				}
				else
				// 关闭
				{
					new Thread()
					{

						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							super.run();
							if (app.socket != null)
							{
								// 因为这里只有一个设备 所以这样写
								app.printWriter.println("zigbee getall");
								app.printWriter.flush();
								try
								{
									String commadnString = app.dataInputStream
											.readLine();
									addressString = commadnString.substring(14,
											18);
								}
								catch (IOException e)
								{
									// TODO Auto-generated catch block
									Log.i("light on", e.toString());
									e.printStackTrace();
								}

								app.printWriter.println("zigbee setdev "
										+ addressString + "off");
								app.printWriter.flush();

							}
						}

					}.start();

				}

			}
		});
	}
}
