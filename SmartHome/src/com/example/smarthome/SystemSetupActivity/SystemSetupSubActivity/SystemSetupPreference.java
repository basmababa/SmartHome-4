package com.example.smarthome.SystemSetupActivity.SystemSetupSubActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class SystemSetupPreference {
	private static Context context;
	private static String serviceIp;
	private static int servicePort;
	public static int MODE = Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE;
	
	public static void init(Context paramContext) {
		// TODO Auto-generated method stub
		context = paramContext;
		SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("settings", MODE);
		serviceIp = localSharedPreferences.getString("serviceip", "192.168.1.1");
	    servicePort = localSharedPreferences.getInt("serviceport", 8080);
	}
	//设置IP
	public static void setServiceIp(String paramString) {
		// TODO Auto-generated method stub
		serviceIp = paramString;
	    context.getSharedPreferences("settings", MODE).edit().putString("serviceip", paramString).commit();
	}
	//获得IP
	public static String getServiceIp() {
		// TODO Auto-generated method stub
		return serviceIp;
	}
	
	//设置Port
	public static void setServicePort(int paramInt) {
		// TODO Auto-generated method stub
		servicePort = paramInt;
	    context.getSharedPreferences("settings", MODE).edit().putInt("serviceport", paramInt);
	}
	//获得Port
	public static int getServicePort() {
		// TODO Auto-generated method stub
		return servicePort;
	}
}
