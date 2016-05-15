package org.qiaoer.mobilesafer361;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by qiaoer on 16/5/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        correctSIM();
    }

    public void correctSIM() {
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean protecting = sp.getBoolean("protecting", true);
        if (protecting) {
            //得到绑定的SIM卡号串
            String bindsim = sp.getString("sim", "");
            //得到手机现在的SIM卡号串
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //为了测试在手机序列号上dafa以模拟SIM卡被更换的情况
            String realsim = tm.getSimSerialNumber();
            if (bindsim.equals(realsim)) {
                Log.i("", "SIM卡未发生变化,还是您的手机");
            } else {
                Log.i("", "SIM卡变化了");

                //由于系统版本的原因,这里的发短信可能与其他手机版本不兼容
                String safeNumber = sp.getString("safephone", "");
                if (!TextUtils.isEmpty(safeNumber)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(safeNumber, null, "您的亲友手机的SIM卡已经被更换!", null, null);
                }
            }
        }
    }
}
