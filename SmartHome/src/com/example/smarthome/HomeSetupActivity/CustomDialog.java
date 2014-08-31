package com.example.smarthome.HomeSetupActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

import com.example.smarthome.R;


public class CustomDialog extends Dialog implements View.OnClickListener
{
  public static final int BUTTON_NEGATIVE = 1;
  public static final int BUTTON_NEUTRAL = 2;
  public static final int BUTTON_POSITIVE = 0;
  private Button buttonNegative = null;
  private Button buttonNeutral = null;
  private Button buttonPositive = null;
  private LinearLayout buttonsLayout = null;
  private Map<Button, DialogInterface.OnClickListener> buttonsMapping = new HashMap();
  private LinearLayout content = null;
  private Context context = null;
  private ImageView icon = null;
  private TextView message = null;
  private TextView title = null;

  public CustomDialog(Context paramContext)
  {
    super(paramContext, R.style.ThemeDialog1);//�ڶ�������theme: ��dialog������
    this.context = paramContext;
    Log.i("1", "CustomDialog���캯�����");//�����
    this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//���ر���
    setContentView(R.layout.customdialog);//��������
    this.icon = ((ImageView)findViewById(R.id.ImageView01));
    this.title = ((TextView)findViewById(R.id.TextView01));
    this.content = ((LinearLayout)findViewById(R.id.LinearLayout04));
    this.message = ((TextView)findViewById(R.id.TextView02));
    this.buttonsLayout = ((LinearLayout)findViewById(R.id.LinearLayout05));
    this.buttonPositive = ((Button)findViewById(R.id.Button01));
    this.buttonNegative = ((Button)findViewById(R.id.Button02));
    this.buttonNeutral = ((Button)findViewById(R.id.Button03));
    this.buttonPositive.setOnClickListener(this);
    this.buttonNegative.setOnClickListener(this);
    this.buttonNeutral.setOnClickListener(this);
    Log.i("1", "CustomDialog���캯������ ");//�����
  }

  public void onClick(View paramView)
  {
    DialogInterface.OnClickListener localOnClickListener = (DialogInterface.OnClickListener)this.buttonsMapping.get(paramView);
    if (localOnClickListener == null)
    {
    	Log.i("1", "�˳��Ի��� ");//�����
      	dismiss();//�˳��Ի���
      	return;
    }
    if (paramView == this.buttonPositive)
        localOnClickListener.onClick(this, 0);
    else if (paramView == this.buttonNegative)
        localOnClickListener.onClick(this, 1);
    else if (paramView == this.buttonNeutral)
        localOnClickListener.onClick(this, 2);
  }

  public void setButton(int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener)
  {
    setButton(paramInt1, this.context.getString(paramInt2), paramOnClickListener);
  }

  //���ð�ť״̬
  /**
   * 
   * @param paramInt ������
   * @param paramCharSequence ��ť��ʾ����
   * @param paramOnClickListener
   */
  public void setButton(int paramInt, CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
	  try{
		  Log.i("1", "setButton�������"); //INFO 
		  Button localButton = null;
		  if (paramInt == BUTTON_POSITIVE)
		  {
			  localButton = this.buttonPositive;
		  }
		  if (paramInt == BUTTON_NEGATIVE)
		  {
			  	localButton = this.buttonNegative;
		  }
		  if (paramInt == BUTTON_NEUTRAL)
		  {
				localButton = this.buttonNeutral;
		  }
		  localButton.setVisibility(View.VISIBLE);//��ʾ
		  localButton.setText(paramCharSequence);			
		  this.buttonsMapping.put(localButton, paramOnClickListener);
	  }catch (IllegalArgumentException e) {
		  System.out.println("IllegalArgumentException");//����쳣��Ϣ
	  }
	  Log.i("1", "setButton��������"); //INFO 
  }

  public void setCenterView(View paramView)
  {
    this.content.removeAllViews();
    this.content.addView(paramView, new LinearLayout.LayoutParams(-1, -1));
  }

  public void setIcon(int paramInt)
  {
    this.icon.setImageResource(paramInt);
  }

  public void setIcon(Drawable paramDrawable)
  {
    this.icon.setImageDrawable(paramDrawable);
  }

  public void setMessage(int paramInt)
  {
    this.message.setText(paramInt);
  }

  public void setMessage(CharSequence paramCharSequence)
  {
    this.message.setText(paramCharSequence);
  }

  public void setTitle(int paramInt)
  {
    this.title.setText(paramInt);
  }

  public void setTitle(CharSequence paramCharSequence)
  {
    this.title.setText(paramCharSequence);
  }

  //��ʾ�Ի���
  public void show()
  {
	  int i = 0;
	  Log.i("1", "show�������"); //INFO 
	  if (this.buttonPositive.getVisibility() == View.VISIBLE)//ȷ����ť���ӻ�
		  i++;
	  if (this.buttonNegative.getVisibility() == View.VISIBLE)//ȡ����ť���ӻ�
		  i++;
	  if (this.buttonNeutral.getVisibility() == View.VISIBLE)//��չ��ť���ӻ�
		  i++;
	  if (i == 0)
		  this.buttonsLayout.setPadding(0, 0, 0, 0);
	  if (i == 1)
		  this.buttonsLayout.setPadding(50, 0, 50, 4);
      else
    	  this.buttonsLayout.setPadding(0, 0, 0, 10);
	  super.show();
	  Log.i("1", "show��������"); //INFO 
	  return;
  }
}