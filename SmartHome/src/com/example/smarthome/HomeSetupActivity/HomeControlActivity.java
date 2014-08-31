//����ļ��ǰ����������Activity
package com.example.smarthome.HomeSetupActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.R;
import com.example.smarthome.SmartHomebaseApp;

public class HomeControlActivity extends Activity implements
		View.OnClickListener
{

	private static Context context;// ������
	private static List<DevView> devs;// �����豸��Ϣ
	private static BaseAdapter devsAdapter;// �豸�б�������
	private static BaseAdapter devZigbeeAdapter;// zigbee�豸������
	private static TextView devsDesc;// �豸��Ŀ��ʾ
	private static int devsOnCount;// �򿪵��豸��Ŀ
	private ImageView devImageView;// ��ȫ�����ر�ȫ�������˳���ť
	private ListView devListView;// �����豸�б���ʾ
	private ListView devListViewZig;
	private LinearLayout devSelected;// ѡ����豸����
	private ImageView helpImage = null;// ����ͼ��
	private ImageView addImageView;// ���ͼ��
	private ImageView moreImageView;// ���๦��ͼ��
	private ImageView refreshImageView;// ˢ��ͼ��
	private ListView devStyleListView;// �豸�����б�
	private ArrayList<HashMap<String, Object>> listItems; // �豸�����б������֡�ͼƬ��Ϣ
	private SimpleAdapter listItemAdapter; // �豸�����б�������
	private RelativeLayout relativeLayout03;// �ײ�����
	private boolean flag = false;// ���
	private LayoutParams layoutParams01;// ���๦�ܲ���
	private String jsonFromSocket = "";// ��¼��Socket��õ���json
	private static List<Map<String, String>> devList;// ��¼json����֮����豸
	private static List<Map<String, String>> devListZigbee;
	private Button listViewButton;
	private boolean first = true;
	private ImageView imageView_close;

	/**
	 * ����豸���豸�б�List<DevView>
	 * 
	 * @param paramDevView
	 *            ��ӵ��豸����
	 */
	public static void addDev(DevView paramDevView)
	{
		devs.add(paramDevView);
		devsAdapter.notifyDataSetChanged();// �����������б�
		devsDesc.setText("[" + devsOnCount + "/" + devs.size() + "]");// ������ʾ�豸��Ŀ
	}

	/**
	 * 
	 * @param mp
	 *            Ҫ��ӵ��豸 ��list�е�map ����ֱ��ɾ�� �Ա��ڸ������� ��ʾ
	 * @param id
	 *            Ҫ��ӵ��豸id ��Ҫ��Ϊ��Socketɾ���õ�
	 * @param type
	 *            Ҫ��������豸 1:��˾, 2:zigbee 3:����
	 */
	public static void addDev(Map<String, String> mp, String id, int type)
	{
		if (type == 1)
		{
			devList.add(mp);

			devsAdapter.notifyDataSetChanged();
			String jsonAdd = "";
			String id1 = mp.get("id");
			String name = mp.get("DeviceName");
			String type1 = mp.get("DeviceType");
			String brand = mp.get("BrandName");
			String jsonAdd1 = "company webinsertdevice {\"DeviceType\":\""
					+ type1
					+ "\",\"DeviceName\":\""
					+ name
					+ "\",\"ShowTitle\":true,\"ShowContent\":false,\"RegionID\":"
					+ id + ",";
			String jsonAdd2 = "\"AssetNo\":\"123456\",\"Manager\":\"����\",\"pid\":-1,\"IPAddress\":\"127.0.0.1\",\"BrandName\":\""
					+ brand + "\",";
			String jsonAdd3 = "\"Interval\":30000,\"MinInterval\":2000,\"ShowType\":0,\"Warranty\":\"2014-01-09\",\"Validity\":\"2014-01-10\",";
			String jsonAdd4 = "\"InstallDate\":\"2014-01-01\",\"SerialNo\":\"HT12345\",\"ServiceUnit\":\"QQ\",\"ServiceMan\":\"����\",";
			String jsonAdd5 = "\"ServiceDate\":\"2014-01-07\",\"BaudRate\":9600,\"ModelName\":\"802P\",\"ProductID\":\"SAA6EA82C68974073887CD0CE2EDD0615\",";
			String jsonAdd6 = "\"COMPort\":\"COM1\",\"Param\":{\"Address\":1,\"Channel\":0,\"HAddress\":0,\"HChannel\":0,\"TBase\":0,\"HBase\":0,";
			String jsonAdd7 = "\"TOffset\":1,\"HOffset\":5},\"Define\":{\"THigh\":28,\"TLow\":0,\"HHigh\":60,\"HLow\":0}}";
			jsonAdd = jsonAdd1 + jsonAdd2 + jsonAdd3 + jsonAdd4 + jsonAdd5
					+ jsonAdd6 + jsonAdd7;
			System.out.println(jsonAdd);
			addDevThread(id, jsonAdd);
			showToastStatic("����豸�ɹ�");
		}
	}

	public static void addDevThread(final String ID, final String json)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				try
				{
					// SocketControl.getSocketConnection("");
					SmartHomebaseApp app = (SmartHomebaseApp) context
							.getApplicationContext();

					if (app.socket != null)// �ǿ�, �õ���Socket
					{
						app.printWriter.println(json);
						app.printWriter.flush();
						// jsonFromSocket =
						// NetworkUtil.dataInputStream.readLine();
					}
					else
					{
						ToastWindow toastWindow = new ToastWindow(context);
						toastWindow.showToast("����ʧ��");
					}
					// SocketControl.closeSocketConnection("");
					showToastStatic("����豸�ɹ�");
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * ɾ���豸���豸�б�List<DevView>
	 * 
	 * @param paramDevView
	 *            ɾ�����豸����
	 */
	public static void deleteDev(DevView paramDevView)
	{
		paramDevView.deleteDev();// ɾ�����ݿ�������豸����
		devs.remove(paramDevView);// ɾ����ǰlist������豸
		devsAdapter.notifyDataSetChanged();

		if (paramDevView.getDevState())// �ж��豸��״̬
			devsOnCount -= 1;
		devsDesc.setText("[" + devsOnCount + "/" + devs.size() + "]");
	}

	/**
	 * 
	 * @param mp
	 *            Ҫɾ�����豸 ��list�е�map ����ֱ��ɾ�� �Ա��ڸ������� ��ʾ
	 * @param id
	 *            Ҫɾ�����豸id ��Ҫ��Ϊ��Socketɾ���õ�
	 * @param type
	 *            Ҫɾ�������豸 1:��˾, 2:zigbee 3:����
	 */
	public static void deleteDev(Map<String, String> mp, String id, int type)
	{
		if (type == 1)// ��˾�豸
		{
			devList.remove(mp);
			// �����￪һ���߳� Ȼ��õ�list ��newһ��handler ִ��notify��� �������Ը�
			deleteDeviceThread(id);
			devsAdapter.notifyDataSetChanged();
			showToastStatic("ɾ���豸�ɹ�");
		}
		else if (type == 2)// zigbee�豸
		{
			devListZigbee.remove(mp);
			devZigbeeAdapter.notifyDataSetChanged();
		}
		else
		// �����豸
		{

		}

	}

	private static void deleteDeviceThread(final String ID)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				try
				{
					// SocketControl.getSocketConnection("");
					SmartHomebaseApp app = (SmartHomebaseApp) context
							.getApplicationContext();

					if (app.socket != null)// �ǿ�, �õ���Socket
					{
						app.printWriter
								.println("company webdeletedevice " + ID);
						app.printWriter.flush();
						// jsonFromSocket =
						// NetworkUtil.dataInputStream.readLine();
					}
					else
					{
						ToastWindow toastWindow = new ToastWindow(context);
						toastWindow.showToast("����ʧ��");
					}
					// SocketControl.closeSocketConnection("");
					showToastStatic("ɾ���豸�ɹ�");
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * �����豸��id�� �õ���Ӧ���豸����
	 * 
	 * @param deviceid
	 *            �豸��id��
	 * @return ��Ӧ���豸
	 */
	public static DevView getDevView(String deviceid)// ID��Ӧ���豸��
	{
		DevView localDevView = null;
		for (int i = 0; i < devs.size(); i++)
		{
			localDevView = (DevView) devs.get(i);
			if (localDevView.getDevId().equals(deviceid))
				break;
		}
		return localDevView;
	}

	/**
	 * ���ظ����豸������
	 * 
	 * @param paramDevView
	 *            �豸����
	 * @param devicename
	 *            Ҫ�޸ĵ�����
	 */
	public static void updateDevName(DevView paramDevView, String devicename)
	{
		paramDevView.setDevName(devicename);
	}

	/**
	 * ���ظ����豸��ID��
	 * 
	 * @param paramDevView
	 *            ������
	 * @param deviceid
	 *            Ҫ�޸ĵ�ID
	 */
	public static void updateDevPort(DevView paramDevView, String deviceid)
	{
		paramDevView.setDevID(deviceid);
	}

	/**
	 * Զ�����Ӹ���Զ���豸��״̬
	 * 
	 * @param paramDevView
	 *            Ҫ�����豸
	 * @param paramBoolean
	 *            Ҫ���豸���ǹر��豸��״̬
	 * @param paramWindow
	 *            ��ǰ����
	 * @param paramUICallback
	 *            Զ�����ӷ��������߳��߳�
	 */
	public static void updateDevState(final DevView paramDevView,
			final boolean paramBoolean, final Window paramWindow,
			final UIThread.UICallback paramUICallback)
	{
		new UIThread(context, new UIThread.UICallback()// ����һ���߳�
				{
					private ToastWindow toast = null;// ��ʾ�Ի���

					public void onBegin(Context paramAnonymousContext)// ��ʼִ�и��²���
					{
						if (paramUICallback != null)
						{
							paramUICallback.onBegin(paramAnonymousContext);
						}
						String str;// ���µ�״̬��ʾ���ǹرջ��Ǵ�
						if (paramBoolean)
						{
							str = paramAnonymousContext
									.getString(R.string.dev_state_open);// ��Զ���豸״̬��ʾ��Ϣ
						}
						else
						{
							str = paramAnonymousContext
									.getString(R.string.dev_state_close);// �ر�Զ���豸״̬��ʾ��Ϣ
						}
						this.toast = new ToastWindow(paramAnonymousContext, str);// ����һ����ʾ��Ϣ
						this.toast.show(paramWindow, 60);// �ڵ�ǰ������ʾ��ʾ��Ϣ
					}

					public void onException(Context paramAnonymousContext,
							Exception paramAnonymousException)// ���³��ֵĴ�����
					{
						if (paramUICallback != null)
							paramUICallback.onException(paramAnonymousContext,
									paramAnonymousException);
						Log.e("MainActivity", paramAnonymousContext
								.getString(R.string.dev_state_failure),
								paramAnonymousException);
						this.toast.setText(R.string.dev_state_failure);// ��ʾ����ʧ��
						this.toast.dismissDelayed();// �رո���ʧ��

					}

					public void onFinally(Context paramAnonymousContext)// �������µĴ���
					{
						if (paramUICallback != null)
							paramUICallback.onFinally(paramAnonymousContext);
					}

					public void onSuccess(Context paramAnonymousContext,
							Object paramAnonymousObject)// ���³ɹ�����
					{
						Log.i("1", "onSuccess���豸"); // INFO
						this.toast.dismiss();// �ر���ʾ
						if (paramUICallback != null)
							paramUICallback.onSuccess(paramAnonymousContext,
									paramAnonymousObject);
						if (paramBoolean)// ���ش��豸
						{
							if (!paramDevView.getDevState())// ��ǰ�豸֮ǰ�ǹرյģ����豸��Ŀ��1
								HomeControlActivity.devsOnCount = 1 + HomeControlActivity.devsOnCount;
						}
						else
						// ���عر��豸
						{
							if (paramDevView.getDevState())// ��ǰ�豸֮ǰ�Ǵ򿪵ģ����豸��Ŀ��1
								HomeControlActivity.devsOnCount -= 1;
						}
						paramDevView.setDevState(paramBoolean);// ���±����豸�б��״̬
						HomeControlActivity.devsDesc.setText("["
								+ HomeControlActivity.devsOnCount + "/"
								+ HomeControlActivity.devs.size() + "]");// ���´��豸
					}
				})
		{
			public Object onRun() throws Exception// Ҫִ�е��̶߳�Ӧ��Ŀ��
			{
				if (paramBoolean)
					SocketControl.openDevice(
							Integer.valueOf(paramDevView.getDevId()),
							paramDevView.getDevType());// ��Զ���豸��״̬
				else
				{
					SocketControl.closeDevice(
							Integer.valueOf(paramDevView.getDevId()),
							paramDevView.getDevType());// �ر�Զ���豸��״̬
				}
				return null;
			}
		}.start();
	}

	/**
	 * Զ�̹ر������豸
	 */
	public void allClose()
	{
		new UIThread(context, new UIThread.UICallback()
		{
			private ToastWindow toast = null;// ��ʾ��ʾ��

			public void onBegin(Context paramAnonymousContext)// ��ʼִ�йرհ�ť��ִ�еĺ���
			{
				this.toast = new ToastWindow(paramAnonymousContext,
						R.string.dev_state_allclose);// ��ʾ��ʾ��ʾ��
				this.toast.show(HomeControlActivity.this.getWindow(), 60);
			}

			// ������
			public void onException(Context paramAnonymousContext,
					Exception paramAnonymousException)
			{
				Log.e("MainActivity", paramAnonymousContext
						.getString(R.string.dev_state_failure),
						paramAnonymousException);
				this.toast.setText(R.string.dev_state_failure);
				this.toast.dismissDelayed();
			}

			// Զ�̹رճɹ�
			public void onSuccess(Context paramAnonymousContext,
					Object paramAnonymousObject)
			{
				this.toast.dismiss();// �ر���ʾ�Ի���
				for (int i = 0; i < HomeControlActivity.devs.size(); i++)
				{
					((DevView) HomeControlActivity.devs.get(i))
							.setDevState(false);
				}
				HomeControlActivity.devsOnCount = 0;
				HomeControlActivity.devsDesc.setText("["
						+ HomeControlActivity.devsOnCount + "/"
						+ HomeControlActivity.devs.size() + "]");
			}
		})
		{
			public Object onRun() throws Exception
			{
				String[] arrayOfString = new String[HomeControlActivity.devs
						.size()];
				for (int i = 0; i < HomeControlActivity.devs.size(); i++)
				{
					arrayOfString[i] = ((DevView) HomeControlActivity.devs
							.get(i)).getDevId();
				}
				SocketControl.closeAllDevice(arrayOfString);
				return null;
			}
		}.start();// ִ���߳�
	}

	/**
	 * Զ�̴������豸
	 */
	public void allOpen()
	{
		new UIThread(context, new UIThread.UICallback()
		{
			private ToastWindow toast = null;// ��ʾ�Ի���

			public void onBegin(Context paramAnonymousContext)
			{
				this.toast = new ToastWindow(paramAnonymousContext,
						R.string.dev_state_allopen);// ȫ���豸��
				this.toast.show(HomeControlActivity.this.getWindow(), 60);
			}

			// ������
			public void onException(Context paramAnonymousContext,
					Exception paramAnonymousException)
			{
				Log.e("MainActivity", paramAnonymousContext
						.getString(R.string.dev_state_failure),
						paramAnonymousException);
				this.toast.setText(R.string.dev_state_failure);
				this.toast.dismissDelayed();
			}

			public void onSuccess(Context paramAnonymousContext,
					Object paramAnonymousObject)
			{
				this.toast.dismiss();// �ر���ʾ�Ի���
				for (int i = 0; i < HomeControlActivity.devs.size(); i++)
				{
					((DevView) HomeControlActivity.devs.get(i))
							.setDevState(true);
				}
				HomeControlActivity.devsOnCount = 0;
				HomeControlActivity.devsDesc.setText("["
						+ HomeControlActivity.devsOnCount + "/"
						+ HomeControlActivity.devs.size() + "]");
			}
		})
		{
			public Object onRun() throws Exception
			{
				String[] arrayOfString = new String[HomeControlActivity.devs
						.size()];
				for (int i = 0; i < HomeControlActivity.devs.size(); i++)
				{
					arrayOfString[i] = ((DevView) HomeControlActivity.devs
							.get(i)).getDevId();
				}
				SocketControl.openAllDevice(arrayOfString);
				return null;
			}
		}.start();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i("1", "onCreate���"); // INFO
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_control);
		context = this;// ��ǰ������
		/*** Ϊ���ǵ�Activity�ϵ���Щ��С������ɳ�ʼ����ͬʱ��Ӽ������� ***/

		// ����رհ�ť
		imageView_close = (ImageView) findViewById(R.id.imageView01_home);
		imageView_close.setOnClickListener(this);
		// ���Ͻǵİ�����ť
		helpImage = (ImageView) findViewById(R.id.ImageView003);
		helpImage.setOnClickListener(this);
		// ���Ͻǵĸ��°�ť
		refreshImageView = (ImageView) findViewById(R.id.ImageView02);
		refreshImageView.setOnClickListener(this);
		// �Ҳ಼��
		relativeLayout03 = (RelativeLayout) findViewById(R.id.RelativeLayout03);
		// �Ҳ��±߰�ť
		addImageView = (ImageView) findViewById(R.id.ImageView03);
		addImageView.setOnClickListener(this);
		// �Ҳ��м䰴ť
		devImageView = (ImageView) findViewById(R.id.ImageView04);
		devImageView.setOnClickListener(this);
		// �Ҳ��ϱ���չ��ť
		moreImageView = (ImageView) findViewById(R.id.ImageView05);
		moreImageView.setBackgroundResource(R.drawable.main_more_bottombar);
		moreImageView.setOnClickListener(this);
		// �м��б��
		devListView = (ListView) findViewById(R.id.ListView01);
		devListViewZig = (ListView) findViewById(R.id.ListView002);
		// ����豸�����б�
		devStyleListView = (ListView) findViewById(R.id.Listview02);
		initdevStyleListView();// ����б��ʼ��
		/*** ***/
		// �б��豸��Ŀ
		devsOnCount = 0;
		devs = DevView.loadFromDB(context);// �����ݿ����豸�б������
		Log.i("1", "devs" + devs.size() + ""); // INFO
		devsDesc = (TextView) findViewById(R.id.TextView03);// �豸�б�
		devsDesc.setText("[" + devsOnCount + "/" + devs.size() + "]");
		// �����߳� ��˾�б�
		new Thread(getSocketRunnableCompany).start();

		// zigbee�б�
		// new Thread(getSocketRunnableZigbee).start();

		// �ӳ�ˢ��
		new Handler().postDelayed(new Runnable()
		{
			public void run()
			{
				// HomeControlActivity.this.refresh();
			}
		}, 500L);
		Log.i("1", "onCreate����1"); // INFO
	}

	// �˵���������
	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		String str1 = getString(R.string.menu_allon);
		paramMenu.addSubMenu(0, 0, 0, str1).setIcon(R.drawable.menu_allon);
		String str2 = getString(R.string.menu_alloff);
		paramMenu.addSubMenu(0, 1, 1, str2).setIcon(R.drawable.menu_alloff);
		String str3 = getString(R.string.menu_exit);
		paramMenu.addSubMenu(0, 2, 2, str3).setIcon(R.drawable.menu_exit);
		return super.onCreateOptionsMenu(paramMenu);
	}

	// �����˵�����Ӧ����
	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{
		super.onOptionsItemSelected(paramMenuItem);
		switch (paramMenuItem.getItemId())
		{

		case 0:
			allOpen();
			Toast.makeText(this, "allon", Toast.LENGTH_LONG).show();
			break;
		case 1:
			allClose();
			Toast.makeText(this, "alloff", Toast.LENGTH_LONG).show();
			break;
		case 2:
			Toast.makeText(this, "exit", Toast.LENGTH_LONG).show();
			finish();
		default:
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View paramView)
	{
		switch (paramView.getId())
		{
		case R.id.ImageView003:// ����˵��
			{
				Toast.makeText(this, "����˵��", Toast.LENGTH_LONG).show();
				Intent localIntent = new Intent(this, HelpActivity.class);
				this.startActivity(localIntent);
				break;
			}
		case R.id.ImageView02:// Զ��ˢ���豸�б�
			{
				refresh();
				break;
			}
		case R.id.ImageView03:// ����豸
			{
				Log.i("1", "����豸���"); // INFO
				Intent localIntent = new Intent(this, AddDeviceActivity.class);
				this.startActivity(localIntent);
				break;
			}
		case R.id.ImageView04:// �м�˵�
			{
				openOptionsMenu();
				break;
			}
		case R.id.ImageView05:// �ұ��ϱ߸��ఴť
			{
				Log.i("1", "�������"); // INFO
				if (flag == false)
				{
					flag = true;
					layoutParams01 = moreImageView.getLayoutParams();
				}
				else
					flag = false;
				if (flag == true)
				{
					relativeLayout03.setVisibility(View.VISIBLE);
					moreImageView.setLayoutParams(refreshImageView
							.getLayoutParams());
					moreImageView
							.setBackgroundResource(R.drawable.main_more_bottombar01);
					refreshImageView.setVisibility(View.INVISIBLE);
				}
				else
				{
					relativeLayout03.setVisibility(View.GONE);
					refreshImageView.setVisibility(View.VISIBLE);
					moreImageView.setLayoutParams(layoutParams01);
					moreImageView
							.setBackgroundResource(R.drawable.main_more_bottombar);
				}
				break;
			}
		case R.id.imageView01_home:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * �õ�Socket
	 */
	Runnable getSocketRunnableCompany = new Runnable()
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub

			try
			{
				SmartHomebaseApp app = (SmartHomebaseApp) getApplication();
				if (app.socket != null)
				{
					// ��������
					app.printWriter.println("company webgetalldevice");
					app.printWriter.flush();
					if (app.dataInputStream == null)
					{
						Log.i("homecontrolactivity", "dataInputStream == null");
					}
					else
					{
						app.dataInputStream = new BufferedReader(
								new InputStreamReader(
										app.socket.getInputStream()));
						jsonFromSocket = app.dataInputStream.readLine();
						DecodeJson decodeJson = new DecodeJson(jsonFromSocket);
						devList = decodeJson.decodejson();
						Message msg = new Message();
						msg.what = 1;
						updateUIAfterSocket.sendMessage(msg);
					}

				}
				else
				{
					ToastWindow toastWindow = new ToastWindow(context);
					toastWindow.showToast("����ʧ��");
				}
				// closeSoket������
				// SocketControl.closeSocketConnection("");

			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				Log.i("yichang", e.toString());
				e.printStackTrace();
			}
		}
	};

	Runnable getSocketRunnableZigbee = new Runnable()
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			// ģ���Socket�õ���json��
			String jsonString = "{\"devices\":[{\"nwk\":\"ffff\",\"type\":\"type02\"}]}";

			try
			{
				// �õ�socket
				// SocketControl.getSocketConnection("");
				SmartHomebaseApp app = (SmartHomebaseApp) getApplication();
				String subString = "";
				if (app.socket != null)
				{
					app.printWriter.println("zigbee getall");
					app.printWriter.flush();
					// ����õ��������ٲ���json�� ����һ��String

					String jsonZigbee = app.dataInputStream.readLine();// �õ����ص�����
					// 3333 ff00 0011 00 b19f 0000000000000000ff1111
					Log.i("homecontrol ", jsonZigbee);
					if (jsonZigbee == null)
					{
						Log.i("homecontrol", "jsonZigbee == null");
					}
					else
					{

						subString = jsonZigbee.substring(14, 18);
						Log.i("homecontrol", "substring = " + subString);

					}
					jsonString = "{\"devices\":[{\"nwk\":\"" + subString
							+ "\",\"type\":\"type02\"}]}";
					DecodeJson decodeJson = new DecodeJson();
					devListZigbee = decodeJson.decodeJsonZigbee(jsonString);
					Message msg = new Message();
					msg.what = 1;
					zigbeeUIhandHandler.sendMessage(msg);
				}
				else
				{
					showToast("�������");
				}

			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				showToast("�������");
				e.printStackTrace();
			}

			// ����json��
			/*
			 * DecodeJson decodeJson = new DecodeJson(); devListZigbee =
			 * decodeJson.decodeJsonZigbee(jsonString); Message msg = new
			 * Message(); msg.what = 1; zigbeeUIhandHandler.sendMessage(msg);
			 */

		}
	};

	/**
     * 
     */

	private Handler updateUIAfterSocket = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				initMiddleListView();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * zigbee ��handler
	 */
	private Handler zigbeeUIhandHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				initMiddleListViewZigbee();
				break;

			default:
				break;
			}
		}
	};

	// ˢ���豸��״̬
	public void refresh()
	{
		Log.i("1", "refresh�������"); // INFO
		new UIThread(context, new UIThread.UICallback()// ����һ�����µ��߳�
				{
					private ToastWindow toast = null;// �����Ի���

					public void onBegin(Context paramAnonymousContext)// ��ʼִ���̵߳ĺ���
					{
						this.toast = new ToastWindow(paramAnonymousContext,
								R.string.dev_state_refresh);
						this.toast.show(HomeControlActivity.this.getWindow(),
								100);
					}

					// ����ʧ��
					public void onException(Context paramAnonymousContext,
							Exception paramAnonymousException)// ʧ����Ϣ
					{
						Log.e("MainActivity", paramAnonymousContext
								.getString(R.string.dev_state_failure),
								paramAnonymousException);
						this.toast.setText(R.string.dev_state_failure);
						this.toast.dismissDelayed();
					}

					public void onSuccess(Context paramAnonymousContext,
							Object paramAnonymousObject)
					{
						this.toast.dismiss();// �رո����豸�Ի���
						Map<String, String> str1 = (Map<String, String>) paramAnonymousObject;// ���е��豸��״̬
						for (int i = 0; i < HomeControlActivity.devs.size(); i++)
						{

							DevView localDevView = (DevView) HomeControlActivity.devs
									.get(i);
							String str2 = str1.get(localDevView.getDevId());
							if (str2.equals("0"))
							{
								if (localDevView.getDevState())
									HomeControlActivity.devsOnCount -= 1;
								localDevView.setDevState(false);
							}
							if (str2.equals("1"))
							{
								if (!localDevView.getDevState())
									HomeControlActivity.devsOnCount = 1 + HomeControlActivity.devsOnCount;
								localDevView.setDevState(true);
							}
						}
						HomeControlActivity.devsDesc.setText("["
								+ HomeControlActivity.devsOnCount + "/"
								+ HomeControlActivity.devs.size() + "]");
						return;
					}
				})
		{
			public Object onRun() throws Exception
			{
				Log.i("1", "onRun"); // INFO
				return SocketControl
						.getAllDeviceState(HomeControlActivity.this.context);// ��������豸��״̬
			}
		}.start();
		Log.i("1", "refresh��������"); // INFO
	}

	// ��ʼ���豸�����б�
	private void initdevStyleListView()
	{
		// TODO Auto-generated method stub
		this.listItems = new ArrayList<HashMap<String, Object>>();
		final String[] arrayOfString = new String[8];// �豸��������
		arrayOfString[0] = context.getString(R.string.dev_name_company);
		arrayOfString[1] = context.getString(R.string.dev_name_zigbee);
		// arrayOfString[2] = context.getString(R.string.dev_name_gated);
		// arrayOfString[3] = context.getString(R.string.dev_name_fan);
		// arrayOfString[4] = context.getString(R.string.dev_name_thermostat);
		// arrayOfString[5] = context.getString(R.string.dev_name_light);
		// arrayOfString[6] = context.getString(R.string.dev_name_socket);
		// arrayOfString[7] = context.getString(R.string.dev_name_other);
		for (int i = 0; i < 2; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", arrayOfString[i]); // ����
			listItems.add(map);
		}
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		listItemAdapter = new SimpleAdapter(this, listItems,// ����Դ
				R.layout.list_item,// ListItem��XML����ʵ��
				// ��̬������ImageItem��Ӧ������
				new String[]
				{ "ItemTitle" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[]
				{ R.id.ItemTitle });
		this.devStyleListView.setAdapter(listItemAdapter);
		setListViewOnSelectedListener();
	}

	// �����豸���͵ļ�����
	private void setListViewOnSelectedListener()
	{
		// TODO Auto-generated method stub
		this.devStyleListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong)
					{
						Log.i("listView", "click");

						if (paramAnonymousInt == 0)
						{
							// ��˾
							ListView listView1 = (ListView) findViewById(R.id.ListView002);
							listView1.setVisibility(View.GONE);
							ListView listView2 = (ListView) findViewById(R.id.ListView01);
							listView2.setVisibility(View.VISIBLE);
							// devsAdapter.notifyDataSetChanged();
						}
						else
						// ��ʾZigbee
						{
							// zigbee
							ListView listView1 = (ListView) findViewById(R.id.ListView01);
							listView1.setVisibility(View.GONE);
							ListView listView2 = (ListView) findViewById(R.id.ListView002);
							listView2.setVisibility(View.VISIBLE);
							// �������Ա�������֪ͨ����socket
							new Thread(getSocketRunnableZigbee).start();
						}
					}
				});

	}

	// ���ÿһ���ѡ��
	protected void onItemClickDevs(View paramAnonymousView)
	{
		// TODO Auto-generated method stub
		// switch(paramAnonymousView.get)
		// {
		// case R.string.dev_name_all:
		// devs.add(paramDevView);
		// devsAdapter.notifyDataSetChanged();//�����������б�
		// devsDesc.setText("[" + devsOnCount + "/" + devs.size() +
		// "]");//������ʾ�豸��Ŀ
		// }
	}

	// �м� �б�
	private void initMiddleListView()
	{
		final List<Map<String, String>> _devList = devList;
		devsAdapter = new BaseAdapter()
		{
			public int getCount()
			{
				return _devList.size();
			}

			public Object getItem(int paramAnonymousInt)
			{
				return Integer.valueOf(paramAnonymousInt);
			}

			public long getItemId(int paramAnonymousInt)
			{
				return paramAnonymousInt;
			}

			public View getView(int position, View paramAnonymousView,
					ViewGroup paramAnonymousViewGroup)
			{
				View view = View.inflate(HomeControlActivity.this,
						R.layout.child_list_item1, null);
				TextView devName = (TextView) view.findViewById(R.id.devName);
				TextView info = (TextView) view.findViewById(R.id.info);
				TextView devType = (TextView) view.findViewById(R.id.devType);
				TextView devBrand = (TextView) view.findViewById(R.id.devBrand);
				listViewButton = (Button) view.findViewById(R.id.contextbtn);
				String tag = devList.get(position).get("ID");
				listViewButton.setTag(devList.get(position));
				// ��ʾ
				devName.setText(devList.get(position).get("DeviceName"));
				devType.setText(devList.get(position).get("DeviceType"));
				info.setText("����");
				devBrand.setText(devList.get(position).get("BrandName"));
				listViewButton.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						@SuppressWarnings("unchecked")
						// �õ�tag ֻ����tag���ܵõ����ŷŽ���������
						Map<String, String> mapDev = (Map<String, String>) v
								.getTag();
						// ������ ����ʵ��Serializable ���ܷŵ�bundle�� ��activity֮�䷽�㴫��
						UtilDataForDeviceDetail myData = new UtilDataForDeviceDetail();
						myData.setMap(mapDev);// ���map
						myData.setBrandName(mapDev.get("BrandName"));
						myData.setDeviceName(mapDev.get("DeviceName"));
						myData.setDeviceType(mapDev.get("DeviceType"));
						myData.setID(mapDev.get("id"));
						myData.setModelName(mapDev.get("ModelName"));
						myData.setX(mapDev.get("X"));
						myData.setY(mapDev.get("Y"));
						Bundle bundle = new Bundle();
						bundle.putSerializable("MY_DATA", myData);
						Intent deviceDetail = new Intent();
						deviceDetail.setClass(HomeControlActivity.this,
								DeviceDetailActivity.class);
						deviceDetail.putExtras(bundle);
						startActivity(deviceDetail);
					}
				});
				return view;// �����ͼ��Ӧ���豸
			}
		};
		this.devListView.setAdapter(devsAdapter);
		// �豸�б����
		this.devListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()// �豸�б������
				{
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong)
					{
						Log.i("1", "devListView.setOnItemClickListener"); // INFO
						boolean bool;
						if (HomeControlActivity.this.devSelected == paramAnonymousView)// ѡ����豸
						{
							Log.i("1", "devSelected"); // INFO
							DevView localDevView = (DevView) paramAnonymousView;// ѡ����豸
							if (localDevView.getDevState())
							{
								bool = false;// �ر��豸
								Log.i("1", "�ر��豸"); // INFO
								HomeControlActivity.updateDevState(
										localDevView, bool,
										HomeControlActivity.this.getWindow(),
										null);
							}
							else
							{
								bool = true;
								Log.i("1", "���豸"); // INFO
								HomeControlActivity.updateDevState(
										localDevView, bool,
										HomeControlActivity.this.getWindow(),
										null);
							}
						}
						else
						{
							if (HomeControlActivity.this.devSelected != null)
							{
								HomeControlActivity.this.devSelected
										.setBackgroundColor(Color.TRANSPARENT);// ����͸��
								HomeControlActivity.this.devSelected
										.findViewById(R.id.contextbtn)
										.setVisibility(View.GONE);// ���ò�����ʾ
							}
							paramAnonymousView
									.setBackgroundResource(R.drawable.device_list_bg);
							paramAnonymousView.findViewById(R.id.contextbtn)
									.setVisibility(View.VISIBLE);
							HomeControlActivity.this.devSelected = ((LinearLayout) paramAnonymousView);
						}
					}
				});

	}

	private void initMiddleListViewZigbee()
	{
		final List<Map<String, String>> _devlistZig = devListZigbee;
		devZigbeeAdapter = new BaseAdapter()
		{

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				// TODO Auto-generated method stub
				View view = View.inflate(HomeControlActivity.this,
						R.layout.child_list_item1, null);
				TextView devName = (TextView) view.findViewById(R.id.devName);
				TextView info = (TextView) view.findViewById(R.id.info);
				TextView devType = (TextView) view.findViewById(R.id.devType);
				TextView devBrand = (TextView) view.findViewById(R.id.devBrand);
				listViewButton = (Button) view.findViewById(R.id.contextbtn);
				String tag = _devlistZig.get(position).get("type");
				listViewButton.setTag(tag + "");
				String type1 = _devlistZig.get(position).get("type");
				if (type1.equals("type01"))
				{
					devName.setText("�緹���豸");
				}
				else if (type1.equals("type02"))
				{
					devName.setText("���");
				}
				info.setText("����");
				devType.setText(_devlistZig.get(position).get("nwk"));
				devBrand.setText("����");
				listViewButton.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						showToast((v.getTag().toString()) + "");
						String devType = (String) v.getTag();
						// ��ת��zigbee�豸�Ľ���
						if (devType.equals("type02"))
						{// ����豸
							Intent lightIntent = new Intent();
							lightIntent.setClass(HomeControlActivity.this,
									lighton.class);
							// lightIntent.setClass(HomeControlActivity.this,
							// lighton.class);//��socket��
							startActivity(lightIntent);
						}
						else if (devType.equals("type01"))
						{// �緹���豸
							Intent dianFanBaoIntent = new Intent();
							dianFanBaoIntent.setClass(HomeControlActivity.this,
									cookerControlActivity.class);
							startActivity(dianFanBaoIntent);
						}
						else
						{

						}

					}
				});
				return view;
			}

			@Override
			public long getItemId(int position)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount()
			{
				// TODO Auto-generated method stub
				return _devlistZig.size();
			}
		};
		this.devListViewZig.setAdapter(devZigbeeAdapter);
		this.devListViewZig
				.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{

					@Override
					public void onItemClick(
							android.widget.AdapterView<?> parent, View view,
							int position, long id)
					{
						// TODO Auto-generated method stub

					}
				});
	}

	private void showToast(String msg)
	{
		Toast.makeText(context, msg, 0).show();
	}

	private static void showToastStatic(String title)
	{
		Toast.makeText(context, title, 0).show();
	}
}
