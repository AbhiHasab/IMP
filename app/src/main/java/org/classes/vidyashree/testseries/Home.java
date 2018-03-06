package org.classes.vidyashree.testseries;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ListView;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by E 4310 on 10/22/2016.
 */
public class Home extends Activity {

    String id;
    String no1;
    String no2;
    String no3;
    private ProgressDialog loading;
    String myJSON;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    String listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home);
        listItem = getIntent().getStringExtra("listItem");
        Log.e("List",listItem);
        WebView mWebView=new WebView(Home.this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=http://vidyashreetestseries.in/secureLogin/VTS/ImpAdmin/"+listItem);
        setContentView(mWebView);
        Log.e("Stringddddd1", String.valueOf(mWebView));
    }
}