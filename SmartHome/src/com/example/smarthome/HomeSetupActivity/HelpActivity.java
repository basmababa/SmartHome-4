package com.example.smarthome.HomeSetupActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.R;

public class HelpActivity extends Activity implements View.OnClickListener
{
  private ImageView backImage = null;//����ͼ��
  private int currPage = 1;//ҳ��
  private LinearLayout msgLinearLayout01 = null;
  private LinearLayout msgLinearLayout02 = null;
  private TextView msgText01 = null;//
  private ImageView nextImage = null;
  private ImageView previousImage = null;//ǰ��ͼ��
  private TextView titleText = null;//����

  //���ؼ���Ӧ����
  public void onClick(View paramView)
  {
	  //���ݵ�ǰ����ҳ������ת
	  Log.i("HelpActivity", "onClick");
	    switch (paramView.getId())
	    {
	    case R.id.ImageView01: //�رհ�ť
	    	finish();
	    case R.id.ImageView02: //����
	    	if (this.currPage == 2)
	        {
	    		this.previousImage.setVisibility(View.GONE);
	    		this.titleText.setText(getString(R.string.help_title_1));
	          	this.msgText01.setText(getString(R.string.help_text_1));
	          	this.msgLinearLayout01.setVisibility(View.VISIBLE);
	          	this.msgLinearLayout02.setVisibility(View.GONE);
	        }
	        if (this.currPage == 3)
	        {
	        	this.previousImage.setVisibility(View.VISIBLE);
	        	this.nextImage.setVisibility(View.VISIBLE);
	        	this.titleText.setText(getString(R.string.help_title_3));
	        	this.msgLinearLayout01.setVisibility(View.GONE);
	          	this.msgLinearLayout02.setVisibility(View.VISIBLE);
	        }
	        this.currPage -= 1;
	        break;
	    case R.id.ImageView03: //ǰ��
	    	Log.i("HelpActivity", this.currPage + "");
	        if (this.currPage == 1)
	        {
	        	this.previousImage.setVisibility(View.VISIBLE);
	        	this.titleText.setText(getString(R.string.help_title_3));
	        	this.msgLinearLayout01.setVisibility(View.GONE);
	        	this.msgLinearLayout02.setVisibility(View.VISIBLE);
	        }
	        if (this.currPage == 2)
	        {
	          this.nextImage.setVisibility(View.GONE);
	          this.titleText.setText(getString(R.string.help_title_4));
	          this.msgText01.setText(getString(R.string.help_text_4));
	          this.msgLinearLayout01.setVisibility(View.VISIBLE);
	          this.msgLinearLayout02.setVisibility(View.GONE);
	        }
	        this.currPage += 1;
	        Toast.makeText(this, "currPage:"+this.currPage, Toast.LENGTH_LONG).show();
	        break;
    	}
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_help);
    this.titleText = ((TextView)findViewById(R.id.TextView02));//������ʾ
    this.msgLinearLayout01 = ((LinearLayout)findViewById(R.id.LinearLayout01));
    this.msgText01 = ((TextView)findViewById(R.id.TextView01));
    this.msgLinearLayout02 = ((LinearLayout)findViewById(R.id.LinearLayout02));
    this.backImage = ((ImageView)findViewById(R.id.ImageView01));
    this.backImage.setOnClickListener(this);
    this.previousImage = ((ImageView)findViewById(R.id.ImageView02));
    this.previousImage.setOnClickListener(this);
    this.nextImage = ((ImageView)findViewById(R.id.ImageView03));
    this.nextImage.setOnClickListener(this);
    this.previousImage.setVisibility(View.GONE);//���˰�ť���ɼ�
    this.titleText.setText(getString(R.string.help_title_1));
    this.msgText01.setText(getString(R.string.help_text_1));
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
	  return super.onKeyDown(paramInt, paramKeyEvent);
  }
}