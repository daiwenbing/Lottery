package activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import application.DSLApplication;
import lottery.dwb.com.lottery.R;

public class MainActivity extends AppCompatActivity {
    private static Boolean mIsExit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DSLApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);
    }
    /**
     * 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mIsExit) {
                mIsExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
                return false;
            } else {
                DSLApplication.getInstance().onTerminate();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
