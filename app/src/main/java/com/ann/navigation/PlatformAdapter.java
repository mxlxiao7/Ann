package com.ann.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ann.R;

import java.util.ArrayList;

/**
 * 功能区Listview Adapter
 * <p>
 * Author:maxiaolong
 * Date:2018/7/13
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class PlatformAdapter extends BaseAdapter {

    private ArrayList<String> mData = new ArrayList();
    private Context mContext;

    public PlatformAdapter(Context context) {
        this.mContext = context;
    }

    public PlatformAdapter(Context context, ArrayList data) {
        this.mContext = context;
        if (data != null && !data.isEmpty()) {
            this.mData = data;
        }
    }


    public void setData(ArrayList data) {
        if (data != null && !data.isEmpty()) {
            this.mData = data;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_item_platform, null);
            holder.img = view.findViewById(R.id.iv_logo);
            holder.info = view.findViewById(R.id.tv_info);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String name = mData.get(i);
        holder.info.setText(name);
        return view;
    }

    static class ViewHolder {
        public ImageView img;
        public TextView info;
    }

}
