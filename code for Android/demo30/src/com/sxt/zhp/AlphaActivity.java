package com.sxt.zhp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 透明动画（淡入淡出动画）
 *
 */
public class AlphaActivity extends Activity{
	private ImageView iv;
	private Animation anim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
		
		iv = (ImageView) findViewById(R.id.imageView1);
		
		anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		
		findViewById(R.id.btnStart).setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				fadeout();
			}
		});
		
		
		
		findViewById(R.id.btnStop).setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				stop();
			}
		});
	}
	
	private void fadeout(){
		iv.startAnimation(anim);
	}
	
	private void stop(){
		iv.clearAnimation();
	}
}
