package com.pitaka.app.pitaka;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.MainActivity.isUpdated;
import static com.pitaka.app.pitaka.MainActivity.listDataHeader;

import static com.pitaka.app.pitaka.MainActivity.listDataItems;
import static java.lang.System.err;



public class Sinhala extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    HashMap<String, List<String>> listDataChild;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sinhala, container, false);


        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.listExView);

        // preparing list data

        prepareListData();


        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild); //here i'm getting an error now

        expListView.setAdapter(listAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void prepareListData(){

        listDataChild = new HashMap<String, List<String>>();

        if(!isUpdated){

            //initial view
        }

        else {
            int i=0;
            while (i<(listDataHeader.size())){
                List<String> detail = new ArrayList<String>();
                detail.add(listDataItems.get(i));
                listDataChild.put(listDataHeader.get(i), detail);
                i++;
            }
        }



    }






}
