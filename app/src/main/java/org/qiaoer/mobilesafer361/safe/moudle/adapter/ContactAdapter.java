package org.qiaoer.mobilesafer361.safe.moudle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.qiaoer.mobilesafer361.R;
import org.qiaoer.mobilesafer361.safe.moudle.entity.ContactInfo;

import java.util.List;

/**
 * Created by qiaoer on 16/5/15.
 */
public class ContactAdapter extends BaseAdapter {
    private List<ContactInfo> mContactInfos;
    private Context mContext;

    public ContactAdapter(List<ContactInfo> contactInfos, Context context) {
        super();
        this.mContactInfos = contactInfos;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mContactInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mContactInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_contact_select, null);

            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.contactNameTextView);
            viewHolder.phoneTextView = (TextView) convertView.findViewById(R.id.contactPhoneTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTextView.setText(mContactInfos.get(position).name);
        viewHolder.phoneTextView.setText(mContactInfos.get(position).phone);
        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;
    }
}
