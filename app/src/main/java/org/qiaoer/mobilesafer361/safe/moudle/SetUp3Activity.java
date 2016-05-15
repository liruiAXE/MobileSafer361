package org.qiaoer.mobilesafer361.safe.moudle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.qiaoer.mobilesafer361.R;

public class SetUp3Activity extends BaseSetUpActivity implements View.OnClickListener {
    private EditText mInputPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initView();
    }

    private void initView() {
        ((RadioButton) findViewById(R.id.thirdRadioButton)).setChecked(true);
        findViewById(R.id.addContactButton).setOnClickListener(this);

        mInputPhoneEditText = (EditText) findViewById(R.id.inputPhoneEditText);
        String safeNumber = sp.getString("safephone", null);
        if (!TextUtils.isEmpty(safeNumber)) {
            mInputPhoneEditText.setText(safeNumber);
        }
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp2Activity.class);
    }

    @Override
    public void showNext() {
        String safeNumber = mInputPhoneEditText.getText().toString().trim();
        if (TextUtils.isEmpty(safeNumber)) {
            Toast.makeText(this, "请输入安全号码", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safephone", safeNumber);
        editor.commit();
        startActivityAndFinishSelf(SetUp4Activity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addContactButton:
                startActivityForResult(new Intent(this, ContactSelectActivity.class), 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    String phone = data.getStringExtra("phone");
                    mInputPhoneEditText.setText(phone);
                }
                break;
        }
    }
}
