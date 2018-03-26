package application;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dwb on 2018/3/26.
 */

public class DSLApplication extends Application{
    private List<Activity> activities = new ArrayList<Activity>();
    // 建立请求队列
    public static RequestQueue queue;
    private static DSLApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    /**
     * Volley请求队列
     * @return
     */
    public static RequestQueue getHttpQueue() {
        return queue;
    }
    public static DSLApplication getInstance() {
        return app;
    }
    public void addActivity(Activity activity) {
        activities.add(activity);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();

        for (Activity activity : activities) {
            activity.finish();
        }
        System.exit(0);
    }
}
