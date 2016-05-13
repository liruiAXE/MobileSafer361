package org.qiaoer.mobilesafer361;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.qiaoer.mobilesafer361.welcome.moudle.adapter.HomeAdapter;

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {
    private GridView homeGridView;
    private long mExitTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);


        initView();
    }

    private void initView() {
        homeGridView = (GridView) findViewById(R.id.homeGridView);

        homeGridView.setAdapter(new HomeAdapter(this));
        homeGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case HomeAdapter.SAFE://手机防盗
                /*if (isSetUpPassword()) {
                    //弹出输入密码对话框
                    showInterPasswordDialog();
                } else {
                    //弹出设置密码对话框
                    showSetUpPasswordDialog();
                }*/
                break;
            case HomeAdapter.CALLMSGSAFE://通讯卫士
//                jumpToActivity(SecurityPhoneActivity.class);
                break;

            case HomeAdapter.APP://软件管家
//                jumpToActivity(AppManagerActivity.class);
                break;
            case HomeAdapter.TROJAN://病毒查杀
//                    jumpToActivity(VirusScanActivity.class);
                break;
            case HomeAdapter.SYSOPTIMIZE://缓存清理
//                jumpToActivity(CacheClearActivity.class);
                break;
            case HomeAdapter.TASKMANAGER://进程管理
//                jumpToActivity(ProcessManagerActivity.class);
                break;
            case HomeAdapter.NETMANAGER://流量统计
//                jumpToActivity(TrafficMonitoringActivity.class);
                break;
            case HomeAdapter.ATOOLS://高级工具
//                jumpToActivity(AdvancedToolsActivity.class);
                break;
            case HomeAdapter.SETTINGS://设置中心
//                jumpToActivity(SettingsActivity.class);
                break;
        }
    }

    public void jumpToActivity(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
