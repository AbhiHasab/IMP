package org.classes.vidyashree.testseries;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    private EditText editTextUserName;
    private EditText editTextPassword;
    Button sub;
    CheckBox mCbShowPwd;
    public static final String USER_NAME = "USERNAME";
    String username;
    String password;
    String token,a1;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_login);
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("token", MODE_WORLD_READABLE);
        a1=pref1.getString("username", null);
//        Log.e("Tokensddd",a1);

        editTextUserName = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        sub= (Button) findViewById(R.id.btn_login);
        mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);

        editTextUserName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on Enter key press
                    editTextUserName.clearFocus();
                    editTextPassword.requestFocus();
                    return true;
                }
                return false;
            }
        });
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on Enter key press
                    // check for username - password correctness here
                    return true;
                }
                return false;
            }
        });

        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (!isChecked) {
                    // show password
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        /*mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (!isChecked) {
                    // show password
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        isInternetOn();*//*mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (!isChecked) {
                    // show password
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        isInternetOn();*/
        isInternetOn();
    }

    private boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editTextUserName.getText().toString().equals("") || editTextPassword.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter User Name and Password",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //wrong password
                        username = editTextUserName.getText().toString();
                        password = editTextPassword.getText().toString();
                        Log.e("Name", username);
                        Log.e("Password", password);
                        login(username, password);

                    }
                }
            });
            SharedPreferences pref=getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("username", username);
            editor.putString("Password", password);
            editor.commit();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " Sorry! You are not Connect with Internet", Toast.LENGTH_LONG).show();
            sub.setEnabled(false);
            return false;
        }
        return false;
    }

    private void login(final String username, final String password) {

        class LoginAsync extends AsyncTask<String, Void, String>
        {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://vidyashreetestseries.in/software/orderForm/testSeries17_05/VTS/impAdminWebservice/Login.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                }
                    catch (ClientProtocolException e)
                {
                    e.printStackTrace();
                }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){

                    pref = getSharedPreferences("testapp", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("username", username);
                    editor.putString("password", password);

                    Log.e("UName",username);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this,Main.class);
                    intent.putExtra(USER_NAME, username);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username, password);
    }
}