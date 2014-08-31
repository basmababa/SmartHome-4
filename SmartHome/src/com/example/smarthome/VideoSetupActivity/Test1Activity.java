package com.example.smarthome.VideoSetupActivity;

import com.example.smarthome.R;
import com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity.MonitorCameraInfo;
import com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity.SjrsSurfaceView;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Test1Activity extends Activity implements View.OnClickListener{

	private Button btn001;
	private Button btn004;
	private Button btn003;
	private Button btn006;
	private Button btn005;
	
	/** ��Ƶ���� */
	private Button btUp;
	/** ��Ƶ���� */
	private Button btDown;
	/** ��Ƶ���� */
	private Button btLeft;
	/** ��Ƶ���� */
	private Button btRigth;
	private Button span;
	private Button stop;
	private TextView connect_tip;
	/** button����¼�*/
	private ButtonListener btnListener;
	/** SurfaceView����������ʾ��Ƶ */
	private SjrsSurfaceView nowSjrsSurfaceView;
	/**	ʵ���������SDK*/
	private SjrsSurfaceView mSurface;
	/** ��ص���Ϣ�� */
	private MonitorCameraInfo cameraInfo;
	/** Ƶ�� */
	private int channel_choice = 1;
	private ProgressDialog progressDialog = null; // ������,������ʾ��Ƶ���
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȫ����ʾ�����ر�����
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_setup);
		context = this;
		this.btn001 = ((Button)findViewById(R.id.btn001_video));
	    this.btn003 = ((Button)findViewById(R.id.btn003_video));
	    this.btn004 = ((Button)findViewById(R.id.btn004_video));
	    this.btn005 = ((Button)findViewById(R.id.btn005_video));
	    this.btn006 = ((Button)findViewById(R.id.btn006_video));
	    this.btn001.setOnClickListener(this);
	    this.btn003.setOnClickListener(this);
	    this.btn004.setOnClickListener(this);
	    this.btn005.setOnClickListener(this);
	    this.btn006.setOnClickListener(this);
	    this.btUp = ((Button)findViewById(R.id.up_video));
		this.btDown = ((Button)findViewById(R.id.down_video));
		this.btLeft = ((Button)findViewById(R.id.left_video));
		this.btRigth = ((Button)findViewById(R.id.right_video));
		this.span = ((Button)findViewById(R.id.span_video));
		this.stop = ((Button)findViewById(R.id.stop_video));
		this.connect_tip = ((TextView)findViewById(R.id.homevideo_connect_tip));
		connect_tip.setText("Ƶ��һ");
		this.btUp.setOnClickListener(btnListener);
		this.btDown.setOnClickListener(btnListener);
		this.btLeft.setOnClickListener(btnListener);
		this.btRigth.setOnClickListener(btnListener);
		this.span.setOnClickListener(btnListener);
		this.stop.setOnClickListener(btnListener);
		this.nowSjrsSurfaceView = ((SjrsSurfaceView)findViewById(R.id.homevideo_video));
