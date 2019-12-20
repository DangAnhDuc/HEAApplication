package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heaapp.R;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    private int[] icons;
    String[] names;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int[] icons, String[] activityNames) {
        this.context = context;
        this.icons = icons;
        this.names = activityNames;
        inflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.spinner_item, null);
        ImageView icon = convertView.findViewById(R.id.icon);
        TextView activityName = convertView.findViewById(R.id.name);
        icon.setImageResource(icons[position]);
        activityName.setText(names[position]);
        return convertView;
    }
}
