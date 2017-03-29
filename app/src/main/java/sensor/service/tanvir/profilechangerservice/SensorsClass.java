package sensor.service.tanvir.profilechangerservice;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Tanvir on 29-Mar-17.
 */

public class SensorsClass implements SensorEventListener {
    private Sensor proximity;
    private SensorManager SM;
    public SensorsClass() {
        SM =  (SensorManager)this.getSystemService(SENSOR_SERVICE);
        proximity = SM.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SM.registerListener(this,proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    
    
    
}
