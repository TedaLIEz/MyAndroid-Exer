/*
 * @aLIEzTED,ZHIYUAN MA,SICONG SHAO in HUSTASCII
 * This is a simple TorrentSearcher using Spider with JSoup to search and download torrent
 * Have Fun in 11.11!
 */
package com.hustascii.torrentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hustascii.torrentdemo.activitys.ResultActivity;
import com.hustascii.torrentdemo.tools.AboutBox;

public class MainActivity extends ActionBarActivity {
	private EditText ev;
	private Button btn;
	private Button btnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		ev = (EditText) findViewById(R.id.search);
		btn = (Button) findViewById(R.id.btn_search);
		btnAbout = (Button) findViewById(R.id.btnAbout);
		ev.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

		/*
		 * Change the user's 'enter' input to search
		 */
		ev.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					if (ev.getText().toString().isEmpty()) {
						Toast.makeText(MainActivity.this, "«Î ‰»Îπÿº¸◊÷",
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

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(ev.getWindowToken(), 0); // HideSoftInput
				if (ev.getText().toString().isEmpty()) {
					Toast.makeText(MainActivity.this, "«Î ‰»Îπÿº¸◊÷",
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
