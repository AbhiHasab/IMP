package org.classes.vidyashree.testseries;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
public class Subjects extends Activity {

    String no,id,no1,no2,no3;
    private ProgressDialog loading;
    String myJSON;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjects);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();
        no = getIntent().getExtras().getString("NO");
        Log.e("IDSSSSSSSS",no);
        //no=Integer.toString(1);
        //isInternetOn();
        getData();
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }

    /*private boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            getData();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " Sorry! You are not Connect with Internet", Toast.LENGTH_LONG).show();

            return false;
        }
        return false;
    }*/

    private void getData() {
        if (no.equals(""))
        {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config2.DATA_URL+no;
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
                        Toast.makeText(Subjects.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config2.JSON_ARRAY);
            for(int i=0;i<result.length();i++){

                JSONObject collegeData = result.getJSONObject(i);
                id = collegeData.getString(Config2.KEY_NAME);
                no1 = collegeData.getString(Config2.KEY_ADDRESS);
                no2 = collegeData.getString(Config2.KEY_TIME);
                no3 = collegeData.getString(Config2.KEY_STAMT);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(Config2.KEY_NAME,no3);
                persons.put(Config2.KEY_ADDRESS,no1);
                persons.put(Config2.KEY_TIME,no2);
                persons.put(Config2.KEY_STAMT,id);

                Log.e("ID",id);
                Log.e("SNO",no1);
                Log.e("SNO",no2);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Subjects.this, personList, R.layout.list_item1,
                    new String[]{Config2.KEY_NAME,Config2.KEY_ADDRESS,Config2.KEY_TIME,Config2.KEY_STAMT},
                    new int[]{R.id.name, R.id.address,R.id.address1,R.id.id}
            );
            list.setAdapter(adapter);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}