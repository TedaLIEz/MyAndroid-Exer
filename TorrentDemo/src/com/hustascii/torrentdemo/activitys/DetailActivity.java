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

import org.jsoup.nodes.Element;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hustascii.torrentdemo.R;
import com.hustascii.torrentdemo.tools.DownloadFile;
import com.hustascii.torrentdemo.tools.Spider;

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
	NotificationManager manager;

	private final int notification_ID = 0;

	// private Button btnpath;
	// private String initpath="/torrent/";

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
			pd.setCanceledOnTouchOutside(false);
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
				map = sd.map(url); // getMap
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
			if (result) {
				pd.dismiss();
				lv.setAdapter(na);
			} else {
				pd.dismiss();
				new AlertDialog.Builder(DetailActivity.this)
						.setMessage("Wops!貌似网络不给力")
						.setPositiveButton("返回", null).show();
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
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// btnpath = (Button)findViewById(R.id.btn1);
		new TestAsyncTask().execute("");
		// sendNotification();
		download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						sendNotification();
						df = new DownloadFile();
						final Boolean res = df.downloadFile(download_url); // Download
																			// torrent
																			// in
																			// thread

						handler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								// notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
								manager.cancel(notification_ID);
								if (res)
									new AlertDialog.Builder(DetailActivity.this)
											.setMessage(
													"下载成功!默认路径为"
															+ Environment
																	.getExternalStorageDirectory()
															+ "/torrent/")
											.setPositiveButton(
													"打开",
													new DialogInterface.OnClickListener() {

														public void onClick(
																DialogInterface dialog,
																int which) {
															// TODO
															// Auto-generated
															// method stub
															Intent open = new Intent();
															open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
															open.setAction(Intent.ACTION_VIEW);

															MimeTypeMap mtm = MimeTypeMap
																	.getSingleton();
															try {

																open.setDataAndType(
																		Uri.fromFile(df
																				.getFile()),
																		mtm.getMimeTypeFromExtension(MimeTypeMap
																				.getFileExtensionFromUrl(download_url)));
																startActivity(open);
															} catch (Exception e) {
																// TODO
																// Auto-generated
																// catch block

																Toast.makeText(
																		DetailActivity.this,
																		"不支持的文件格式",
																		Toast.LENGTH_LONG)
																		.show();
															}

														}
													})
											.setNegativeButton("返回", null)
											.show();

								else
									Toast.makeText(DetailActivity.this,
											"下载失败!", Toast.LENGTH_LONG).show();
							}
						});

					}
				}).start();

			}
		});

		/*
		 * btnpath.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent path=new
		 * Intent(DetailActivity.this,FileExplorerActivity.class);
		 * startActivity(path); } });
		 */
	}

	private void sendNotification() {
		Builder builder = new Notification.Builder(DetailActivity.this);
		builder.setSmallIcon(R.drawable.icon_download);
		builder.setContentText("下载中...");
		builder.setTicker("开始下载");
		builder.setWhen(System.currentTimeMillis());
		builder.setContentTitle("通知栏");
		builder.setDefaults(Notification.DEFAULT_VIBRATE);
		Notification notification = builder.build();
		manager.notify(notification_ID, notification);
	}

	class NameAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private TextView filetext;

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
			// ViewHolder holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.filelist, null);
			filetext = (TextView) convertView.findViewById(R.id.filelist);
			Element e = filename.get(pos);
			filetext.setText(e.text());
			return convertView;
		}

	}

	/*
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); Intent path=new Intent();
	 * initpath=path.getStringExtra("path");
	 * 
	 * }
	 */
}
