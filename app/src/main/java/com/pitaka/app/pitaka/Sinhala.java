package com.pitaka.app.pitaka;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Dimension;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.MainActivity.isUpdated;
import static com.pitaka.app.pitaka.MainActivity.listDataHeader;
import static com.pitaka.app.pitaka.MainActivity.listDataItems;
import static com.pitaka.app.pitaka.MainActivity.pathName;
import static com.pitaka.app.pitaka.Paali.collapseMiddle;
import static com.pitaka.app.pitaka.Paali.expandPaliList;
import static com.pitaka.app.pitaka.Paali.expandedMiddle;
import static com.pitaka.app.pitaka.Paali.paliCollaps;
import static com.pitaka.app.pitaka.Paali.previousItemM;
import static com.pitaka.app.pitaka.Sinhala2.collapseRight;
import static com.pitaka.app.pitaka.Sinhala2.expandedRight;
import static com.pitaka.app.pitaka.Sinhala2.previousItemR;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Collaps;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Expand;


public class Sinhala extends Fragment {


    ExpandableListAdapter listAdapter;
    private static ExpandableListView expListViewSinhal1;
    Button button;
    TextView pathTag;
    public static int previousItemL = -1;
    public static int expandedLeft = -1;

    public static int collapseLeft = -1;

    HashMap<String, List<String>> listDataChild;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sinhala, container, false);

        pathTag = (TextView) view.findViewById(R.id.path);

        pathTag.setTextColor(Color.BLACK);


        // get the listview
        expListViewSinhal1 = (ExpandableListView) view.findViewById(R.id.listExView);

        // preparing list data

        prepareListData();


        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        expListViewSinhal1.setAdapter(listAdapter);


        expListViewSinhal1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                collapseLeft = -1;
                expandedLeft = groupPosition;
                //left
                if (expandedLeft != expandedMiddle) {
                    expandPaliList(groupPosition);
                }
                if (expandedLeft != expandedRight) {
                    sinhala2Expand(groupPosition);
                }

                if (previousItemL != -1 && groupPosition != previousItemL) {
                    expListViewSinhal1.collapseGroup(previousItemL);
                }
                previousItemL = groupPosition;

            }
        });

        expListViewSinhal1.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                collapseLeft = i;
                expandedLeft = -1;
                if (collapseLeft != collapseMiddle) {
                    paliCollaps(i);
                }
                if (collapseLeft != collapseRight) {
                    sinhala2Collaps(i);
                }

            }
        });

        return view;
    }

    public static void expandList(int position) {
        if (isUpdated) {
            expListViewSinhal1.expandGroup(position, true);
        }
    }

    public static void collapseList(int position) {
        expListViewSinhal1.collapseGroup(position);
    }

    public void prepareListData() {

        listDataChild = new HashMap<String, List<String>>();

        if (!isUpdated) {

            //initial view
        } else {

            pathTag.setText(pathName);
            int i = 0;
            while (i < (listDataHeader.size())) {
                List<String> detail = new ArrayList<String>();
                detail.add(listDataItems.get(i));
                listDataChild.put(listDataHeader.get(i), detail);
                i++;
            }
        }
    }
}
