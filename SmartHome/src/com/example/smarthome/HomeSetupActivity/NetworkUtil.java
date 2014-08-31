package com.example.smarthome.HomeSetupActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class NetworkUtil {

//	public static String SERVER_IP = "192.168.1.33";//默认IP  这个IP是开发板上ifconfig显示出来的IP
	public static String SERVER_IP = "10.21.63.113";//默认IP  这个IP是开发板上ifconfig显示出来的IP
  	public static int SERVER_PORT = 23;//设置的URL和端口
	public static Socket socket = null;//保存Socket
	public static PrintWriter printWriter = null;//输出流
	public static BufferedReader dataInputStream = null;//输入流
	
}
