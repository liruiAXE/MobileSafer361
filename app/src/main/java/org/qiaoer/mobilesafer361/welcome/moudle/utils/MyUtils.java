package org.qiaoer.mobilesafer361.welcome.moudle.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

/**
 * Created by qiaoer on 16/5/13.
 */
public class MyUtils {
    /**
     * 获取版本号
     *
     * @param context
     * @return 返回版本号
     */
    public static String getVersion(Context context) {
        //PackageManager可以获取清单文件中的所有信息
        PackageManager packageManager = context.getPackageManager();
        try {
            //getPackageName()获取到当前程序的包名
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 安装新版本
     *
     * @param activity
     */
    public static void installAPK(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //添加默认分类
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //设置数据和类型
        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobilesafer3612.0.apk")), "application/vnd.android.package-archive");
        //如果开启的Activity退出时会调用当前Activity的onActivityResult
        activity.startActivityForResult(intent, 0);
    }
}
