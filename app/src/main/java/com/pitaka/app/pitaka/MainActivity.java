package com.pitaka.app.pitaka;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.os.Bundle;
import android.graphics.Typeface;

import android.support.design.widget.TabLayout;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.pitaka.app.pitaka.nLevel.NLevelAdapter;
import com.pitaka.app.pitaka.nLevel.NLevelItem;
import com.pitaka.app.pitaka.nLevel.NLevelView;
import com.pitaka.app.pitaka.nLevel.SomeObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public static Boolean isUpdated=false;
    public static List<String> listDataHeader = new ArrayList<String>();
    public static List<String> listDataItems = new ArrayList<String>();

    public static List<String> listData2Header = new ArrayList<String>();
    public static List<String> listData2Items = new ArrayList<String>();

    List<String> tableList = new ArrayList<String>();
    List<String> tableList2 = new ArrayList<String>();

    List<String> dataList = new ArrayList<String>();
    List<String> meanList = new ArrayList<String>();

    ViewPager viewPager;
    DrawerLayout drawer;
    EditText searchBar,searchBar3;
    TextView searchBar2,searchBar4;


    List<NLevelItem> list;
    ListView listView;

    String jsonStringList = "[{\"title\":\"winyapitaka\",\"children\":[]},{\"title\":\"suthrapitaka\",\"children\":[{\"title\":\"deeganikaya\",\"children\":[{\"title\":\"seelakkandaWaggapali\",\"children\":[{\"title\":\"BrahmajalaSuttan\",\"children\":[]},{\"title\":\"SaamanchapalaSuththan\",\"children\":[]},{\"title\":\"Ambattasuththan\",\"children\":[]}]},{\"title\":\"mahawaggapaali\",\"children\":[]}]},{\"title\":\"majjimanikaya\",\"children\":[]},{\"title\":\"sanukthanikaya\",\"children\":[]}]},{\"title\":\"abhidammapitaka\",\"children\":[]}]";
    //String jsonStringList = "[{\"title\":\"Root 1\",\"children\":[{\"title\":\"Child 11\",\"children\":[{\"title\":\"Extended Child 111\",\"children\":[{\"title\":\"Super Extended Child 1111\",\"children\":[{\"title\":\"Super Extended Child 1111\",\"children\":[{\"title\":\"Super Extended Child 1111\",\"children\":[]}]}]}]},{\"title\":\"Extended Child 112\",\"children\":[]},{\"title\":\"Extended Child 113\",\"children\":[]}]},{\"title\":\"Child 12\",\"children\":[{\"title\":\"Extended Child 121\",\"children\":[]},{\"title\":\"Extended Child 122\",\"children\":[]}]},{\"title\":\"Child 13\",\"children\":[]}]},{\"title\":\"Root 2\",\"children\":[{\"title\":\"Child 21\",\"children\":[{\"title\":\"Extended Child 211\",\"children\":[]},{\"title\":\"Extended Child 212\",\"children\":[]},{\"title\":\"Extended Child 213\",\"children\":[]}]},{\"title\":\"Child 22\",\"children\":[{\"title\":\"Extended Child 221\",\"children\":[]},{\"title\":\"Extended Child 222\",\"children\":[]}]},{\"title\":\"Child 23\",\"children\":[]}]},{\"title\":\"Root 1\",\"children\":[]}]";

    private View mRightDrawerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Sinhala1"));
        tabLayout.addTab(tabLayout.newTab().setText("Paali"));
        tabLayout.addTab(tabLayout.newTab().setText("Sinhala2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ListView listV=findViewById(R.id.listV);
        ListView listV2=findViewById(R.id.listV2);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tableList);
        listV.setAdapter(adapter);

        final ArrayAdapter<String> adapterr=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dataList);
        listV2.setAdapter(adapterr);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    listDataHeader.clear();
                    listData2Header.clear();
                    createVerseList(tableList.get(i));
                    isUpdated = true;
                    viewPager.getAdapter().notifyDataSetChanged();
                    drawer.closeDrawer(GravityCompat.START);
                }

                catch (Exception e){
                    Toast.makeText(MainActivity.this, "No database found!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        mDBHelper.openDataBase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Pitaka.lk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        listView = (ListView) findViewById(R.id.listView1);

        searchBar = findViewById(R.id.searchBar);
        searchBar2 = findViewById(R.id.searchBar2);

        searchBar3 = findViewById(R.id.searchBar3);
        searchBar4 = findViewById(R.id.searchBar4);

        final Button serchSuthraBT = (Button) findViewById(R.id.search_Button_navigation);
        final Button thripitakaBT = (Button) findViewById(R.id.thripitaka_Button_navigation);
        thripitakaBT.setVisibility(View.GONE);
        searchBar.setVisibility(View.GONE);
        searchBar2.setVisibility(View.GONE);
        serchSuthraBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.GONE);
                serchSuthraBT.setVisibility(View.GONE);
                thripitakaBT.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.VISIBLE);
                searchBar2.setVisibility(View.VISIBLE);

            }
        });

        thripitakaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.VISIBLE);
                thripitakaBT.setVisibility(View.GONE);
                serchSuthraBT.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.GONE);
                searchBar2.setVisibility(View.GONE);
            }
        });

        createTableNameList();
        final int count = tableList2.size();
        for (int j = 0; j < count; j++) {

                tableList.add(tableList2.get(j));

        }

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                    SinglishTranslator st = new SinglishTranslator();
                    String msg = st.convertText(searchBar.getText().toString());

                    searchBar2.setText(msg);


                    tableList.clear();
                    createTableNameList();


                    for (int j = 0; j < count; j++) {

                        if (tableList2.get(j).toLowerCase().startsWith(msg)) {
                            tableList.add(tableList2.get(j));
                        }
                    }




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        final Spinner lanuage =findViewById(R.id.language);
        final Spinner dictionary=findViewById(R.id.dictionary);

        final List<String> dicList = new ArrayList<String>();
        final List<String> lanList = new ArrayList<String>();

        dicList.add("Dictonary 1");
        dicList.add("Dictonary 2");
        dicList.add("Dictonary 3");

        lanList.add("Sinhala to Paali");
        lanList.add("Paali to Sinhala");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dicList);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lanList);

        lanuage.setAdapter(adapter2);
        dictionary.setAdapter(adapter3);


        searching();
        searchBar3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SinglishTranslator st = new SinglishTranslator();
                String msg = st.convertText(searchBar3.getText().toString());

                searchBar4.setText(msg);

                searching();

                adapterr.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listV2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataList.get(i);
                Toast.makeText(MainActivity.this, dataList.get(i)+"-"+meanList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        /////////

        mRightDrawerView = findViewById(R.id.Right_drawer);


        /////////
        NLevelExpandableListView();

    }

    private void NLevelExpandableListView() {


        list = new ArrayList<NLevelItem>();
        final LayoutInflater inflater = LayoutInflater.from(this);
        nestedLoop(jsonStringList, null, inflater, 0);

        NLevelAdapter adapter = new NLevelAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                TextView t = (TextView) arg1.findViewById(R.id.textView);
                //          t.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_black_24dp,0,0,0);
                ((NLevelAdapter) listView.getAdapter()).toggle(arg2);
                ((NLevelAdapter) listView.getAdapter()).getFilter().filter();

            }
        });



    }

    private void nestedLoop(String levelList, NLevelItem nLevelItem, final LayoutInflater inflater, int level) {

        try {

            JSONArray jsonArrayStringList = new JSONArray(levelList);
            int length = jsonArrayStringList.length();

            for (int i = 0; i < length; i++) {
                JSONObject itemObject = jsonArrayStringList.getJSONObject(i);
                int childrenSize = itemObject.getJSONArray("children").length();
                NLevelItem Parent = itemView(i, itemObject.getString("title"), nLevelItem, inflater, level, !(childrenSize > 0));
                list.add(Parent);

                if (childrenSize > 0) {
                    nestedLoop(itemObject.getJSONArray("children").toString(), Parent, inflater, level + 1);
                }
            }

        } catch (Exception e) {

        }

    }

    private NLevelItem itemView(int itemRow, final String Title, NLevelItem nLevelItem, final LayoutInflater inflater, final int level, final boolean isLast) {

        NLevelItem superChild = new NLevelItem(new SomeObject(Title), nLevelItem, new NLevelView() {
            @Override
            public View getView(NLevelItem item) {
                View view = inflater.inflate(R.layout.list_item, null);
                TextView tv = (TextView) view.findViewById(R.id.textView);
                String name = (String) ((SomeObject) item.getWrappedObject()).getName();
                tv.setText(name);
                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_folder_white_24dp, 0, 0, 0);
                tv.setCompoundDrawablePadding(15);

                if (level == 0) {
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
                    tv.setTextColor(Color.BLACK);


                } else if (level == 3) {
                    tv.setTypeface(null, Typeface.ITALIC);
                    //tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
                    tv.setTextColor(Color.WHITE);

                } else {
                    //tv.setTypeface(Typeface.DEFAULT_BOLD);
                    //tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
                    tv.setTextColor(Color.parseColor("#744308"));


                }

                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                mlp.setMargins(level * 50, 5, 5, 5);

                if (isLast) {
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(MainActivity.this, "Clicked on: "+Title, Toast.LENGTH_SHORT).show();

                            listDataHeader.clear();
                            listDataItems.clear();
                            listData2Header.clear();
                            listData2Items.clear();
                            try {
                                createVerseList(Title);
                                isUpdated = true;
                                viewPager.getAdapter().notifyDataSetChanged();
                                drawer.closeDrawer(GravityCompat.START);
                            }

                            catch (Exception e){
                                Toast.makeText(MainActivity.this, "No database found!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }

                return view;
            }
        });

        return superChild;
    }

    public void createVerseList(String verse) {
        Cursor res = mDBHelper.getData(verse);


        if (res.getCount() == 0) {
            return;
        } else {

            while (res.moveToNext()) {

                //Sinhala
                listDataHeader.add(res.getString(0));
                listDataItems.add(res.getString(2));
                //Paali
                listData2Header.add(res.getString(1));
                listData2Items.add(res.getString(3));

            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_main:


                drawer.openDrawer(mRightDrawerView);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void searching() {

        dataList.clear();
        meanList.clear();
        Cursor res = mDBHelper.search(searchBar4.getText().toString());

        if (res.getCount() == 0) {
            //no data
            dataList.add("No Matches Found!");

            return;
        } else {

            while (res.moveToNext()) {
                if(!res.getString(0).contains("_")){
                    dataList.add(res.getString(1));
                    meanList.add(res.getString(0));
                    //Toast.makeText(this, res.getString(0), Toast.LENGTH_SHORT).show();
                }


            }


        }


    }
    public void createTableNameList() {

        Cursor res = mDBHelper.getTableNameList();

        if (res.getCount() == 0) {
            //no data

            return;
        } else {

            while (res.moveToNext()) {
                if(!res.getString(0).contains("_")){
                    tableList2.add(res.getString(0));
                }


            }

        }


    }

}
