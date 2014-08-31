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

	private boolean connstart = false;//连接状态
	private DataInputStream dataInputStream;//数据输入流
	private DataOutputStream dataOutputStream;//数据输出流

	
	//连接服务器
	private boolean LoadSysteminfo()
	{
	    try
	    {
	      if (NetworkUtil.socket.isConnected())
	      {
	        this.connstart = true;//连接成功
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
	//获得所有设备的状态
		public static Map<String, String> getAllDeviceState(Context context) throws Exception
		{
			return getData(context);
		}
		
		private static  Map<String, String> getData(Context context) throws Exception
		{
			DatabaseHelper localDatabaseHelper = new DatabaseHelper(context);//连接数据库
			List<String> localList = localDatabaseHelper.rawQueryForFirstField("select DeviceId from DEV_INFO", null);//查询设备ID
			Log.i("UIThread",localList + "");
			int devnum = localList.size();//获得的本地设备数
			Log.i("UIThread", devnum+""); //INFO
			HashMap<String, String> localHashMap = new HashMap<String, String>();
			for(int i = 0; i < devnum; i++)
			{
				String str1 = localList.get(i);
				String str2 = getSingleDeviceState(Integer.parseInt(localList.get(i)),HomeControlActivity.getDevView(localList.get(i)).getDevType());//获得设备的状态
				localHashMap.put(str1, str2);
			}
			Log.i("UIThread", "getAllDeviceState函数开始4" + localHashMap); //INFO 
			return localHashMap;
			
		}
		
		//对设备paramString暂时用不上的设备请求
		public static void getSocketConnection(String paramString) throws Exception
		{
		    try
		    {
		    	if(NetworkUtil.socket != null)
		    	{
		    		Log.i("socket", "socket1连接");
		    		NetworkUtil.printWriter = new PrintWriter(NetworkUtil.socket.getOutputStream()); // 向服务器写数据的输出流 
		    		NetworkUtil.dataInputStream = new BufferedReader(new InputStreamReader(NetworkUtil.socket.getInputStream())); // 获取服务器返回数据的输入流 
		    		//连接Socket成功
		    		NetworkUtil.socket.setSoTimeout(500);//设置5秒之后即认为是超时
		    	}
		    	else
		    	{
		    		Log.i("socket", "socket2连接" + NetworkUtil.SERVER_IP + NetworkUtil.SERVER_PORT);
					NetworkUtil.socket = new Socket(NetworkUtil.SERVER_IP,NetworkUtil.SERVER_PORT);//实例化Socket对象
					NetworkUtil.printWriter = new PrintWriter(NetworkUtil.socket.getOutputStream()); // 向服务器写数据的输出流 
		    		NetworkUtil.dataInputStream = new BufferedReader(new InputStreamReader(NetworkUtil.socket.getInputStream())); // 获取服务器返回数据的输入流 
		    		//连接Socket成功
		    		NetworkUtil.socket.setSoTimeout(500);//设置5秒之后即认为是超时
		    	}
		    	Log.i("socket", "socket1连接"+NetworkUtil.dataInputStream);
		    }
		    catch (Exception localException)
		    {
		    	throw new Exception(localException);
		    }
	}
	
	/**
	 * 
	 * @param paramInt1 对应每个设备的ID号
	 * @param paramString2 对应设备的类型
	 * @return	返回远程设备的状态
	 * @throws Exception
	 */
	private static String getSingleDeviceState(int paramInt1, String paramString2) throws Exception
	{
		String res;//返回的结果
		String sendstr = "[{\"id\":" + paramInt1 + ",\"DeviceType\":\"" + paramString2 + "\",\"Command\":\"" + "OPEN" + "\"}]"; //发送对应设备ID查询状态命令
		getSocketConnection("");//获得远程连接
		NetworkUtil.printWriter.println(sendstr);// 发送命令
		NetworkUtil.printWriter.flush(); //清除缓冲区
		res = NetworkUtil.dataInputStream.readLine();//读取返回的数据
		System.out.println(res);
		closeSocketConnection("");//关闭远程连接
		if(res.equals("ON"))//设备打开
		    	return "1";
		else if(res.equals("OFF"))//设备关闭
		    	return "0";
		else
		{
			return "#";//未知状态
		}
	}
	
	/**
	 * 更具设备ID更新远程设备的的状态
	 * @param devicename 设备ID
	 * @param paramString2 设备类型
	 * @throws Exception 远程出错
	 */
	public static void openDevice(int devicename, String paramString2) throws Exception
	{
		
		String res;//打开的结果
		String sendstr = "[{\"id\":" + devicename + ",\"DeviceType\":\"" + paramString2 + "\",\"Command\":\"" + "OPEN" + "\"}]"; //发送对应设备ID打开命令
		getSocketConnection("");//获得远程连接
		NetworkUtil.printWriter.println(sendstr);// 发送命令
		NetworkUtil.printWriter.flush(); //清除缓冲区
		res = NetworkUtil.dataInputStream.readLine();//读取返回的数据
		System.out.println(res);
		closeSocketConnection("");//关闭远程连接
	}
	
	/**
	 * 关闭远程设备
	 * @param paramInt1 ID号
	 * @param paramString2	设备类型
	 * @throws Exception
	 */
	public static void closeDevice(int paramInt1, String paramString2)  throws Exception{
		// TODO Auto-generated method stub
		//关闭一个设备
		String res;//打开的结果
		String sendstr = "[{\"id\":" + paramInt1 + ",\"DeviceType\":\"" + paramString2 + "\",\"Command\":\"" + "OPEN" + "\"}]";//发送对应设备ID关闭命令
		getSocketConnection("");//获得远程连接
		NetworkUtil.printWriter.println(sendstr);// 发送命令
		NetworkUtil.printWriter.flush(); //清除缓冲区
		res = NetworkUtil.dataInputStream.readLine();//读取返回的数据
		System.out.println(res);
		closeSocketConnection("");//关闭远程连接
	}
	
	//关闭远程服务器的连接
	public static void closeSocketConnection(String string) throws IOException {
		// TODO Auto-generated method stub
		try {
			NetworkUtil.dataInputStream.close();//关闭输入流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			NetworkUtil.printWriter.close();//关闭输出流
			NetworkUtil.socket.close();//关闭Socket
			NetworkUtil.socket = null;
		 }
	}


	/**
	 * 关闭所有的设备
	 * @param arrayOfString 所有设备的数组
	 */
	public static void closeAllDevice(String[] arrayOfString) throws Exception{
		// TODO Auto-generated method stub
		{
		    String str;
		    if ((arrayOfString != null) && (arrayOfString.length > 0))
		      str = "";
		    for (int i = 0; i < arrayOfString.length; i++)
		    {
		    	//远程关闭设备
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
		    	//远程关闭设备
		    	openDevice(Integer.parseInt(arrayOfString[i]),String.valueOf(HomeControlActivity.getDevView(arrayOfString[i]).getDevType()));
		    }
		 }
	}
}
