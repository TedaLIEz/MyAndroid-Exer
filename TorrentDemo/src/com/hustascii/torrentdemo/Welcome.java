package com.hustascii.torrentdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome extends Activity {
	private ImageView iv_icon;
	private ImageView iv_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		iv_icon = (ImageView) findViewById(R.id.start);
		iv_text = (ImageView) findViewById(R.id.welcome);
		
		// Define Animation
		new AnimationUtils();
		Animation anim_pic = AnimationUtils.loadAnimation(this,
				R.anim.anim_set_pic);
		new AnimationUtils();
		Animation anim_str = AnimationUtils.loadAnimation(this,
				R.anim.anim_set_str);
		anim_pic.setInterpolator(this,android.R.anim.bounce_interpolator);
		anim_str.setStartOffset(2000);
		
		// Set Animation
		iv_icon.setAnimation(anim_pic);
		iv_text.setAnimation(anim_str);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent start = new Intent(Welcome.this, MainActivity.class);
				startActivity(start);
				Welcome.this.finish();
			}

		}, 4500);
	}
}
