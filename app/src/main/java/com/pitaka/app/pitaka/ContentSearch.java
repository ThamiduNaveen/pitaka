package com.pitaka.app.pitaka;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pitaka.app.pitaka.Sinhala.collapseList;
import static com.pitaka.app.pitaka.Sinhala.expandList;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Collaps;
import static com.pitaka.app.pitaka.Sinhala2.sinhala2Expand;


public class ContentSearch extends AppCompatActivity {

    private DatabaseHelper2 mDBHelper3;
    private SQLiteDatabase mDb3;

    public static List<String> listData4Items = new ArrayList<>();

    HashMap<String, List<String>> listDataChild;

    ExpandableListAdapter listAdapter;

    String msg;

    int selection=0;

    TextView tv;



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

        initViews();


        tv=findViewById(R.id.preview);

        searchT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                listData4Items.clear();

                SinglishTranslator st = new SinglishTranslator();
                msg = st.convertText(searchT.getText().toString());
                tv.setText(msg);

                if(!msg.equals("")){
                    if(selection==1){
                        searchContentS(msg);
                    }
                    else {
                        searchContentP(msg);
                    }

                }


                initViews();



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final Button paaliB = (Button) findViewById(R.id.paaliS);
        final Button sinhalaB = (Button) findViewById(R.id.sinhalaS);
        paaliB.setVisibility(View.GONE);

        paaliB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paaliB.setVisibility(View.GONE);
                sinhalaB.setVisibility(View.VISIBLE);
                selection=0;
                searchT.setText("");
                listData4Items.clear();


            }
        });

        sinhalaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinhalaB.setVisibility(View.GONE);
                paaliB.setVisibility(View.VISIBLE);
                selection=1;
                searchT.setText("");
                listData4Items.clear();
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
                listData4Items.add(res.getString(5)+">>"+res.getString(0));
                String innerContent = res.getString(2);
                innerContent = innerContent.replaceAll(msg,"<font color='red'>"+msg+"</font>");
                listData4Items.add(innerContent);
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
                listData4Items.add(res.getString(5)+">>"+res.getString(1));
                String innerContent = res.getString(3);
                innerContent = innerContent.replaceAll(msg,"<font color='red'>"+msg+"</font>");
                listData4Items.add(innerContent);
            }
        }
    }



    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new DataAdapter((ArrayList<String>) listData4Items);
        recyclerView.setAdapter(adapter);


        final GestureDetector mGestureDetector = new GestureDetector(ContentSearch.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    String tblName=listData4Items.get(recyclerView.getChildPosition(child));
                    String[] strArray = tblName.split(">>");
                    tblName=strArray[3];
                    //Toast.makeText(ContentSearch.this, tblName, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("TABLE_NAME", tblName);
                    startActivity(intent);

                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

}
