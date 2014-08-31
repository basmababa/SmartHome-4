package com.example.smarthome.HomeSetupActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class SocketControl {

	private boolean connstart = false;//����״̬
	private DataInputStream dataInputStream;//����������
	private DataOutputStream dataOutputStream;//���������

	
	//���ӷ�����
	private boolean LoadSysteminfo()
	{
	    try
	    {
	      if (NetworkUtil.socket.isConnected())
	      {
	        this.connstart = true;//���ӳɹ�
	        this.dataOutputStream = new DataOutputStream(NetworkUtil.socket.getOutputStream());
	        this.dataInputStream = new DataInputStream(NetworkUtil.socket.getInputStream());
	      }
	    }
	    catch (Exception localException)
	    {
	    	return false;
	    }
	    return true;
	}
	//��������豸��״̬
		public static Map<String, String> getAllDeviceState(Context context) throws Exception
		{
			return getData(context);
		}
		
		private static  Map<String, String> getData(Context context) throws Exception
		{
			DatabaseHelper localDatabaseHelper = new DatabaseHelper(context);//�������ݿ�
			List<String> localList = localDatabaseHelper.rawQueryForFirstField("select DeviceId from DEV_INFO", null);//��ѯ�豸ID
			Log.i("UIThread",localList + "");
			int devnum = localList.size();//��õı����豸��
			Log.i("UIThread", devnum+""); //INFO
			HashMap<String, String> localHashMap = new HashMap<String, String>();
			for(int i = 0; i < devnum; i++)
			{
				String str1 = localList.get(i);
				String str2 = getSingleDeviceState(Integer.parseInt(localList.get(i)),HomeControlActivity.getDevView(localList.get(i)).getDevType());//����豸��״̬
				localHashMap.put(str1, str2);
			}
			Log.i("UIThread", "getAllDeviceState������ʼ4" + localHashMap); //INFO 
			return localHashMap;
			
		}
		
		//���豸paramString��ʱ�ò��ϵ��豸����
		public static void getSocketConnection(String paramString) throws Exception
		{
		    try
		    {
		    	if(NetworkUtil.socket != null)
		    	{
		    		Log.i("socket", "socket1����");
		    		NetworkUtil.printWriter = new PrintWriter(NetworkUtil.socket.getOutputStream()); // �������д���ݵ������ 
		    		NetworkUtil.dataInputStream = new BufferedReader(new InputStreamReader(NetworkUtil.socket.getInputStream())); // ��ȡ�������������ݵ������� 
		    		//����Socket�ɹ�
		    		NetworkUtil.socket.setSoTimeout(500);//����5��֮����Ϊ�ǳ�ʱ
		    	}
		    	else
		    	{
		    		Log.i("socket", "socket2����" + NetworkUtil.SERVER_IP + NetworkUtil.SERVER_PORT);
					NetworkUtil.socket = new Socket(NetworkUtil.SERVER_IP,NetworkUtil.SERVER_PORT);//ʵ����Socket����
					NetworkUtil.printWriter = new PrintWriter(NetworkUtil.socket.getOutputStream()); // �������д���ݵ������ 
		    		NetworkUtil.dataInputStream = new BufferedReader(new InputStreamReader(NetworkUtil.socket.getInputStream())); // ��ȡ�������������ݵ������� 
		    		//����Socket�ɹ�
		    		NetworkUtil.socket.setSoTimeout(500);//����5��֮����Ϊ�ǳ�ʱ
		    	}
		    	Log.i("socket", "socket1����"+NetworkUtil.dataInputStream);
		    }
		    catch (Exception localException)
		    {
		    	throw new Exception(localException);
		    }
	}
	
	/**
	 * 
	 * @param paramInt1 ��Ӧÿ���豸��ID��
	 * @param paramString2 ��Ӧ�豸������
	 * @return	����Զ���豸��״̬
	 * @throws Exception
	 */
	private static String getSingleDeviceState(int paramInt1, String paramString2) throws Exception
	{
		String res;//���صĽ��
		String sendstr = "[{\"id\":" + paramInt1 + ",\"DeviceType\":\"" + paramString2 + "\",\"Command\":\"" + "OPEN" + "\"}]"; //���Ͷ�Ӧ�豸ID��ѯ״̬����
		getSocketConnection("");//���Զ������
		NetworkUtil.printWriter.println(sendstr);// ��������
		NetworkUtil.printWriter.flush(); //���������
		res = NetworkUtil.dataInputStream.readLine();//��ȡ���ص�����
		System.out.println(res);
		closeSocketConnection("");//�ر�Զ������
		if(res.equals("ON"))//�豸��
		    	return "1";
		else if(res.equals("OFF"))//�豸�ر�
		    	return "0";
		else
		{
			return "#";//δ֪״̬
		}
	}
	
	/**
	 * �����豸ID����Զ���豸�ĵ�״̬
	 * @param devicename �豸ID
	 * @param paramString2 �豸����
	 * @throws Exception Զ�̳���
	 */
	public static void openDevice(int devicename, String paramString2) throws Exception
	{
		
		String res;//�򿪵Ľ��
		String sendstr = "[{\"id\":" + devicename + ",\"DeviceType\":\"" + paramString2 + "\",\"Command\":\"" + "OPEN" + "\"}]"; //���Ͷ�Ӧ�豸ID������
		getSocketConnection("");//���Զ������
		NetworkUtil.printWriter.println(sendstr);// ��������
		NetworkUtil.printWriter.flush(); //���������
		res = NetworkUtil.dataInputStream.readLine();//��ȡ���ص�����
		System.out.println(res);
		closeSocketConnection("");//�ر�Զ������
	}
	
	/**
	 * �ر�Զ���豸
	 * @param paramInt1 ID��
	 * @param paramString2	�豸����
	 * @throws Exception
	 */
	public static void closeDevice(int paramInt1, String paramString2)  throws Exception{
		// TODO Auto-generated method stub
		//�ر�һ���豸
		String res;//�򿪵Ľ��
		String sendstr = "[{\"id\":" + paramInt1 + ",\"DeviceType\":\"" + paramString2 + "\",\"Command\":\"" + "OPEN" + "\"}]";//���Ͷ�Ӧ�豸ID�ر�����
		getSocketConnection("");//���Զ������
		NetworkUtil.printWriter.println(sendstr);// ��������
		NetworkUtil.printWriter.flush(); //���������
		res = NetworkUtil.dataInputStream.readLine();//��ȡ���ص�����
		System.out.println(res);
		closeSocketConnection("");//�ر�Զ������
	}
	
	//�ر�Զ�̷�����������
	public static void closeSocketConnection(String string) throws IOException {
		// TODO Auto-generated method stub
		try {
			NetworkUtil.dataInputStream.close();//�ر�������
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			NetworkUtil.printWriter.close();//�ر������
			NetworkUtil.socket.close();//�ر�Socket
			NetworkUtil.socket = null;
		 }
	}


	/**
	 * �ر����е��豸
	 * @param arrayOfString �����豸������
	 */
	public static void closeAllDevice(String[] arrayOfString) throws Exception{
		// TODO Auto-generated method stub
		{
		    String str;
		    if ((arrayOfString != null) && (arrayOfString.length > 0))
		      str = "";
		    for (int i = 0; i < arrayOfString.length; i++)
		    {
		    	//Զ�̹ر��豸
		    	closeDevice(Integer.parseInt(arrayOfString[i]),String.valueOf(HomeControlActivity.getDevView(arrayOfString[i]).getDevType()));
		    }
		 }
	}


	public static void openAllDevice(String[] arrayOfString) throws Exception{
		// TODO Auto-generated method stub
		{
		    String str;
		    if ((arrayOfString != null) && (arrayOfString.length > 0))
		      str = "";
		    for (int i = 0; i < arrayOfString.length; i++)
		    {
		    	//Զ�̹ر��豸
		    	openDevice(Integer.parseInt(arrayOfString[i]),String.valueOf(HomeControlActivity.getDevView(arrayOfString[i]).getDevType()));
		    }
		 }
	}
}
