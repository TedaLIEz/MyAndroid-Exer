/*
 * To list the torrent with the keyword which user put
 */
package com.hustascii.torrentdemo.activitys;

import java.util.ArrayList;

import com.hustascii.torrentdemo.R;
import com.hustascii.torrentdemo.beans.Result;
import com.hustascii.torrentdemo.tools.Spider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ResultActivity extends Activity {
	private ListView resultlv;
	private ArrayList<Result> resultal;

	private ProgressDialog pd;
	Spider sd = new Spider();

	private class TestAsyncTask extends AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(ResultActivity.this);
			pd.setMessage("获取数据中...");
			pd.show();
		}

		@Override
		protected Boolean doInBackground(String... key) {
			resultal = new ArrayList<Result>();
			try {
				resultal = sd.collect(key[0]);

				return true;
			} catch (Exception e) {
				// TODO TimeoutException
				return false;

			}

		}

		@Override
		protected void onPostExecute(Boolean result) {
			pd.dismiss();

			Myadapter name_adapter = new Myadapter(ResultActivity.this);
			if (result) {
				if (resultal.isEmpty()) {
					new AlertDialog.Builder(ResultActivity.this)
							.setMessage("貌似没有答案")
							.setPositiveButton("返回", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent back = new Intent(
											ResultActivity.this,
											com.hustascii.torrentdemo.MainActivity.class);
									startActivity(back);
								}
							}).show();
				} else {
					resultlv.setAdapter(name_adapter);
					resultlv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int pos, long arg3) {
							Intent url = new Intent(ResultActivity.this,
									DetailActivity.class);
							url.putExtra("url", resultal.get(pos).getUrl());
							startActivity(url);
						}
					});
				}

			} else {
				new AlertDialog.Builder(ResultActivity.this)
						.setMessage("Wops!貌似网络不给力")
						.setPositiveButton("返回", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent back = new Intent(
										ResultActivity.this,
										com.hustascii.torrentdemo.MainActivity.class);
								startActivity(back);
							}
						}).show();
			}

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resultactivity);

		resultlv = (ListView) findViewById(R.id.resultlist);
		Intent intent = getIntent();
		String key = intent.getStringExtra("key");
		new TestAsyncTask().execute(key);
	}

	class Myadapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public Myadapter(Context ctx) {
			super();
			mInflater = LayoutInflater.from(ctx);
		}

		@Override
		public int getCount() {
			return resultal.size();

		}

		@Override
		public Object getItem(int pos) {
			return resultal.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitem, null);
			holder.namelist = (TextView) convertView
					.findViewById(R.id.namelist);
			holder.sizelist = (TextView) convertView
					.findViewById(R.id.sizelist);
			Result r = resultal.get(pos);
			holder.namelist.setText(r.getName());
			holder.sizelist.setText(r.getSize());
			return convertView;
		}

	}

	public final class ViewHolder {
		public TextView namelist;
		public TextView sizelist;
	}

}
