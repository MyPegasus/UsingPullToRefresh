package com.example.mypegasus.usingpulltorefresh;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private PullToRefreshListView lv;
	private ArrayAdapter<String> adapter;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (PullToRefreshListView) findViewById(R.id.mylv);

//		String[] data = new String[] {"jikexueyuan", "eoe"};
		List<String> data = new ArrayList<>();
		data.add("jikexueyuan");
		data.add("eoe");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
		lv.setAdapter(adapter);

		/*lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

			}
		});*/

		// 下拉时显示的文本 默认值“下拉以刷新...”
//		lv.setPullLabel("正在加载。。。");
		lv.getLoadingLayoutProxy().setPullLabel("下拉以刷新。。。");
		// 释放时显示的文本  默认值 “放开以刷新...”
//		lv.setReleaseLabel("释放。。。");
		lv.getLoadingLayoutProxy().setReleaseLabel("放开以刷新。。。");
		// 刷新时现实的文本 默认值“正在加载...”
//		lv.setRefreshingLabel("加载成功。。。");
		lv.getLoadingLayoutProxy().setRefreshingLabel("正在加载。。。");
		// 设置最后一次更改时间
//		lv.setLastUpdatedLabel(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()));
		lv.getLoadingLayoutProxy().setLastUpdatedLabel(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()));
		// 设置加载时的图片
		Drawable d = getDrawable(R.drawable.android);
		//lv.setLoadingDrawable(d);
//		lv.setDividerDrawable(d);
		lv.getLoadingLayoutProxy().setLoadingDrawable(d);
		// 设置字体样式，字体好像换不了，加粗，斜体都没问题
		lv.getLoadingLayoutProxy().setTextTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD_ITALIC));
		// 设置模式
		// 1.DISABLED 禁用下拉刷新
		// 2.BOTH 上下拉刷新
		// 3.MANUAL_REFRESH_ONLY
		// 4.PULL_FROM_END 从底部向上拉刷新
		// 5.PULL_FROM_START 从头向下拉刷新
		// 6.PULL_DOWN_TO_REFRESH 从头向下拉刷新 弃用
		// 7.PULL_UP_TO_REFRESH 从底部向上拉刷新 弃用
		lv.setMode(PullToRefreshBase.Mode.BOTH);

		lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void aVoid) {
//						super.onPostExecute(aVoid);
						adapter.addAll("Hello", "大家好");
						lv.onRefreshComplete();
					}
				}.execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
