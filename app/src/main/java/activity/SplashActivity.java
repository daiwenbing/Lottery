package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import application.DSLApplication;
import lottery.dwb.com.lottery.R;

/**
 * Created by dwb on 2018/3/26.
 */

public class SplashActivity extends Activity {
    private static final int MILLI_TIME = 1000;
    private  Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DSLApplication.getInstance().addActivity(this);
        setContentView(R.layout.special_gride);
        view();
    }
    public void view(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                    intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
            }
        }, MILLI_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
