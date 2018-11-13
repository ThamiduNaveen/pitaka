package com.pitaka.app.pitaka;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.MainActivity.isUpdated;
import static com.pitaka.app.pitaka.MainActivity.listData2Header;
import static com.pitaka.app.pitaka.MainActivity.listData2Items;

import static com.pitaka.app.pitaka.MainActivity.pathName;
import static com.pitaka.app.pitaka.Sinhala.collapseLeft;
import static com.pitaka.app.pitaka.Sinhala.collapseList;
import static com.pitaka.app.pitaka.Sinhala.expandList;
import static com.pitaka.app.pitaka.Sinhala.expandedLeft;
import static com.pitaka.app.pitaka.Sinhala.previousItemL;
import static com.pitaka.app.pitaka.Sinhala2.collapseRight;
import static com.pitaka.app.pitaka.Sinhala2.expandedRight;
import static com.pitaka.app.pitaka.Sinhala2.previousItemR;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Collaps;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Expand;


public class Paali extends Fragment {



    ExpandableListAdapter listAdapter;
    private static ExpandableListView expPaliListView;

    public static int expandedMiddle = -1;

    public static int collapseMiddle = -1;

    public static int previousItemM = -1;

    TextView pathTag;

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

        pathTag = (TextView) view.findViewById(R.id.path2);
        pathTag.setTextColor(Color.BLACK);


        // get the listview
        expPaliListView = (ExpandableListView) view.findViewById(R.id.listExView);

        // preparing list data

        prepareListData();


        listAdapter = new ExpandableListAdapter(getContext(), listData2Header, listDataChild); //here i'm getting an error now

        expPaliListView.setAdapter(listAdapter);


        expPaliListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override//middle
            public void onGroupExpand(int groupPosition) {

                collapseMiddle = -1;
                expandedMiddle = groupPosition;
                if (expandedMiddle != expandedLeft) {
                    expandList(groupPosition);
                }
                if (expandedMiddle != expandedRight) {
                    sinhala2Expand(groupPosition);
                }


                if (previousItemM != -1 && groupPosition != previousItemM) {
                    expPaliListView.collapseGroup(previousItemM);
                }
                previousItemM = groupPosition;

            }


        });
        expPaliListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                collapseMiddle = i;
                expandedMiddle = -1;
                if (collapseMiddle != collapseLeft) {
                    collapseList(i);
                }
                if (collapseMiddle != collapseRight) {
                    sinhala2Collaps(i);
                }


            }
        });


        return view;
    }

    public static void expandPaliList(int position) {

        expPaliListView.expandGroup(position, true);

    }

    public static void paliCollaps(int position) {
        expPaliListView.collapseGroup(position);
    }

    public void prepareListData() {

        listDataChild = new HashMap<String, List<String>>();

        if (!isUpdated) {

            //initial view
        } else {

            pathTag.setText(pathName);
            int i = 0;
            while (i < (listData2Header.size())) {
                List<String> detail = new ArrayList<String>();
                detail.add(listData2Items.get(i));
                listDataChild.put(listData2Header.get(i), detail);
                i++;

            }

        }


    }


}
