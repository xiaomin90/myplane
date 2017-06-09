package cn.minjs.com.myplane.uitls;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by js.min on 2017/6/7.
 * mail : js.min@flyingwings.cn
 */

public class PlaneLog {

    public static SimpleDateFormat mDataFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public static void e(String tag,String content) {
        Log.e(tag,mDataFormat.format(new Date()) + content);
    }

    public static void d(String tag,String content) {
        Log.d(tag,mDataFormat.format(new Date()) + content);
    }

    public static void i(String tag,String content) {
        Log.i(tag,mDataFormat.format(new Date()) + content);
    }

    public static void v(String tag,String content) {
        Log.v(tag,mDataFormat.format(new Date()) + content);
    }

    public static  void w(String tag,String content) {
        Log.w(tag,mDataFormat.format(new Date()) + content);
    }
}
