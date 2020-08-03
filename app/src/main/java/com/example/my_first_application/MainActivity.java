package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/* 參考教學網頁 : https://codelabs.developers.google.com/codelabs/android-training-activity-lifecycle-and-state/index.html?index=..%2F..android-training#0 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public void onClickToLogin(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickToReleaseTask(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ReleaseTaskActivity.class);
        startActivity(intent);
    }

    public void onClickToShowTask(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ShowTask.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Log the start of the onCreate() method.
        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        /*Button button = (Button)findViewById(R.id.button_tmp);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}