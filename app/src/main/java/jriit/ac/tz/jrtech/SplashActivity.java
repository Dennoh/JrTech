package jriit.ac.tz.jrtech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //method to display splash screen
        displaySplashScreen();
    }

    public void displaySplashScreen() {

        Thread mythread = new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    //amount of time splash will be delayed 4000 = 4sec
                    int displaytime = 4000;
                    int waittime = 0;
                    while (waittime < displaytime) {
                        sleep(100);
                        waittime = waittime + 100;
                    }
                    super.run();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    Intent a = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(a);
                    finish();
                }
            }
        };
        mythread.start();

    }
}