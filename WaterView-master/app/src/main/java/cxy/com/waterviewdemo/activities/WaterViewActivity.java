package cxy.com.waterviewdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import cxy.com.waterviewdemo.R;
import cxy.com.waterviewdemo.custom.WaterView;

public class WaterViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WaterView waterView;
    private Button btn_start;
    private Button btn_reset;
    private Button btn_stop;
    private Button btn_recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.water_view);
        initViw();

        waterView.setListener(new WaterView.Listener() {
            @Override
            public void finish() {
                Toast.makeText(WaterViewActivity.this, "已经满了！！！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViw() {
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_recover = (Button) findViewById(R.id.btn_recover);
        btn_reset.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_recover.setOnClickListener(this);
        waterView = (WaterView) findViewById(R.id.waterview);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                waterView.start();
                break;
            case R.id.btn_recover:
                waterView.recover();
                break;
            case R.id.btn_stop:
                waterView.stop();
                break;
            case R.id.btn_reset:
                waterView.reset();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
