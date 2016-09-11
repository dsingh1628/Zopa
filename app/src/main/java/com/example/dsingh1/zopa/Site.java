package com.example.dsingh1.zopa;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by DSingh1 on 5/1/2016.
 */
public class Site {

        String name;
        String jsonCode;
        String logo;
        LinkedHashMap<String, String> lebel_meta;


    Bitmap image;


    public Site(String name, String jsonCode, String logo, LinkedHashMap<String, String> lebel_meta, Bitmap image) {
        this.name = name;
        this.jsonCode = jsonCode;
        this.logo = logo;
        this.lebel_meta = lebel_meta;
        this.image = image;
    }

    public Site( JSONObject site) throws JSONException {
        lebel_meta = new LinkedHashMap<>();

        name = (String) site.get("site_name");

        jsonCode = (String)site.get("jsonCode");
        logo =  (String)site.get("logo");

        JSONArray metafields = (JSONArray)site.get("keyValues");


        for (int i=0;i<metafields.length();i++){
            JSONObject meta =  (JSONObject) metafields.get(i);
            String label = (String) meta.get("key");
             String meta_valu = (String) meta.get("value");
            Log.d("label metaval",label+" "+meta_valu);
            lebel_meta.put(label,meta_valu);
        }

    }


    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {

        String op = "";
        op+=  "name: "+name+"\n";
        op+= "logo"+logo+"\n";
        op+= lebel_meta.toString();


        return  op;
    }
}
