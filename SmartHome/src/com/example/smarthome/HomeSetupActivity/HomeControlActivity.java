//这个文件是按键后跳入的Activity
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

	private static Context context;// 上下文
	private static List<DevView> devs;// 所有设备信息
	private static BaseAdapter devsAdapter;// 设备列表适配器
	private static BaseAdapter devZigbeeAdapter;// zigbee设备适配器
	private static TextView devsDesc;// 设备数目显示
	private static int devsOnCount;// 打开的设备数目
	private ImageView devImageView;// 打开全部，关闭全部或者退出按钮
	private ListView devListView;// 所有设备列表显示
	private ListView devListViewZig;
	private LinearLayout devSelected;// 选择的设备布局
	private ImageView helpImage = null;// 帮助图标
	private ImageView addImageView;// 添加图标
	private ImageView moreImageView;// 更多功能图标
	private ImageView refreshImageView;// 刷新图标
	private ListView devStyleListView;// 设备类型列表
	private ArrayList<HashMap<String, Object>> listItems; // 设备类型列表存放文字、图片信息
	private SimpleAdapter listItemAdapter; // 设备类型列表适配器
	private RelativeLayout relativeLayout03;// 底部布局
	private boolean flag = false;// 标记
	private LayoutParams layoutParams01;// 更多功能布局
	private String jsonFromSocket = "";// 记录从Socket里得到的json
	private static List<Map<String, String>> devList;// 记录json解析之后的设备
	private static List<Map<String, String>> devListZigbee;
	private Button listViewButton;
	private boolean first = true;
	private ImageView imageView_close;

	/**
	 * 添加设备到设备列表List<DevView>
	 * 
	 * @param paramDevView
	 *            添加的设备对象
	 */
	public static void addDev(DevView paramDevView)
	{
		devs.add(paramDevView);
		devsAdapter.notifyDataSetChanged();// 更新适配器列表
		devsDesc.setText("[" + devsOnCount + "/" + devs.size() + "]");// 更新显示设备数目
	}

	/**
	 * 
	 * @param mp
	 *            要添加的设备 在list中的map 可以直接删除 以便于更新数据 显示
	 * @param id
	 *            要添加的设备id 主要是为了Socket删除用的
	 * @param type
	 *            要添加哪种设备 1:公司, 2:zigbee 3:其他
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
			String jsonAdd2 = "\"AssetNo\":\"123456\",\"Manager\":\"李四\",\"pid\":-1,\"IPAddress\":\"127.0.0.1\",\"BrandName\":\""
					+ brand + "\",";
			String jsonAdd3 = "\"Interval\":30000,\"MinInterval\":2000,\"ShowType\":0,\"Warranty\":\"2014-01-09\",\"Validity\":\"2014-01-10\",";
			String jsonAdd4 = "\"InstallDate\":\"2014-01-01\",\"SerialNo\":\"HT12345\",\"ServiceUnit\":\"QQ\",\"ServiceMan\":\"张三\",";
			String jsonAdd5 = "\"ServiceDate\":\"2014-01-07\",\"BaudRate\":9600,\"ModelName\":\"802P\",\"ProductID\":\"SAA6EA82C68974073887CD0CE2EDD0615\",";
			String jsonAdd6 = "\"COMPort\":\"COM1\",\"Param\":{\"Address\":1,\"Channel\":0,\"HAddress\":0,\"HChannel\":0,\"TBase\":0,\"HBase\":0,";
			String jsonAdd7 = "\"TOffset\":1,\"HOffset\":5},\"Define\":{\"THigh\":28,\"TLow\":0,\"HHigh\":60,\"HLow\":0}}";
			jsonAdd = jsonAdd1 + jsonAdd2 + jsonAdd3 + jsonAdd4 + jsonAdd5
					+ jsonAdd6 + jsonAdd7;
			System.out.println(jsonAdd);
			addDevThread(id, jsonAdd);
			showToastStatic("添加设备成功");
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

					if (app.socket != null)// 非空, 得到了Socket
					{
						app.printWriter.println(json);
						app.printWriter.flush();
						// jsonFromSocket =
						// NetworkUtil.dataInputStream.readLine();
					}
					else
					{
						ToastWindow toastWindow = new ToastWindow(context);
						toastWindow.showToast("联网失败");
					}
					// SocketControl.closeSocketConnection("");
					showToastStatic("添加设备成功");
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
	 * 删除设备到设备列表List<DevView>
	 * 
	 * @param paramDevView
	 *            删除的设备对象
	 */
	public static void deleteDev(DevView paramDevView)
	{
		paramDevView.deleteDev();// 删除数据库里面的设备数据
		devs.remove(paramDevView);// 删除当前list里面的设备
		devsAdapter.notifyDataSetChanged();

		if (paramDevView.getDevState())// 判断设备的状态
			devsOnCount -= 1;
		devsDesc.setText("[" + devsOnCount + "/" + devs.size() + "]");
	}

	/**
	 * 
	 * @param mp
	 *            要删除的设备 在list中的map 可以直接删除 以便于更新数据 显示
	 * @param id
	 *            要删除的设备id 主要是为了Socket删除用的
	 * @param type
	 *            要删除哪种设备 1:公司, 2:zigbee 3:其他
	 */
	public static void deleteDev(Map<String, String> mp, String id, int type)
	{
		if (type == 1)// 公司设备
		{
			devList.remove(mp);
			// 在这里开一个线程 然后得到list 再new一个handler 执行notify语句 这样可以搞
			deleteDeviceThread(id);
			devsAdapter.notifyDataSetChanged();
			showToastStatic("删除设备成功");
		}
		else if (type == 2)// zigbee设备
		{
			devListZigbee.remove(mp);
			devZigbeeAdapter.notifyDataSetChanged();
		}
		else
		// 其他设备
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

					if (app.socket != null)// 非空, 得到了Socket
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
						toastWindow.showToast("联网失败");
					}
					// SocketControl.closeSocketConnection("");
					showToastStatic("删除设备成功");
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
	 * 根据设备的id号 得到对应的设备对象
	 * 
	 * @param deviceid
	 *            设备的id号
	 * @return 对应的设备
	 */
	public static DevView getDevView(String deviceid)// ID对应的设备号
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
	 * 本地更新设备的名称
	 * 
	 * @param paramDevView
	 *            设备对象
	 * @param devicename
	 *            要修改的名称
	 */
	public static void updateDevName(DevView paramDevView, String devicename)
	{
		paramDevView.setDevName(devicename);
	}

	/**
	 * 本地更新设备的ID号
	 * 
	 * @param paramDevView
	 *            备对象
	 * @param deviceid
	 *            要修改的ID
	 */
	public static void updateDevPort(DevView paramDevView, String deviceid)
	{
		paramDevView.setDevID(deviceid);
	}

	/**
	 * 远程连接更新远程设备的状态
	 * 
	 * @param paramDevView
	 *            要更新设备
	 * @param paramBoolean
	 *            要打开设备还是关闭设备的状态
	 * @param paramWindow
	 *            当前窗口
	 * @param paramUICallback
	 *            远程连接服务器的线程线程
	 */
	public static void updateDevState(final DevView paramDevView,
			final boolean paramBoolean, final Window paramWindow,
			final UIThread.UICallback paramUICallback)
	{
		new UIThread(context, new UIThread.UICallback()// 创建一个线程
				{
					private ToastWindow toast = null;// 提示对话框

					public void onBegin(Context paramAnonymousContext)// 开始执行更新操作
					{
						if (paramUICallback != null)
						{
							paramUICallback.onBegin(paramAnonymousContext);
						}
						String str;// 更新的状态显示，是关闭还是打开
						if (paramBoolean)
						{
							str = paramAnonymousContext
									.getString(R.string.dev_state_open);// 打开远程设备状态提示信息
						}
						else
						{
							str = paramAnonymousContext
									.getString(R.string.dev_state_close);// 关闭远程设备状态提示信息
						}
						this.toast = new ToastWindow(paramAnonymousContext, str);// 创建一个提示信息
						this.toast.show(paramWindow, 60);// 在当前窗口显示提示信息
					}

					public void onException(Context paramAnonymousContext,
							Exception paramAnonymousException)// 更新出现的错误处理
					{
						if (paramUICallback != null)
							paramUICallback.onException(paramAnonymousContext,
									paramAnonymousException);
						Log.e("MainActivity", paramAnonymousContext
								.getString(R.string.dev_state_failure),
								paramAnonymousException);
						this.toast.setText(R.string.dev_state_failure);// 提示更新失败
						this.toast.dismissDelayed();// 关闭更新失败

					}

					public void onFinally(Context paramAnonymousContext)// 结束更新的处理
					{
						if (paramUICallback != null)
							paramUICallback.onFinally(paramAnonymousContext);
					}

					public void onSuccess(Context paramAnonymousContext,
							Object paramAnonymousObject)// 更新成功处理
					{
						Log.i("1", "onSuccess打开设备"); // INFO
						this.toast.dismiss();// 关闭提示
						if (paramUICallback != null)
							paramUICallback.onSuccess(paramAnonymousContext,
									paramAnonymousObject);
						if (paramBoolean)// 本地打开设备
						{
							if (!paramDevView.getDevState())// 当前设备之前是关闭的，打开设备数目加1
								HomeControlActivity.devsOnCount = 1 + HomeControlActivity.devsOnCount;
						}
						else
						// 本地关闭设备
						{
							if (paramDevView.getDevState())// 当前设备之前是打开的，打开设备数目减1
								HomeControlActivity.devsOnCount -= 1;
						}
						paramDevView.setDevState(paramBoolean);// 更新本地设备列表的状态
						HomeControlActivity.devsDesc.setText("["
								+ HomeControlActivity.devsOnCount + "/"
								+ HomeControlActivity.devs.size() + "]");// 更新打开设备
					}
				})
		{
			public Object onRun() throws Exception// 要执行的线程对应的目的
			{
				if (paramBoolean)
					SocketControl.openDevice(
							Integer.valueOf(paramDevView.getDevId()),
							paramDevView.getDevType());// 打开远程设备的状态
				else
				{
					SocketControl.closeDevice(
							Integer.valueOf(paramDevView.getDevId()),
							paramDevView.getDevType());// 关闭远程设备的状态
				}
				return null;
			}
		}.start();
	}

	/**
	 * 远程关闭所有设备
	 */
	public void allClose()
	{
		new UIThread(context, new UIThread.UICallback()
		{
			private ToastWindow toast = null;// 显示提示框

			public void onBegin(Context paramAnonymousContext)// 开始执行关闭按钮的执行的函数
			{
				this.toast = new ToastWindow(paramAnonymousContext,
						R.string.dev_state_allclose);// 显示显示提示框
				this.toast.show(HomeControlActivity.this.getWindow(), 60);
			}

			// 出错处理
			public void onException(Context paramAnonymousContext,
					Exception paramAnonymousException)
			{
				Log.e("MainActivity", paramAnonymousContext
						.getString(R.string.dev_state_failure),
						paramAnonymousException);
				this.toast.setText(R.string.dev_state_failure);
				this.toast.dismissDelayed();
			}

			// 远程关闭成功
			public void onSuccess(Context paramAnonymousContext,
					Object paramAnonymousObject)
			{
				this.toast.dismiss();// 关闭提示对话框
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
		}.start();// 执行线程
	}

	/**
	 * 远程打开所有设备
	 */
	public void allOpen()
	{
		new UIThread(context, new UIThread.UICallback()
		{
			private ToastWindow toast = null;// 提示对话框

			public void onBegin(Context paramAnonymousContext)
			{
				this.toast = new ToastWindow(paramAnonymousContext,
						R.string.dev_state_allopen);// 全部设备打开
				this.toast.show(HomeControlActivity.this.getWindow(), 60);
			}

			// 出错处理
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
				this.toast.dismiss();// 关闭提示对话框
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
		Log.i("1", "onCreate入口"); // INFO
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_control);
		context = this;// 当前上下文
		/*** 为我们的Activity上的这些个小部件完成初始化，同时添加监听器吧 ***/

		// 上面关闭按钮
		imageView_close = (ImageView) findViewById(R.id.imageView01_home);
		imageView_close.setOnClickListener(this);
		// 右上角的帮助按钮
		helpImage = (ImageView) findViewById(R.id.ImageView003);
		helpImage.setOnClickListener(this);
		// 右上角的更新按钮
		refreshImageView = (ImageView) findViewById(R.id.ImageView02);
		refreshImageView.setOnClickListener(this);
		// 右侧布局
		relativeLayout03 = (RelativeLayout) findViewById(R.id.RelativeLayout03);
		// 右侧下边按钮
		addImageView = (ImageView) findViewById(R.id.ImageView03);
		addImageView.setOnClickListener(this);
		// 右侧中间按钮
		devImageView = (ImageView) findViewById(R.id.ImageView04);
		devImageView.setOnClickListener(this);
		// 右侧上边扩展按钮
		moreImageView = (ImageView) findViewById(R.id.ImageView05);
		moreImageView.setBackgroundResource(R.drawable.main_more_bottombar);
		moreImageView.setOnClickListener(this);
		// 中间列表框
		devListView = (ListView) findViewById(R.id.ListView01);
		devListViewZig = (ListView) findViewById(R.id.ListView002);
		// 左边设备类型列表
		devStyleListView = (ListView) findViewById(R.id.Listview02);
		initdevStyleListView();// 左边列表初始化
		/*** ***/
		// 列表设备数目
		devsOnCount = 0;
		devs = DevView.loadFromDB(context);// 从数据库获得设备列表的数据
		Log.i("1", "devs" + devs.size() + ""); // INFO
		devsDesc = (TextView) findViewById(R.id.TextView03);// 设备列表
		devsDesc.setText("[" + devsOnCount + "/" + devs.size() + "]");
		// 启动线程 公司列表
		new Thread(getSocketRunnableCompany).start();

		// zigbee列表
		// new Thread(getSocketRunnableZigbee).start();

		// 延迟刷新
		new Handler().postDelayed(new Runnable()
		{
			public void run()
			{
				// HomeControlActivity.this.refresh();
			}
		}, 500L);
		Log.i("1", "onCreate结束1"); // INFO
	}

	// 菜单创建函数
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

	// 上述菜单的响应处理
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
		case R.id.ImageView003:// 帮助说明
			{
				Toast.makeText(this, "帮助说明", Toast.LENGTH_LONG).show();
				Intent localIntent = new Intent(this, HelpActivity.class);
				this.startActivity(localIntent);
				break;
			}
		case R.id.ImageView02:// 远程刷新设备列表
			{
				refresh();
				break;
			}
		case R.id.ImageView03:// 添加设备
			{
				Log.i("1", "添加设备入口"); // INFO
				Intent localIntent = new Intent(this, AddDeviceActivity.class);
				this.startActivity(localIntent);
				break;
			}
		case R.id.ImageView04:// 中间菜单
			{
				openOptionsMenu();
				break;
			}
		case R.id.ImageView05:// 右边上边更多按钮
			{
				Log.i("1", "更多入口"); // INFO
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
	 * 得到Socket
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
					// 发送命令
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
					toastWindow.showToast("联网失败");
				}
				// closeSoket别忘了
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
			// 模拟从Socket得到的json串
			String jsonString = "{\"devices\":[{\"nwk\":\"ffff\",\"type\":\"type02\"}]}";

			try
			{
				// 得到socket
				// SocketControl.getSocketConnection("");
				SmartHomebaseApp app = (SmartHomebaseApp) getApplication();
				String subString = "";
				if (app.socket != null)
				{
					app.printWriter.println("zigbee getall");
					app.printWriter.flush();
					// 这里得到的数据再不是json了 而是一个String

					String jsonZigbee = app.dataInputStream.readLine();// 得到返回的数据
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
					showToast("网络错误");
				}

			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				showToast("网络错误");
				e.printStackTrace();
			}

			// 解析json串
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
	 * zigbee 的handler
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

	// 刷新设备的状态
	public void refresh()
	{
		Log.i("1", "refresh函数入口"); // INFO
		new UIThread(context, new UIThread.UICallback()// 创建一个更新的线程
				{
					private ToastWindow toast = null;// 弹出对话框

					public void onBegin(Context paramAnonymousContext)// 开始执行线程的函数
					{
						this.toast = new ToastWindow(paramAnonymousContext,
								R.string.dev_state_refresh);
						this.toast.show(HomeControlActivity.this.getWindow(),
								100);
					}

					// 更新失败
					public void onException(Context paramAnonymousContext,
							Exception paramAnonymousException)// 失败信息
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
						this.toast.dismiss();// 关闭更新设备对话框
						Map<String, String> str1 = (Map<String, String>) paramAnonymousObject;// 所有的设备的状态
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
						.getAllDeviceState(HomeControlActivity.this.context);// 获得所有设备的状态
			}
		}.start();
		Log.i("1", "refresh函数结束"); // INFO
	}

	// 初始化设备类型列表
	private void initdevStyleListView()
	{
		// TODO Auto-generated method stub
		this.listItems = new ArrayList<HashMap<String, Object>>();
		final String[] arrayOfString = new String[8];// 设备类型数组
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
			map.put("ItemTitle", arrayOfString[i]); // 文字
			listItems.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(this, listItems,// 数据源
				R.layout.list_item,// ListItem的XML布局实现
				// 动态数组与ImageItem对应的子项
				new String[]
				{ "ItemTitle" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[]
				{ R.id.ItemTitle });
		this.devStyleListView.setAdapter(listItemAdapter);
		setListViewOnSelectedListener();
	}

	// 设置设备类型的监听器
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
							// 公司
							ListView listView1 = (ListView) findViewById(R.id.ListView002);
							listView1.setVisibility(View.GONE);
							ListView listView2 = (ListView) findViewById(R.id.ListView01);
							listView2.setVisibility(View.VISIBLE);
							// devsAdapter.notifyDataSetChanged();
						}
						else
						// 显示Zigbee
						{
							// zigbee
							ListView listView1 = (ListView) findViewById(R.id.ListView01);
							listView1.setVisibility(View.GONE);
							ListView listView2 = (ListView) findViewById(R.id.ListView002);
							listView2.setVisibility(View.VISIBLE);
							// 这样可以避免他们通知请求socket
							new Thread(getSocketRunnableZigbee).start();
						}
					}
				});

	}

	// 左侧每一项的选择
	protected void onItemClickDevs(View paramAnonymousView)
	{
		// TODO Auto-generated method stub
		// switch(paramAnonymousView.get)
		// {
		// case R.string.dev_name_all:
		// devs.add(paramDevView);
		// devsAdapter.notifyDataSetChanged();//更新适配器列表
		// devsDesc.setText("[" + devsOnCount + "/" + devs.size() +
		// "]");//更新显示设备数目
		// }
	}

	// 中间 列表
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
				// 显示
				devName.setText(devList.get(position).get("DeviceName"));
				devType.setText(devList.get(position).get("DeviceType"));
				info.setText("正常");
				devBrand.setText(devList.get(position).get("BrandName"));
				listViewButton.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						@SuppressWarnings("unchecked")
						// 得到tag 只有用tag才能得到方才放进来的数据
						Map<String, String> mapDev = (Map<String, String>) v
								.getTag();
						// 帮助类 必须实现Serializable 才能放到bundle中 在activity之间方便传递
						UtilDataForDeviceDetail myData = new UtilDataForDeviceDetail();
						myData.setMap(mapDev);// 添加map
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
				return view;// 获得视图对应的设备
			}
		};
		this.devListView.setAdapter(devsAdapter);
		// 设备列表监听
		this.devListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener()// 设备列表监听器
				{
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong)
					{
						Log.i("1", "devListView.setOnItemClickListener"); // INFO
						boolean bool;
						if (HomeControlActivity.this.devSelected == paramAnonymousView)// 选择的设备
						{
							Log.i("1", "devSelected"); // INFO
							DevView localDevView = (DevView) paramAnonymousView;// 选择的设备
							if (localDevView.getDevState())
							{
								bool = false;// 关闭设备
								Log.i("1", "关闭设备"); // INFO
								HomeControlActivity.updateDevState(
										localDevView, bool,
										HomeControlActivity.this.getWindow(),
										null);
							}
							else
							{
								bool = true;
								Log.i("1", "打开设备"); // INFO
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
										.setBackgroundColor(Color.TRANSPARENT);// 设置透明
								HomeControlActivity.this.devSelected
										.findViewById(R.id.contextbtn)
										.setVisibility(View.GONE);// 设置不可显示
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
					devName.setText("电饭煲设备");
				}
				else if (type1.equals("type02"))
				{
					devName.setText("电灯");
				}
				info.setText("正常");
				devType.setText(_devlistZig.get(position).get("nwk"));
				devBrand.setText("正常");
				listViewButton.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						showToast((v.getTag().toString()) + "");
						String devType = (String) v.getTag();
						// 跳转到zigbee设备的界面
						if (devType.equals("type02"))
						{// 电灯设备
							Intent lightIntent = new Intent();
							lightIntent.setClass(HomeControlActivity.this,
									lighton.class);
							// lightIntent.setClass(HomeControlActivity.this,
							// lighton.class);//带socket的
							startActivity(lightIntent);
						}
						else if (devType.equals("type01"))
						{// 电饭煲设备
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
