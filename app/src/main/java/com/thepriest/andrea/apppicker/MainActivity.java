package com.thepriest.andrea.apppicker;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
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
    private static final int GET_CONTENT_RESULT_CODE = 1001;
    private String sAction;

    @Override
    protected void onCreate(Bundle bundle) {
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
        String stringAction = null;
        String stringType = null;
        Intent chooseIntent = null;
        Intent newInt = null;
        Intent intent = this.getIntent();
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate: " + intent.toString());
        stringAction = intent.getAction();
        stringType = intent.getType();
        Log.v(TAG, "action " + stringAction);
        Log.v(TAG, "type " + stringType);
        String urlText = intent.getDataString();
        //Uri uri = findUrlInString(urlText);
        if (urlText == null) urlText = "";
        Uri uri = Uri.parse(urlText);
        if (uri != null) {
            sAction = intent.getAction();
            if (sAction.equalsIgnoreCase("android.media.action.STILL_IMAGE_CAMERA")) {
                chooseIntent = new Intent(sAction, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent, getString(R.string.app_name));
            } else if (sAction.equalsIgnoreCase(Intent.ACTION_PICK)) {
                chooseIntent = new Intent(sAction, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent,/* i.getAction() + " " +*/ chooseIntent.getType());
            } else if (sAction.equalsIgnoreCase("android.intent.action.GET_CONTENT")) {
                if (BuildConfig.DEBUG) Log.d(TAG, "onCreate: GET_CONTENT");
/*
                chooseIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                chooseIntent.addCategory(Intent.CATEGORY_OPENABLE);
                chooseIntent.setType(stringType);
                this.startActivityForResult(chooseIntent, GET_CONTENT_RESULT_CODE);
                    return;
*/
                if (Build.VERSION.SDK_INT < 190) {
                    chooseIntent = new Intent();
                    chooseIntent.setType(stringType);
                    chooseIntent.setAction(Intent.ACTION_GET_CONTENT);
                    chooseIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    //startActivityForResult(chooseIntent, GET_CONTENT_RESULT_CODE);
                } else {
                    chooseIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    chooseIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    chooseIntent.setType(stringType);
                    //chooseIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    startActivityForResult(chooseIntent, GET_CONTENT_RESULT_CODE);
                    return;
                }
                newInt = Intent.createChooser(chooseIntent,/* i.getAction() + " " +*/ chooseIntent.getType());
//                chooseIntent = new Intent(Intent.ACTION_GET_CONTENT, uri);
/*
               // chooseIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
*/
//chooseIntent.setType(stringType);
                //chooseIntent.setDataAndType(intent.getData(), stringType);
/*
                newInt = Intent.createChooser(chooseIntent,*/
/* i.getAction() + " " +*//*
 chooseIntent.getType());
*/
            } else if (sAction.equalsIgnoreCase("android.media.action.IMAGE_CAPTURE")) {
                chooseIntent = new Intent(sAction, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent, getString(R.string.app_name));
            } else if (sAction.equalsIgnoreCase("android.media.action.VIDEO_CAPTURE")) {
                chooseIntent = new Intent(sAction, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent, getString(R.string.app_name));
            } else if (sAction.equalsIgnoreCase("com.whatsapp.action.WHATSAPP_RECORDING")) {
                chooseIntent = new Intent(sAction, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent,/* i.getAction() + " " +*/ chooseIntent.getType());
            } else if (sAction.equalsIgnoreCase("android.intent.action.RINGTONE_PICKER")) {
                chooseIntent = new Intent(sAction, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent, getString(R.string.app_name));
            } else if (sAction.equalsIgnoreCase(Intent.ACTION_SENDTO)) {
                chooseIntent = new Intent(Intent.ACTION_VIEW, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent,/* i.getAction() + " " +*/ chooseIntent.getType());
            } else if (sAction.equalsIgnoreCase(Intent.ACTION_EDIT)) {
                chooseIntent = new Intent(Intent.ACTION_EDIT, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent,/* i.getAction() + " " +*/ chooseIntent.getType());
            } else if (sAction.equalsIgnoreCase("org.openintents.action.VIEW_DIRECTORY")) {
                chooseIntent = new Intent(sAction, uri);
                chooseIntent.setDataAndType(intent.getData(), stringType);
                newInt = Intent.createChooser(chooseIntent, urlText);
            } else if (sAction.equalsIgnoreCase(Intent.ACTION_VIEW)) {
                chooseIntent = new Intent(Intent.ACTION_VIEW, uri);
                if (urlText.startsWith("http")) {
                    if (BuildConfig.DEBUG) Log.d(TAG, "onCreate: http");
                    chooseIntent.setDataAndType(uri, "text/html");
                    chooseIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    chooseIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                    chooseIntent.setClipData(intent.getClipData());
                    chooseIntent.putExtras(intent);
                    //chooseIntent.fillIn(intent,0);
                    //chooseIntent.setSelector(intent);
//i.putExtra(Intent.EXTRA_TEXT,urlText);
                    //i.setData(uri);
                   } else {
                    if (BuildConfig.DEBUG) Log.d(TAG, "onCreate: NO http");
                    chooseIntent.setDataAndType(intent.getData(), stringType);
                }
                chooseIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                newInt = Intent.createChooser(chooseIntent, urlText);
 /*
  newInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                newInt.setClipData(intent.getClipData());
                newInt.putExtras(intent);
*/
            } else {
                Toast.makeText(this, sAction, Toast.LENGTH_SHORT).show();
                if (BuildConfig.DEBUG) Log.d(TAG, "onCreate ELSE sAction: " + sAction);
            }
        }
        if (sAction.equalsIgnoreCase("android.intent.action.GET_CONTENT")) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onCreate: android.intent.action.GET_CONTENT");
            //chooseIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            newInt.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            //startActivityForResult(newInt,GET_CONTENT_RESULT_CODE);
            startActivity(newInt);
            //startActivity(newInt);
        } else if (sAction.equalsIgnoreCase(Intent.ACTION_OPEN_DOCUMENT)) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onCreate: Intent.ACTION_OPEN_DOCUMENT");
            //chooseIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            //startActivityForResult(chooseIntent,GET_CONTENT_RESULT_CODE);
        } else startActivity(newInt);
        this.finish();
        /**
         * return
         */
        if (true) return;
        if (uri != null) {
            Intent i = null;
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_PICK) || intent.getAction().equalsIgnoreCase("android.intent.action.RINGTONE_PICKER") || intent.getAction().equalsIgnoreCase("android.media.action.STILL_IMAGE_CAMERA") || intent.getAction().equalsIgnoreCase("android.media.action.IMAGE_CAPTURE"))
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
                //i.fillIn(intent,0);
                //i.setSelector(intent);
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
            newInt = null;
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_PICK) || intent.getAction().equalsIgnoreCase("android.intent.action.RINGTONE_PICKER") || intent.getAction().equalsIgnoreCase("android.media.action.STILL_IMAGE_CAMERA") || intent.getAction().equalsIgnoreCase("android.media.action.IMAGE_CAPTURE"))
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
            newInt = Intent.createChooser(intent2, urlText);
            //startActivity(cleanIntent(newInt));
            this.startActivity(Intent.createChooser(cleanIntent(intent2), "Open " + urlText));
            //    this.startActivity(Intent.createChooser(cleanIntent(intent2), "Open " + urlText));
        }

        this.finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + intent + "]");
        switch (requestCode) {
            case GET_CONTENT_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = intent.getData().getPath();
                    //Toast.makeText(this, "GET_CONTENT: " + FilePath, Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "onActivityResult: " + FilePath);
                    this.setResult(resultCode, intent);
                } else if (resultCode == RESULT_CANCELED) {
                    // Operation failed or cancelled. Handle in your own way.
                    //Toast.makeText(this, "GET_CONTENT resultCode: RESULT_CANCELED", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        finish();
    }

    protected void onActivityResult2(int requestCode, int resultCode, Intent intent) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "onActivityResult2() called with: n = [" + requestCode + "], n2 = [" + resultCode + "], intent = [" + intent + "]");
        if (requestCode == GET_CONTENT_RESULT_CODE) {
            this.setResult(resultCode, intent);
            //this.finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart() called");
        super.onStart();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() called");
        super.onPause();
    }

    private void startActivityWithLog(Intent intent) {

    }

    private Intent cleanIntent(Intent passedIntent) {
        //Intent myIntent= passedIntent.getSelector();
        List<ResolveInfo> launchables = getPackageManager().queryIntentActivities(passedIntent, MATCH_ALL);
        for (ResolveInfo resInfo : launchables) {
            if (BuildConfig.DEBUG) Log.d(TAG, "cleanIntent: " + resInfo.resolvePackageName);
            if (resInfo.resolvePackageName != null) {
                if (resInfo.resolvePackageName.equalsIgnoreCase("com.thepriest.andrea.apppicker")) {
                    if (BuildConfig.DEBUG) Log.d(TAG, "cleanIntent: cleaning...");
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
            intent = cleanIntent(intent);
            List<ResolveInfo> resInfo = packageManager.queryIntentActivities(intent, MATCH_ALL);
            Log.d(TAG, "launchIntent resInfo.size(): " + resInfo.size());
            for (ResolveInfo ress : resInfo) {
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
            Intent chooserIntent = Intent.createChooser(intent, "ggggggggg");
            //chooserIntent=cleanIntent(chooserIntent);
            chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

            startActivity(chooserIntent);
            return;
        }
    }
}
