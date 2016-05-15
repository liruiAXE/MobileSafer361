package org.qiaoer.mobilesafer361.safe.moudle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.qiaoer.mobilesafer361.R;
import org.qiaoer.mobilesafer361.safe.moudle.adapter.ContactAdapter;
import org.qiaoer.mobilesafer361.safe.moudle.entity.ContactInfo;
import org.qiaoer.mobilesafer361.safe.moudle.utils.ContactInfoParser;

import java.util.List;

/**
 * Created by qiaoer on 16/5/15.
 */
public class ContactSelectActivity extends Activity implements View.OnClickListener {
    private ListView mListView;
    private static final int DISPLAY_CONTACTS = 0x1;
    private ContactAdapter mContactAdapter;
    private List<ContactInfo> systemContacts;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DISPLAY_CONTACTS:
                    if (systemContacts != null) {

                        mContactAdapter = new ContactAdapter(systemContacts, ContactSelectActivity.this);
                        mListView.setAdapter(mContactAdapter);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_select);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.titleBarTextView)).setText("选择联系人");
        ImageView titleBarLeftImageView = (ImageView) findViewById(R.id.titleBarLeftImageView);
        titleBarLeftImageView.setOnClickListener(this);
        titleBarLeftImageView.setImageResource(R.mipmap.back);
        //设置导航栏颜色
        findViewById(R.id.titlebarRelativeLayout).setBackgroundColor(getResources().getColor(R.color.purple));

        mListView = (ListView) findViewById(R.id.contactListView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                systemContacts = ContactInfoParser.getSystemContact(ContactSelectActivity.this);
                systemContacts.addAll(ContactInfoParser.getSIMContacts(ContactSelectActivity.this));
                mHandler.sendEmptyMessage(DISPLAY_CONTACTS);
            }
        }).start();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactInfo item = (ContactInfo) mContactAdapter.getItem(i);
                Intent intent = new Intent();
                intent.putExtra("phone", item.phone);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleBarLeftImageView:
                finish();
                break;
        }
    }
}
