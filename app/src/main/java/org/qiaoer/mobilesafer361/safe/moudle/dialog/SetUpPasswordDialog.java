package org.qiaoer.mobilesafer361.safe.moudle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.qiaoer.mobilesafer361.R;

/**
 * Created by qiaoer on 16/5/14.
 */
public class SetUpPasswordDialog extends Dialog implements View.OnClickListener {
    private TextView mTitleTextView;//对话框标题
    private EditText mFirstPasswordEditText;//第一个密码框
    private EditText mConfirmPasswordEditText;//确认密码框

    private Button okButton;
    private Button cancelButton;

    private MyCallBack myCallBack;//回调接口


    public SetUpPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);//引入自定义对话框样式
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_password_dialog);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTitleTextView = (TextView) findViewById(R.id.setupPasswordTitleTextView);
        mFirstPasswordEditText = (EditText) findViewById(R.id.firstPasswordEditText);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.secondConfirmPasswordEditText);

        okButton = (Button) findViewById(R.id.setupPasswordOkButton);
        cancelButton = (Button) findViewById(R.id.setupPasswordCancelButton);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    public void setMyCallBack(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
    }

    /**
     * 设置对话框标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setupPasswordOkButton:
                myCallBack.ok();
                break;
            case R.id.setupPasswordCancelButton:
                myCallBack.cancel();
                break;
            default:
                break;
        }
    }

    public interface MyCallBack {
        void ok();

        void cancel();
    }
}
