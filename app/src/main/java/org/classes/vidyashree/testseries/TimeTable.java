package org.classes.vidyashree.testseries;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
public class TimeTable extends Activity {

    String id;
    String no1;
    String no2;
    String no3;
    private ProgressDialog loading;
    String myJSON;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();
        no2 = getIntent().getExtras().getString("NO");
        //no2=Integer.toString(1);
        Log.e("Value",no2);
        //isInternetOn();
        getData();
    }

    private void getData() {
        if (no2.equals(""))
        {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config9.DATA_URL+no2;
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
                        Toast.makeText(TimeTable.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config9.JSON_ARRAY);
            for(int i=0;i<result.length();i++){

                JSONObject collegeData = result.getJSONObject(i);
                id = collegeData.getString(Config6.KEY_STAMT);
                HashMap<String,String> persons = new HashMap<String,String>();
                persons.put(Config9.KEY_STAMT,id);
                Log.e("ID",id);
                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    TimeTable.this, personList, R.layout.list_item4,
                    new String[]{Config9.KEY_STAMT},
                    new int[]{R.id.address1}
            );
            list.setAdapter(adapter);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name4=((TextView)view.findViewById(R.id.address1)).getText().toString();
                Log.e("Subcategory", name4);
                Intent i1=new Intent(getApplicationContext(),Home.class);
                i1.putExtra("listItem", name4);
                startActivity(i1);
            }
        });
    }
}
