package com.wen.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {
	
	private static MyApplication myAppInstance = null;
	private List<Activity> allActivity = new ArrayList<Activity>();
	
	public MyApplication(){}
	
	public static MyApplication getInstance(){
		if(myAppInstance == null){
			myAppInstance = new MyApplication();
		}
		return myAppInstance;
	}
	
	public void addActivity(Activity activity){
		allActivity.add(activity);
	}
	
	public boolean removeActivity(Activity activity){
		if(activity == null){
			return false;
		}
		return allActivity.remove(activity);
	}

	public void exitApp(){
		for(int i=allActivity.size()-1; i>=0; i--){
			allActivity.get(i).finish();
		}
		System.exit(0);
	}
}
