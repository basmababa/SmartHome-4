package com.example.smarthome.HomeSetupActivity;

import java.net.*;
import java.io.*;

import android.app.Application;


public class Connect {
		
//	MyIp myIp;
//    myIp = (MyIp)getApplication();
    private String ip = NetworkUtil.SERVER_IP;
	//提交选择模式及定时信息
	public void sendSure(final String info){
		Runnable runnable = new Runnable() {
			public void run() {
				try {					 
				 	Socket cs = new Socket(ip, 23); 
					cs.setSoTimeout(6000);
					PrintStream cps = new PrintStream(cs.getOutputStream());
					cps.println(info);
					BufferedReader br=new BufferedReader(new InputStreamReader(cs.getInputStream()));
					String receive =null;
					receive=br.readLine();			
					br.close();
					cs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}
	//提交取消/保温接口
	public void sendKeepWarm(final String info){
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					Socket cs = new Socket(ip,23);
					cs.setSoTimeout(6000);
					PrintStream cps = new PrintStream(cs.getOutputStream());
					cps.println(info);
					BufferedReader br=new BufferedReader(new InputStreamReader(cs.getInputStream()));
					String receive =null;
					receive=br.readLine();			
					br.close();
					cs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};			
		new Thread(runnable).start();
	}

	//接受电饭煲煮好反馈接口
	public void receive(){
		
	}
	
}


