package org.qiaoer.mobilesafer361.safe.moudle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.qiaoer.mobilesafer361.App;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = BootCompleteReceiver.class.getSimpleName();
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ((App) context.getApplicationContext()).correctSIM();
    }
}
