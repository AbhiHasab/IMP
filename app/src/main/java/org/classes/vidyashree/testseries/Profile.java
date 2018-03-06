package org.classes.vidyashree.testseries;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 06/05/2016.
 */

public class Profile extends Activity {

    TextView textViewResult,gender,dob,email,pcon,cnumber,scoll, id;
    ProgressDialog loading;
    String name,gender1,dob1,email1,scon,pcon1,scll,no,scl2,scl3,name1,name2,name3;
    int a;
    int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    EditText editTextId;
    private Button buttonGetImage;
    private ImageView imageView;
    private final String imageURL = "http://takalkarclasses.com/Takalkarweb/getImage.php?id=";
    public static final String UPLOAD_URL = "http://192.168.43.19/Uplode.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private Uri filePath;
    String a3,a1,a4,a5,a6;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        gender = (TextView)findViewById(R.id.gender);
        dob=(TextView)findViewById(R.id.dob);
        id=(TextView)findViewById(R.id.id);
        email=(TextView)findViewById(R.id.email);
        cnumber=(TextView)findViewById(R.id.connumber);
        pcon=(TextView)findViewById(R.id.parnumber);
        scoll=(TextView)findViewById(R.id.batch);
        //scoll=(TextView)findViewById(R.id.schoolname);
        imageView = (ImageView) findViewById(R.id.imageview_profile);
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });*/

        no =getIntent().getExtras().getString("NO");
        Log.e("LName",no);

        name = getIntent().getExtras().getString("Name");
        name2 = getIntent().getExtras().getString("Name1");
        name3 = getIntent().getExtras().getString("School3");
        Log.e("MName",name);
        Log.e("MName",name2);
        Log.e("MName",name3);

        gender1 = getIntent().getExtras().getString("Gender");
        Log.e("LName",gender1);

        String merge=no+" " + name +" "+gender1;
        Log.e("Fullname",merge);

        name1 = getIntent().getExtras().getString("NO1");
        Log.e("MNane",name1);

        dob1 = getIntent().getExtras().getString("DOB");
        Log.e("Gender",dob1);
        email1 = getIntent().getExtras().getString("Email");
        Log.e("DOB",email1);
        scon = getIntent().getExtras().getString("SContact");
        Log.e("Email",scon);
        pcon1 = getIntent().getExtras().getString("PContact");
        Log.e("Mobile",pcon1);
        scll = getIntent().getExtras().getString("School");
        Log.e("Number",scll);
        scl2 = getIntent().getExtras().getString("School1");
        Log.e("Number1",scl2);
        scl3 = getIntent().getExtras().getString("School2");
        Log.e("Number2",scl3);
        id.setText(name2);
        textViewResult.setText(merge);
        gender.setText(dob1);
        dob.setText(email1);
        email.setText(scon);
        cnumber.setText(scll);
        pcon.setText(scl3);
        scoll.setText(name3);
        //isInternetOn();
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("testapp", MODE_PRIVATE);
        a3=pref1.getString("username", null);
        //      Log.e("jjjjjj",a3);

        SharedPreferences pref2 = getApplicationContext().getSharedPreferences("token", MODE_WORLD_READABLE);
        a1=pref2.getString("username", null);
//        Log.e("Tokensddd",a1);
        insertToDatabase(a3, a1, name1);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

    private void insertToDatabase(String a3, String a1, String name1) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Profile.this, "Please wait", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("testapp", MODE_PRIVATE);
                a4=pref1.getString("username", null);
                Log.e("IDSSS",a4);

                SharedPreferences pref2 = getApplicationContext().getSharedPreferences("token", MODE_WORLD_READABLE);
                a5=pref2.getString("username", null);
                Log.e("Tokensddd",a5);

                a6= getIntent().getExtras().getString("NO1");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("title", a4));
                nameValuePairs.add(new BasicNameValuePair("id", a5));
                nameValuePairs.add(new BasicNameValuePair("username", a6));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://vidyashreetestseries.in/software/orderForm/testSeries17_05/VTS/impAdminWebservice/tokeninsert.php");
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
                loadingDialog.dismiss();
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                //TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                //textViewResult.setText("Inserted");
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(a3,a1,name1);
    }

    /*private boolean isInternetOn() {
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            getImage();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " Sorry! You are not Connect with Internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        uploadImage();
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profile.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
    private void getImage() {

        class GetImage extends AsyncTask<String,Void,Bitmap>{

            ImageView bmImage;
            ProgressDialog loading;

            public GetImage(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                loading.dismiss();
                bmImage.setImageBitmap(bitmap);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profile.this,"Uploding Image","Please wait...",true,true);
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = imageURL+ strings[0];
                Bitmap mIcon = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    mIcon = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
                return mIcon;
            }
        }
        GetImage gi = new GetImage(imageView);
        gi.execute(no);
    }*/
}