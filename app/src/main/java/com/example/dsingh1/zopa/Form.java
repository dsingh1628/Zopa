package com.example.dsingh1.zopa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import backend.Global_Param;
import backend.Root;

public class Form extends AppCompatActivity {

    Spinner spinner;
    int TotalKeysValues=0;
    ImageView imageView;
    LinearLayout mainLL;
    RelativeLayout rl;
    TextView site_name;
    Button submit;
   public static Site currentSite=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        spinner=(Spinner)findViewById(R.id.spinner);
        final MyDbHelper myDbHelper=  new MyDbHelper(this);
        final List<String> siteList=myDbHelper.getSiteList();
        if(siteList.size()==0){
            Toast.makeText(this,"No records found",Toast.LENGTH_LONG).show();
            return;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,siteList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setVisibility(View.VISIBLE);
        imageView=(ImageView)findViewById(R.id.logo);
        mainLL=(LinearLayout)findViewById(R.id.dynamicll);
        rl=(RelativeLayout)findViewById(R.id.relative_layout);
        submit=(Button)findViewById(R.id.submit);
        site_name=(TextView)findViewById(R.id.site_name);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(((LinearLayout) mainLL).getChildCount() > 0)
                    ((LinearLayout) mainLL).removeAllViews();

                String text = spinner.getSelectedItem().toString();
                  site_name.setText(text);
                site_name.setVisibility(View.VISIBLE);
                   submit.setVisibility(View.VISIBLE);
                    Log.d("before", "getsitedetials");
                    HashMap<String, Object> data = myDbHelper.getSiteDetials(text);
                    Log.d(" data.get(\"logo\")", data.get("logo").toString());
                String jsonCOde=data.get("jsonCode").toString();
                    Bitmap logoImage = Utility.getPhoto((byte[]) data.get("logo"));
                    imageView.setImageBitmap(logoImage);
                    imageView.setVisibility(View.VISIBLE);
                    Log.d("data", data.toString());
                    List<LinkedHashMap<String, String>> keyValues = myDbHelper.getKeyValuesData(text);
                    Log.d("reio>", keyValues.toString());
                    for (LinkedHashMap<String, String> metafield : keyValues
                            ) {
                        LinearLayout ll = new LinearLayout(Form.this);
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rl.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                        ll.setLayoutParams(lp);
                        TextView tv = new TextView(Form.this);
                        tv.setTextColor(Color.BLUE);
                        tv.setText(metafield.get("key"));
                        tv.setWidth(rl.getWidth() / 2);
                        EditText et = new EditText(Form.this);
                        et.setTag(metafield.get("value"));
                        et.setWidth(rl.getWidth() / 2);
                        et.setTextColor(Color.BLUE);
                        Log.d("ll.getWidth()", rl.getWidth() + "");
                        SharedPreferences sharedPreferences=Form.this.getPreferences(Context.MODE_PRIVATE);
                        String credential=sharedPreferences.getString(site_name.getText().toString()+tv.getText().toString(),null);
                        Log.d("final check",site_name.getText().toString()+tv.getText().toString());

                        currentSite = new Site(site_name.getText().toString(),jsonCOde,"",null,logoImage);

                        if(credential!=null){
                            et.setText(credential);
                            Log.d("reio", credential);
                        }
                        ll.addView(tv);
                        ll.addView(et);
                        mainLL.addView(ll);
                        /*mainLL.setBackground(new BitmapDrawable(getResources(), logoImage));
                        mainLL.getBackground().setAlpha(200);*/
                    }
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> credentials = new HashMap<String, String>();
                if (mainLL.getChildCount() > 0) {
                    for (int i = 0; i < mainLL.getChildCount(); i++) {
                        LinearLayout ll = (LinearLayout) mainLL.getChildAt(i);
                        String key = "", value = "",gloablparamkey="";
                        for (int j = 0; j < ll.getChildCount(); j++) {

                            View view = ll.getChildAt(j);
                            Log.d("child", view.toString());
                             if (view instanceof EditText) {

                                if (((EditText) view).getText().toString().equals("")) {

                                    Toast.makeText(Form.this, "fields are mandatory", Toast.LENGTH_SHORT).show();
                                    return;

                                }
                                value = ((EditText) view).getText().toString();
                                 gloablparamkey= (String) view.getTag();

                            }else if (view instanceof TextView) {
                                key = site_name.getText().toString() + ((TextView) view).getText().toString();
                                Log.d("inside text view", ((TextView) view).getText().toString());
                            }
                        }
                        credentials.put(key, value);
                        Global_Param.put(gloablparamkey,value);
                        Log.d("gloablparam", key + " " + value+" "+gloablparamkey);

                    }
                }
                // call rohit's stub

                 new Login().execute(currentSite.jsonCode);


                // if stub return true then save the credenitals in shared prefresnces
                if (true) {
                    SharedPreferences sharedPreferences = Form.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> cred = credentials.keySet();
                    Iterator iterator = cred.iterator();
                    while (iterator.hasNext()) {
                        String k=iterator.next().toString();
                        String val= credentials.get(k);
                        Log.d("finalcheck",k+val);
                        editor.putString(k, val);

                    }
                    editor.commit();
                }
            }
        });

    }

    private class Login extends AsyncTask<String,Void,String>{

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=ProgressDialog.show(Form.this,"Connecting to Server","please wait...");
        }

        @Override
        protected String doInBackground(String... params) {


            String ret="";
            try {
              ret=  Root.login(params[0]);
            } catch (Exception e) {

                Log.d("error","in login");
                e.printStackTrace();
            }

            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            Intent i = new Intent(Form.this, Main2Activity.class);
            i.putExtra("message", s);
            startActivity(i);
            Toast.makeText(Form.this,s,Toast.LENGTH_LONG).show();
        }
    }
}
