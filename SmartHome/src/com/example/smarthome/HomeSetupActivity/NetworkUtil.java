package com.example.smarthome.HomeSetupActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class NetworkUtil {

//	public static String SERVER_IP = "192.168.1.33";//Ĭ��IP  ���IP�ǿ�������ifconfig��ʾ������IP
	public static String SERVER_IP = "10.21.63.113";//Ĭ��IP  ���IP�ǿ�������ifconfig��ʾ������IP
  	public static int SERVER_PORT = 23;//���õ�URL�Ͷ˿�
	public static Socket socket = null;//����Socket
	public static PrintWriter printWriter = null;//�����
	public static BufferedReader dataInputStream = null;//������
	
}
