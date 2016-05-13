package org.qiaoer.mobilesafer361.welcome.moudle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.qiaoer.mobilesafer361.R;

/**
 * Created by qiaoer on 16/5/13.
 */
public class HomeAdapter extends BaseAdapter {
    public static final int SAFE = 0;
    public static final int CALLMSGSAFE = 1;
    public static final int APP = 2;
    public static final int TROJAN = 3;
    public static final int SYSOPTIMIZE = 4;
    public static final int TASKMANAGER = 5;
    public static final int NETMANAGER = 6;
    public static final int ATOOLS = 7;
    public static final int SETTINGS = 8;


    private Context mContext;

    public HomeAdapter(Context context) {
        this.mContext = context;
    }

    private int[] imageId = {
            R.mipmap.safe, R.mipmap.callmsgsafe, R.mipmap.app,
            R.mipmap.trojan, R.mipmap.sysoptimize, R.mipmap.taskmanager,
            R.mipmap.netmanager, R.mipmap.atools, R.mipmap.settings,
    };
    private String[] names = {"手机防盗", "通讯卫士", "软件管家",
            "手机杀毒", "缓存清理", "进程管理",
            "流量统计", "高级工具", "设置中心"
    };

    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public Object getItem(int i) {
        return names[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_home, null);
            viewHolder.homeItemIconImageView = (ImageView) view.findViewById(R.id.homeItemIconImageView);
            viewHolder.homeItemTextView = (TextView) view.findViewById(R.id.homeItemTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.homeItemIconImageView.setImageResource(imageId[i]);
        viewHolder.homeItemTextView.setText(names[i]);
        return view;
    }

    class ViewHolder {
        ImageView homeItemIconImageView;
        TextView homeItemTextView;
    }
}
