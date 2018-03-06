package org.classes.vidyashree.testseries;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by swanand on 2/15/2016.
 */
public class SplashActivity extends Activity {
  ImageView imageView;
    SharedPreferences prefs;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences("testapp", MODE_PRIVATE);
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");
        Log.e("userName", username);
        Log.e("Password", password);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if(username != null && !username.isEmpty())
                {
                    Intent in = new Intent(SplashActivity.this,Main.class);
                    in.putExtra("USERNAME", username);
                    finish();
                    startActivity(in);
                }
                else
                {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }
        }, 3500);
    }
}