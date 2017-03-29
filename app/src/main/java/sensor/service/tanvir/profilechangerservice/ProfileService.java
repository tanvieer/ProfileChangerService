package sensor.service.tanvir.profilechangerservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Tanvir on 29-Mar-17.
 */

public class ProfileService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    private int counter = 1;
    public static Thread thrd;
    public static boolean startthread = true;
    public ProfileService() {
        super("ProfileChanger");
    }





    //=====================================================================================================================================
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //Toast.makeText(this, "on start ", Toast.LENGTH_SHORT).show();
        Log.d("MYProfileService", "On Start called");

        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Toast.makeText(ProfileService.this, "5 sec passed, counter = "+counter, Toast.LENGTH_SHORT).show();
            }
        };


          thrd = new Thread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(ProfileService.this, "Counter: "+Integer.toString(counter), Toast.LENGTH_SHORT).show();


                while (startthread) {
                    counter++;
                    Log.d("MYProfileService","Counter: "+Integer.toString(counter));
                    try {
                        Thread.sleep(5000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(counter >100){
                        startthread = false;
                        Log.d("MYProfileService","Thread stopped");
                    }
                }
            }
        });
        thrd.start();
       //thrd.stop();
    }


    //=====================================================================================================================================

    @Override
    protected void onHandleIntent(Intent intent) {
        try{
            //Thread.sleep(5000);

            //Toast.makeText(this, "Counter = " + counter, Toast.LENGTH_SHORT).show();
            Log.d("MYProfileService","onHandleIntent Counter = "+Integer.toString(counter));
           // counter++;
        }
        catch (Exception e){
            Log.d("MYProfileService", "Service exception");
        }
    }


























/*






    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d("MYProfileService", "On Bind Called");
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MYProfileService", "On Destroy called");
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MYProfileService", "On Create called");
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        Log.d("MYProfileService", "setIntentRedelivery called enabled ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MYProfileService", "onStartCommand called ");
        Toast.makeText(this, "on start command ", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    */
}
