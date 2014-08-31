package com.example.smarthome.HomeSetupActivity;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class UIThread extends Thread
{
	protected UICallback callback = null;//返回对象
	protected Context context = null;//上下文
	protected Handler handler = null;//消息处理
  
	/**
	 * 构造函数
	 * @param paramContext 上下文
	 * @param paramUICallback 传递的对象
	 */
public UIThread(Context paramContext, UICallback paramUICallback)
{
	if ((paramContext == null) || (paramUICallback == null))throw new NullPointerException();//
	this.context = paramContext;
	this.callback = paramUICallback;
}

public Object onRun() throws Exception
{
	return null;
}

public final void run()//主要的函数
{
	Log.i("UIThread", "UIThread的run函数开始"); //INFO 
	try
	{
		super.run();
		Log.i("UIThread", "UIThread的run函数开始1"); //INFO 
		final Object localObject2 = onRun();
		Log.i("UIThread", "UIThread的run函数开始2"); //INFO 
		this.handler.post(new Runnable(){
			public void run()
			{
				Log.i("UIThread", "UIThread的onSuccess函数开始"); //INFO 
				UIThread.this.callback.onSuccess(UIThread.this.context, localObject2);//存有所有设备的状态
			}
		});
	}
	  catch (final Exception localException)
	  {

		  this.handler.post(new Runnable(){
			  public void run()
			  {
				  UIThread.this.callback.onException(UIThread.this.context, localException);
			  }
		  });
//		  this.handler.post(new Runnable()
//		  {
//			  public void run()
//			  {
//				  UIThread.this.callback.onFinally(UIThread.this.context);
//			  }
//		  });
	  }finally
	  {
		  this.handler.post(new Runnable()
		  {
			  public void run()
			  {
				  UIThread.this.callback.onFinally(UIThread.this.context);
			  }
		  });
    }
  }

  public final void start()
  {
	  Log.i("UIThread", "UIThread的start函数开始"); //INFO 
	  this.handler = new Handler();
	  this.callback.onBegin(this.context);//开始执行
	  super.start();
	  Log.i("UIThread", "UIThread的start函数结束"); //INFO 
  }

  public static class UICallback
  {
    public void onBegin(Context paramContext)
    {
    }

    public void onException(Context paramContext, Exception paramException)
    {
    }

    public void onFinally(Context paramContext)
    {
    }

    public void onSuccess(Context paramContext, Object paramObject)
    {
    }
  }
}