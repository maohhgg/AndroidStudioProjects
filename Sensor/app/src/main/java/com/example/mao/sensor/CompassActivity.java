package com.example.mao.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private ImageView compassImg;
    private TextView text;

    SensorEventListener listener = new SensorEventListener() {

        float[] meterValues = new float[3];
        float[] magneticValues = new float[3];
        private float lastRotateDegreee;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    meterValues = sensorEvent.values.clone();
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magneticValues = sensorEvent.values.clone();
                    break;
                default:
                    break;
            }
            float[] R = new float[9];
            float[] values = new float[9];
            SensorManager.getRotationMatrix(R,null,meterValues,magneticValues);
            SensorManager.getOrientation(R,values);
            float rotateDegree = -(float) Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree - lastRotateDegreee) > 1){
                RotateAnimation animation = new RotateAnimation(lastRotateDegreee,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setFillAfter(true);
                compassImg.startAnimation(animation);
                lastRotateDegreee = rotateDegree;
                text.setText( sensorEvent.values[0]+"°");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassImg = (ImageView) findViewById(R.id.compass);
        text = (TextView) findViewById(R.id.text);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor meterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(listener,meterSensor,SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(listener,magneticSensor,SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null){
            sensorManager.unregisterListener(listener);
        }
    }
}
