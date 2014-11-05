package com.hustascii.torrentdemo.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;
import android.util.Log;

public class DownloadFile {
	public  Boolean downloadFile(String urlPath) {
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			String dirName = Environment.getExternalStorageDirectory()
					+ "/MyTorrent/";
			File f = new File(dirName);
			if (!f.exists()) {
				f.mkdir();
			}
			String newFilename = urlPath
					.substring(urlPath.lastIndexOf('/') + 1);
			// Log.i("tag", newFilename);
			newFilename = dirName + newFilename;
			File file = new File(newFilename);
			if (file.exists()) {
				file.delete();
			}
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(newFilename);
				byte[] bt = new byte[1024];
				int bytesum = 0;
				int byteread = 0;
				while ((byteread = is.read(bt)) != -1) {
					bytesum += byteread;
					Log.i("tag", String.valueOf(bytesum));
					fos.write(bt, 0, byteread);
				}
				fos.flush();
				fos.close();
				is.close();
				return true;
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}
