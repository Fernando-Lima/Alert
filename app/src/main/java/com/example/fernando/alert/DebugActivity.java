package com.example.fernando.alert;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by fernando on 28/09/16.
 */

public class DebugActivity extends AppCompatActivity{
    protected static final String TAG = "ciclo";

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        Log.i(TAG,getLocalClassName()+".icicle");
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG,getLocalClassName()+".onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,getLocalClassName()+".onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,getLocalClassName()+".onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,getLocalClassName()+".onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,getLocalClassName()+".onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,getLocalClassName()+".onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,getLocalClassName()+".onDestroy");
    }
}
