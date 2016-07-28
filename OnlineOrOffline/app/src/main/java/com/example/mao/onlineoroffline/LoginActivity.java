package com.example.mao.onlineoroffline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by MAO on 2016/7/24.
 */
public class LoginActivity extends BaseActivity {
    private EditText username,passwd;
    private CheckBox check;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.passwd);
        check = (CheckBox) findViewById(R.id.check);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isRemember = pref.getBoolean("remember",false);

        if(isRemember){
            username.setText(pref.getString("username",""));
            passwd.setText(pref.getString("password",""));
            check.setChecked(true);
        }

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = username.getText().toString();
                String pw = passwd.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                if(check.isChecked()){
                    editor.putBoolean("remember",true);
                    editor.putString("username",un);
                    editor.putString("password",pw);
                    editor.commit();
                } else {
                    editor.clear();
                }
                Log.i("MainActivity","username is " + un + " Password is " + pw);
                if(un.equals("admin")  && pw.equals("mao555")){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        });
    }
}
