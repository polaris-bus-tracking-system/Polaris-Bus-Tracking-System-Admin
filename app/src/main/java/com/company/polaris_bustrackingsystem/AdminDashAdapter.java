package com.company.polaris_bustrackingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdminDashAdapter extends ArrayAdapter<AdminDash> {

    public AdminDashAdapter(@NonNull Context context, ArrayList<AdminDash> list){
        super(context,0,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_card,parent,false);
        }

        AdminDash adminDash = getItem(position);
        TextView tvAdminDash = listItemView.findViewById(R.id.tvAdminDash);
        ImageView ivAdminDash = listItemView.findViewById(R.id.ivAdminDash);

        tvAdminDash.setText(adminDash.getName());
        ivAdminDash.setImageResource(adminDash.getImg());

        return listItemView;
    }
}
