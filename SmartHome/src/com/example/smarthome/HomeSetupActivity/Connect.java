package com.example.smarthome.HomeSetupActivity;

import java.net.*;
import java.io.*;

import android.app.Application;


public class Connect {
		
//	MyIp myIp;
//    myIp = (MyIp)getApplication();
    private String ip = NetworkUtil.SERVER_IP;
	//�ύѡ��ģʽ����ʱ��Ϣ
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
	//�ύȡ��/���½ӿ�
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

	//���ܵ緹����÷����ӿ�
	public void receive(){
		
	}
	
}


