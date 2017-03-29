package sensor.service.tanvir.profilechangerservice;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static sensor.service.tanvir.profilechangerservice.ProfileService.sensorThread;

public class MainActivity extends AppCompatActivity {

    private Intent _pocketMode;
    private Intent _tableMode;
    private Intent _lightMode;
    ////private Intent pocketModeIntentService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////pocketModeIntentService = new Intent(this, ProfileService.class);
        _pocketMode = new Intent(this, PocketMode.class);  // proximity
        _tableMode = new Intent(this, TableMode.class); // Accelerometer
        _lightMode = new Intent(this, LightMode.class);  // Light
    }

    public void startPocketMode(View v) throws InterruptedException {
        ////ProfileService.startthread = true;
        ////startService(pocketModeIntentService);

        stopAll();
        Thread.sleep(100);

        startService(_pocketMode);
        Toast.makeText(this, "Pocket Mode Started", Toast.LENGTH_SHORT).show();

    }

    public void startTableMode(View v) throws InterruptedException {

        stopAll();
        Thread.sleep(100);

        startService(_tableMode);  //Accelerometer   akhono start kori nai
        Toast.makeText(this, "Table Mode Started", Toast.LENGTH_SHORT).show();
    }

    public void startLightMode(View v) throws InterruptedException {

        stopAll();
        Thread.sleep(100);

        startService(_lightMode);
        Toast.makeText(this, "Light Mode Started", Toast.LENGTH_SHORT).show();

    }

    public void stopButton(View v){
        ////ProfileService.startthread = false;
        ////stopService(pocketModeIntentService);
        stopAll();
        Toast.makeText(this, "ALL Profile Changing Service Stoped", Toast.LENGTH_SHORT).show();

    }

    private void stopAll(){
        stopService(_pocketMode);
        stopService(_tableMode);
        stopService(_lightMode);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("LifeCycle","Onstop called");
       // ProfileService.startthread = false;
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("LifeCycle","onRestart called");
    }

}
