package org.qiaoer.mobilesafer361.safe.moudle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.qiaoer.mobilesafer361.R;

public class SetUp4Activity extends BaseSetUpActivity {
    private TextView mStatusTextView;
    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        initView();
    }

    private void initView() {
        ((RadioButton) findViewById(R.id.fourthRadioButton)).setChecked(true);
        mStatusTextView = (TextView) findViewById(R.id.setup4StatusTextView);
        mToggleButton = (ToggleButton) findViewById(R.id.securityFunctionToggleButton);
        boolean protecting = sp.getBoolean("protecting", true);
        if (protecting) {
            mStatusTextView.setText("防盗保护已经开启");
            mToggleButton.setChecked(true);
        } else {
            mStatusTextView.setText("防盗保护还未开启");
            mToggleButton.setChecked(false);
        }

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mStatusTextView.setText("防盗保护已经开启");
                } else {
                    mStatusTextView.setText("防盗保护还未开启");
                }
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("protecting", isChecked);
                editor.commit();
            }
        });

    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp3Activity.class);
    }

    @Override
    public void showNext() {
        //跳转至防盗保护页面
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isSetUp", true);
        editor.commit();
        startActivityAndFinishSelf(LostFindActivity.class);
    }
}
