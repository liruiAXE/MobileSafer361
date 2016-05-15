package org.qiaoer.mobilesafer361.safe.moudle.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.telephony.TelephonyManager;

import org.qiaoer.mobilesafer361.safe.moudle.entity.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoer on 16/5/15.
 */
public class ContactInfoParser {
    public static List<ContactInfo> getSystemContact(Context context) {
        ContentResolver resolver = context.getContentResolver();

        //1. 查询raw_contacts表,把联系人的id取出来
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUril = Uri.parse("content://com.android.contacts/data");

        List<ContactInfo> infos = new ArrayList<>();
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            if (id != null) {
                System.out.println("联系人id:" + id);
                ContactInfo info = new ContactInfo();
                info.id = id;
                //2. 根据联系人的id,查询data表,把这个id的数据取出来
                //系统API查询data表的时候,不是真正的查询data表,而是查询data表的视图
                Cursor dataCursor = resolver.query(dataUril, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{id}, null);
                while (dataCursor.moveToNext()) {
                    String data1 = dataCursor.getString(0);
                    String mimetype = dataCursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        System.out.println("姓名:" + data1);
                        info.name = data1;
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                        System.out.println("电话:" + data1);
                        info.phone = data1;
                    }
                }
                infos.add(info);
                dataCursor.close();

            }

        }
        cursor.close();
        return infos;
    }

    public static List<ContactInfo> getSIMContacts(Context context) {
        List<ContactInfo> infos = new ArrayList<>();

        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = mTelephonyManager.getSimState();
        if (simState == TelephonyManager.SIM_STATE_READY) {
            Uri uri = Uri.parse("content://icc/adn");
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                ContactInfo contactInfo = new ContactInfo();
                String id = cursor.getString(cursor.getColumnIndex(Contacts.People._ID));
                String name = cursor.getString(cursor.getColumnIndex(Contacts.People.NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(Contacts.People.NUMBER));
                contactInfo.id = id;
                contactInfo.name = name;
                contactInfo.phone = phoneNumber;
                infos.add(contactInfo);
            }
            cursor.close();

        }
        return infos;
    }
}
