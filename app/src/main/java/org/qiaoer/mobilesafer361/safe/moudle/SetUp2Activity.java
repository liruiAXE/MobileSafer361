package org.qiaoer.mobilesafer361.safe.moudle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import org.qiaoer.mobilesafer361.R;

public class SetUp2Activity extends BaseSetUpActivity implements View.OnClickListener {
    private TelephonyManager mTelephonyManager;
    private Button mBindSIMButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        initView();
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp1Activity.class);
    }

    @Override
    public void showNext() {
        if (!isBind()) {
            Toast.makeText(this, "您还没有绑定SIM卡!", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivityAndFinishSelf(SetUp3Activity.class);
    }

    private void initView() {
        //设置第二个小圆点的颜色
        ((RadioButton) findViewById(R.id.secondRadioButton)).setChecked(true);
        mBindSIMButton = (Button) findViewById(R.id.bindSimButton);
        mBindSIMButton.setOnClickListener(this);

        if (isBind()) {
            mBindSIMButton.setEnabled(false);
        } else {
            mBindSIMButton.setEnabled(true);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bindSimButton:
                //绑定SIM卡
                bindSIM();
                break;
            default:
                break;
        }
    }

    /**
     * 绑定SIM卡
     */
    private void bindSIM() {
        if (!isBind()) {
            String simSerialNumber = mTelephonyManager.getSimSerialNumber();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("sim", simSerialNumber);
            editor.commit();
            Toast.makeText(this, "SIM卡绑定成功!", Toast.LENGTH_SHORT).show();
        } else {
            //已经绑定,提醒用户
            Toast.makeText(this, "SIM卡已经绑定!", Toast.LENGTH_SHORT).show();
        }
        mBindSIMButton.setEnabled(false);
    }

    private boolean isBind() {
        String simString = sp.getString("sim", null);
        if (TextUtils.isEmpty(simString)) {
            return false;
        }
        return true;
    }


}
