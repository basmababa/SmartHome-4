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
	private Connect ctrl=new Connect();//������
	private String infoToSend="";//���͸��緹�ҿ�����Ϣ
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
		
		//��ʼ��
		ImageView im1=(ImageView)(findViewById(R.id.imageView1));
		ImageView im2=(ImageView)(findViewById(R.id.imageView2));
		ImageView im3=(ImageView)(findViewById(R.id.imageView3));
		ImageView im4=(ImageView)(findViewById(R.id.imageView4));
		ImageView im5=(ImageView)(findViewById(R.id.imageView5));
		ImageView im6=(ImageView)(findViewById(R.id.imageView6));
		//���  6���׵��ɺڵ�
		points[0]=im1;points[1]=im2;
		points[2]=im3;points[3]=im4;
		points[4]=im5;points[5]=im6;
		//�Ϸ����� �رհ�ť
		imageview_close = (ImageView)findViewById(R.id.imageView01_cooker);
		//�Ϸ������رհ�ť����
		imageview_close.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//ģʽ
		final ImageButton modelButton=(ImageButton)(findViewById(R.id.imageButton1));
		//ȷ��
		final ImageButton sureButton=(ImageButton)(findViewById(R.id.imageButton2));
		//ȡ������
		final ImageButton keepWarmButton=(ImageButton)(findViewById(R.id.imageButton3));
		//����
		final Button downButton=(Button)(findViewById(R.id.button1));
		//����
		final Button upButton=(Button)(findViewById(R.id.button2));
		//�¶���ʾ
		final TextView tempDispText=(TextView)(findViewById(R.id.editTextIP));
		
		/* ����һ������ʱ���ڲ��� */
		class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
				}
			@Override
			public void onFinish() 
			{//��ʱ���ʱ����
				tempDispText.setText("00");
				tempDispText.setClickable(true);
			}
			@Override
			public void onTick(long millisUntilFinished)
			{//��ʱ������ʾ
				tempDispText.setClickable(false);
				tempDispText.setText(millisUntilFinished /1000+"");
				
			}
		}
		
		/*Ϊģʽ��ť��Ӽ���
		 * �׵��ɺڵ�
		 */
		modelButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��ȫ��
				for(int i=0;i<6;i++){
					points[i].setImageResource(R.drawable.dark);
				}
			   //����Ҫ��ģʽ�ĵ�
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
		
		
		//Ϊȷ���ύ��ť����
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
			        //�ύѡ����Ϣ								
				    timing=tempDispText.getText().toString().trim();				
					infoToSend="update type01 "+ipaddress+" "+(current_model+1)+" "+timing;
					//���Ϳ�������
					ctrl.sendSure(infoToSend);		
 					TimeCount time;
					String sans=tempDispText.getText().toString();
					int t;
					t=Integer.parseInt(sans)*1000;
					time = new TimeCount(t, 1000);//����CountDownTimer����
					time.start();//��ʼ��ʱ    
					} catch (JSONException e) { 
						System.out.println("Jsons parse error !"); 
						showToast("Jsons parse error !");
						e.printStackTrace(); 
					} 
				}
			};
	        //new thread for get socket
			(new Thread(){//������һ�����̣߳����ڷ���socket���Ӳ���					 
	        	@Override
				 public void run() {
				 super.run();
				 try { 
					 	Socket cs=new Socket(NetworkUtil.SERVER_IP, 23);
						cs.setSoTimeout(6000);//�ȴ��ͻ����ӵ�ʱ�䲻����6��
						PrintStream cps=new PrintStream(cs.getOutputStream());
						cps.println("allget");
						cps.flush();
						BufferedReader br=new BufferedReader(new InputStreamReader(cs.getInputStream()));
						String receive =null;
						receive=br.readLine();			
						Message msg = new Message(); 
						Bundle b = new Bundle();// ������� 
						b.putString("receive", receive); 
						msg.setData(b); 
						myHandler.sendMessage(msg);
						//�ر���
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
		
		//Ϊת��Ϊ����ģʽ����
		keepWarmButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�ύѡ����Ϣ
				timing=tempDispText.getText().toString().trim();
				infoToSend="update type01 "+ipaddress+" c";
				//���ͱ�������
				ctrl.sendKeepWarm(infoToSend);
				if(current_model != -1)
				points[current_model].setImageResource(R.drawable.dark);
				tempDispText.setText("00");
			}
		});
		
		//Ϊ���ٶ�ʱ��ť����
		downButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timing=tempDispText.getText().toString().trim();
				if(timing=="")timing="0";//��ֹ���ֿ��ַ���
				int time=Integer.parseInt(timing);
				if(time>0){
					time--;
					timing=time+"";
					if(time < 10)timing = "0" + timing;
					tempDispText.setText(timing);
				}				
			}
		});
		//Ϊ���Ӷ�ʱ��ť���ü���
		upButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timing=tempDispText.getText().toString().trim();
				if(timing=="")
					timing="0";//��ֹ���ֿ��ַ���
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










