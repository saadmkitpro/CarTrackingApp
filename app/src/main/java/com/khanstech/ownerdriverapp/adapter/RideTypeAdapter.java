package com.khanstech.ownerdriverapp.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.khanstech.ownerdriverapp.R;

public class RideTypeAdapter extends BaseAdapter {

    private Context context;
    private String[] data;

    public RideTypeAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return this.data.length;
    }

    @Override
    public Object getItem(int i) {
        return this.data[i];
    }

    public String[] getAllItems() {
        return this.data;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_ride_type, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        holder.ridename.setText(getItem(i).toString());
        TypedArray imgs = context.getResources().obtainTypedArray(R.array.ride_type_img);
        holder.rideimg.setImageResource(imgs.getResourceId(i, 0));
        return view;
    }
}

class ViewHolder {
    protected TextView ridename;
    protected ImageView rideimg;

    ViewHolder(View v) {
        ridename = (TextView) v.findViewById(R.id.tv_ride_type);
        rideimg = (ImageView) v.findViewById(R.id.img_ride_type);
    }
}


