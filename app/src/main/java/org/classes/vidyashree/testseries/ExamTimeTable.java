package org.classes.vidyashree.testseries;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by E 4310 on 10/15/2016.
 */
public class ExamTimeTable extends AppCompatActivity {

    String no,id,no1,no2,no3,no4,no5;
    private ProgressDialog loading;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    WebView webview;
    int num1, num2, num3, num4, num5;
    JSONArray result;
    JSONObject collegeData;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examtimetable);
        webview = (WebView) findViewById(R.id.webView1);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();
        no = getIntent().getExtras().getString("NO");
       // getData();
       /* webview.addJavascriptInterface(new WebAppInterface(), "Android");
        webview.getSettings().setJavaScriptEnabled(true);*/
        String url="http://vidyashreetestseries.in/secureLogin/VTS/ImpAdmin/graphReport.php?studId="+no;
        Log.e("Url1",url);
        webview.loadUrl(url);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }

    /*private void getData() {
        if (no.equals(""))
        {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config5.DATA_URL+no;
        Log.e("data",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ExamTimeTable.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        try
        {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray(Config5.JSON_ARRAY);
            for(int i=0;i<result.length();i++){

                collegeData = result.getJSONObject(i);
                id = collegeData.getString(Config5.KEY_STAMT);
                no1 = collegeData.getString(Config5.KEY_STAMT1);
                no2 = collegeData.getString(Config5.KEY_STAMT3);
                no3 = collegeData.getString(Config5.KEY_STAMT4);
                no1=no1.concat( collegeData.getString(Config5.KEY_STAMT1));

                Log.e("ID",id);
                Log.e("NO",no1);
                Log.e("No1",no2);
                Log.e("No2",no3);
                ArrayList<Integer> third = new ArrayList<Integer>();
                third.add(Log.e("Showsss", String.valueOf(Integer.valueOf(no2))));
            }
        }

            catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private class WebAppInterface {

        String no6;
        @JavascriptInterface
        public int getNum1() throws JSONException {
            Log.e("njknkjn",no2);
            return Integer.parseInt(no2);
        }
    }*/
}