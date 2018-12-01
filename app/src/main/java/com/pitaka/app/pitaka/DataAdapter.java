package com.pitaka.app.pitaka;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> contents;

    public DataAdapter(ArrayList<String> contents) {
        this.contents = contents;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        if(i%2==0){
            viewHolder.tv_head.setText(contents.get(i));
            viewHolder.tv_content.setText(contents.get(i+1));
        }
        else {
            viewHolder.tv_head.setVisibility(View.GONE);
            viewHolder.tv_content.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return (contents.size()/2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_content;
        private TextView tv_head;
        public ViewHolder(View view) {
            super(view);

            tv_content = (TextView)view.findViewById(R.id.tv_content);
            tv_head = (TextView)view.findViewById(R.id.tv_head);

        }

    }



}
