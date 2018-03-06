package org.classes.vidyashree.testseries;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by E 4310 on 10/26/2016.
 */
public class Main extends ActionBarActivity {

    String value;
    private ProgressDialog loading;
    String id="";
    String id1="";
    String name="";
    String address="";
    String vc = "";
    String fname = "";
    String email1 = "";
    String cno1 = "";
    String pno1 = "";
    String mno1 = "";
    String schname = "";
    String no = "";
    String boid = "";
    String stdid = "";
    String no1 = "";
    String boid1 = "";
    String mediumid = "",a1,a2,a3,a4,a5;
    GridView gridview;
    String a="kkmk";
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.action);


        gridview = (GridView) findViewById(R.id.gridview);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        List<ItemObject> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(Main.this, allItems);
        gridview.setAdapter(customAdapter);
        value= getIntent().getExtras().getString("USERNAME");
//        Log.e("UserName",value);


        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("testapp", MODE_PRIVATE);
        a3=pref1.getString("username", null);
  //      Log.e("jjjjjj",a3);

        SharedPreferences pref2 = getApplicationContext().getSharedPreferences("token", MODE_WORLD_READABLE);
        a1=pref2.getString("username", null);
    //    Log.e("Tokensddd",a1);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Intent i=new Intent(getApplicationContext(),Profile.class);
                    i.putExtra("NO", fname);
                    i.putExtra("NO1", id1);
                    i.putExtra("Name", no);
                    i.putExtra("Name1", no1);
                    i.putExtra("Gender", name);
                    i.putExtra("DOB", address);
                    i.putExtra("Email", vc);
                    i.putExtra("SContact", email1);
                    i.putExtra("PContact", cno1);
                    i.putExtra("School", pno1);
                    i.putExtra("School1", mno1);
                    i.putExtra("School2", schname);
                    i.putExtra("School3", boid1);
                    Log.e("ID1", String.valueOf(id));
                    Log.e("SNO1",no);
                    Log.e("PName1",name);
                    Log.e("PAddress1",address);
                    Log.e("PAddress1",vc);
                    Log.e("PEmail1",email1);
                    Log.e("Scontect1",cno1);
                    Log.e("Pcontect1",pno1);
                    Log.e("Mcontect1",mno1);
                    Log.e("School Name1",schname);
                    Log.e("SchoolName2",boid);
                    Log.e("SchoolName3",stdid);
                    Log.e("SchoolName4",mediumid);
                    startActivity(i);
                }
                else if (position==1)
                {
                    Intent j=new Intent(getApplicationContext(),Attendance.class);
                    j.putExtra("NO", id1);
                    startActivity(j);
                }
                else if (position==2)
                {
                    Intent k=new Intent(getApplicationContext(),TimeTable.class);
                    k.putExtra("NO", id1);
                    startActivity(k);
                }
                else if (position==3)
                {
                    Intent i=new Intent(getApplicationContext(),TestMarks.class);
                    i.putExtra("NO", id1);
                    startActivity(i);
                }
                else if (position==4)
                {
                    Intent i=new Intent(getApplicationContext(),Subjects.class);
                    i.putExtra("NO", id1);
                    startActivity(i);
                }
                else if (position==5)
                {
                    Intent i=new Intent(getApplicationContext(),Fee.class);
                    i.putExtra("NO", id1);
                    startActivity(i);
                }
                else if (position==6)
                {
                    Intent i=new Intent(getApplicationContext(),ExamTimeTable.class);
                    i.putExtra("NO", id1);
                    startActivity(i);
                }
                else if (position==7)
                {
                    Intent i=new Intent(getApplicationContext(),Remark.class);
                    i.putExtra("NO", id1);
                    Log.e("LoginID",id1);
                    startActivity(i);
                }
            }
        });
        isInternetOn();
    }


    private boolean isInternetOn() {
        ConnectivityManager connec = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            getData();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {
            gridview.setEnabled(false);
            Toast.makeText(this, " Sorry! You are not Connect with Internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    private void getData() {
        if (value.equals(""))
        {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL+value;
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
                        Toast.makeText(Main.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response) {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            id1 = collegeData.getString(Config.KEY_SID);
            fname = collegeData.getString(Config.KEY_ID);
            no = collegeData.getString(Config.KEY_RNO);
            no1 = collegeData.getString(Config.KEY_RNO1);
            name = collegeData.getString(Config.KEY_NAME);
            address = collegeData.getString(Config.KEY_ADDRESS);
            vc = collegeData.getString(Config.KEY_VC);
            email1 = collegeData.getString(Config.KEY_email);
            cno1 = collegeData.getString(Config.KEY_cno);
            pno1 = collegeData.getString(Config.KEY_pno);
            mno1 = collegeData.getString(Config.KEY_mno);
            schname = collegeData.getString(Config.KEY_schno);
            boid = collegeData.getString(Config.KEY_schno1);
            boid1 = collegeData.getString(Config.JSON_ARRAY1);
            stdid = collegeData.getString(Config.KEY_schno2);
            mediumid = collegeData.getString(Config.KEY_schno3);

            SharedPreferences pref = getSharedPreferences("SharID", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userid", id1);
            Log.e("UId",id1);
            editor.commit();

            Log.e("abc",id1);
            Log.e("ID",fname);
            Log.e("SNO",no);
            Log.e("SNO1",no1);
            Log.e("PName",name);
            Log.e("PAddress",address);
            Log.e("PAddress",vc);
            Log.e("PEmail",email1);
            Log.e("Scontect",cno1);
            Log.e("Pcontect",pno1);
            Log.e("Mcontect",mno1);
            Log.e("School Name",schname);
            Log.e("School1",boid);
            Log.e("School2",stdid);
            Log.e("School3",mediumid);
            Log.e("School4",boid1);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private List<ItemObject> getAllItemObject(){
        ItemObject itemObject = null;
        List<ItemObject> items = new ArrayList<>();
        items.add(new ItemObject("Profile", "one"));
        items.add(new ItemObject("Question Paper Demo", "two"));
        items.add(new ItemObject("Question and Ansher", "three"));
        items.add(new ItemObject("Test Marks", "four"));
        items.add(new ItemObject("Fees", "five"));
        items.add(new ItemObject("Time Table", "six"));
        items.add(new ItemObject("Reports", "seven"));
        items.add(new ItemObject("Questions/Ansher", "eight"));
        return items;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_notification)
        {
            Intent i1=new Intent(getApplicationContext(),Notification.class);
            startActivity(i1);
        }
        if (id == R.id.action_settings) {
            SharedPreferences pref=getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
            pref.edit().remove("username").commit();
            pref.edit().remove("Password").commit();
            SharedPreferences pref1=getApplicationContext().getSharedPreferences("SharID",MODE_PRIVATE);
            pref.edit().remove("userid").commit();
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}