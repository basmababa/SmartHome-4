package com.example.smarthome.HomeSetupActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** 
 * SQLiteOpenHelper��һ�������࣬�����������ݿ�Ĵ����Ͱ汾�������ṩ��������Ĺ��� 
 * ��һ��getReadableDatabase()��getWritableDatabase()���Ի��SQLiteDatabase����ͨ���ö�����Զ����ݿ���в��� 
 * �ڶ����ṩ��onCreate()��onUpgrade()�����ص����������������ٴ������������ݿ�ʱ�������Լ��Ĳ��� 
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
	public DatabaseHelper(Context paramContext)
	{
		super(paramContext, "DeviceInfo", null, 1);
		Log.i("1", "DatabaseHelper��ں���"); //INFO 
	}

	//ִ��SQL���
	public void execSQL(String paramString)
	{
		SQLiteDatabase localSQLiteDatabase = null;
		try
		{
			localSQLiteDatabase = getWritableDatabase();//��ȡ���ݿ����
			localSQLiteDatabase.execSQL(paramString);//ִ��SQL���
			return;
		}
		finally
		{
			if (localSQLiteDatabase != null)
				localSQLiteDatabase.close();
		}
	}

	/**
	 * ִ��SQL���
	 * @param paramString ִ�е�SQL���
	 * @param paramArrayOfObject ִ��SQL���Ĳ���
	 */
	public void execSQL(String paramString, Object[] paramArrayOfObject)
	{
		SQLiteDatabase localSQLiteDatabase = null;
		try
		{
			// ֻ�е�����DatabaseHelper��getWritableDatabase()��������getReadableDatabase()����֮�󣬲Żᴴ�����һ������
			localSQLiteDatabase = getWritableDatabase();//��ȡ���ݿ����
			localSQLiteDatabase.execSQL(paramString, paramArrayOfObject);//ִ��SQL���
			return;
		}
		finally
		{
			if(localSQLiteDatabase != null)
				localSQLiteDatabase.close();
		}
	}

	//�ú������ڵ�һ�δ�����ʱ��ִ�У�ʵ�����ǵ�һ�εõ�SQLiteDatabase�����ʱ��Ż�����������  
	public void onCreate(SQLiteDatabase paramSQLiteDatabase)
	{
		//ִ��SQL��䣬������Ӧ�ı�
		Log.i("1", "������Ӧ�ı�"); //INFO 
		paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DEV_INFO (ID INTEGER PRIMARY KEY,DeviceId TEXT, DeviceName TEXT,DeviceType TEXT,BrandName TEXT,ModelName TEXT,X INTEGER,Y INTEGER);");
	}
	
	//�����ݿ�
	public void onOpen(SQLiteDatabase paramSQLiteDatabase)
	{
		super.onOpen(paramSQLiteDatabase);
	}

	//�������ݿ�
	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
	{
	}

	//�������id�ź��豸����
	public List<Map<String, String>> rawQuery(String paramString, String[] paramArrayOfString)
	{
	  //���в�ѯ����
	  Log.i("DatabaseHelper", "rawQuer�������"); //INFO
	  LinkedList<Map<String, String>> localLinkedList = new LinkedList<Map<String, String>>();
	  SQLiteDatabase localSQLiteDatabase = null;
	  Cursor localCursor = null;
	  HashMap<String, String> localHashMap;
	  try
	  {
		  localSQLiteDatabase = getReadableDatabase();//������ݿ�
		  if (localSQLiteDatabase != null)
		  {
			  Log.i("DatabaseHelper", "������ݿ�"); //INFO 
			  localCursor = localSQLiteDatabase.rawQuery(paramString, paramArrayOfString);
			  int j = localCursor.getColumnCount();//���Cursor������
			  Log.i("DatabaseHelper", "������ݿ�����"+j +"������ݿ�����"+ localCursor.getCount()); //INFO 
			  if (localCursor.moveToFirst() == true)//�α겻Ϊ��
			  {
				  Log.i("DatabaseHelper", "�α겻Ϊ��"); //INFO 
				  do 
				  {  
				  //����ƶ��ɹ�  
				  //������ȡ��  
					  Log.i("DatabaseHelper", "����ƶ�"); //INFO 
					  localHashMap = new HashMap<String, String>();
					  for(int i = 0; i < j; i++)//��ѭ��
					  {
						  Log.i("DatabaseHelper", "����ƶ�"); //INFO 
						  String str1 = localCursor.getColumnName(i);
						  String str2 = localCursor.getString(i);
						  Log.i("DatabaseHelper", "str1" + str1); //INFO 
						  Log.i("DatabaseHelper", "str2" + str2); //INFO 
						  if (str2 == null)str2 = "";
						  localHashMap.put(str1, str2);
					  }
					  localLinkedList.add(localHashMap);
				  
				  }while(localCursor.moveToNext() != false);
			  }
		  }
	  }catch(SQLException e){e.printStackTrace();}
	  finally
	  {
		  if (!localCursor.isClosed()) {  
			  localCursor.close(); 
		  } 
		  localSQLiteDatabase.close();
	  }
	return localLinkedList; 
  }

  public List<String> rawQueryForFirstField(String paramString, String[] paramArrayOfString)
  {
    LinkedList localLinkedList = new LinkedList();
    SQLiteDatabase localSQLiteDatabase = null;
    Cursor localCursor = null;
    try
    {
      localSQLiteDatabase = getReadableDatabase();
      localCursor = localSQLiteDatabase.rawQuery(paramString, paramArrayOfString);
      Log.i("localCursor", "��ȡ������" + localCursor.getColumnCount()); //INFO 
      while (localCursor.moveToNext())
      {
    	  Log.i("localCursor", "������" + localCursor.getString(0));
    	  String str = localCursor.getString(0);
    	  if (str == null)str = "";
    	  localLinkedList.add(str);
      }
    }
    finally
    {
    	if (localCursor != null);
      	{
    	  	localCursor.close();
      	}
        if (localSQLiteDatabase != null)
        {
          localSQLiteDatabase.close();
          Log.i("localCursor", "localSQLiteDatabase.close()");
        }
    }
	return localLinkedList;
  }
}