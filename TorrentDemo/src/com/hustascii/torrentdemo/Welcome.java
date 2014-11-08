/*
 * Make the Welcome Animation Activity
 */
package com.hustascii.torrentdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends Activity{
	private ImageView iv;
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		iv=(ImageView)findViewById(R.id.start);
		tv=(TextView)findViewById(R.id.welcome);
		new AnimationUtils();
		Animation anim_pic=AnimationUtils.loadAnimation(this,R.anim.anim_set_pic);
		new AnimationUtils();
		Animation anim_str=AnimationUtils.loadAnimation(this, R.anim.anim_set_str);
		anim_str.setStartOffset(1000);
		iv.setAnimation(anim_pic);
		tv.setAnimation(anim_str);
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO MainActivity
				Intent start = new Intent(Welcome.this,MainActivity.class);
				startActivity(start);
				Welcome.this.finish();
			}
			
		}, 3200);
	}
}
