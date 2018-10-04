package com.pitaka.app.pitaka;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ContentSearch extends AppCompatActivity {

    private DatabaseHelper2 mDBHelper3;
    private SQLiteDatabase mDb3;

    public static List<String> listData4Header = new ArrayList<String>();
    public static List<String> listData4Items = new ArrayList<String>();

    HashMap<String, List<String>> listDataChild;

    ExpandableListAdapter listAdapter;

    ExpandableListView expListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_search);
        Toolbar toolbar=findViewById(R.id.toolbar);


        final EditText searchT=findViewById(R.id.text_search);

        mDBHelper3 = new DatabaseHelper2(this);

        try {
            mDBHelper3.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb3 = mDBHelper3.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        mDBHelper3.openDataBase();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.listExView);

        // preparing list data


        //searchContentP("");
        prepareListData();



        listAdapter = new ExpandableListAdapter(this, listData4Header, listDataChild);

        expListView.setAdapter(listAdapter);

        searchT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                listData4Header.clear();
                listData4Items.clear();

                SinglishTranslator st = new SinglishTranslator();
                String msg = st.convertText(searchT.getText().toString());
                if(!msg.equals("")){
                    searchContentS(msg);
                }
                //searchContentS(msg);  //Sinhala Search
                //searchContentP(msg);  //Paali Search
                prepareListData();


                listAdapter = new ExpandableListAdapter(ContentSearch.this, listData4Header, listDataChild);

                expListView.setAdapter(listAdapter);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void searchContentS(String text) {

        Cursor res = mDBHelper3.searchContentSinhala(text);

        if (res.getCount() == 0) {
            //no data

            return;
        } else {

            while (res.moveToNext()) {
                listData4Header.add(res.getString(0));
                listData4Items.add(res.getString(2));

            }
        }
    }

    public void searchContentP(String text) {

        Cursor res = mDBHelper3.searchContentPaali(text);

        if (res.getCount() == 0) {
            //no data



            return;
        } else {

            while (res.moveToNext()) {
                listData4Header.add(res.getString(0));
                listData4Items.add(res.getString(2));

            }
        }
    }

    public void prepareListData(){

        listDataChild = new HashMap<String, List<String>>();

            int i=0;
            while (i<(listData4Header.size())){
                List<String> detail = new ArrayList<String>();
                detail.add(listData4Items.get(i));
                listDataChild.put(listData4Header.get(i), detail);
                i++;
            }
    }
}
