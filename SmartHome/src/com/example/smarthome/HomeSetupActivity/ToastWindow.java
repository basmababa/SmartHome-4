package com.example.smarthome.HomeSetupActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.R;

public class ToastWindow extends PopupWindow
{
  private Context context = null;
  private TextView text = null;

  public ToastWindow(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    this.text = new TextView(paramContext);
    this.text.setTextColor(Color.WHITE);
    setContentView(this.text);//���ð�����ͼ
    setBackgroundDrawable(paramContext.getResources().getDrawable(R.drawable.toast_dialog_bg));//������ʾ�Ի���ı���
    setWidth(-2);
    setHeight(-2);
  }

  public ToastWindow(Context paramContext, int paramInt)
  {
    this(paramContext);
    this.text.setText(paramInt);
  }

  public ToastWindow(Context paramContext, CharSequence paramCharSequence)
  {
    this(paramContext);
    this.text.setText(paramCharSequence);
  }

  public void dismissDelayed()
  {
    dismissDelayed(2800);
  }

  public void dismissDelayed(int paramInt)
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        ToastWindow.this.dismiss();
      }
    }
    , paramInt);
  }

  public void setText(int paramInt)
  {
    this.text.setText(paramInt);
  }

  public void setText(CharSequence paramCharSequence)
  {
    this.text.setText(paramCharSequence);
  }

  /**
   * ��ʾ��ʾ��Ϣ
   * @param paramWindow ������
   * @param paramInt ����
   */
  	public void show(Window paramWindow, int paramInt)
	{
  		showAtLocation(paramWindow.getDecorView(), 80, 0, paramInt);//���öԻ����λ��
	}

	public void showCenter(Window paramWindow)
	{
		showAtLocation(paramWindow.getDecorView(), 17, 0, 0);
	}
	
	public void showToast(String text)
	{
		Toast.makeText(context, text, 0).show();
	}
	
	
}

