//		this.nowSjrsSurfaceView.setBackgroundResource(R.drawable.video_bg);
		init();//��ʼ������SDK
		Log.i("Video", "Video");
	}
	
	private void init() {
		// TODO Auto-generated method stub
		mSurface = new SjrsSurfaceView(Test1Activity.this);
	}
	
	/**
	 * ��ʾ
	 */
	protected void onResume() {
		super.onResume();
		//�ӳ�ˢ��
//        new Handler().postDelayed(new Runnable()
//        {
//        	public void run()
//        	{
        		// ���û���ڲ��ŵĻ�
        		if (!nowSjrsSurfaceView.playFlag) {
        			progressDialog = new ProgressDialog(context);
        			progressDialog.setTitle("��������");
        			progressDialog.setMessage("���������豸����");//�д��Ľ�
        			progressDialog.show();
        			// ��ص���Ϣ��
        			cameraInfo = new MonitorCameraInfo();
        			//224.186.114.116
        			cameraInfo.serverip = "10.21.63.116";
        			cameraInfo.serverport = 8000;
        			cameraInfo.username = "maomao";
        			cameraInfo.userpwd = "123456";
        			cameraInfo.channel = channel_choice;
        			cameraInfo.describe = "���Ե�";
        			nowSjrsSurfaceView.setMonitorInfo(cameraInfo);
        			// ��ʼʵʱԤ��
        			nowSjrsSurfaceView.startPlay();
        			progressDialog.dismiss(); // �رս�����
        		}
//        	}
//        	}, 500L);
        Log.i("1", "onCreate����1"); //INFO 
	}
	
	/**
	 * ��ͣ
	 */
	protected void onPause() {
		super.onPause();
		if (nowSjrsSurfaceView.playFlag) {
			nowSjrsSurfaceView.stopPlay(); // ֹͣʵʱԤ��
		}
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId())
	    {
	    	case R.id.btn001_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	changeChoise(0);
	  	      	break;
	    	}
	    	case R.id.btn003_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	changeChoise(1);
	  	      break;
	    	}
	    	case R.id.btn004_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	    		this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	changeChoise(3);
	  	      	break;
	    	}
	    	case R.id.btn005_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	    		this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	changeChoise(2);
	  	      	break;
	    	}
	    	case R.id.btn006_video:
	    	{
	    		this.btn001.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn003.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn004.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn005.setBackgroundResource(R.drawable.itembutton_manage1);
	  	      	this.btn006.setBackgroundResource(R.drawable.itembutton_manage01);
	  	      	changeChoise(4);
	  	      	break;	
	    	}
	    	default:
	    }
	}

	private void changeChoise(int i){
		// TODO Auto-generated method stub
		if (nowSjrsSurfaceView.playFlag) {
			nowSjrsSurfaceView.stopPlay(); // ֹͣʵʱԤ��
		}
	    switch (i)
	    {
	    	case 0:
	    	{
	    		connect_tip.setText("Ƶ��һ");
	    		channel_choice = 1;
	    		break;
	    	}
	    	case 1:
	    	{
	    		connect_tip.setText("Ƶ����");
	    		channel_choice = 2;
	    		break;
	    	}
	    	case 2:
	    	{
	    		connect_tip.setText("Ƶ����");
	    		channel_choice = 3;
	    		break;
	    	}
	    	case 3:
	    	{
	    		connect_tip.setText("Ƶ����");
	    		channel_choice = 4;
	    		break;
	    	}
	    	case 4:
	    	{
	    		connect_tip.setText("Ƶ����");
	    		channel_choice = 5;
	    		break;
	    	}
	    	default:
	    }
	    changeChannel();
	}
	
	public void changeChannel() {
		// TODO Auto-generated method stub
		if (!nowSjrsSurfaceView.playFlag) {
			// ��ص���Ϣ��
			cameraInfo = new MonitorCameraInfo();
			//224.186.114.116
			cameraInfo.serverip = "10.21.63.116";
			cameraInfo.serverport = 8000;
			cameraInfo.username = "maomao";
			cameraInfo.userpwd = "123456";
			cameraInfo.channel = channel_choice;
			cameraInfo.describe = "���Ե�";

			nowSjrsSurfaceView.setMonitorInfo(cameraInfo);
			// ��ʼʵʱԤ��
			nowSjrsSurfaceView.startPlay(); 
		}
	}

	/**
	 * ���򰴼�����
	 * ע�⣺�˴���ͨ���Ų��� ʵ��Ϊ��2 ������ָ��Ϊ��1(��ͨ��)�ſ���������
	 */
	public class ButtonListener implements OnTouchListener,OnClickListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.up_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,21,0);
				System.out.println("����");
				break;
			case R.id.down_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,22,0);
				System.out.println("����");
				break;
			case R.id.left_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,23,0);
				System.out.println("����");
				break;
			case R.id.right_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,24,0);
				System.out.println("����");
				break;
//			case R.id.bt_up_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,25,0);
//				System.out.println("����");
//				break;
//			case R.id.bt_up_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,26,0);
//				System.out.println("����");
//				break;
//			case R.id.bt_down_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,27,0);
//				System.out.println("����");
//				break;
//			case R.id.bt_down_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,28,0);
//				System.out.println("����");
//				break;
			/*case R.id.bt_amplification:
				boolean iss = mSurface.SjrsSurface().NET_DVR_PTZControlWithSpeed(cameraInfo.playNum,15,0,3);
				System.out.println("�쳣��"+mSurface.SjrsSurface().NET_DVR_GetLastError());
				System.out.println("����Ŵ�"+iss);
				break;
			case R.id.bt_shrink:
				boolean is = mSurface.SjrsSurface().NET_DVR_PTZControlWithSpeed(cameraInfo.playNum,16,0,3);
				System.out.println("������С"+is);
				break;*/
			default:
				break;
			}
			return false;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.up_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,21,1);
				System.out.println("���������ƶ�");
				break;
			case R.id.down_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,22,1);
				System.out.println("���������ƶ�");
				break;
			case R.id.left_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,23,1);
				System.out.println("���������ƶ�");
				break;
			case R.id.right_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,24,1);
				System.out.println("���������ƶ�");
				break;
//			case R.id.bt_up_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,25,1);
//				System.out.println("���������ƶ�");
//				break;
//			case R.id.bt_up_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,26,1);
//				System.out.println("���������ƶ�");
//				break;
//			case R.id.bt_down_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,27,1);
//				System.out.println("���������ƶ�");
//				break;
//			case R.id.bt_down_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,28,1);
//				System.out.println("���������ƶ�");
//				break;
			/*case R.id.bt_amplification:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(0,1,13,1);
				System.out.println("��������Ŵ�");
				break;
			case R.id.bt_shrink:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(0,1,14,1);
				System.out.println("����������С");
				break;*/
			default:
				break;
			}
		}
	}
}


