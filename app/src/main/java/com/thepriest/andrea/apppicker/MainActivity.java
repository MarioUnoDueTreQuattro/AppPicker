package com.thepriest.andrea.apppicker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpVersion;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
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


        Intent intent = this.getIntent();
        String urlText = intent.getDataString();
        Uri uri = Uri.parse(urlText);
        if (uri != null) {
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            //i.setData(uri);
            i.setDataAndType(uri, "text/html");
            // i.setType("text/plain");
            startActivity(Intent.createChooser(i, getString(R.string.open) + urlText));
//                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.maidofknowledge.com"));
//                    Intent chooser = Intent.createChooser(sendIntent, "Choose Your Browser");
//                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(chooser);
//                    }
        } else {
            String string = intent.getAction();
            String string2 = intent.getType();
            Log.v((String)"action", (String)("" + string));
            Log.v((String)"type", (String)("" + string2));
            Intent intent2 = new Intent();
            intent2.setAction(string);
            intent2.setDataAndType(intent.getData(), string2);
            this.startActivity(Intent.createChooser((Intent)intent2, (CharSequence)"Open With"));
        }

        this.finish();
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
}
