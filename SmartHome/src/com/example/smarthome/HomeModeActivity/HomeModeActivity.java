package com.example.smarthome.HomeModeActivity;
import com.example.smarthome.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class HomeModeActivity extends Activity implements View.OnClickListener{

    private ImageView backImageView;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_mode);
        this.backImageView = ((ImageView)findViewById(R.id.homemodebackImageView));//É¾³ýÉè±¸°´Å¥
		this.backImageView.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_mode, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.homemodebackImageView://
		{
			finish();
			break;
		}
		}
	}
}
    
