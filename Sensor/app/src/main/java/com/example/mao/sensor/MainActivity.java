package com.example.mao.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private TextView light,meterX,meterY,meterZ,meterType,gravityX,gravityY,gravityZ,gravityType,gyroscopeX,gyroscopeY,gyroscopeZ,gyroscopeType;
    private SensorManager sensorManager;

    SensorEventListener lightListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float value = sensorEvent.values[0];
            light.setText("光线强度：" + value + "lx");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener meterListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = Math.abs(sensorEvent.values[0]);
            float y = Math.abs(sensorEvent.values[1]);
            float z = Math.abs(sensorEvent.values[2]);
            String type = "";
            meterX.setText("X加速度：" + sensorEvent.values[0]);
            meterY.setText("Y加速度：" + sensorEvent.values[1]);
            meterZ.setText("Z加速度：" + sensorEvent.values[2]);
            if (x > y && x > z) {
                type = "侧放";
            } else if (y > z && y > x) {
                type = "竖放";
            } else if (z > y && z > x) {
                type = "平躺";
            }

            meterType.setText("手机方向："+type);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    SensorEventListener gravityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            gravityX.setText("X加速度：" + sensorEvent.values[0]);
            gravityY.setText("Y加速度：" + sensorEvent.values[1]);
            gravityZ.setText("Z加速度：" + sensorEvent.values[2]);

            float x = Math.abs(sensorEvent.values[0]);
            float y = Math.abs(sensorEvent.values[1]);
            float z = Math.abs(sensorEvent.values[2]);
            String type = "";
            if (x > y && x > z) {
                type = "侧放";
            } else if (y > z && y > x) {
                type = "竖放";
            } else if (z > y && z > x) {
                type = "平躺";
            }
            gravityType.setText("手机方向："+type);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener gyroscopeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            gyroscopeX.setText("X旋转角速度：" + sensorEvent.values[0]);
            gyroscopeY.setText("Y旋转角速度：" + sensorEvent.values[1]);
            gyroscopeZ.setText("Z旋转角速度：" + sensorEvent.values[2]);

            float x = Math.abs(sensorEvent.values[0]);
            float y = Math.abs(sensorEvent.values[1]);
            float z = Math.abs(sensorEvent.values[2]);
            String type = "";
            if (x > y && x > z) {
                if (sensorEvent.values[0] > 0) {
                    type = "向下旋转";
                } else {
                    type = "向上旋转";
                }
            } else if (y > z && y > x) {
                if (sensorEvent.values[0] > 0) {
                    type = "向右旋转";
                } else {
                    type = "向左旋转";
                }
            }
            gyroscopeType.setText(type);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        light = (TextView) findViewById(R.id.sensor_light);
        meterX = (TextView) findViewById(R.id.sensor_meter_x);
        meterY = (TextView) findViewById(R.id.sensor_meter_y);
        meterZ = (TextView) findViewById(R.id.sensor_meter_z);
        meterType = (TextView) findViewById(R.id.sensor_meter_type);

        gravityX = (TextView) findViewById(R.id.sensor_gravity_x);
        gravityY = (TextView) findViewById(R.id.sensor_gravity_y);
        gravityZ = (TextView) findViewById(R.id.sensor_gravity_z);
        gravityType = (TextView) findViewById(R.id.sensor_gravity_type);

        gyroscopeX = (TextView) findViewById(R.id.sensor_gyroscope_x);
        gyroscopeY = (TextView) findViewById(R.id.sensor_gyroscope_y);
        gyroscopeZ = (TextView) findViewById(R.id.sensor_gyroscope_z);
        gyroscopeType = (TextView) findViewById(R.id.sensor_gyroscope_type);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(lightListener,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);

        Sensor meterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(meterListener,meterSensor,SensorManager.SENSOR_DELAY_NORMAL);

        /**
         * Sensor.TYPE_GRAVITY 只有重力加速度，最大为9.8
         * Sensor.TYPE_ACCELEROMETER 重力加速度 + 手机移动时的加速度  最大为无上限
         */
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(gravityListener,gravitySensor,SensorManager.SENSOR_DELAY_NORMAL);

        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(gyroscopeListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(lightListener);
        sensorManager.unregisterListener(meterListener);
        sensorManager.unregisterListener(gravityListener);
        sensorManager.unregisterListener(gyroscopeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
