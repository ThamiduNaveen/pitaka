package com.pitaka.app.pitaka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.MainActivity.isUpdated;
import static com.pitaka.app.pitaka.MainActivity.listData2Header;
import static com.pitaka.app.pitaka.MainActivity.listData2Items;

import static com.pitaka.app.pitaka.Sinhala.expandList;
import static com.pitaka.app.pitaka.Sinhala.setPosition;


public class Paali extends Fragment {

    ExpandableListAdapter listAdapter;
    static ExpandableListView expPaliListView;

    HashMap<String, List<String>> listDataChild;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paali, container, false);


        // get the listview
        expPaliListView = (ExpandableListView) view.findViewById(R.id.listExView);

        // preparing list data

        prepareListData();


        listAdapter = new ExpandableListAdapter(getContext(), listData2Header, listDataChild); //here i'm getting an error now

        expPaliListView.setAdapter(listAdapter);





        expPaliListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                expandList(groupPosition);
                if(groupPosition != previousItem )
                    expPaliListView.collapseGroup(previousItem );
                previousItem = groupPosition;

            }




        });



        return view;
    }

    public static void expandPaliList(int position) {
        if(isUpdated){
            expPaliListView.expandGroup(position,true);
        }
    }

    public void prepareListData(){

        listDataChild = new HashMap<String, List<String>>();

        if(!isUpdated){

            //initial view
        }

        else {
            int i=0;
            while (i<(listData2Header.size())){
                List<String> detail = new ArrayList<String>();
                detail.add(listData2Items.get(i));
                listDataChild.put(listData2Header.get(i), detail);
                i++;

            }

        }



    }


}
