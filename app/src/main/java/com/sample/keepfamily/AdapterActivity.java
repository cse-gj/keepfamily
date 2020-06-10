package com.sample.keepfamily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.CustomViewHolder> {

    private ArrayList<DataActivity> arrayList;
    private Context context;

    public AdapterActivity(ArrayList<DataActivity> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_activity,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_latitude.setText(Double.toString(arrayList.get(position).getLatitude()));
        //System.out.println(arrayList.get(position).getLatitude());
        holder.tv_longitude.setText(Double.toString(arrayList.get(position).getLongitude()));
        holder.tv_range.setText(String.valueOf(arrayList.get(position).getRange()));

    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_latitude;
        TextView tv_longitude;
        TextView tv_range;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_latitude = itemView.findViewById(R.id.tv_latitude);
            this.tv_longitude = itemView.findViewById(R.id.tv_longitude);
            this.tv_range = itemView.findViewById(R.id.tv_range);

        }
    }
}
