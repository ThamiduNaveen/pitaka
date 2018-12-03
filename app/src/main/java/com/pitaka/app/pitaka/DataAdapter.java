package com.pitaka.app.pitaka;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> contents;
    private ArrayList<String> numbersArray = new ArrayList<String>();

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

        for (int num = 1; num <= 100; num++) {
            numbersArray.add(String.valueOf(num));
        }

        if (i % 2 == 0) {
            viewHolder.tv_head.setText(contents.get(i));

            String inStr = contents.get(i + 1);
            int startIndex = 0;
            int endIndex = 0;
            for (String numberStr : numbersArray) {
                if (inStr.contains(numberStr)) {
                    if (numberStr.length() == 1 && StringUtils.isNumeric("" + inStr.charAt(inStr.indexOf(numberStr) + 1))) {

                    } else {
                        if (inStr.indexOf(numberStr) > 0) {
                            if (numberStr.length() == 1 && StringUtils.isNumeric("" + inStr.charAt(inStr.indexOf(numberStr) - 1))) {
                                continue;
                            }
                        }
                        if (inStr.indexOf(numberStr) < inStr.indexOf("font")) {
                            Log.e("TEST", numberStr + " start");
                            startIndex = inStr.indexOf(numberStr);
                            if (startIndex > 0) {
                                if (StringUtils.isNumeric("" + inStr.charAt(startIndex - 1))) {
                                    startIndex--;
                                }
                            }
                        } else {
                            Log.e("TEST", numberStr + " end");
                            endIndex = inStr.indexOf(numberStr);
                            break;
                        }
                    }
                }
            }
            String outStr = "";
            if (endIndex == 0) {
                outStr = inStr.substring(startIndex);
            } else {
                outStr = inStr.substring(startIndex, endIndex - 1);
            }

            viewHolder.tv_content.setText(Html.fromHtml(outStr));
        } else {
            viewHolder.tv_head.setVisibility(View.GONE);
            viewHolder.tv_content.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return (contents.size() / 2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_content;
        private TextView tv_head;

        public ViewHolder(View view) {
            super(view);

            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_head = (TextView) view.findViewById(R.id.tv_head);

        }

    }


}
