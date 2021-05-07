package com.example.findworker;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DeserializeJsonArray {
    JSONArray jsonArray;
    public DeserializeJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
    public List<String> convertToStringList(){
        List<String> nameList = new ArrayList<>();
        Log.d("DESERIALIZE",jsonArray.length()+"");
        for(int i=0;i<jsonArray.length();i++){
            try {
                String name = jsonArray.getJSONObject(i).getString("name");
                nameList.add(name);
            }catch (JSONException exception){
                exception.printStackTrace();
            }
        }
        return nameList;
    }
}
