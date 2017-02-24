package cxy.com.waterviewdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cxy.com.waterviewdemo.R;

public class MyTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_text_view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
