package com.thepriest.andrea.apppicker;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.pm.PackageManager.MATCH_ALL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle bundle) {
        String stringAction = null;
        String stringType = null;
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        super.onCreate(bundle);
/*
        Intent intent = this.getIntent();
        if (intent.getAction().equals(Intent.ACTION_VIEW)) {
           // String urlText = intent.getStringExtra(Intent.EXTRA_TEXT);
            String urlText = intent.getDataString();

        Uri uri = findUrlInString(urlText);
        //Uri uri = Uri.parse(urlText);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        //Intent chooser = Intent.createChooser(targetedShareIntents, "Delen");
        if (uri != null) {
            //i.setData(uri);
            i.setDataAndType(uri, "text/html");
            // i.setType("text/plain");
            startActivity(Intent.createChooser(i, "getString(R.string.open_in_browser) + urlText"));
//                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.maidofknowledge.com"));
//                    Intent chooser = Intent.createChooser(sendIntent, "Choose Your Browser");
//                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(chooser);
//                    }
        } else {
            Toast.makeText(this,"getString(R.string.error)", Toast.LENGTH_LONG).show();
        }
        }
        else Log.d(TAG, "onCreate: NO Intent.ACTION_SEND");
*/
/*
        launchIntent();
        if (true) return;
*/
        Intent intent = this.getIntent();
        Log.d(TAG, "onCreate: " + intent.toString());
        String urlText = intent.getDataString();
        //Uri uri = findUrlInString(urlText);
        if (urlText == null) urlText = "";
        Uri uri = Uri.parse(urlText);
        if (uri != null) {
            Intent i = null;
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_PICK) || intent.getAction().equalsIgnoreCase("android.intent.action.RINGTONE_PICKER"))
                i = new Intent("android.intent.action.RINGTONE_PICKER", uri);
            else i = new Intent(Intent.ACTION_VIEW, uri);
//            Intent i = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,
//                    Intent.CATEGORY_APP_BROWSER);
            //i.setData(uri);
            stringAction = intent.getAction();
            stringType = intent.getType();
            // i.setAction(string);
            if (urlText.startsWith("http")) {
                Log.d(TAG, "onCreate: http");
                i.setDataAndType(uri, "text/html");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                //i.putExtra(Intent.EXTRA_TEXT,urlText);
                //i.setData(uri);
            } else {
                Log.d(TAG, "onCreate: NO http");
                i.setDataAndType(intent.getData(), stringType);
            }
            //i.addCategory(Intent.CATEGORY_APP_BROWSER);
            //i.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
            // i.setType("text/plain");
            Log.v(TAG, (String) ("action " + stringAction));
            Log.v(TAG, (String) ("type " + stringType));
            Log.d(TAG, "onCreate urlText: " + urlText);
            //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //List<ResolveInfo> allActivities = getApplicationContext().getPackageManager().queryIntentActivities(intent, 0);
            // intent.setSelector(i);
            Intent newInt = null;
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_PICK) || intent.getAction().equalsIgnoreCase("android.intent.action.RINGTONE_PICKER"))
                newInt = Intent.createChooser(i,/* i.getAction() + " " +*/ i.getType());
            else newInt = Intent.createChooser(i, urlText);
            //LauncherActivity.launchBrowser(newInt);
            newInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(cleanIntent(newInt));
            //   Intent chooser = Intent.createChooser(i, urlText);
            //  if (intent.resolveActivity(getPackageManager()) != null) startActivity(chooser);

            //startActivity(Intent.createChooser(i, /*getString(R.string.open) +*/ urlText));
