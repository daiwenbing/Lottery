package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  获取系统时间转换为
 * yyyy-MM-dd
 * yyyy-MM-dd HH:mm:ss
 * @author dwb
 * Created by dwb on 2016/11/18.
 */

public class DateChange {

//  String time;
//	public DateChange(String time) {
//		this.time=time;
//	}
    /**
     * 获取当前时间
     * @return
     */
    public String getTime1(){
        long time= System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date(time);
        String t1=format.format(d1);
        return t1;

    }
    /**
     * 获取当前时间
     * @return
     */
    public String getTime2(String time){
        //long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date d1=new Date(time);
        String t1=format.format(d1);
        return t1;
    }

}
