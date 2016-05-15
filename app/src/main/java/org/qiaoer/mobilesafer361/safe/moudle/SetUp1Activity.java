package org.qiaoer.mobilesafer361.safe.moudle;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import org.qiaoer.mobilesafer361.R;

public class SetUp1Activity extends BaseSetUpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        initView();
    }

    private void initView() {
        ((RadioButton) findViewById(R.id.firstRadioButton)).setChecked(true);
    }

    @Override
    public void showPre() {
        Toast.makeText(this, "当前页面已经是第一页", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNext() {
        startActivityAndFinishSelf(SetUp2Activity.class);
    }
}
