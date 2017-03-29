package sensor.service.tanvir.profilechangerservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceIntent = new Intent(this, ProfileService.class);
    }

    public void startButton(View v){
        ProfileService.startthread = true;
        startService(serviceIntent);
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

    }
    public void stopButton(View v){
        ProfileService.startthread = false;
        stopService(serviceIntent);
        Toast.makeText(this, "Service stoped", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("LifeCycle","Onstop called");
        ProfileService.startthread = false;
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("LifeCycle","onRestart called");
    }


}
