package service;


import android.content.Intent;
import android.os.IBinder;

import com.allenliu.versionchecklib.core.AVersionService;

import javabean.UpdateInfo;
import utils.DSLConnections;

/**更新app service
 * Created by dwb on 2017/8/17.
 */

public class DownLoadAppService extends AVersionService {
    // 更新版本要用到的一些信息
    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private UpdateInfo info;
    public DownLoadAppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onResponses(AVersionService service, String response) {
        service.showVersionDialog(DSLConnections.APP_ULR, "检测到新版本请及时更新","必须更新哦!");
    }

}
