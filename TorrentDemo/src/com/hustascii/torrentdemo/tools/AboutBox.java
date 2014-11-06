/*
 * Make the AboutBox
 */
package com.hustascii.torrentdemo.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutBox {
	static String VersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO: handle exception
			return "Unknown";
		}

	}
	public static void Show(Activity callingActivity){
		SpannableString aboutText = new SpannableString("Version"+VersionName(callingActivity)+"\n\n"+callingActivity.getString(com.hustascii.torrentdemo.R.string.about));
		View about;
		TextView tvAbout;
		try {
			LayoutInflater inflater= callingActivity.getLayoutInflater();
			about = inflater.inflate(com.hustascii.torrentdemo.R.layout.aboutbox, (ViewGroup)callingActivity.findViewById(com.hustascii.torrentdemo.R.id.aboutView));
			tvAbout=(TextView)about.findViewById(com.hustascii.torrentdemo.R.id.aboutText);
		} catch (InflateException e) {
			// TODO: handle exception
			about=tvAbout=new TextView(callingActivity);
		}
		tvAbout.setText(aboutText);
		Linkify.addLinks(tvAbout, Linkify.ALL);
		new AlertDialog.Builder(callingActivity)
		.setTitle("About"+callingActivity.getString(com.hustascii.torrentdemo.R.string.app_name))
		.setCancelable(true)
		.setPositiveButton("OK",null)
		.setView(about)
		.show();
	}
}
