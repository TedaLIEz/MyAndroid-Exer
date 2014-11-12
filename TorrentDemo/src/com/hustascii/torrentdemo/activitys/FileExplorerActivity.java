package com.hustascii.torrentdemo.activitys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hustascii.torrentdemo.R;

public class FileExplorerActivity extends Activity {
	private ListView lvfiles;
	private Button btn;

	File currentParent;
	File[] currentFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fileexplorer);

		lvfiles = (ListView) findViewById(R.id.pathlist);
		btn = (Button) findViewById(R.id.btnfile);

		File root = new File("/mnt/sdcard/");
		if (root.exists()) {
			currentParent = root;
			currentFiles = root.listFiles();
			inflateListView(currentFiles);
		}

		lvfiles.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				File[] tem = currentFiles[pos].listFiles();
				if (tem == null || tem.length == 0) {
					Toast.makeText(FileExplorerActivity.this, "当前路径不可访问",
							Toast.LENGTH_LONG).show();
				} else {
					currentParent = currentFiles[pos];
					currentFiles = tem;
					inflateListView(currentFiles);
				}
			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					String path = currentParent.getCanonicalPath();
					Intent filepath = new Intent(FileExplorerActivity.this,
							DetailActivity.class);
					filepath.putExtra("path", path);
					startActivity(filepath);
					FileExplorerActivity.this.finish();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Toast.makeText(FileExplorerActivity.this, "出问题啦",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	private void inflateListView(File[] files) {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			if (files[i].isDirectory()) {
				listItem.put("icon", R.drawable.folder);
				listItem.put("filename", files[i].getName());
			}

			listItems.add(listItem);
		}
		SimpleAdapter adapter = new SimpleAdapter(FileExplorerActivity.this,
				listItems, R.layout.list_file, new String[] { "filename",
						"icon" }, new int[] { R.id.file_name, R.id.fileicon });
		lvfiles.setAdapter(adapter);

	}
}
