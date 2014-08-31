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
 * SurfaceView������������Ƶ����ʾ
 * ��Ҫ��ʾ��Ƶ��SurfaceView���󴴽���ɺ󣨼�surfaceCreated()�����������������ӷ�����
 * ����ʵʱԤ����������ʵʱԤ��ʱ���ܻ����SurfaceView��δ��ȫ���سɹ������µ���������ʾ�쳣
 */
public class SjrsSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private HCNetSDK videoCtr;    //�����sdk
	private Player myPlayer = null;  //���ſ�sdk
	public int playPort = -1;   //���Ŷ˿�
	public boolean playFlag = false;   //���ű�־
	public MonitorCameraInfo cameraInfo = null;   //��ص���Ϣ
	private int i=0;
	private SurfaceHolder holder = null;//���ڹ���SjrsSurfaceView

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

	//ʵ������������android sdk
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
	//ʵ��Callback�ӿ������һ������
	public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1,
			int paramInt2, int paramInt3) {
	}

	@Override
	//ʵ��Callback�ӿ������һ������
	public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
		holder = this.getHolder();
	}

	@Override
	//ʵ��Callback�ӿ������һ������
	public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {}

	public void setMonitorInfo(MonitorCameraInfo setMonitorInfo) {
		this.cameraInfo = setMonitorInfo;
	}

	/**
	 * flag 1/��ͣ 0/�ָ�
	 */
	public void pausePaly(int flag) {
		myPlayer.pause(playPort, flag);
	}

	/**
	 * ֹͣ����&�ͷ���Դ
	 */
	public void stopPlay() {
		try {
			playFlag = false;
			videoCtr.NET_DVR_StopRealPlay(playPort);//ֹͣԤ��
			videoCtr.NET_DVR_Logout_V30(cameraInfo.userId);//�û�ע��
			cameraInfo.userId = -1;
			videoCtr.NET_DVR_Cleanup();//�ͷ�SDK��Դ���ڳ������֮ǰ����

			//ֹͣ���ź��ͷ�Player��Դ
			if (myPlayer != null) {
				myPlayer.stop(playPort);
				myPlayer.closeStream(playPort);
				myPlayer.freePort(playPort);
				playPort = -1;
				//ʹ�û�ͼ������ͷ���Դ
				destroyDrawingCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//�ݲ����κβ���
		}
	}

	/**
	 *	��ʼʵʱԤ�� 
	 **/
	public void startPlay() {
		try {
			//ʵ�������ſ�API
			//************************************************************
			//����ʼ���֣��������ڼ��ز���������������ԲŻ���������Ľ����
			myPlayer = Player.getInstance();													
			SjrsSurface();
			//��ʼ����������android sdk
			videoCtr.NET_DVR_Init();   //bolean��
			System.out.println("��ʼ������ֵ");
			System.out.println(videoCtr.NET_DVR_Init());
//			Toast.makeText(this, "Deleted Successfully!", Toast.LENGTH_LONG).show(); 
			//���ô���ص�����
			videoCtr.NET_DVR_SetExceptionCallBack(mExceptionCallBack);  
			//�������ӳ�ʱʱ��
			videoCtr.NET_DVR_SetConnectTime(4000);
			//��ȡ���в��Ŷ˿�
			playPort = myPlayer.getPort();  //int��,�ڻص��������г��֣���ȡ���Ŷ˿�
			System.out.println("*******************palyPort****************");
			System.out.println(playPort);
			NET_DVR_DEVICEINFO_V30 deviceInfo = new NET_DVR_DEVICEINFO_V30();//����豸����
			//��¼������
			cameraInfo.userId = videoCtr.NET_DVR_Login_V30(cameraInfo.serverip,
					cameraInfo.serverport, cameraInfo.username,
					cameraInfo.userpwd, deviceInfo);

			System.out.println("�������豸��Ϣ************************");
			System.out.println("userId=" + cameraInfo.userId);
			System.out.println("ͨ����ʼ=" + deviceInfo.byStartChan);  //��ʼͨ���ţ�Ŀǰ�豸ͨ���Ŵ�1��ʼ 
			System.out.println("ͨ������=" + deviceInfo.byChanNum);  //byChanNum�豸ģ��ͨ������ 
			System.out.println("�豸����=" + deviceInfo.byDVRType);   //72XXHV_ST15 DVR 
			System.out.println("ipͨ������=" + deviceInfo.byIPChanNum);    //byIPChanNum �豸�������ͨ������ 0��

			byte[] sbbyte = deviceInfo.sSerialNumber;
			String sNo = "";
			for (int i = 0; i < sbbyte.length; i++) {
				sNo += String.valueOf(sbbyte[i]);
			}

			System.out.println("�豸���к�=" + sNo);
			System.out.println("************************");

			// �����ṹ��
			NET_DVR_CLIENTINFO clientInfo = new NET_DVR_CLIENTINFO();
			// ���ͨ����
			clientInfo.lChannel = cameraInfo.channel;
			// ����������֤ͼ�������ԣ�tcp���ӷ�ʽ�����Ҫ��֤ͼ�������ȣ���ѡ�������� 
			clientInfo.lLinkMode = 0x80000000; 
			// �ಥ��ַ ��Ҫ�ಥʱ����
			clientInfo.sMultiCastIP = null;
			// ����ʵʱԤ��	mRealDataCallback��Ϊ���ݻش��ص����� 
			cameraInfo.playNum = videoCtr.NET_DVR_RealPlay_V30(cameraInfo.userId, clientInfo,mRealPlayCallBack, false);
			System.out.println("playFlags=" + cameraInfo.playNum);
			System.out.println("GetLastError="+ videoCtr.NET_DVR_GetLastError());

		} catch (Exception e) {
			e.printStackTrace();
			// �ͷ���Դ
			stopPlay();
		}
	}

	/**
	 * �쳣�ص�����
	 */
	private ExceptionCallBack mExceptionCallBack = new ExceptionCallBack() {

		public void fExceptionCallBack(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			System.out.println("�쳣�ص��������У�");
		}
	};

	private RealPlayCallBack mRealPlayCallBack = new RealPlayCallBack() {
		@Override
		public void fRealDataCallBack(int lRealHandle, int dataType,                   //lRealHandle��ǰԤ����� NET_DVR_RealPlay_V30�ķ���ֵ				
				byte[] paramArrayOfByte, int byteLen) {                                   //byteLen ��������С
			//�˿�����״̬������
			System.out.println("LRealHandle="+lRealHandle);
			
			System.out.println("**********�����ص�������ôִ�е�*******************");
			if (playPort == -1)
				return;
			// dataType��������
			System.out.println("**************dataType****************"+dataType);
			if(i==0)
				SystemClock.sleep(600);
			i++;
			switch (dataType) {
			case 1: 
				// ��������
				if (myPlayer.openStream(playPort, paramArrayOfByte, byteLen,1024*1024)) {
					// 1.����״̬	2.��Ƶģʽ��1Ϊʵʱ
					
					System.out.println("**************openStream success***********************");
					
					if (myPlayer.setStreamOpenMode(playPort, 1)) {
						// ����Ҫ���ŵĿؼ���	
						
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
	 * ��ӡ�����Ӧ���쳣
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