//                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.maidofknowledge.com"));
//                    Intent chooser = Intent.createChooser(sendIntent, "Choose Your Browser");
//                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(chooser);
//                    }
        } else {
            Log.d(TAG, "onCreate: uri == null");
            stringAction = intent.getAction();
            stringType = intent.getType();
            Log.v(TAG, (String) ("action " + stringAction));
            Log.v(TAG, (String) ("type " + stringType));
            Log.d(TAG, "onCreate urlText: " + urlText);
            Intent intent2 = new Intent();
            intent2.setAction(stringAction);
            intent2.setDataAndType(intent.getData(), stringType);
            Intent newInt = Intent.createChooser(intent2, urlText);
            //startActivity(cleanIntent(newInt));
            this.startActivity(Intent.createChooser(cleanIntent(intent2), "Open " + urlText));
            //    this.startActivity(Intent.createChooser(cleanIntent(intent2), "Open " + urlText));
        }

        this.finish();

    }

    private Intent cleanIntent(Intent passedIntent) {
        //Intent myIntent= passedIntent.getSelector();
        List<ResolveInfo> launchables = getPackageManager().queryIntentActivities(passedIntent, MATCH_ALL);
        for (ResolveInfo resInfo : launchables) {
            Log.d(TAG, "cleanIntent: " + resInfo.resolvePackageName);
            if (resInfo.resolvePackageName != null) {
                if (resInfo.resolvePackageName.equalsIgnoreCase("com.thepriest.andrea.apppicker")) {
                    Log.d(TAG, "cleanIntent: cleaning...");
                    launchables.remove(resInfo);
                }
            }
        }
        return passedIntent;
    }

    private Uri findUrlInString(String urlText) {

        int indexOfUrl = urlText.toLowerCase().indexOf(HttpVersion.HTTP.toLowerCase());
        if (indexOfUrl == -1)
            return null;
        else {
            String containsURL = urlText.substring(indexOfUrl);

            int endOfUrl = containsURL.indexOf(" ");

            String url;
            if (endOfUrl != -1) {
                url = containsURL.substring(0, endOfUrl);
            } else {
                url = containsURL;
            }

            return Uri.parse(url);
        }
    }

    private void startIntentFromResolveInfo(ResolveInfo resolveInfo) {
//        if (this.autoStart != null) {
//            this.autoStart.cancel();
//        }
        ActivityInfo ai = resolveInfo.activityInfo;
        Intent intent = new Intent(getIntent());
        intent.addFlags(50331648);
        intent.setComponent(new ComponentName(ai.applicationInfo.packageName, ai.name));
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            intent.setComponent(null);
            intent.setPackage(ai.packageName);
            startActivity(intent);
        }
        finish();
    }

    private Intent makeMyIntent() {
        Intent intent = new Intent(getIntent());
        intent.setComponent(null);
        intent.setFlags(intent.getFlags() & -8388609);
        return intent;
    }

    private void launchIntent() {
        Intent launchIntent = getIntent();
        // this.original = makeMyIntent();
        Intent intent = new Intent("android.intent.action.VIEW");
        String urlText = launchIntent.getDataString();
        //Uri uri = findUrlInString(urlText);
        if (urlText == null) urlText = "";
        Uri uri = Uri.parse(urlText);
        if (uri != null) {
            Log.d(TAG, "launchIntent urlText: " + urlText);
            intent.setDataAndType(uri, "text/html");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setDataAndType(launchIntent.getData(), launchIntent.getType());
/*
        int id = -1;
        PackageManager packageManager = getPackageManager();
        try {
            id = getPackageManager().getActivityInfo(getComponentName(), PackageManager.MATCH_ALL).metaData.getInt("id", -1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
*/
            //this.item = getHandleItem(id);
            PackageManager packageManager = getPackageManager();
            intent=cleanIntent(intent);
            List<ResolveInfo> resInfo = packageManager.queryIntentActivities(intent, MATCH_ALL);
            Log.d(TAG, "launchIntent resInfo.size(): " + resInfo.size());
            for (ResolveInfo ress : resInfo){
                Log.d(TAG, "launchIntent: " + ress.toString());
            }

            if (resInfo.size() == 1) {
                Log.d(TAG, "launchIntent: " + resInfo.get(0).toString());
                Toast.makeText(this, "No application found to open the selected file", Toast.LENGTH_LONG).show();
                finish();
            }
            if (resInfo.size() == 2) {
                Log.d(TAG, "launchIntent: resInfo.size() == 2" + resInfo.get(1).toString());
                ResolveInfo resolveInfo = (ResolveInfo) resInfo.get(0);
                if (resolveInfo.activityInfo.packageName.equals(getPackageName())) {
                    startIntentFromResolveInfo((ResolveInfo) resInfo.get(1));
                } else {
                    startIntentFromResolveInfo(resolveInfo);
                }
                finish();
            }
                    Intent chooserIntent=Intent.createChooser(intent,"ggggggggg");
                    //chooserIntent=cleanIntent(chooserIntent);
                    chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

                    startActivity(chooserIntent);
                    return;
        }
    }
}
