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
 * SQLiteOpenHelper是一个辅助类，用来管理数据库的创建和版本他，它提供两个方面的功能 
 * 第一，getReadableDatabase()、getWritableDatabase()可以获得SQLiteDatabase对象，通过该对象可以对数据库进行操作 
 * 第二，提供了onCreate()、onUpgrade()两个回调函数，允许我们再创建和升级数据库时，进行自己的操作 
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
	public DatabaseHelper(Context paramContext)
	{
		super(paramContext, "DeviceInfo", null, 1);
		Log.i("1", "DatabaseHelper入口函数"); //INFO 
	}

	//执行SQL语句
	public void execSQL(String paramString)
	{
		SQLiteDatabase localSQLiteDatabase = null;
		try
		{
			localSQLiteDatabase = getWritableDatabase();//获取数据库对象
			localSQLiteDatabase.execSQL(paramString);//执行SQL语句
			return;
		}
		finally
		{
			if (localSQLiteDatabase != null)
				localSQLiteDatabase.close();
		}
	}

	/**
	 * 执行SQL语句
	 * @param paramString 执行的SQL语句
	 * @param paramArrayOfObject 执行SQL语句的参数
	 */
	public void execSQL(String paramString, Object[] paramArrayOfObject)
	{
		SQLiteDatabase localSQLiteDatabase = null;
		try
		{
			// 只有调用了DatabaseHelper的getWritableDatabase()方法或者getReadableDatabase()方法之后，才会创建或打开一个连接
			localSQLiteDatabase = getWritableDatabase();//获取数据库对象
			localSQLiteDatabase.execSQL(paramString, paramArrayOfObject);//执行SQL语句
			return;
		}
		finally
		{
			if(localSQLiteDatabase != null)
				localSQLiteDatabase.close();
		}
	}

	//该函数是在第一次创建的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法  
	public void onCreate(SQLiteDatabase paramSQLiteDatabase)
	{
		//执行SQL语句，创建相应的表
		Log.i("1", "创建相应的表"); //INFO 
		paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DEV_INFO (ID INTEGER PRIMARY KEY,DeviceId TEXT, DeviceName TEXT,DeviceType TEXT,BrandName TEXT,ModelName TEXT,X INTEGER,Y INTEGER);");
	}
	
	//打开数据库
	public void onOpen(SQLiteDatabase paramSQLiteDatabase)
	{
		super.onOpen(paramSQLiteDatabase);
	}

	//更新数据库
	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
	{
	}

	//获得所有id号和设备名称
	public List<Map<String, String>> rawQuery(String paramString, String[] paramArrayOfString)
	{
	  //按行查询操作
	  Log.i("DatabaseHelper", "rawQuer函数入口"); //INFO
	  LinkedList<Map<String, String>> localLinkedList = new LinkedList<Map<String, String>>();
	  SQLiteDatabase localSQLiteDatabase = null;
	  Cursor localCursor = null;
	  HashMap<String, String> localHashMap;
	  try
	  {
		  localSQLiteDatabase = getReadableDatabase();//获得数据库
		  if (localSQLiteDatabase != null)
		  {
			  Log.i("DatabaseHelper", "获得数据库"); //INFO 
			  localCursor = localSQLiteDatabase.rawQuery(paramString, paramArrayOfString);
			  int j = localCursor.getColumnCount();//获得Cursor的列数
			  Log.i("DatabaseHelper", "获得数据库列数"+j +"获得数据库行数"+ localCursor.getCount()); //INFO 
			  if (localCursor.moveToFirst() == true)//游标不为空
			  {
				  Log.i("DatabaseHelper", "游标不为空"); //INFO 
				  do 
				  {  
				  //光标移动成功  
				  //把数据取出  
					  Log.i("DatabaseHelper", "光标移动"); //INFO 
					  localHashMap = new HashMap<String, String>();
					  for(int i = 0; i < j; i++)//列循环
					  {
						  Log.i("DatabaseHelper", "光标移动"); //INFO 
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
      Log.i("localCursor", "获取的列数" + localCursor.getColumnCount()); //INFO 
      while (localCursor.moveToNext())
      {
    	  Log.i("localCursor", "的数据" + localCursor.getString(0));
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