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

import java.util.ArrayDeque;
import java.util.Deque;

public class CompassActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private ImageView compassImg;
    private TextView text;
    private float lastRotateDegreee;
    private MovingAverage meterAverage = new MovingAverage(3);
    private MovingAverage magneticAverage = new MovingAverage(3);

    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    meterAverage.pushValue(sensorEvent.values);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magneticAverage.pushValue(sensorEvent.values);
                    break;
                default:
                    break;
            }
            drawCompass(meterAverage.getValue(),magneticAverage.getValue());

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

        sensorManager.registerListener(listener, meterSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private void drawCompass(float[] meterValues, float[] magneticValues) {
        float[] R = new float[9];
        float[] values = new float[9];
        SensorManager.getRotationMatrix(R, null, meterValues, magneticValues);
        SensorManager.getOrientation(R, values);
        float rotateDegree = -(float) Math.toDegrees(values[0]);
        rotateDegree = Math.round(rotateDegree * 10) / 10;
        if (Math.abs(rotateDegree - lastRotateDegreee) > 1) {
            RotateAnimation animation = new RotateAnimation(lastRotateDegreee, rotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(300);
            animation.setFillAfter(true);
            compassImg.startAnimation(animation);
            lastRotateDegreee = rotateDegree;

            // 要经过一次数据格式的转换，转换为度
            int val = (int) Math.toDegrees(values[0]);
            if (val == 0) {
                text.setText("正北");
            } else if (val == 90) {
                text.setText("正东");
            } else if (val == 180) {
                text.setText("正南");
            } else if (val == -90) {
                text.setText("正西");
            } else {
                text.setText(val + "°");
            }
        }
    }
    public class MovingAverage{
        private Deque<float[]> circularBuffer;
        private float[] avg = new float[3];                 //返回到传感器平均值
        private float[] sum = new float[3];                 //数值中传感器数据的和
        private int circularIndex;
        private int count;


        public MovingAverage(int k){
            circularBuffer = new ArrayDeque<>(k);
            count = k;
            circularIndex = 0;
        }
        public float[] getValue(){
            avg[0] = sum[0] / circularIndex;
            avg[1] = sum[1] / circularIndex;
            avg[2] = sum[2] / circularIndex;
            return avg;
        }
        public long getCount(){
            return circularIndex;
        }
        private void primeBuffer(float[] val){
            circularBuffer.addFirst(val);
            sum[0] += Math.round(val[0] * 10) / 10;
            sum[1] += Math.round(val[1] * 10) / 10;
            sum[2] += Math.round(val[2] * 10) / 10;
        }

        public void pushValue(float[] x){
            if (circularIndex < count) {
                circularIndex++;
            } else {
                float[] lastValue = circularBuffer.pollFirst();
                sum[0] -= lastValue[0];
                sum[1] -= lastValue[1];
                sum[2] -= lastValue[2];
            }
            primeBuffer(x);
        }
    }
}
