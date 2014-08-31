package com.example.smarthome.VideoSetupActivity.VideoSetupSubActivity;

import org.MediaPlayer.PlayM4.Player;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.RealPlayCallBack;

/**
 * SurfaceView，用来播放视频并显示
 * 在要显示视频的SurfaceView对象创建完成后（即surfaceCreated()方法被触发）再连接服务器
 * 进行实时预览，否则在实时预览时可能会出现SurfaceView尚未完全加载成功，导致调调数据显示异常
 */
public class SjrsSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private HCNetSDK videoCtr;    //网络库sdk
	private Player myPlayer = null;  //播放库sdk
	public int playPort = -1;   //播放端口
	public boolean playFlag = false;   //播放标志
	public MonitorCameraInfo cameraInfo = null;   //监控点信息
	private int i=0;
	private SurfaceHolder holder = null;//用于管理SjrsSurfaceView

	public SjrsSurfaceView(Context paramContext) {
		super(paramContext);
		initSurfaceView();
	}

	public SjrsSurfaceView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initSurfaceView();
	}

	public SjrsSurfaceView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet);
		initSurfaceView();
	}

	private void initSurfaceView() {
		getHolder().addCallback(this);
	}

	//实例化海康威视android sdk
	public HCNetSDK SjrsSurface(){
		if(videoCtr == null){
			videoCtr = new HCNetSDK();
		}
		return videoCtr;
	}
	
	public boolean onDown(MotionEvent paramMotionEvent) {
		return false;
	}

	public boolean onFling(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
		return false;
	}

	public void onLongPress(MotionEvent paramMotionEvent) {
	}

	public boolean onScroll(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
		return false;
	}

	public void onShowPress(MotionEvent paramMotionEvent) {
	}

	public boolean onSingleTapUp(MotionEvent paramMotionEvent) {
		return false;
	}
	
	@Override
	//实现Callback接口里面的一个方法
	public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1,
			int paramInt2, int paramInt3) {
	}

	@Override
	//实现Callback接口里面的一个方法
	public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
		holder = this.getHolder();
	}

	@Override
	//实现Callback接口里面的一个方法
	public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {}

	public void setMonitorInfo(MonitorCameraInfo setMonitorInfo) {
		this.cameraInfo = setMonitorInfo;
	}

	/**
	 * flag 1/暂停 0/恢复
	 */
	public void pausePaly(int flag) {
		myPlayer.pause(playPort, flag);
	}

	/**
	 * 停止播放&释放资源
	 */
	public void stopPlay() {
		try {
			playFlag = false;
			videoCtr.NET_DVR_StopRealPlay(playPort);//停止预览
			videoCtr.NET_DVR_Logout_V30(cameraInfo.userId);//用户注销
			cameraInfo.userId = -1;
			videoCtr.NET_DVR_Cleanup();//释放SDK资源，在程序结束之前调用

			//停止播放后释放Player资源
			if (myPlayer != null) {
				myPlayer.stop(playPort);
				myPlayer.closeStream(playPort);
				myPlayer.freePort(playPort);
				playPort = -1;
				//使用绘图缓存后释放资源
				destroyDrawingCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//暂不做任何操作
		}
	}

	/**
	 *	开始实时预览 
	 **/
	public void startPlay() {
		try {
			//实例化播放库API
			//************************************************************
			//错误开始出现，下面由于加载不了那玩意儿，所以才会出现这样的结果。
			myPlayer = Player.getInstance();													
			SjrsSurface();
			//初始化海康威视android sdk
			videoCtr.NET_DVR_Init();   //bolean类
			System.out.println("初始化返回值");
			System.out.println(videoCtr.NET_DVR_Init());
//			Toast.makeText(this, "Deleted Successfully!", Toast.LENGTH_LONG).show(); 
			//设置错误回掉函数
			videoCtr.NET_DVR_SetExceptionCallBack(mExceptionCallBack);  
			//设置连接超时时长
			videoCtr.NET_DVR_SetConnectTime(4000);
			//获取空闲播放端口
			playPort = myPlayer.getPort();  //int类,在回调函数中有出现，获取播放端口
			System.out.println("*******************palyPort****************");
			System.out.println(playPort);
			NET_DVR_DEVICEINFO_V30 deviceInfo = new NET_DVR_DEVICEINFO_V30();//获得设备参数
			//登录服务器
			cameraInfo.userId = videoCtr.NET_DVR_Login_V30(cameraInfo.serverip,
					cameraInfo.serverport, cameraInfo.username,
					cameraInfo.userpwd, deviceInfo);

			System.out.println("下面是设备信息************************");
			System.out.println("userId=" + cameraInfo.userId);
			System.out.println("通道开始=" + deviceInfo.byStartChan);  //起始通道号，目前设备通道号从1开始 
			System.out.println("通道个数=" + deviceInfo.byChanNum);  //byChanNum设备模拟通道个数 
			System.out.println("设备类型=" + deviceInfo.byDVRType);   //72XXHV_ST15 DVR 
			System.out.println("ip通道个数=" + deviceInfo.byIPChanNum);    //byIPChanNum 设备最大数字通道个数 0个

			byte[] sbbyte = deviceInfo.sSerialNumber;
			String sNo = "";
			for (int i = 0; i < sbbyte.length; i++) {
				sNo += String.valueOf(sbbyte[i]);
			}

			System.out.println("设备序列号=" + sNo);
			System.out.println("************************");

			// 参数结构体
			NET_DVR_CLIENTINFO clientInfo = new NET_DVR_CLIENTINFO();
			// 浏览通道号
			clientInfo.lChannel = cameraInfo.channel;
			// 子码流（保证图像连续性）tcp连接方式，如果要保证图像清晰度，可选用主码流 
			clientInfo.lLinkMode = 0x80000000; 
			// 多播地址 需要多播时配置
			clientInfo.sMultiCastIP = null;
			// 启动实时预览	mRealDataCallback即为数据回传回掉函数 
			cameraInfo.playNum = videoCtr.NET_DVR_RealPlay_V30(cameraInfo.userId, clientInfo,mRealPlayCallBack, false);
			System.out.println("playFlags=" + cameraInfo.playNum);
			System.out.println("GetLastError="+ videoCtr.NET_DVR_GetLastError());

		} catch (Exception e) {
			e.printStackTrace();
			// 释放资源
			stopPlay();
		}
	}

	/**
	 * 异常回掉函数
	 */
	private ExceptionCallBack mExceptionCallBack = new ExceptionCallBack() {

		public void fExceptionCallBack(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			System.out.println("异常回掉函数运行！");
		}
	};

	private RealPlayCallBack mRealPlayCallBack = new RealPlayCallBack() {
		@Override
		public void fRealDataCallBack(int lRealHandle, int dataType,                   //lRealHandle当前预览句柄 NET_DVR_RealPlay_V30的返回值				
				byte[] paramArrayOfByte, int byteLen) {                                   //byteLen 缓冲区大小
			//端口连接状态返回码
			System.out.println("LRealHandle="+lRealHandle);
			
			System.out.println("**********看看回调函数怎么执行的*******************");
			if (playPort == -1)
				return;
			// dataType数据类型
			System.out.println("**************dataType****************"+dataType);
			if(i==0)
				SystemClock.sleep(600);
			i++;
			switch (dataType) {
			case 1: 
				// 打开流连接
				if (myPlayer.openStream(playPort, paramArrayOfByte, byteLen,1024*1024)) {
					// 1.连接状态	2.视频模式：1为实时
					
					System.out.println("**************openStream success***********************");
					
					if (myPlayer.setStreamOpenMode(playPort, 1)) {
						// 放入要播放的控件中	
						
						System.out.println("********************setStreamOpenMode Success*****************");
						
						if (myPlayer.play(playPort, holder)) {
							
							
							System.out.println("*******************play Success?***********************");
							
							
							playFlag = true;
						} else {
							playError(3);
						}
					} else {
						playError(2);
					}
				} else {
					playError(1);
					System.out.println("step1 error ----openStream error");
				}
				break;
			case 2:
				
				if (playFlag && myPlayer.inputData(playPort, paramArrayOfByte,byteLen)) {
					playFlag = true;
					System.out.println("*************case2 enter************");
					System.out.println("palyFlag="+playFlag);
				} else {
					playError(4);
					playFlag = false;
				}
			}
		}
	};

	/**
	 * 打印出相对应的异常
	 * @param step
	 */
	private void playError(int step) {

		switch (step) {
		case 1:
			System.out.println("openStream error,step=" + step);
			break;
		case 2:
			System.out.println("setStreamOpenMode error,step=" + step);
			break;
		case 3:
			System.out.println("play error,step=" + step);
			break;
		case 4:
			System.out.println("inputData error,step=" + step);
			break;
		}
		stopPlay();
	}
}