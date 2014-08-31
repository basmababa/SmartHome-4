package com.example.smarthome.HomeSetupActivity;

import java.io.Serializable;
import java.util.Map;

public class UtilDataForDeviceDetail implements Serializable
{ 
	//company
	private String ID;
	private String DeviceName;
	private String DeviceType;
	private String BrandName;
	private String ModelName;
	private String X;
	private String Y;
	
	private Map<String, String> map;
	
	public Map<String, String> getMap()
	{
		return map;
	}
	public void setMap(Map<String, String> map)
	{
		this.map = map;
	}
	//zigbee
	private String nwk;
	private String type;
	
	//get and set
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getDeviceName()
	{
		return DeviceName;
	}
	public void setDeviceName(String deviceName)
	{
		DeviceName = deviceName;
	}
	public String getDeviceType()
	{
		return DeviceType;
	}
	public void setDeviceType(String deviceType)
	{
		DeviceType = deviceType;
	}
	public String getBrandName()
	{
		return BrandName;
	}
	public void setBrandName(String brandName)
	{
		BrandName = brandName;
	}
	public String getModelName()
	{
		return ModelName;
	}
	public void setModelName(String modelName)
	{
		ModelName = modelName;
	}
	public String getX()
	{
		return X;
	}
	public void setX(String x)
	{
		X = x;
	}
	public String getY()
	{
		return Y;
	}
	public void setY(String y)
	{
		Y = y;
	}
	public String getNwk()
	{
		return nwk;
	}
	public void setNwk(String nwk)
	{
		this.nwk = nwk;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	
}
