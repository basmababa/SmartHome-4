package com.example.smarthome.HomeSetupActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DecodeJson
{
	private String jsoString = "";
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private Map<String , String> map;
	private List<Map<String, String>> list;
	/**
	 * ���캯��
	 */
	public DecodeJson()
	{
		
	}
	/**
	 * ����json�Ĺ��캯��
	 * @param string Ҫ������json�ַ���
	 */
	public DecodeJson(String string)
	{
		this.jsoString = string;
	}
	/**
	 * ����json
	 * @return List<Map<String, String>> ����ʽlist������ ���쳣�ͷ���null
	 */
	public List<Map<String, String>> decodeJsonZigbee(String jsonString)
	{
		try
		{
			jsonObject = new JSONObject(jsonString);
			//jsonObject = 
			list = new ArrayList<Map<String,String>>();
			String subJson =  jsonObject.getString("devices");
			jsonArray = new JSONArray(subJson);
			for (int i = 0; i < jsonArray.length(); i++)
			{
				map = new HashMap<String, String>();
				jsonObject = jsonArray.getJSONObject(i);
				String type    = jsonObject.getString("type");
				String nwk			 = jsonObject.getString("nwk");

				map.put("nwk", nwk);
				map.put("type", type);
				
				list.add(map);
				Log.i("DECODE--JSON", "json ��������");
			}
			return list;
		} catch (JSONException e)//  �����쳣  ����null
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EXCEPTION--JSON", "json ���������쳣");
			return null;
		}
	}

	/**
	 * 
	 * @param jsonString
	 * @return List<Map<String, String>> ����ʽlist������ ���쳣�ͷ���null
	 */
	public List<Map<String , String>> decodejson()
	{
		try
		{
			jsonArray = new JSONArray(jsoString);
			list = new ArrayList<Map<String,String>>();
			for (int i = 0; i < jsonArray.length(); i++)
			{
				map = new HashMap<String, String>();
				jsonObject = jsonArray.getJSONObject(i);
				String id			 = jsonObject.getString("id");
				String DeviceName    = jsonObject.getString("DeviceName");
				String DeviceType    = jsonObject.getString("DeviceType");
				String BrandName     = jsonObject.getString("BrandName");
				String ModelName     = jsonObject.getString("ModelName");
				String X		     = jsonObject.getString("X");
				String Y		     = jsonObject.getString("Y");
				map.put("id", id);
				map.put("DeviceName", DeviceName);
				map.put("DeviceType", DeviceType);
				map.put("BrandName", BrandName);
				map.put("ModelName", ModelName);
				map.put("X", X);
				map.put("Y", Y);
				list.add(map);
				Log.i("DECODE--JSON", "json ��������");
			}
			return list;
		} catch (JSONException e)//  �����쳣  ����null
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("EXCEPTION--JSON", "json ���������쳣");
			return null;
		}	
	}
}
