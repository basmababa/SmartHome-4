package com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity;

import com.example.smarthome.R;

import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class VideoPinDaoYiSubActivity extends Activity{

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
	private int channel_choice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Video", "Video");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_pin_dao_yi_sub);
		this.btUp = ((Button)findViewById(R.id.up_video));
		this.btDown = ((Button)findViewById(R.id.down_video));
		this.btLeft = ((Button)findViewById(R.id.left_video));
		this.btRigth = ((Button)findViewById(R.id.right_video));
		this.span = ((Button)findViewById(R.id.span_video));
		this.stop = ((Button)findViewById(R.id.stop_video));
		
		this.connect_tip = ((TextView)findViewById(R.id.homevideo_connect_tip));
		Intent localIntent = this.getIntent();
		System.out.println(localIntent.getStringExtra("pindao"));
		connect_tip.setText("Ƶ����" + localIntent.getStringExtra("pindao"));
		channel_choice = Integer.valueOf(localIntent.getStringExtra("pindao"));
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
		mSurface = new SjrsSurfaceView(VideoPinDaoYiSubActivity.this);
	}
	
	/**
	 * ��ʾ
	 */
	protected void onResume() {
		super.onResume();
		// ���û���ڲ��ŵĻ�
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
	 * ��ͣ
	 */
	protected void onPause() {
		super.onPause();
		if (nowSjrsSurfaceView.playFlag) {
			nowSjrsSurfaceView.stopPlay(); // ֹͣʵʱԤ��
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_pin_dao_yi_sub, menu);
		return true;
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
