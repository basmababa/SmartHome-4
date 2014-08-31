package com.example.smarthome.HomeSetupActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.smarthome.R;
public class DevView extends LinearLayout implements View.OnClickListener
{
	public static final String DEV_TYPE_AIR = "空调设备";		//空气
	public static final String DEV_TYPE_GATED = "门控设备";
	public static final String DEV_TYPE_FAN = "风扇设备";
	public static final String DEV_TYPE_THERMOSTAT = "温控设备";
	public static final String DEV_TYPE_LIGHT = "灯光设备";
	public static final String DEV_TYPE_SOCKET = "插座设备";
	public static final String DEV_TYPE_OTHER = "其他设备";
	private Context context;//上下文
	private ImageView devIcon;//设备图标
	private TextView devInfo;//设备名称
	private ImageView devMore;//更多信息按钮
	private String devId;//设备id
	private String devName;//设备名称
	private String devType;//设备类型
	private String devbrand;//设备品牌
	private TextView devNameView;//设备的名称
	private boolean devState;//设备状态
	

	  //设备构造函数
	  /**
	   * 
	   * @param paramContext 上下文
	   * @param deviceid	设备id
	   * @param devicename	设备名称
	   * @param devicestyle	设备类型
	   * @param devicebrand	设备品牌
	   * @param paramBoolean 是否添加到数据库
	   */
	//deviceid devicename devicestyle devicebrand 
	public DevView(Context paramContext, String deviceid, String devicename, String devicestyle, String devicebrand, boolean paramBoolean)
	{
		super(paramContext);
	    this.context = paramContext;
	    this.devName = devicename;
	    this.devType = devicestyle;
	    this.devId = deviceid;
	    this.devbrand = devicebrand;
	    this.devState = false;//设备状态
	    if (paramBoolean)//添加设备到数据库判断设备ID
	    {
	    	DatabaseHelper localDatabaseHelper = new DatabaseHelper(paramContext);
	    	String[] arrayOfString = new String[1];//定义数组
	    	arrayOfString[0] = deviceid;//设备id
	    	if (localDatabaseHelper.rawQuery("select 1 from DEV_INFO where DeviceId = ?", arrayOfString).size() > 0)throw new IllegalArgumentException();
	    	Object[] arrayOfObject = new Object[7];
	    	arrayOfObject[0] = this.devId;
	    	arrayOfObject[1] = this.devName;
	    	arrayOfObject[2] = this.devType;
	    	arrayOfObject[3] = this.devbrand;
	    	arrayOfObject[4] = "XXX";
	    	arrayOfObject[5] = 0;
	    	arrayOfObject[6] = 0;
	    	localDatabaseHelper.execSQL("insert into DEV_INFO(DeviceId, DeviceName,DeviceType,BrandName,ModelName,X,Y) values (?,?,?,?,?,?,?)", arrayOfObject);
	    }
	    LinearLayout localLinearLayout = (LinearLayout)LayoutInflater.from(paramContext).inflate(R.layout.child_list_item, null);//获得子项设备
	    addView(localLinearLayout, new LinearLayout.LayoutParams(-1, -1));//设置参数
	    this.devIcon = ((ImageView)localLinearLayout.findViewById(R.id.devIcon));
	    this.devNameView = ((TextView)localLinearLayout.findViewById(R.id.devName));
	    this.devInfo = ((TextView)localLinearLayout.findViewById(R.id.info));
	    this.devMore = ((ImageView)localLinearLayout.findViewById(R.id.contextbtn));
	    this.devIcon.setImageResource(getDevIconResStyle(devType, this.devState));//设置设备图标和状态
	    this.devNameView.setText(this.devName);//设置设备名称
	    this.devInfo.setText(getDevStateResId(this.devState));//设置开关状态
	    this.devMore.setOnClickListener(this);//添加更多监听
	}

	  /**
	   * 根据设备类型和状态，查找图标
	   * @param paramString 设备类型
	   * @param paramBoolean 设备状态
	   * @return 资源定位
	   */
	public static int getDevIconResStyle(String paramString, boolean paramBoolean)
	{
		int i = 0;	
		if(paramString.equals(DEV_TYPE_AIR))
		{
			if (paramBoolean)
				i = R.drawable.dev001_on;
			else
				i = R.drawable.dev001_off;
		}
		else
		if(paramString.equals(DEV_TYPE_GATED))
		{
			if (paramBoolean)
				i = R.drawable.dev005_on;
			else
				i = R.drawable.dev005_off;
		}
		else
		if(paramString.equals(DEV_TYPE_FAN))
		{
			if (paramBoolean)
				i = R.drawable.dev004_on;
			else
				i = R.drawable.dev004_off;
		}
		else
		if(paramString.equals(DEV_TYPE_THERMOSTAT))
		{
			if (paramBoolean)
				i = R.drawable.dev003_on;
			else
				i = R.drawable.dev003_off;
		}
		else
		if(paramString.equals(DEV_TYPE_LIGHT))
		{
			if (paramBoolean)
				i = R.drawable.dev002_on;
			else
				i = R.drawable.dev002_off;
		}
		else
		if(paramString.equals(DEV_TYPE_SOCKET))
		{
			if (paramBoolean)
				i = R.drawable.dev006_on;
			else
				i = R.drawable.dev006_off;
		}
		else
		if(paramString.equals(DEV_TYPE_OTHER))
		{
			if (paramBoolean)
				i = R.drawable.dev007_on;
			else
				i = R.drawable.dev007_off;
		}
		else
		{
			if (paramBoolean)
				i = R.drawable.dev007_on;
			else
				i = R.drawable.dev007_off;
		}
		return i;
	}

