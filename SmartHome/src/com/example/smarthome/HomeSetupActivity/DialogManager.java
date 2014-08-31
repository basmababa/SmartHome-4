package com.example.smarthome.HomeSetupActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public abstract class DialogManager
{
  public static CustomDialog showAlertDialog(Context paramContext, String paramString1, String paramString2)
  {
    CustomDialog localCustomDialog = new CustomDialog(paramContext);
    localCustomDialog.setTitle(paramString1);
    localCustomDialog.setMessage(paramString2);
    localCustomDialog.show();
    return localCustomDialog;
  }

  public static CustomDialog showAlertDialog(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    CustomDialog localCustomDialog = new CustomDialog(paramContext);
    localCustomDialog.setTitle(paramString1);
    localCustomDialog.setMessage(paramString2);
    localCustomDialog.setButton(0, paramString3, null);
    localCustomDialog.show();
    return localCustomDialog;
  }

  public static CustomDialog showAlertDialog(Context paramContext, String paramString1, String paramString2, String paramString3, DialogInterface.OnClickListener paramOnClickListener)
  {
    CustomDialog localCustomDialog = new CustomDialog(paramContext);
    localCustomDialog.setTitle(paramString1);
    localCustomDialog.setMessage(paramString2);
    localCustomDialog.setButton(0, paramString3, paramOnClickListener);
    localCustomDialog.show();
    return localCustomDialog;
  }
  	
  /**
   * 
   * @param paramContext ������ 
   * @param paramString1 ����
   * @param paramString2 ��ʾ����
   * @param paramString3 ȷ�ϰ�ť��ʾ��Ϣ
   * @param paramOnClickListener1 ȷ�ϰ�ť��Ӧ
   * @param paramString4 ȡ����ť��ʾ��Ϣ
   * @param paramOnClickListener2 ȡ����ť��Ӧ
   * @return
   */
	public static CustomDialog showAlertDialog(Context paramContext, String paramString1, String paramString2, String paramString3, DialogInterface.OnClickListener paramOnClickListener1, String paramString4, DialogInterface.OnClickListener paramOnClickListener2)
	{
		CustomDialog localCustomDialog = new CustomDialog(paramContext);
		localCustomDialog.setTitle(paramString1);
		localCustomDialog.setMessage(paramString2);
		localCustomDialog.setButton(0, paramString3, paramOnClickListener1);
		localCustomDialog.setButton(1, paramString4, paramOnClickListener2);
		localCustomDialog.show();
		return localCustomDialog;
	}

  public static CustomDialog showAlertDialog(Context paramContext, String paramString1, String paramString2, String paramString3, DialogInterface.OnClickListener paramOnClickListener1, String paramString4, DialogInterface.OnClickListener paramOnClickListener2, String paramString5, DialogInterface.OnClickListener paramOnClickListener3)
  {
    CustomDialog localCustomDialog = new CustomDialog(paramContext);
    localCustomDialog.setTitle(paramString1);
    localCustomDialog.setMessage(paramString2);
    localCustomDialog.setButton(0, paramString3, paramOnClickListener1);
    localCustomDialog.setButton(1, paramString4, paramOnClickListener2);
    localCustomDialog.setButton(2, paramString5, paramOnClickListener3);
    localCustomDialog.show();
    return localCustomDialog;
  }

  public static CustomDialog showNoCancelAlertDialog(Context paramContext, String paramString1, String paramString2, String paramString3, DialogInterface.OnClickListener paramOnClickListener)
  {
    CustomDialog localCustomDialog = new CustomDialog(paramContext);
    localCustomDialog.setCancelable(false);
    localCustomDialog.setTitle(paramString1);
    localCustomDialog.setMessage(paramString2);
    localCustomDialog.setButton(0, paramString3, paramOnClickListener);
    localCustomDialog.show();
    return localCustomDialog;
  }

  public static ProgressDialog showProgressDialog(Context paramContext, String paramString1, String paramString2)
  {
    ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
    localProgressDialog.setTitle(paramString1);
    localProgressDialog.setMessage(paramString2);
    localProgressDialog.show();
    return localProgressDialog;
  }
}