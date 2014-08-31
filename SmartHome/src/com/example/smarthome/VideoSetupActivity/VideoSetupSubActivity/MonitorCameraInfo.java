package com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity;

import java.io.File;

import android.os.Environment;

/**
 * 监控点信息类
 * @author cont
 */
public class MonitorCameraInfo {
	public String serverip = "10.21.63.116";
	public int serverport = 8000;
	public String username = "maomao";
	public String userpwd = "123456";
	public int channel = 1;
	public String describe = "测试点";
	
	/**登录帐号id */
	public int userId = 0;
	/**播放返回值 */
	public int playNum = 0;
	/**抓图存放路劲 */
	public String filepath = getSDRootPath()+"/HCNetSDK";
	
	public MonitorCameraInfo() {}
	
	/**
	 * 获得SD卡根路径
	 * 
	 * @return
	 */
	public static String getSDRootPath() {
		File sdDir = null;
		// 判断
		boolean sdCardExist = hasSdcard();
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
			return sdDir.toString();
		} else {
			return null;
		}
	}
	/**
	 * 是否存在Sd card
	 * 
	 * @return true:存在；false:不存在
	 */
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
}
