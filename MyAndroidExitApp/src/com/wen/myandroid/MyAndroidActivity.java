package com.wen.myandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wen.application.MyApplication;

public class MyAndroidActivity extends Activity {
	
	private static final String TAG = "MyAndroidActivity";
	private boolean isFirstActivityCreate = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_android);

		Log.v(TAG, "onCreate");
		Log.v(TAG, "savedInstanceState == " + savedInstanceState);
		
		
		//跳转事件
		Button button01 = (Button) findViewById(R.id.btn01);	
		button01.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MyAndroidActivity.this, Activity02.class);
				startActivity(intent);
			}
		});
		
		//退出Activity事件
		Button exitBtn = (Button) findViewById(R.id.btn_exit);
		exitBtn.setOnClickListener(new Button.OnClickListener(){
			
			@Override			
			public void onClick(View arg0){
				//MyAndroidActivity.this.finish();
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
		super.onDestroy();
		MyApplication.getInstance().removeActivity(this);
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
