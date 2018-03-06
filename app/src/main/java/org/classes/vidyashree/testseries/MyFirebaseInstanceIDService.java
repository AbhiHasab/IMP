package org.classes.vidyashree.testseries;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by E 4310 on 12/9/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    SharedPreferences prefs;
    @Override
    public void onTokenRefresh() {
        String token= FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
    }

    private void registerToken(String token) {
        OkHttpClient client=new OkHttpClient();
        RequestBody body= new FormBody.Builder()
                .add("Token",token)
                .build();
        Log.e("Token1",token);

        Request request=new Request.Builder()
                .url("http://takalkarclasses.com/firebasewebservice/fcm/register.php?Token=")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences pref = getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("token", token);

        Log.e("Tokenss",token);
        editor.commit();
        SharedPreferences pref1=getApplicationContext().getSharedPreferences("token",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref.edit();
        editor1.putString("username", token);
        editor1.commit();
        //insertToDatabase(token);
    }
    private void insertToDatabase(final String token) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
/*
                String paramUsername = params[0];
                String paramAddress = params[1];
*/
                //String name = editTextName.getText().toString();

                prefs = getSharedPreferences("testapp", MODE_PRIVATE);
                String username = prefs.getString("username", "");
                //final String password = prefs.getString("Password", "");
                Log.e("userName", username);
                //Log.e("Password", password);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("address", token));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://192.168.43.19/fcm/save.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                //TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                //textViewResult.setText("Inserted");
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(token);
    }
}