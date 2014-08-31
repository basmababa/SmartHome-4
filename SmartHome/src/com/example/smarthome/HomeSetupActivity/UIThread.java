package com.example.smarthome.HomeSetupActivity;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class UIThread extends Thread
{
	protected UICallback callback = null;//���ض���
	protected Context context = null;//������
	protected Handler handler = null;//��Ϣ����
  
	/**
	 * ���캯��
	 * @param paramContext ������
	 * @param paramUICallback ���ݵĶ���
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

public final void run()//��Ҫ�ĺ���
{
	Log.i("UIThread", "UIThread��run������ʼ"); //INFO 
	try
	{
		super.run();
		Log.i("UIThread", "UIThread��run������ʼ1"); //INFO 
		final Object localObject2 = onRun();
		Log.i("UIThread", "UIThread��run������ʼ2"); //INFO 
		this.handler.post(new Runnable(){
			public void run()
			{
				Log.i("UIThread", "UIThread��onSuccess������ʼ"); //INFO 
				UIThread.this.callback.onSuccess(UIThread.this.context, localObject2);//���������豸��״̬
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
	  Log.i("UIThread", "UIThread��start������ʼ"); //INFO 
	  this.handler = new Handler();
	  this.callback.onBegin(this.context);//��ʼִ��
	  super.start();
	  Log.i("UIThread", "UIThread��start��������"); //INFO 
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