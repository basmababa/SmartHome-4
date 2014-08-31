package com.example.smarthome.HomeSetupActivity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarthome.R;

import android.R.integer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.storage.OnObbStateChangeListener;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class cookerControlActivity extends Activity {

	private int current_model=-1;
	private ImageView[] points = new ImageView[6];
	private Connect ctrl=new Connect();//控制器
	private String infoToSend="";//发送给电饭煲控制信息
	private String timing="0";
	private String ipaddress;
	private ImageView imageview_close;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cooker_control);
		
		context = this;
		
		//初始化
		ImageView im1=(ImageView)(findViewById(R.id.imageView1));
		ImageView im2=(ImageView)(findViewById(R.id.imageView2));
		ImageView im3=(ImageView)(findViewById(R.id.imageView3));
		ImageView im4=(ImageView)(findViewById(R.id.imageView4));
		ImageView im5=(ImageView)(findViewById(R.id.imageView5));
		ImageView im6=(ImageView)(findViewById(R.id.imageView6));
		//点击  6个白点变成黑点
		points[0]=im1;points[1]=im2;
		points[2]=im3;points[3]=im4;
		points[4]=im5;points[5]=im6;
		//上方横条 关闭按钮
		imageview_close = (ImageView)findViewById(R.id.imageView01_cooker);
		//上方横条关闭按钮监听
		imageview_close.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//模式
		final ImageButton modelButton=(ImageButton)(findViewById(R.id.imageButton1));
		//确认
		final ImageButton sureButton=(ImageButton)(findViewById(R.id.imageButton2));
		//取消保温
		final ImageButton keepWarmButton=(ImageButton)(findViewById(R.id.imageButton3));
		//减温
		final Button downButton=(Button)(findViewById(R.id.button1));
		//增温
		final Button upButton=(Button)(findViewById(R.id.button2));
		//温度显示
		final TextView tempDispText=(TextView)(findViewById(R.id.editTextIP));
		
		/* 定义一个倒计时的内部类 */
		class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
				}
			@Override
			public void onFinish() 
			{//计时完毕时触发
				tempDispText.setText("00");
				tempDispText.setClickable(true);
			}
			@Override
			public void onTick(long millisUntilFinished)
			{//计时过程显示
				tempDispText.setClickable(false);
				tempDispText.setText(millisUntilFinished /1000+"");
				
			}
		}
		
		/*为模式按钮添加监听
		 * 白点变成黑点
		 */
		modelButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//灯全灭
				for(int i=0;i<6;i++){
					points[i].setImageResource(R.drawable.dark);
				}
			   //亮需要的模式的灯
				current_model=(++current_model)%6;
				points[current_model].setImageResource(R.drawable.light);		
				if(current_model==0)
					tempDispText.setText("08");	
				else if(current_model==1)
					tempDispText.setText("40");	
				else if(current_model==2)
					tempDispText.setText("90");
				else if(current_model==3)
					tempDispText.setText("20");
				else if(current_model==4)
					tempDispText.setText("45");
				else if(current_model==5)
					tempDispText.setText("60");
			}
		});
		
		
		//为确定提交按钮监听
		sureButton.setOnClickListener(new Button.OnClickListener() {		
				 
			@Override
			public void onClick(View v) {
							
			final Handler myHandler = new Handler() {  
				
				@Override  
				public void handleMessage(Message msg) {      
					super.handleMessage(msg);   
					Bundle b = msg.getData();  
					String receive = b.getString("receive");
					try { 
					JSONArray jsonObjs = new JSONObject(receive).getJSONArray("devices"); 
					for(int i = 0; i < jsonObjs.length() ; i++){ 
			        	JSONObject jsonObj = (JSONObject)jsonObjs.getJSONObject(i);
			        	String nwk = jsonObj.getString("nwk"); 
			            String type = jsonObj.getString("type");		            					           
			            if(type.equals("type01")) ipaddress=nwk;
					}
			        //提交选择信息								
				    timing=tempDispText.getText().toString().trim();				
					infoToSend="update type01 "+ipaddress+" "+(current_model+1)+" "+timing;
					//发送控制命令
					ctrl.sendSure(infoToSend);		
 					TimeCount time;
					String sans=tempDispText.getText().toString();
					int t;
					t=Integer.parseInt(sans)*1000;
					time = new TimeCount(t, 1000);//构造CountDownTimer对象
					time.start();//开始计时    
					} catch (JSONException e) { 
						System.out.println("Jsons parse error !"); 
						showToast("Jsons parse error !");
						e.printStackTrace(); 
					} 
				}
			};
	        //new thread for get socket
			(new Thread(){//这里有一个新线程，用于放置socket连接操作					 
	        	@Override
				 public void run() {
				 super.run();
				 try { 
					 	Socket cs=new Socket(NetworkUtil.SERVER_IP, 23);
						cs.setSoTimeout(6000);//等待客户连接的时间不超过6秒
						PrintStream cps=new PrintStream(cs.getOutputStream());
						cps.println("allget");
						cps.flush();
						BufferedReader br=new BufferedReader(new InputStreamReader(cs.getInputStream()));
						String receive =null;
						receive=br.readLine();			
						Message msg = new Message(); 
						Bundle b = new Bundle();// 存放数据 
						b.putString("receive", receive); 
						msg.setData(b); 
						myHandler.sendMessage(msg);
						//关闭流
						br.close();
						cs.close();				 
					}catch (IOException e) {
						showToast("get socket error!");
						e.printStackTrace();
					}			
				}
			}).start();									
			}
		});
		
		//为转换为保温模式监听
		keepWarmButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//提交选择信息
				timing=tempDispText.getText().toString().trim();
				infoToSend="update type01 "+ipaddress+" c";
				//发送保温命令
				ctrl.sendKeepWarm(infoToSend);
				if(current_model != -1)
				points[current_model].setImageResource(R.drawable.dark);
				tempDispText.setText("00");
			}
		});
		
		//为减少定时按钮监听
		downButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timing=tempDispText.getText().toString().trim();
				if(timing=="")timing="0";//防止出现空字符串
				int time=Integer.parseInt(timing);
				if(time>0){
					time--;
					timing=time+"";
					if(time < 10)timing = "0" + timing;
					tempDispText.setText(timing);
				}				
			}
		});
		//为增加定时按钮设置监听
		upButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timing=tempDispText.getText().toString().trim();
				if(timing=="")
					timing="0";//防止出现空字符串
				int time=Integer.parseInt(timing);
				time++;
				timing=time+"";
				if(time < 10)timing = "0" + timing;
				tempDispText.setText(timing);				
			}
		});
		
	}
	
	private void showToast(String msg)
	{
		Toast.makeText(context, msg, 0).show();
	}
	

}










