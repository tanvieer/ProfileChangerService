package sensor.service.tanvir.profilechangerservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static sensor.service.tanvir.profilechangerservice.ProfileService.sensorThread;

/**
 * Created by Tanvir on 29-Mar-17.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";
    private int counter = 1;
    public static Thread controllerThread;
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
        ActionStart();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(sel);
        startthread = false;
        Log.d(TAG, "On Destroy Called");
    }


































    private void ActionStart(){
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

    /*       final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
               // Toast.makeText(MyService.this, "5 sec passed, counter = "+counter, Toast.LENGTH_SHORT).show();
            }
        };


     controllerThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (startthread) {
                    counter++;
                    Log.d(TAG,"Counter: "+Integer.toString(counter));
                    try {
                        Thread.sleep(5000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

              *//*      if(counter >100){
                        startthread = false;
                        Log.d(TAG,"Thread stopped");
                        sm.unregisterListener(sel);
                    }*//*
                    if(!startthread){
                        sm.unregisterListener(sel);

                        Log.d(TAG,"Sensor stopped unregisterListener");
                    }


                }
            }
        });
        controllerThread.start();*/



        //==============================Sensor=====================================

        sm=(SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        Sensor s=sm.getSensorList(Sensor.TYPE_PROXIMITY).get(0);
        sm.registerListener(sel,s,SensorManager.SENSOR_DELAY_NORMAL);

        sensorThread =new Thread(){
            public void run()
            {
                sel=new SensorEventListener() {

                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        // TODO Auto-generated method stub
                        double x;
                        x=event.values[0];

                        if(x==0.0){
                            // myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            Log.d(TAG,"vibrate mode on");
                        }
                        if(x==1.0){
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
