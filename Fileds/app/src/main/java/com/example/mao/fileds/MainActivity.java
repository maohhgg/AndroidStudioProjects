package com.example.mao.fileds;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Spinner s;
    private Button d,t;
    private String[] spinner = new String[]{"我是SB","你是SB","他是SB","我们都是SB","除了我都是SB","去死吧，没有其他的选择"};
    private String check;
    private Map<String,Integer> data = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s = (Spinner) findViewById(R.id.Spinner);
        s.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spinner));

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MainActivity","当前选择的是: " + spinner[i]);
                check = spinner[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        d = (Button) findViewById(R.id.btnDate);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String date = String.format("%d/%d/%d",i,i1+1,i2);
                        d.setText(date);
                    }
                },2016,6,22).show();
            }
        });

        t = (Button) findViewById(R.id.btnTime);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String date = String.format("%d:%d",i,i1);
                        t.setText(date);
                    }
                },21,20,true).show();
            }
        });

        RadioGroup rbg = (RadioGroup) findViewById(R.id.radioGroup);


        for (String s : spinner) {
            RadioButton rb = new RadioButton(MainActivity.this);
            rb.setText(s);
            int id = View.generateViewId();
            rb.setId(id);
            data.put(s,id);
            rbg.addView(rb);
        }
        findViewById(R.id.checkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = data.get(check);
                RadioButton rb = (RadioButton) findViewById(id);
                if(rb.isChecked()){
                    Toast.makeText(MainActivity.this,"YOURE A CREATE",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"NO NOT THIS",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

