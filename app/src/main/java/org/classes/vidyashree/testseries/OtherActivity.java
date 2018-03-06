package org.classes.vidyashree.testseries;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Asus on 04/05/2016.
 */
public class OtherActivity extends Activity {

    Button profile,attendance,subject,testmark,fees,remark,timetable, etimetable;
    String value;
    private TextView textViewResult,gender,dob,email,pcon,cnumber,scoll;
    private ProgressDialog loading;
    String passedArg;
    int a;
    String id="";
    String name="";
    String address="";
    String vc = "";
    String email1 = "";
    String cno1 = "";
    String pno1 = "";
    String mno1 = "";
    String schname = "";
    String no = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_other);

        profile=(Button)findViewById(R.id.profile);
        attendance=(Button)findViewById(R.id.attendance);
        subject=(Button)findViewById(R.id.subject);
        testmark=(Button)findViewById(R.id.testmark);
        fees=(Button)findViewById(R.id.fees);
        timetable=(Button)findViewById(R.id.TimeTable);
        etimetable=(Button)findViewById(R.id.ExamTimeTable);
        remark=(Button)findViewById(R.id.remark);
        value= getIntent().getExtras().getString("USERNAME");

        getData();

        Log.e("Name",value);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Profile.class);
                i.putExtra("NO", id);
                i.putExtra("Name", no);
                i.putExtra("Gender", name);
                i.putExtra("DOB", address);
                i.putExtra("Email", vc);
                i.putExtra("SContact", email1);
                i.putExtra("PContact", cno1);
                i.putExtra("School", pno1);
                i.putExtra("School1", mno1);
                i.putExtra("School2", schname);
                startActivity(i);
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(getApplicationContext(),Attendance.class);
                j.putExtra("NO", id);
                startActivity(j);
            }
        });

        subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(getApplicationContext(),Subjects.class);
                k.putExtra("NO", id);
                startActivity(k);
            }
        });

        testmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),TestMarks.class);
                i.putExtra("NO", id);
                startActivity(i);
            }
        });

        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Fee.class);
                i.putExtra("NO", id);
                startActivity(i);
            }
        });
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),TimeTable.class);
                i.putExtra("NO", no);
                startActivity(i);
            }
        });
        etimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ExamTimeTable.class);
                i.putExtra("NO", id);
                startActivity(i);
            }
        });

        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Remark.class);
                i.putExtra("NO", id);
                startActivity(i);
            }
        });
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
                        Toast.makeText(OtherActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            id = collegeData.getString(Config.KEY_ID);
            no = collegeData.getString(Config.KEY_RNO);
            name = collegeData.getString(Config.KEY_NAME);
            address = collegeData.getString(Config.KEY_ADDRESS);
            vc = collegeData.getString(Config.KEY_VC);
            email1 = collegeData.getString(Config.KEY_email);
            cno1 = collegeData.getString(Config.KEY_cno);
            pno1 = collegeData.getString(Config.KEY_pno);
            mno1 = collegeData.getString(Config.KEY_mno);
            schname = collegeData.getString(Config.KEY_schno);

            Log.e("ID",id);
            Log.e("SNO",no);
            Log.e("PName",name);
            Log.e("PAddress",address);
            Log.e("PAddress",vc);
            Log.e("PEmail",email1);
            Log.e("Scontect",cno1);
            Log.e("Pcontect",pno1);
            Log.e("Mcontect",mno1);
            Log.e("School Name",schname);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}