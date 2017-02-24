package cxy.com.waterviewdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cxy.com.waterviewdemo.R;
import cxy.com.waterviewdemo.custom.MyTopBar;

public class MyTopBarActivity extends AppCompatActivity {

    private MyTopBar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_top_bar);

        myToolBar = (MyTopBar) findViewById(R.id.myTopBar);
        myToolBar.setButtonClickListener(new MyTopBar.MyToolbarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(MyTopBarActivity.this, "左键被点击", Toast.LENGTH_LONG).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(MyTopBarActivity.this, "右键被点击", Toast.LENGTH_LONG).show();
            }
        });

//        myToolBar.setButtonVisble(MyToolBar.TAG_RIGHT_BUTTON, View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
