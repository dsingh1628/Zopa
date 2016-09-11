package com.example.dsingh1.zopa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    JSONObject jsonObject;
    Button load,skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner)findViewById(R.id.spinner);
        load=(Button)findViewById(R.id.load_button);
        skip=(Button)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Form.class));

            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new loading().execute(new URL("http://www.reio.in/Zopa/getData.php"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }



            }
        });




    }

    private class loading extends AsyncTask<URL,Void,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please wait....");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            JSONArray Json_sites=null;
            try {
                Log.d("zopa_debug site: ",s);
                Json_sites = new JSONArray(s);
            } catch (JSONException e) {
                Log.d("zopa_error: ","while parsing the sites: main activity : onPostExecute");
                e.printStackTrace();
            }

            List<Site> sites  = new ArrayList<>();

          if(Json_sites!= null  ){
              for( int i =0;i<Json_sites.length();i++ ){
                  try {
                      Site site = new Site((JSONObject)Json_sites.get(i));
                      sites.add(site);

                  } catch (JSONException e) {
                      Log.d("zopa_error: ","while parsing the site JSONObject : main activity : onPostExecute");
                      e.printStackTrace();
                  }
              }
          }

            pd.dismiss();
            new InsertSite().execute(sites);
            startActivity(new Intent(MainActivity.this, Form.class));

        }

        protected class InsertSite extends AsyncTask<List<Site>,Void,List<Site>>{

            ProgressDialog pd;

            @Override
            protected void onPostExecute(List<Site> sites) {

                MyDbHelper myDbHelper=new MyDbHelper(MainActivity.this);
                myDbHelper.deleteData();
                for (Site site :
                        sites) {

                    myDbHelper.insertSite(site.name, site.jsonCode, site.image);
                    Log.d("metaval",site.lebel_meta.toString());
                    myDbHelper.insertKeyValues(site.name, site.lebel_meta);

                }
                pd.dismiss();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd=ProgressDialog.show(MainActivity.this,"Saving Data","Please wait...");
            }

            @Override
            protected List<Site> doInBackground(List<Site>... sites) {


                for (Site params :
                        sites[0]) {
                    Bitmap image=null;

                    try {
                        image= BitmapFactory.decodeStream(new URL(params.logo).openConnection().getInputStream());
                        params.setImage(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return sites[0];
                }

        }


        @Override
        protected String doInBackground(URL... params) {

            String response="";
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) params[0].openConnection();
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
               response= readStream(in);
                }catch(IOException e){
                    e.printStackTrace();
                }

            return response;
        }
        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }
    }
}
