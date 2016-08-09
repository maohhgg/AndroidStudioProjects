package com.example.mao.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class JsonActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.json_JSONObject).setOnClickListener(this);
        findViewById(R.id.json_GSON).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.json_GSON:
                HttpRequset.sendRequestWhitHttpURLConnection("http://222.222.222.200/data/?json&serialize",new Handler(){
                    public void handleMessage(Message msg){
                    if (msg.what == 1){
                        String mes = parseJSONWithGSON( (String)msg.obj);
                        HttpRequset.AlertDialogShow(JsonActivity.this,mes);
                    }
                }});
                break;

            case R.id.json_JSONObject:
                HttpRequset.sendRequestWhitHttpURLConnection("http://222.222.222.200/data/?json&unserialize",new Handler(){
                    public void handleMessage(Message msg){
                    if (msg.what == 1){
                        String mes = parseJSONWithJSONObject( (String)msg.obj);
                        HttpRequset.AlertDialogShow(JsonActivity.this,mes);
                    }
                }});
                break;
        }
    }

    public String parseJSONWithJSONObject(String jsonString){
        String responce = "";
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0;i < jsonArray.length();i++){
                JSONObject keyObject = jsonArray.getJSONObject(i);
                responce += "id ------" + keyObject.getString("id") + "\n";
                responce += "name ------" + keyObject.getString("name") + "\n";
                responce += "version ------" + keyObject.getString("version") + "\n";
            }
            return responce;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responce;
    }

    public String parseJSONWithGSON(String jsonString){
        Gson gson = new Gson();
        Category category = gson.fromJson(jsonString, new TypeToken<Category>(){}.getType());
        String response = "";
        response += "id ------ " + category.getId() + "\n";
        response += "name ------ " + category.getName() + "\n";
        List<Category> categoryChildrenList = category.getChildren();
        for (Category category1 : categoryChildrenList){
            response += "    id ------ " + category1.getId() + "\n";
            response += "    name ------ " + category1.getName() + "\n";
        }
        return response;
    }
}
