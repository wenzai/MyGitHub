package com.wen.mylistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyListViewActivity extends Activity {
	
	private static final String TAG = "MyListViewActivity";
	
	private ListView listView = null;
	private List<Map<String, Object>> data = null;		//ListView的总体数据List
	private MyListViewAdapter adapter = null;
	
	//每次加载分页的数据量
	private int pageSize = 10;
	
	private static final int PAGETYPE = 1;
	
	//加载更多
	private TextView moreTextView = null;
	
	//加载进度条
	private RelativeLayout loadProgressBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_list_view);
		
		//取得ListView对象
		listView = (ListView) findViewById(R.id.lv_id);
		
		data = InitValue.initValue(0, 15);	//初始化时默认加载的是15条记录
		
		//在ListView中添加"加载更多"
		addPageMore();
		
		//添加"加载更多"一定要在设置Adapter之前
		adapter = new MyListViewAdapter();
		
		//构建ListView和Data之间的桥梁:BaseAdapter
		listView.setAdapter(adapter);
	}
	
	/**
	 * 在ListView中添加“加载更多”
	 */
	private void addPageMore(){
		
		View view = LayoutInflater.from(this).inflate(R.layout.list_page_load, null);
		moreTextView = (TextView) view.findViewById(R.id.more_id);
		loadProgressBar = (RelativeLayout) view.findViewById(R.id.load_id);	//当前状态为View.GONE
		
		moreTextView.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//隐藏加载更多
				moreTextView.setVisibility(View.GONE);
				
				//显示加载进度条
				loadProgressBar.setVisibility(View.VISIBLE);
				
				//启动加载数据的线程
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// 休眠3000毫秒,数据加载需要时间
						try{
							Thread.sleep(3000);
						}catch(InterruptedException e){
							e.printStackTrace();
						}
						
						changeListView(data.size(), pageSize);
						
						Message msg = handler.obtainMessage(PAGETYPE);
						handler.sendMessage(msg);
					}
				}).start();
			}
		});
		
		//数据加载完成之后，最后添加“加载更多”的显示
		listView.addFooterView(view);
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {
			case PAGETYPE:				
				adapter.count += pageSize;		//改变适配器的数目
				adapter.notifyDataSetChanged();	//通知适配器，发现改变操作
				moreTextView.setVisibility(View.VISIBLE);	//再次显示"加载更多"
				loadProgressBar.setVisibility(View.GONE);	//再次隐藏“进度条”
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};
	
	/**
	 * 加载下一页的数据
	 * @param pageStart	加载前一次页面中所含的item数目
	 * @param pageSize	每次加载的数据
	 */
	private void changeListView(int pageStart, int pageSize){
		//定义一个临时的List对象存放要添加的数据
		List<Map<String, Object>> tempList = InitValue.initValue(pageStart, pageSize);
		for(Map<String, Object> addValue:tempList){
			this.data.add(addValue);	//往最初的List添加
		}
		tempList.clear();	//清空addData数据
	}
	
	/**
	 * 构造ListView与data的桥梁:Adapter
	 * @author wen
	 */
	private class MyListViewAdapter extends BaseAdapter{

		int count = data.size();
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/**
		 *获取适配器所构造的ListView中每一项
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(MyListViewActivity.this)
						.inflate(R.layout.list_page_item, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.title_text);
			TextView content = (TextView) convertView.findViewById(R.id.content_text);
			title.setText(data.get(position).get("title").toString());
			content.setText(data.get(position).get("content").toString());
			Log.v(TAG, "position == " + position);
			return convertView;
		}
		
	}

}



/**
 * 构造数据格式
 * @author wen
 */
class InitValue{
	
	public static int page = 1;
	
	/**
	 * 
	 * @param pageStart	起始数
	 * @param pageSize	每页显示数目
	 * @return
	 */
	public static List<Map<String, Object>> initValue(int pageStart, 
			int pageSize){
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0; i<pageSize; i++){
			map = new HashMap<String, Object>();
			map.put("title", "标题  " + page);
			map.put("content", "内容  " + page);
			page++;
			list.add(map);
		}
		return list;
	}
}