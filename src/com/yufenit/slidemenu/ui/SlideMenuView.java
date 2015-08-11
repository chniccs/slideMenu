package com.yufenit.slidemenu.ui;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yufenit.slidemenu.R;

public class SlideMenuView extends ViewGroup {

	private View menuContainer;
	private View contentsContainer;
	private int startX;
	private static BaseAdapter adapter;
	private static List<Object> list;
	
	private static onSlideMenuViewInit listener;

	public SlideMenuView(Context context) {
		super(context);

		initView(context);
	}

	public SlideMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化view的方法
	 * 
	 * @param context
	 */
	private void initView(Context context) {

		menuContainer = View.inflate(context, R.layout.menu, null);
		contentsContainer = View.inflate(context, R.layout.content, null);
		menuLl = (LinearLayout) menuContainer.findViewById(R.id.ll);
		contentsLl = (LinearLayout) contentsContainer.findViewById(R.id.content_ll);
		
		initListView(menuContainer);

		addView(contentsContainer);
		addView(menuContainer);
	}
	/**
	 * 实现设置菜单列表的方法
	 * @param menuContainer2
	 */
	private void initListView(View menuContainer2) {
		
		mLv = (ListView) menuContainer.findViewById(R.id.menu_lv);
		
		System.out.println("initListView执行了");
		if(adapter!=null&& list!=null){
			
			mLv.setAdapter(adapter);
			//设置listView的点击事件
			mLv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					listener.onItemClick(position);
					
				}
				
			});
		}
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 设置菜单的长宽
		menuContainer.measure(160, heightMeasureSpec);

		// 设置内容的长宽
		contentsContainer.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		contentsContainer.layout(l, t, r, b);

		menuContainer.layout(-menuContainer.getMeasuredWidth(), 0, 0,
				menuContainer.getMeasuredHeight());

	}

	/**
	 * 实现触摸事件
	 */

	int pos = 0;
	private LinearLayout menuLl;
	private LinearLayout contentsLl;
	private ListView mLv;

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			startX = (int) event.getRawX();

			break;
		case MotionEvent.ACTION_MOVE:

			int newX = (int) event.getRawX();

			int dX = newX - startX;

			pos += dX;
			
			if (pos >= menuContainer.getMeasuredWidth()) {
				pos = menuContainer.getMeasuredWidth();
			}

			if (pos <= 0) {
				pos = 0;
			}
			scrollTo(-pos, 0);
			
			float i=((float)pos)/160f;
			
			//设置菜单内的缩放
			menuLl.setScaleX(i);
			
			menuLl.setScaleY(i);
			
			//设置内容页的缩放
			contentsLl.setScaleX(1.0f-i/6);
			contentsLl.setScaleY(1.0f-i/6);

			startX = (int) event.getRawX();

			break;

		case MotionEvent.ACTION_UP:

			if (pos >= (menuContainer.getMeasuredWidth() / 2)) {
				//让菜单展示
				scrollTo(-160, 0);
				//让菜单大小设置为实际大小
				menuLl.setScaleX(1.0f);
				menuLl.setScaleY(1.0f);
				
			} else {
				//让菜单隐藏
				scrollTo(0, 0);
				//让内容复位
				contentsContainer.layout(0, 0,
						contentsContainer.getMeasuredWidth(),
						contentsContainer.getMeasuredHeight());
				//让内容大小恢复
				contentsLl.setScaleX(1.0f);
				contentsLl.setScaleY(1.0f);
			}

			break;

		}

		return true;
	}
	/**
	 * 设置展示的数据及适配器的方法
	 * @param adapter 适配器，不能为空，与list结合展示数据
	 * @param list 集合存储数据，不能为空
	 */
	//TODO  设置回调
	public static void setAdapter_listView(BaseAdapter madapter,List<Object> mlist,onSlideMenuViewInit mlistener){
		System.out.println("setAdapter_listView执行了");
		adapter=madapter;
		list=mlist;
		listener=mlistener;
	}
	
	/**
	 * 回调方法的接口
	 * @author Administrator
	 * 
	 *
	 */
	public interface onSlideMenuViewInit{
		/**
		 *  条目被点击的回调方法
		 * @param position 当前被点击的条目的位置
		 */
		void onItemClick(int position);
		
	}

}
