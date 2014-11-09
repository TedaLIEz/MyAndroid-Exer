/*
 * @aLIEzTED,ZHIYUAN MA,SICONG SHAO in HUSTASCII
 * This is a simple TorrentSearcher using Spider with JSoup to search and download torrent
 * Have Fun in 11.11!
 */
package com.hustascii.torrentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hustascii.torrentdemo.activitys.ResultActivity;
import com.hustascii.torrentdemo.tools.AboutBox;

public class MainActivity extends ActionBarActivity {
	private EditText ev;
	private ImageButton btnSearch;
	private ImageButton btnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		ev = (EditText) findViewById(R.id.search);
		btnSearch = (ImageButton) findViewById(R.id.btn_search);
		btnAbout = (ImageButton) findViewById(R.id.btnAbout);
		ev.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

		/*
		 * Change the user's 'enter' input to search
		 */

		ev.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(ev.getWindowToken(), 0); // HideSoftInput
				if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					if (ev.getText().toString().isEmpty()) {
						Toast.makeText(MainActivity.this, "请输入关键字",
								Toast.LENGTH_SHORT).show();
					} else {

						String key = ev.getText().toString();
						Intent intent = new Intent(MainActivity.this,
								ResultActivity.class);
						intent.putExtra("key", key);
						startActivity(intent);
					}
					return true;
				}
				return false;
			}
		});

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(ev.getWindowToken(), 0); // HideSoftInput
				if (ev.getText().toString().isEmpty()) {
					Toast.makeText(MainActivity.this, "请输入关键字",
							Toast.LENGTH_SHORT).show();
				} else {

					String key = ev.getText().toString();
					Intent intent = new Intent(MainActivity.this,
							ResultActivity.class);
					intent.putExtra("key", key);
					startActivity(intent);
				}
			}
		});

		btnAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // TODO Auto-generated method
				AboutBox.Show(MainActivity.this);
			}
		});

	}
	/*
	 * Press Button twice to exit(non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	boolean isExit;
	public void exit(){
		if(!isExit){
			isExit=true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0,2000);		
		}else{
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}
	Handler mHandler =new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			isExit=false;
		}
	};
}
