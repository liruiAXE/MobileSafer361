package org.qiaoer.mobilesafer361.welcome.moudle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.qiaoer.mobilesafer361.R;
import org.qiaoer.mobilesafer361.welcome.moudle.utils.MyUtils;
import org.qiaoer.mobilesafer361.welcome.moudle.utils.VersionUpdateUtils;

public class SplashActivity extends Activity {
    private TextView splashVersionTextView;
    private String mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        mVersion = MyUtils.getVersion(getApplicationContext());
        initView();

        final VersionUpdateUtils updateUtils = new VersionUpdateUtils(mVersion, this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateUtils.getCloudVersion();//获取服务器版本号
            }
        }).start();
    }

    private void initView() {
        splashVersionTextView = (TextView) findViewById(R.id.splashVersionTextView);
        splashVersionTextView.setText("版本号:" + mVersion);
    }
}
