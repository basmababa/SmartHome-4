package com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity;

import java.io.File;

import android.os.Environment;

/**
 * ��ص���Ϣ��
 * @author cont
 */
public class MonitorCameraInfo {
	public String serverip = "10.21.63.116";
	public int serverport = 8000;
	public String username = "maomao";
	public String userpwd = "123456";
	public int channel = 1;
	public String describe = "���Ե�";
	
	/**��¼�ʺ�id */
	public int userId = 0;
	/**���ŷ���ֵ */
	public int playNum = 0;
	/**ץͼ���·�� */
	public String filepath = getSDRootPath()+"/HCNetSDK";
	
	public MonitorCameraInfo() {}
	
	/**
	 * ���SD����·��
	 * 
	 * @return
	 */
	public static String getSDRootPath() {
		File sdDir = null;
		// �ж�
		boolean sdCardExist = hasSdcard();
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
			return sdDir.toString();
		} else {
			return null;
		}
	}
	/**
	 * �Ƿ����Sd card
	 * 
	 * @return true:���ڣ�false:������
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
