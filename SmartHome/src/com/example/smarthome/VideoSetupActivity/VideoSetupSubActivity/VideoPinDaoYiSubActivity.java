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

	/** 视频向上 */
	private Button btUp;
	/** 视频向下 */
	private Button btDown;
	/** 视频向左 */
	private Button btLeft;
	/** 视频向右 */
	private Button btRigth;
	private Button span;
	private Button stop;
	private TextView connect_tip;
	/** button点击事件*/
	private ButtonListener btnListener;
	/** SurfaceView对象，用来显示视频 */
	private SjrsSurfaceView nowSjrsSurfaceView;
	/**	实例化网络库SDK*/
	private SjrsSurfaceView mSurface;
	/** 监控点信息类 */
	private MonitorCameraInfo cameraInfo;
	/** 频道 */
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
		connect_tip.setText("频道：" + localIntent.getStringExtra("pindao"));
		channel_choice = Integer.valueOf(localIntent.getStringExtra("pindao"));
		this.btUp.setOnClickListener(btnListener);
		this.btDown.setOnClickListener(btnListener);
		this.btLeft.setOnClickListener(btnListener);
		this.btRigth.setOnClickListener(btnListener);
		this.span.setOnClickListener(btnListener);
		this.stop.setOnClickListener(btnListener);
		this.nowSjrsSurfaceView = ((SjrsSurfaceView)findViewById(R.id.homevideo_video));
//		this.nowSjrsSurfaceView.setBackgroundResource(R.drawable.video_bg);
		init();//初始化网络SDK
		Log.i("Video", "Video");
	}

	private void init() {
		// TODO Auto-generated method stub
		mSurface = new SjrsSurfaceView(VideoPinDaoYiSubActivity.this);
	}
	
	/**
	 * 显示
	 */
	protected void onResume() {
		super.onResume();
		// 如果没有在播放的话
		if (!nowSjrsSurfaceView.playFlag) {
			// 监控点信息类
			cameraInfo = new MonitorCameraInfo();
			//224.186.114.116
			cameraInfo.serverip = "10.21.63.116";
			cameraInfo.serverport = 8000;
			cameraInfo.username = "maomao";
			cameraInfo.userpwd = "123456";
			cameraInfo.channel = channel_choice;
			cameraInfo.describe = "测试点";

			nowSjrsSurfaceView.setMonitorInfo(cameraInfo);
			// 开始实时预览
			nowSjrsSurfaceView.startPlay(); 
		}
	}
	
	/**
	 * 暂停
	 */
	protected void onPause() {
		super.onPause();
		if (nowSjrsSurfaceView.playFlag) {
			nowSjrsSurfaceView.stopPlay(); // 停止实时预览
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_pin_dao_yi_sub, menu);
		return true;
	}
	
	/**
	 * 方向按键监听
	 * 注意：此处的通道号参数 实质为：2 但必须指定为：1(主通道)才可以做控制
	 */
	public class ButtonListener implements OnTouchListener,OnClickListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.up_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,21,0);
				System.out.println("向上");
				break;
			case R.id.down_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,22,0);
				System.out.println("向下");
				break;
			case R.id.left_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,23,0);
				System.out.println("向左");
				break;
			case R.id.right_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,24,0);
				System.out.println("向右");
				break;
//			case R.id.bt_up_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,25,0);
//				System.out.println("上左");
//				break;
//			case R.id.bt_up_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,26,0);
//				System.out.println("上右");
//				break;
//			case R.id.bt_down_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,27,0);
//				System.out.println("下左");
//				break;
//			case R.id.bt_down_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,28,0);
//				System.out.println("下右");
//				break;
			/*case R.id.bt_amplification:
				boolean iss = mSurface.SjrsSurface().NET_DVR_PTZControlWithSpeed(cameraInfo.playNum,15,0,3);
				System.out.println("异常："+mSurface.SjrsSurface().NET_DVR_GetLastError());
				System.out.println("焦距放大"+iss);
				break;
			case R.id.bt_shrink:
				boolean is = mSurface.SjrsSurface().NET_DVR_PTZControlWithSpeed(cameraInfo.playNum,16,0,3);
				System.out.println("焦距缩小"+is);
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
				System.out.println("结束向上移动");
				break;
			case R.id.down_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,22,1);
				System.out.println("结束向下移动");
				break;
			case R.id.left_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,23,1);
				System.out.println("结束向左移动");
				break;
			case R.id.right_video:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,24,1);
				System.out.println("结束向右移动");
				break;
//			case R.id.bt_up_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,25,1);
//				System.out.println("结束上左移动");
//				break;
//			case R.id.bt_up_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,26,1);
//				System.out.println("结束上右移动");
//				break;
//			case R.id.bt_down_left:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,27,1);
//				System.out.println("结束下左移动");
//				break;
//			case R.id.bt_down_rigth:
//				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(cameraInfo.userId,1,28,1);
//				System.out.println("结束下右移动");
//				break;
			/*case R.id.bt_amplification:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(0,1,13,1);
				System.out.println("结束焦距放大");
				break;
			case R.id.bt_shrink:
				mSurface.SjrsSurface().NET_DVR_PTZControl_Other(0,1,14,1);
				System.out.println("结束焦距缩小");
				break;*/
			default:
				break;
			}
		}
	}

}
