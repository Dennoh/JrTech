package jriit.ac.tz.jrtech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webViewMainSite;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        if (haveNetworkConnection()) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            LoadWebView();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadWebView() {
        webViewMainSite = findViewById(R.id.webviewMain);
        //This is the id you gave to the WebView in the main.xml
        webViewMainSite.getSettings().setJavaScriptEnabled(true);
        webViewMainSite.getSettings().setSupportZoom(true);       //Zoom Control on web (You don't need this
        //if ROM supports Multi-Touch
        webViewMainSite.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
        webViewMainSite.setWebViewClient(new WebViewClient());

        // Load URL
        webViewMainSite.loadUrl("https://jriit.ac.tz/");

        // Sets the Chrome Client, and defines the onProgressChanged
        // This makes the Progress bar be updated.
        final Activity MyActivity = this;
        webViewMainSite.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                MyActivity.setTitle("Loading...");
                MyActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if (progress == 100)
                    MyActivity.setTitle(getResources().getString(R.string.app_name));
//                logo.setVisibility(View.GONE);
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public void mainClicked(View view) {

        switch (view.getId()) {
            case R.id.services_img:
                startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
                break;
            case R.id.services_txt:
                startActivity(new Intent(getApplicationContext(), ServicesActivity.class));
                break;
            case R.id.settings_chat:
                chatNow();
                break;
            case R.id.settings_chatnow:
                chatNow();
                break;
            case R.id.Connect_img:
                startActivity(new Intent(getApplicationContext(), ContactsActivity.class));
                break;
            case R.id.Connect_txt:
                startActivity(new Intent(getApplicationContext(), ContactsActivity.class));
                break;
            case R.id.settings_img:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.settings_txt:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.linearChat:
                chatNow();
//                Toast.makeText(this, "erer", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void chatNow() {
        String contact = "+255754360590"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = MainActivity.this.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NameNotFoundException", "NameNotFoundException: " + e);
            Toast.makeText(MainActivity.this, "WhatsApp not installed in your phone", Toast.LENGTH_SHORT).show();
            //    Toasty.warning(MainActivity.this, "WhatsApp not installed in your phone", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}