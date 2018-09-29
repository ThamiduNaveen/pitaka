package com.pitaka.app.pitaka;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.MainActivity.isUpdated;
import static com.pitaka.app.pitaka.MainActivity.listDataHeader;
import static com.pitaka.app.pitaka.MainActivity.listDataItems;
import static com.pitaka.app.pitaka.Paali.expPaliListView;
import static com.pitaka.app.pitaka.Paali.expandPaliList;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Collaps;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Expand;


public class Sinhala extends Fragment {


    ExpandableListAdapter listAdapter;
    public static ExpandableListView expListView;
    Button button;
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


        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);




        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                expPaliListView.expandGroup(groupPosition, true);
                sinhala2Collaps(previousItem);
                sinhala2Expand(groupPosition);

                if(groupPosition != previousItem ) {
                    expListView.collapseGroup(previousItem);
                    previousItem = groupPosition;
                    setPosition = groupPosition;
                }
            }
        });

        return view;
    }

    public static void expandList(int position) {
        if(isUpdated){
            expListView.expandGroup(position,true);
        }
    }
    public static void collapseList(int position){
        expListView.collapseGroup(position);
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
