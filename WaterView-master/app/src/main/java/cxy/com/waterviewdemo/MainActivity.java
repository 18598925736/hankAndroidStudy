package cxy.com.waterviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cxy.com.waterviewdemo.activities.MyTextViewActivity;
import cxy.com.waterviewdemo.activities.MyTopBarActivity;
import cxy.com.waterviewdemo.activities.WaterViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_water_view = (Button) findViewById(R.id.btn_water_view);
        Button btn_my_text_view = (Button) findViewById(R.id.btn_my_text_view);
        Button btn_my_top_bar = (Button) findViewById(R.id.btn_my_top_bar);

        btn_water_view.setOnClickListener(this);
        btn_my_text_view.setOnClickListener(this);
        btn_my_top_bar.setOnClickListener(this);
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_water_view:
                intent = new Intent(MainActivity.this, WaterViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_text_view:
                intent = new Intent(MainActivity.this, MyTextViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_top_bar:
                intent = new Intent(MainActivity.this, MyTopBarActivity.class);
                startActivity(intent);
                break;
        }
    }
}
