package teconnectivity.feeling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Timer timer=new Timer();                                       //设置，过场动画延时三秒
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this,LogActivity.class));
            }
        };timer.schedule(timerTask,3000);
    }
}
