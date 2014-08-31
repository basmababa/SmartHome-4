package com.example.smarthome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Application;
import android.util.Log;

public class SmartHomebaseApp extends Application
{

	private static final String TAG = "SmartHomebaseApp";

	public String SERVER_IP = "10.21.63.113";// 默认IP
												// 这个IP是开发板上ifconfig显示出来的IP
	public int SERVER_PORT = 23;// 设置的URL和端口
	public Socket socket = null;// 保存Socket
	public PrintWriter printWriter = null;// 输出流
	public BufferedReader dataInputStream = null;// 输入流

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "oncreate");
		getSocketConnection("");
	}

	public void getSocketConnection(String paramString)
	{
		new Thread(getSocketConnectionRunnable).start();
	}

	Runnable getSocketConnectionRunnable = new Runnable()
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			try
			{
				if (socket != null)
				{
					Log.i("socket", "socket1连接");
					printWriter = new PrintWriter(socket.getOutputStream()); // 向服务器写数据的输出流
					dataInputStream = new BufferedReader(new InputStreamReader(
							socket.getInputStream())); // 获取服务器返回数据的输入流
					// 连接Socket成功
					socket.setSoTimeout(5000);// 设置5秒之后即认为是超时
				}
				else
				{
					Log.i("socket", "socket2连接" + SERVER_IP + SERVER_PORT);
					socket = new Socket(SERVER_IP, SERVER_PORT);// 实例化Socket对象
					printWriter = new PrintWriter(socket.getOutputStream()); // 向服务器写数据的输出流
					dataInputStream = new BufferedReader(new InputStreamReader(
							socket.getInputStream())); // 获取服务器返回数据的输入流
					// 连接Socket成功
					socket.setSoTimeout(5000);// 设置5秒之后即认为是超时
				}
				// Log.i(TAG, "socket1连接" + dataInputStream.readLine());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				Log.i(TAG, "socket1连接error");
				e.printStackTrace();
				Log.i(TAG, "---" + e.toString());
			}
		}

	};

	public void closeSocket()
	{
		if (socket != null)
		{
			try
			{
				socket.close();
				printWriter.close();
				dataInputStream.close();
				dataInputStream = null;
				printWriter = null;
				socket = null;
				Log.i(TAG, "socket closed");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				Log.e(TAG, "socket close error");
				e.printStackTrace();
			}

		}
	}
}
