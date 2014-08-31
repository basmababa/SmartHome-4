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

	public String SERVER_IP = "10.21.63.113";// Ĭ��IP
												// ���IP�ǿ�������ifconfig��ʾ������IP
	public int SERVER_PORT = 23;// ���õ�URL�Ͷ˿�
	public Socket socket = null;// ����Socket
	public PrintWriter printWriter = null;// �����
	public BufferedReader dataInputStream = null;// ������

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
					Log.i("socket", "socket1����");
					printWriter = new PrintWriter(socket.getOutputStream()); // �������д���ݵ������
					dataInputStream = new BufferedReader(new InputStreamReader(
							socket.getInputStream())); // ��ȡ�������������ݵ�������
					// ����Socket�ɹ�
					socket.setSoTimeout(5000);// ����5��֮����Ϊ�ǳ�ʱ
				}
				else
				{
					Log.i("socket", "socket2����" + SERVER_IP + SERVER_PORT);
					socket = new Socket(SERVER_IP, SERVER_PORT);// ʵ����Socket����
					printWriter = new PrintWriter(socket.getOutputStream()); // �������д���ݵ������
					dataInputStream = new BufferedReader(new InputStreamReader(
							socket.getInputStream())); // ��ȡ�������������ݵ�������
					// ����Socket�ɹ�
					socket.setSoTimeout(5000);// ����5��֮����Ϊ�ǳ�ʱ
				}
				// Log.i(TAG, "socket1����" + dataInputStream.readLine());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				Log.i(TAG, "socket1����error");
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
