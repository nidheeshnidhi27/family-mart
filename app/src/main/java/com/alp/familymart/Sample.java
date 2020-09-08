package com.alp.familymart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Sample extends AppCompatActivity {

    ImageView splash;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2420;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.sample);

        splash = findViewById(R.id.splashscreen);

        splash.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotation) );

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               Intent mainIntent = new Intent(Sample.this,WebviewActivity.class);
                Sample.this.startActivity(mainIntent);
                Sample.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
