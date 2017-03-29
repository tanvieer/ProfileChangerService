package sensor.service.tanvir.profilechangerservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Tanvir on 29-Mar-17.
 */

public class LightMode extends Service {
    private static final String TAG = "MyLightMode";
    public static Thread sensorThread;
    private SensorEventListener sel;
    private SensorManager sm;
    private AudioManager myAudioManager;
    public static boolean startthread = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind Called");
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand Called");
        LightCheckingStart();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(sel);
        startthread = false;
        Log.d(TAG, "On Destroy Called");
    }



    //==============================ProximitySensor=====================================
    private void LightCheckingStart(){
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        sm=(SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        Sensor s=sm.getSensorList(Sensor.TYPE_LIGHT).get(0);
        sm.registerListener(sel,s,SensorManager.SENSOR_DELAY_NORMAL);

        sensorThread =new Thread(){
            public void run()
            {
                sel=new SensorEventListener() {

                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        // TODO Auto-generated method stub
                        double x;
                        x= (int)event.values[0];
                        if(x<5 && myAudioManager.getRingerMode() != AudioManager.RINGER_MODE_VIBRATE ){
                            // myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            Log.d(TAG,"vibrate mode on");
                        }
                        else if ( x>500 && myAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            Log.d(TAG,"normal mode on");
                        }

                       Log.d(TAG,"x="+Double.toString(x));
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        // TODO Auto-generated method stub

                    }
                };

            }
        };
        sensorThread.start();
        try {
            sensorThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sm.registerListener(sel,s,SensorManager.SENSOR_DELAY_NORMAL);
    }
}
