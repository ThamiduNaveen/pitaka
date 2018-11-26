package com.pitaka.app.pitaka;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.MainActivity.isUpdated;
import static com.pitaka.app.pitaka.MainActivity.listData3Header;
import static com.pitaka.app.pitaka.MainActivity.listData3Items;
import static com.pitaka.app.pitaka.MainActivity.pathName;
import static com.pitaka.app.pitaka.Paali.collapseMiddle;
import static com.pitaka.app.pitaka.Paali.expandPaliList;
import static com.pitaka.app.pitaka.Paali.expandedMiddle;
import static com.pitaka.app.pitaka.Paali.paliCollaps;
import static com.pitaka.app.pitaka.Paali.previousItemM;
import static com.pitaka.app.pitaka.Sinhala.collapseLeft;
import static com.pitaka.app.pitaka.Sinhala.collapseList;
import static com.pitaka.app.pitaka.Sinhala.expandList;
import static com.pitaka.app.pitaka.Sinhala.expandedLeft;
import static com.pitaka.app.pitaka.Sinhala.previousItemL;


public class Sinhala2 extends Fragment {


    ExpandableListAdapter listAdapter;
    private static ExpandableListView expListViewSinhala2;
    public static int previousItemR = -1;

    public static int expandedRight = -1;

    public static int collapseRight = -1;

    TextView pathTag;

    HashMap<String, List<String>> listDataChild;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sinhala2, container, false);

        pathTag = (TextView) view.findViewById(R.id.path3);
        pathTag.setTextColor(Color.BLACK);


        // get the listview
        expListViewSinhala2 = (ExpandableListView) view.findViewById(R.id.listExView);

        // preparing list data

        prepareListData();


        listAdapter = new ExpandableListAdapter(getContext(), listData3Header, listDataChild); //here i'm getting an error now

        expListViewSinhala2.setAdapter(listAdapter);


        expListViewSinhala2.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                collapseRight = -1;
                expandedRight = groupPosition;
                if (expandedRight != expandedLeft) {
                    expandList(groupPosition);
                }
                if (expandedRight != expandedMiddle) {
                    expandPaliList(groupPosition);
                }


                if (previousItemR != -1 && groupPosition != previousItemR) {
                    expListViewSinhala2.collapseGroup(previousItemR);
                }
                previousItemR = groupPosition;
            }
        });

        expListViewSinhala2.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                collapseRight = i;
                expandedRight = -1;
                if (collapseRight != collapseLeft) {
                    collapseList(i);
                }
                if (collapseRight != collapseMiddle) {
                    paliCollaps(i);
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

    public static void sinhala2Expand(int position) {
        expListViewSinhala2.expandGroup(position, true);
    }

    public static void sinhala2Collaps(int position) {
        expListViewSinhala2.collapseGroup(position);
    }

    public void prepareListData() {

        listDataChild = new HashMap<String, List<String>>();

        if (!isUpdated) {

            //initial view
        } else {

            pathTag.setText(pathName);
            int i = 0;
            while (i < (listData3Header.size())) {
                List<String> detail = new ArrayList<String>();
                detail.add(listData3Items.get(i));
                listDataChild.put(listData3Header.get(i), detail);
                i++;
            }
        }
    }
}