	  //更新设备的状态，是否打开on/off
	public static int getDevStateResId(boolean paramBoolean)
	{
	    if (paramBoolean)
	    	return R.string.dev_state_on;
	    else
	    	return R.string.dev_state_off;
	}
	
	private void initDevName(String paramString)
	{
	    this.devName = paramString;
	    this.devNameView.setText(paramString);
}

	//从数据库加载列表
	public static List<DevView> loadFromDB(Context paramContext)
	{
		//从数据库加载数据 id,DeviceName,DeviceType,BrandName,ModelName id号 设备名称 设备类型  品牌 产品名称
		List<Map<String, String>> localList = new DatabaseHelper(paramContext).rawQuery("select DeviceId,DeviceName,DeviceType,BrandName,ModelName from DEV_INFO", null);
		Log.i("1", "DatabaseHelper函数返回" + localList.toString()); //INFO 
		LinkedList<DevView> localLinkedList = new LinkedList<DevView>();
		for (int i = 0; i < localList.size(); i++)
		{
	    
			Map localMap = (Map)localList.get(i);
			String str1 = (String)localMap.get("DeviceId");//设备id
			String str2 = (String)localMap.get("DeviceName");//设备名称
			String str3 = (String)localMap.get("DeviceType");//设备类型
			String str4 = (String)localMap.get("BrandName");//设备类型
			DevView localDevView = new DevView(paramContext, str1, str2, str3, str4, false);
			localDevView.initDevName(str2); 
			localLinkedList.add(localDevView);//设备列表
		}
		return localLinkedList;
	}
	
	//删除数据库里面的对应ID的设备数据
	public void deleteDev()
	{
		// 创建了一个DatabaseHelper对象，只执行这句话是不会创建或打开连接的
		DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);
		Object[] arrayOfObject = new Object[1];
		arrayOfObject[0] = getDevId();
		localDatabaseHelper.execSQL("delete from DEV_INFO where DeviceId = ?", arrayOfObject);
	}

	public String getDevName()
	{
	    return this.devName;
	}
	
	public String getDevId()
	{
	    return this.devId;
	}
	
	public boolean getDevState()
	{
	    return this.devState;
	}
	
	public String getDevType()
	{
	    return this.devType;
	}
	
	public String getDevBrand()
	{
	    return this.devbrand;
	}


	public void onClick(View paramView)
	{
		Log.i("DeviceActivity", "onClick创建");
	  	Intent localIntent = new Intent(this.context, DeviceActivity.class);
	  	localIntent.putExtra("DEV_VIEW_SIGN", getDevId());//传递要显示的设备ID
	  	this.context.startActivity(localIntent);
	}

	/**
	 * 更新数据库设备的名称
	 * @param paramString 要修改的名称
	 */
	public void setDevName(String devicename)
	{
		DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = devicename;//名称
		arrayOfObject[1] = getDevId();//ID号
		localDatabaseHelper.execSQL("update DEV_INFO set DeviceName = ? where DeviceId = ?", arrayOfObject);
		this.devName = devicename;//更新当前设备的名称
		this.devNameView.setText(devicename);//更改列表里面设备的名称
	}
	
	/**
	 * 更新数据库设备的品牌
	 * @param paramString 要修改的品牌
	 */
	public void setDevBrand(String devicebrand)
	{
		DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = devicebrand;//名称
		arrayOfObject[1] = getDevId();//ID号
		localDatabaseHelper.execSQL("update DEV_INFO set BrandName = ? where DeviceId = ?", arrayOfObject);
		this.devbrand = devicebrand;//更新当前设备的名称
	}

	/**
	 * 修改设备的deviceid
	 * @param paramInt 要修改的ID
	 */
	public void setDevID(String deviceid)
	{
		DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);//获得数据库，但还没连接
		String[] arrayOfString = new String[2];
		arrayOfString[0] = deviceid;//要修改的设备id
		arrayOfString[1] = getDevId();//当前设备id
		if (localDatabaseHelper.rawQuery("select 1 from DEV_INFO where DeviceId = ? and DeviceId <> ?", arrayOfString).size() > 0)
			throw new IllegalArgumentException();//判断要修改的id是否存在
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = deviceid;
		arrayOfObject[1] = getDevId();
		localDatabaseHelper.execSQL("update DEV_INFO set DeviceId = ? where DeviceId = ?", arrayOfObject);//修改数据库里面设备的ID
		this.devId = deviceid;//设备列表里的id
  	}

  	//设置设备的状态
  	public void setDevState(boolean paramBoolean)
  	{
  		this.devState = paramBoolean;
  		this.devIcon.setImageResource(getDevIconResStyle(this.devType, paramBoolean));//设置设备列表的图标
  		this.devInfo.setText(getDevStateResId(paramBoolean));//显示设备是on / off
  	}
  	
  	public static List<DevView> loadDevFromSocket()
  	{
  		List<DevView> s = new ArrayList<DevView>();
  		return s;
  	}




}