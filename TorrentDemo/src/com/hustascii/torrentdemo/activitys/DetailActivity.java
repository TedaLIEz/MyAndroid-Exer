/*
 * Print the FileName in the torrent which user has chosen
 */
package com.hustascii.torrentdemo.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hustascii.torrentdemo.R;
import com.hustascii.torrentdemo.tools.DownloadFile;
import com.hustascii.torrentdemo.tools.Spider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.nodes.*;

public class DetailActivity extends Activity {
	private ListView lv;
	private ImageButton download;
	private Spider sd;
	private ProgressDialog pd;
	private List<Element> filename;
	public String download_url;
	private String url;
	private Map<String, Map<String, List<Element>>> map;
	private NameAdapter na;
	private static Handler handler = new Handler();
	public DownloadFile df;
	/*
	 * DownLoad Torrent
	 */
	
	/*
	 * AsyncTask in getting torrents
	 */
	private class TestAsyncTask extends AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(DetailActivity.this);
			pd.setMessage("听说用力摇手机可以提高加载速度哦~");
			pd.show();
		}

		@Override
		protected Boolean doInBackground(String... key) {
			Intent intent = getIntent();
			url = intent.getStringExtra("url");

			filename = new ArrayList<Element>();
			sd = new Spider();

			map = new HashMap<String, Map<String, List<Element>>>();
			try {
				map = sd.map(url);                       //getMap
				Map<String, List<Element>> map_tmp = map.get(url);
				Set<String> mapSet = map_tmp.keySet();
				Iterator<String> i = mapSet.iterator();
				download_url = i.next();
				
				filename = map.get(url).get(download_url); 
				
				na = new NameAdapter(DetailActivity.this);
				return true;
			} catch (Exception e) {
				// TODO HttpException
				return false;
			}

			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
			pd.dismiss();
			lv.setAdapter(na);
			}else{
				Toast.makeText(DetailActivity.this, "Wops!貌似网络不给力", Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detailactivity);
		lv = (ListView) findViewById(R.id.detaillist);
		download = (ImageButton) findViewById(R.id.download);

		new TestAsyncTask().execute("");

		download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(DetailActivity.this, "开始下载", Toast.LENGTH_SHORT)
						.show();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						df=new DownloadFile();
						final Boolean res=df.downloadFile(download_url);                  //Download torrent in thread
						handler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (res)
									Toast.makeText(
											DetailActivity.this,
											"下载成功!默认路径为"
													+ Environment
															.getExternalStorageDirectory()
													+ "/MyTorrent/",
											Toast.LENGTH_LONG).show();
								else
									Toast.makeText(DetailActivity.this,
											"下载失败!", Toast.LENGTH_LONG).show();
							}
						});
					}
				}).start();

			}
		});
	}
	class NameAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		public NameAdapter(Context ctx) {
			super();
			mInflater = LayoutInflater.from(ctx);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return filename.size();
		}

		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return filename.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.filelist, null);
			holder.filetext = (TextView) convertView
					.findViewById(R.id.filelist);
			Element e = filename.get(pos);
			holder.filetext.setText(e.text());
			return convertView;
		}
		
	}
	public final class ViewHolder {
		public TextView filetext;
		
	}
}
