package com.yufenit.slidemenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yufenit.slidemenu.ui.SlideMenuView;

public class MainActivity extends Activity {

	private SlideMenuView mSmv;
	private List<Object> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = new ArrayList<Object>();

		for (int i = 1; i < 5; i++) {
			list.add("菜单" + i);
		}
		
		//TODO 此处是通过静态方法先加载数据，在后台通过异步方法实现
		SlideMenuView.setAdapter_listView(new MyAdapter(), list);
		//加载好数据后再创建view并添加到此activity中
		mSmv = new SlideMenuView(getApplicationContext());
		addContentView(mSmv, new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			TextView tv;
			if (convertView == null) {

				tv = new TextView(MainActivity.this);

				convertView = tv;
			} else {
				tv = (TextView) convertView;
			}

			tv.setText(list.get(position).toString());

			return tv;
		}

	}

}
