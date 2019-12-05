package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heaapp.R;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    int icons[];
    String[] activityNames;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int[] icons, String[] activityNames) {
        this.context = context;
        this.icons = icons;
        this.activityNames = activityNames;
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
        ImageView icon =  convertView.findViewById(R.id.activity_icon);
        TextView activityName =  convertView.findViewById(R.id.activity_name);
        icon.setImageResource(icons[position]);
        activityName.setText(activityNames[position]);
        return convertView;
    }
}
