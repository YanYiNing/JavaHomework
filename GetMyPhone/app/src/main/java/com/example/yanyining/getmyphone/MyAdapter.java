package com.example.yanyining.getmyphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by YanYiNing on 2016/12/2.
 */

public class MyAdapter extends BaseAdapter {
    private List<PhoneInfo> lists;
    private Context context;
    private RelativeLayout layout;

    public MyAdapter(List<PhoneInfo> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.people_info, null);
            holder = new ViewHolder();
            holder.nametv = (TextView) convertView.findViewById(R.id.textName);
            holder.numbertv = (TextView) convertView.findViewById(R.id.textNumber);
            holder.nametv.setText(lists.get(position).getName());
            holder.numbertv.setText(lists.get(position).getNumber());
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            holder.nametv.setText(lists.get(position).getName());
            holder.numbertv.setText(lists.get(position).getNumber());
        }
        return convertView;
    }
    public static class ViewHolder{
        TextView nametv;
        TextView numbertv;
    }
}
