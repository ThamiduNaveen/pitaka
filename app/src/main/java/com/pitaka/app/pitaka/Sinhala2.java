package com.pitaka.app.pitaka;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.MainActivity.isUpdated;
import static com.pitaka.app.pitaka.MainActivity.listData3Header;
import static com.pitaka.app.pitaka.MainActivity.listData3Items;
import static com.pitaka.app.pitaka.Sinhala.expandList;



public class Sinhala2 extends Fragment {


    ExpandableListAdapter listAdapter;
    static ExpandableListView expListView;
    int previousItem = -1;
    public static int setPosition=0;

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


        listAdapter = new ExpandableListAdapter(getContext(), listData3Header, listDataChild); //here i'm getting an error now

        expListView.setAdapter(listAdapter);



        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override
            public void onGroupExpand(int groupPosition) {
//                expandList(groupPosition);
                if(groupPosition != previousItem ) {
                    expListView.collapseGroup(previousItem);
                    previousItem = groupPosition;
                    setPosition = groupPosition;
                }
            }
        });


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
    public static void sinhala2Expand(int position){
        expListView.expandGroup(position,true);
    }

    public static void sinhala2Collaps(int position){
        expListView.collapseGroup(position);
    }

    public void prepareListData(){

        listDataChild = new HashMap<String, List<String>>();

        if(!isUpdated){

            //initial view
        }

        else {
            int i=0;
            while (i<(listData3Header.size())){
                List<String> detail = new ArrayList<String>();
                detail.add(listData3Items.get(i));
                listDataChild.put(listData3Header.get(i), detail);
                i++;

            }


        }



    }






}
