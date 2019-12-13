package com.ysl.stepone.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.stepone.R;

/**
 * FileName: MyTableViewAdatper
 * Author: y.liang
 * Date: 2019-12-13 14:58
 */

public class MyTableViewAdatper extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_test, parent, false);
        return new RecyclerView.ViewHolder(view){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.title_view);
        textView.setText("row index is " + position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
