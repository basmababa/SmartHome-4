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
	public static final String DEV_TYPE_AIR = "�յ��豸";		//����
	public static final String DEV_TYPE_GATED = "�ſ��豸";
	public static final String DEV_TYPE_FAN = "�����豸";
	public static final String DEV_TYPE_THERMOSTAT = "�¿��豸";
	public static final String DEV_TYPE_LIGHT = "�ƹ��豸";
	public static final String DEV_TYPE_SOCKET = "�����豸";
	public static final String DEV_TYPE_OTHER = "�����豸";
	private Context context;//������
	private ImageView devIcon;//�豸ͼ��
	private TextView devInfo;//�豸����
	private ImageView devMore;//������Ϣ��ť
	private String devId;//�豸id
	private String devName;//�豸����
	private String devType;//�豸����
	private String devbrand;//�豸Ʒ��
	private TextView devNameView;//�豸������
	private boolean devState;//�豸״̬
	

	  //�豸���캯��
	  /**
	   * 
	   * @param paramContext ������
	   * @param deviceid	�豸id
	   * @param devicename	�豸����
	   * @param devicestyle	�豸����
	   * @param devicebrand	�豸Ʒ��
	   * @param paramBoolean �Ƿ���ӵ����ݿ�
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
	    this.devState = false;//�豸״̬
	    if (paramBoolean)//����豸�����ݿ��ж��豸ID
	    {
	    	DatabaseHelper localDatabaseHelper = new DatabaseHelper(paramContext);
	    	String[] arrayOfString = new String[1];//��������
	    	arrayOfString[0] = deviceid;//�豸id
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
	    LinearLayout localLinearLayout = (LinearLayout)LayoutInflater.from(paramContext).inflate(R.layout.child_list_item, null);//��������豸
	    addView(localLinearLayout, new LinearLayout.LayoutParams(-1, -1));//���ò���
	    this.devIcon = ((ImageView)localLinearLayout.findViewById(R.id.devIcon));
	    this.devNameView = ((TextView)localLinearLayout.findViewById(R.id.devName));
	    this.devInfo = ((TextView)localLinearLayout.findViewById(R.id.info));
	    this.devMore = ((ImageView)localLinearLayout.findViewById(R.id.contextbtn));
	    this.devIcon.setImageResource(getDevIconResStyle(devType, this.devState));//�����豸ͼ���״̬
	    this.devNameView.setText(this.devName);//�����豸����
	    this.devInfo.setText(getDevStateResId(this.devState));//���ÿ���״̬
	    this.devMore.setOnClickListener(this);//��Ӹ������
	}

	  /**
	   * �����豸���ͺ�״̬������ͼ��
	   * @param paramString �豸����
	   * @param paramBoolean �豸״̬
	   * @return ��Դ��λ
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

	  //�����豸��״̬���Ƿ��on/off
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

	//�����ݿ�����б�
	public static List<DevView> loadFromDB(Context paramContext)
	{
		//�����ݿ�������� id,DeviceName,DeviceType,BrandName,ModelName id�� �豸���� �豸����  Ʒ�� ��Ʒ����
		List<Map<String, String>> localList = new DatabaseHelper(paramContext).rawQuery("select DeviceId,DeviceName,DeviceType,BrandName,ModelName from DEV_INFO", null);
		Log.i("1", "DatabaseHelper��������" + localList.toString()); //INFO 
		LinkedList<DevView> localLinkedList = new LinkedList<DevView>();
		for (int i = 0; i < localList.size(); i++)
		{
	    
			Map localMap = (Map)localList.get(i);
			String str1 = (String)localMap.get("DeviceId");//�豸id
			String str2 = (String)localMap.get("DeviceName");//�豸����
			String str3 = (String)localMap.get("DeviceType");//�豸����
			String str4 = (String)localMap.get("BrandName");//�豸����
			DevView localDevView = new DevView(paramContext, str1, str2, str3, str4, false);
			localDevView.initDevName(str2); 
			localLinkedList.add(localDevView);//�豸�б�
		}
		return localLinkedList;
	}
	
	//ɾ�����ݿ�����Ķ�ӦID���豸����
	public void deleteDev()
	{
		// ������һ��DatabaseHelper����ִֻ����仰�ǲ��ᴴ��������ӵ�
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
		Log.i("DeviceActivity", "onClick����");
	  	Intent localIntent = new Intent(this.context, DeviceActivity.class);
	  	localIntent.putExtra("DEV_VIEW_SIGN", getDevId());//����Ҫ��ʾ���豸ID
	  	this.context.startActivity(localIntent);
	}

	/**
	 * �������ݿ��豸������
	 * @param paramString Ҫ�޸ĵ�����
	 */
	public void setDevName(String devicename)
	{
		DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = devicename;//����
		arrayOfObject[1] = getDevId();//ID��
		localDatabaseHelper.execSQL("update DEV_INFO set DeviceName = ? where DeviceId = ?", arrayOfObject);
		this.devName = devicename;//���µ�ǰ�豸������
		this.devNameView.setText(devicename);//�����б������豸������
	}
	
	/**
	 * �������ݿ��豸��Ʒ��
	 * @param paramString Ҫ�޸ĵ�Ʒ��
	 */
	public void setDevBrand(String devicebrand)
	{
		DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = devicebrand;//����
		arrayOfObject[1] = getDevId();//ID��
		localDatabaseHelper.execSQL("update DEV_INFO set BrandName = ? where DeviceId = ?", arrayOfObject);
		this.devbrand = devicebrand;//���µ�ǰ�豸������
	}

	/**
	 * �޸��豸��deviceid
	 * @param paramInt Ҫ�޸ĵ�ID
	 */
	public void setDevID(String deviceid)
	{
		DatabaseHelper localDatabaseHelper = new DatabaseHelper(this.context);//������ݿ⣬����û����
		String[] arrayOfString = new String[2];
		arrayOfString[0] = deviceid;//Ҫ�޸ĵ��豸id
		arrayOfString[1] = getDevId();//��ǰ�豸id
		if (localDatabaseHelper.rawQuery("select 1 from DEV_INFO where DeviceId = ? and DeviceId <> ?", arrayOfString).size() > 0)
			throw new IllegalArgumentException();//�ж�Ҫ�޸ĵ�id�Ƿ����
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = deviceid;
		arrayOfObject[1] = getDevId();
		localDatabaseHelper.execSQL("update DEV_INFO set DeviceId = ? where DeviceId = ?", arrayOfObject);//�޸����ݿ������豸��ID
		this.devId = deviceid;//�豸�б����id
  	}

  	//�����豸��״̬
  	public void setDevState(boolean paramBoolean)
  	{
  		this.devState = paramBoolean;
  		this.devIcon.setImageResource(getDevIconResStyle(this.devType, paramBoolean));//�����豸�б��ͼ��
  		this.devInfo.setText(getDevStateResId(paramBoolean));//��ʾ�豸��on / off
  	}
  	
  	public static List<DevView> loadDevFromSocket()
  	{
  		List<DevView> s = new ArrayList<DevView>();
  		return s;
  	}




}