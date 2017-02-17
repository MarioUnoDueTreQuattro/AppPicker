package com.thepriest.andrea.apppicker;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.danylovolokh.androidlogger.AndroidLogger;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class LauncherActivity extends AppCompatActivity {
    private static final String TAG = "LauncherActivity";
    private static Context mContext;
    private DrawerLayout drawerLayout;
    private WebView webView;
    private NavigationView navigationView;
private Button button_app_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidLogger.d(TAG, "onCreate");
        mContext = getApplicationContext();
        setContentView(R.layout.activity_launcher);
        //finish();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        webView = (WebView) findViewById(R.id.webView);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Uri path = Uri.parse("android.resource://com.thepriest.andrea.apppicker/" + R.raw.help);
        webView.loadUrl("file:///android_res/raw/help.html");
        button_app_detail= (Button) findViewById(R.id.button_app_detail);
        button_app_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startApplicationDetailsActivity("com.thepriest.andrea.apppicker");
            }
        });
    }

    private void startApplicationDetailsActivity(String string) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT <= 8) {
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
        } else {
            intent.setClassName("com.android.settings", "com.android.settings.applications.InstalledAppDetails");
        }
        intent.putExtra("pkg", string);
        intent.setData(Uri.parse((String) ("package:" + string)));
        try {
            this.startActivity(intent);
            return;
        } catch (ActivityNotFoundException exception) {
            exception.printStackTrace();
            return;
        }
    }

    public static void launchBrowser(Intent intent) {
        Log.d(TAG, "launchBrowser: ");
        Log.d(TAG, "launchBrowser: " + intent.getAction());
        if (intent != null) {
            if (intent.getAction().equals(Intent.ACTION_CHOOSER)) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "intent.getAction().equals(Intent.ACTION_SEND)");
                //String urlText = intent.getStringExtra(Intent.EXTRA_TEXT);
                ClipData clip = intent.getClipData();
                String urlText = clip.getItemAt(0).getUri().toString();
                //String urlText = intent.getDataString();
                Log.d(TAG, "launchBrowser: " + urlText);
                //Uri uri = findUrlInString(urlText);
                Uri uri = Uri.parse(urlText);
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //Intent intentChooser = Intent.createChooser(intent,"Choose navigator");
                //final PackageManager packageManager = getPackageManager();
                // List<ResolveInfo> targetedShareIntents = packageManager.queryIntentActivities(intent, 0);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                //Intent chooser = Intent.createChooser(targetedShareIntents, "Delen");
                if (uri != null) {
                    //i.setData(uri);
                    i.setDataAndType(uri, "text/html");
                    // i.setType("text/plain");
                    i.setFlags(FLAG_ACTIVITY_NEW_TASK);

                    mContext.startActivity(i);
//                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.maidofknowledge.com"));
//                    Intent chooser = Intent.createChooser(sendIntent, "Choose Your Browser");
//                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(chooser);
//                    }
                } else {
//                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}
