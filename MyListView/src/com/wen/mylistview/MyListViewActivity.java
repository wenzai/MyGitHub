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
	private List<Map<String, Object>> data = null;		//ListView����������List
	private MyListViewAdapter adapter = null;
	
	//ÿ�μ��ط�ҳ��������
	private int pageSize = 10;
	
	private static final int PAGETYPE = 1;
	
	//���ظ���
	private TextView moreTextView = null;
	
	//���ؽ�����
	private RelativeLayout loadProgressBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_list_view);
		
		//ȡ��ListView����
		listView = (ListView) findViewById(R.id.lv_id);
		
		data = InitValue.initValue(0, 15);	//��ʼ��ʱĬ�ϼ��ص���15����¼
		
		//��ListView�����"���ظ���"
		addPageMore();
		
		//���"���ظ���"һ��Ҫ������Adapter֮ǰ
		adapter = new MyListViewAdapter();
		
		//����ListView��Data֮�������:BaseAdapter
		listView.setAdapter(adapter);
	}
	
	/**
	 * ��ListView����ӡ����ظ��ࡱ
	 */
	private void addPageMore(){
		
		View view = LayoutInflater.from(this).inflate(R.layout.list_page_load, null);
		moreTextView = (TextView) view.findViewById(R.id.more_id);
		loadProgressBar = (RelativeLayout) view.findViewById(R.id.load_id);	//��ǰ״̬ΪView.GONE
		
		moreTextView.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//���ؼ��ظ���
				moreTextView.setVisibility(View.GONE);
				
				//��ʾ���ؽ�����
				loadProgressBar.setVisibility(View.VISIBLE);
				
				//�����������ݵ��߳�
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// ����3000����,���ݼ�����Ҫʱ��
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
		
		//���ݼ������֮�������ӡ����ظ��ࡱ����ʾ
		listView.addFooterView(view);
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {
			case PAGETYPE:				
				adapter.count += pageSize;		//�ı�����������Ŀ
				adapter.notifyDataSetChanged();	//֪ͨ�����������ָı����
				moreTextView.setVisibility(View.VISIBLE);	//�ٴ���ʾ"���ظ���"
				loadProgressBar.setVisibility(View.GONE);	//�ٴ����ء���������
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};
	
	/**
	 * ������һҳ������
	 * @param pageStart	����ǰһ��ҳ����������item��Ŀ
	 * @param pageSize	ÿ�μ��ص�����
	 */
	private void changeListView(int pageStart, int pageSize){
		//����һ����ʱ��List������Ҫ��ӵ�����
		List<Map<String, Object>> tempList = InitValue.initValue(pageStart, pageSize);
		for(Map<String, Object> addValue:tempList){
			this.data.add(addValue);	//�������List���
		}
		tempList.clear();	//���addData����
	}
	
	/**
	 * ����ListView��data������:Adapter
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
		 *��ȡ�������������ListView��ÿһ��
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
 * �������ݸ�ʽ
 * @author wen
 */
class InitValue{
	
	public static int page = 1;
	
	/**
	 * 
	 * @param pageStart	��ʼ��
	 * @param pageSize	ÿҳ��ʾ��Ŀ
	 * @return
	 */
	public static List<Map<String, Object>> initValue(int pageStart, 
			int pageSize){
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0; i<pageSize; i++){
			map = new HashMap<String, Object>();
			map.put("title", "����  " + page);
			map.put("content", "����  " + page);
			page++;
			list.add(map);
		}
		return list;
	}
}