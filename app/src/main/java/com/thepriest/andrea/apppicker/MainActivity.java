package com.thepriest.andrea.apppicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        super.onCreate(bundle);
        Intent intent = this.getIntent();
        String string = intent.getAction();
        String string2 = intent.getType();
        Log.v((String)"action", (String)("" + string));
        Log.v((String)"type", (String)("" + string2));
        Intent intent2 = new Intent();
        intent2.setAction(string);
        intent2.setDataAndType(intent.getData(), string2);
        this.startActivity(Intent.createChooser((Intent)intent2, (CharSequence)"Open With"));
        this.finish();}
}
