package com.wen.myandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wen.application.MyApplication;

public class Activity02 extends Activity {
	
	private static final String TAG = "Activity02";
	private boolean isFirstActivityCreate = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreate");
		Log.v(TAG, "savedInstanceState == " + savedInstanceState);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity02_layout);
		
		//跳转事件
		Button btn2 = (Button) findViewById(R.id.btn02);
		btn2.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Activity02.this, MyAndroidActivity.class);
				startActivity(intent);
			}
		});
		
		//退出事件
		Button exitBtn = (Button) findViewById(R.id.exit_app);	
		exitBtn.setOnClickListener(new Button.OnClickListener() {
				
			@Override
			public void onClick(View arg0) {
				MyApplication.getInstance().exitApp();
			}
		});

		//在每个Activity创建的时候把每个Activity添加到List列表中
		if(this.isFirstActivityCreate){
			MyApplication.getInstance().addActivity(this);
			this.isFirstActivityCreate = false;
		}
	}


			
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onStart");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onResume");
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onRestart");
		super.onRestart();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onPause");
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onDestroy");
		MyApplication.getInstance().removeActivity(this);
		super.onDestroy();
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Log.v(TAG, "finish");
		super.finish();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onStop");
		super.onStop();
	}
}
