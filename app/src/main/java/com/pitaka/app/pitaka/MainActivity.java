package com.pitaka.app.pitaka;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.graphics.Typeface;

import android.support.annotation.NonNull;
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

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public static Boolean isUpdated = false;
    public static List<String> listDataHeader = new ArrayList<String>();
    public static List<String> listDataItems = new ArrayList<String>();

    public static List<String> listData2Header = new ArrayList<String>();
    public static List<String> listData2Items = new ArrayList<String>();

    List<String> tableList = new ArrayList<String>();
    List<String> dataList = new ArrayList<String>();
    List<String> tableList2 = new ArrayList<String>();

    ViewPager viewPager;
    DrawerLayout drawer;
    EditText searchBar, searchBar3;
    TextView searchBar2, searchBar4;


    List<NLevelItem> list;
    ListView listView;

    //String jsonStringList;
    String jsonStringList = "[{\"title\":\"විනයපිටක\",\"children\":[]},{\"title\":\"සුත්තපිටක\",\"children\":[{\"title\":\"දීඝනිකාය\",\"children\":[{\"title\":\"සීලක්ඛන්ධවග්ගපාළි\",\"children\":[{\"title\":\"1. බ්‍රහ්මජාලසුත්තං\",\"children\":[]},{\"title\":\"2. සාමඤ්ඤඵලසුත්තං\",\"children\":[]},{\"title\":\"3. අම්බට්ඨසුත්තං\",\"children\":[]},{\"title\":\"4. සොණදණ්ඩසුත්තං\",\"children\":[]},{\"title\":\"5. කූටදන්තසුත්තං\",\"children\":[]},{\"title\":\"6. මහාලිසුත්තං\",\"children\":[]},{\"title\":\"7. ජාලියසුත්තං\",\"children\":[]},{\"title\":\"8. මහාසීහනාදසුත්තං\",\"children\":[]},{\"title\":\"9. පොට්ඨපාදසුත්තං\",\"children\":[]},{\"title\":\"10. සුභසුත්තං\",\"children\":[]},{\"title\":\"11. කෙවට්ටසුත්තං\",\"children\":[]},{\"title\":\"12. ලොහිච්චසුත්තං\",\"children\":[]},{\"title\":\"13. තෙවිජ්ජසුත්තං\",\"children\":[]}]},{\"title\":\"මහාවග්ගපාළි\",\"children\":[{\"title\":\"1. මහාපදානසුත්තං\",\"children\":[]},{\"title\":\"2. මහානිදානසුත්තං\",\"children\":[]},{\"title\":\"3. මහාපරිනිබ්බානසුත්තං\",\"children\":[]},{\"title\":\"4. මහාසුදස්සනසුත්තං\",\"children\":[]},{\"title\":\"5. ජනවසභසුත්තං\",\"children\":[]},{\"title\":\"6. මහාගොවින්දසුත්තං\",\"children\":[]},{\"title\":\"7. මහාසමයසුත්තං\",\"children\":[]},{\"title\":\"8. සක්කපඤ්හසුත්තං\",\"children\":[]},{\"title\":\"9. මහාසතිපට්ඨානසුත්තං\",\"children\":[]},{\"title\":\"10. පායාසිසුත්තං\",\"children\":[]}]},{\"title\":\"පාථිකවග්ගපාළි\",\"children\":[{\"title\":\"1. පාථිකසුත්තං\",\"children\":[]},{\"title\":\"2. උදුම්බරිකසුත්තං\",\"children\":[]},{\"title\":\"3. චක්කවත්තිසුත්තං\",\"children\":[]},{\"title\":\"4. අග්ගඤ්ඤසුත්තං\",\"children\":[]},{\"title\":\"5. සම්පසාදනීයසුත්තං\",\"children\":[]},{\"title\":\"6. පාසාදිකසුත්තං\",\"children\":[]},{\"title\":\"7. ලක්ඛණසුත්තං\",\"children\":[]},{\"title\":\"8. සිඞ්ගාලසුත්තං\",\"children\":[]},{\"title\":\"9. ආටානාටියසුත්තං\",\"children\":[]},{\"title\":\"10. සඞ්ගීතිසුත්තං\",\"children\":[]},{\"title\":\"11. දසුත්තරසුත්තං\",\"children\":[]}]}]},{\"title\":\"මජ්ඣිමනිකාය\",\"children\":[{\"title\":\"මූලපණ්ණාසපාළි\",\"children\":[{\"title\":\"1. මූලපරියායවග්ගො\",\"children\":[]},{\"title\":\"2. සීහනාදවග්ගො\",\"children\":[]},{\"title\":\"3. ඔපම්මවග්ගො\",\"children\":[]},{\"title\":\"4. මහායමකවග්ගො\",\"children\":[]},{\"title\":\"5. චූළයමකවග්ගො\",\"children\":[]}]},{\"title\":\"මජ්ඣිමපණ්ණාසපාළි\",\"children\":[{\"title\":\"1. ගහපතිවග්ගො\",\"children\":[]},{\"title\":\"2. භික්ඛුවග්ගො\",\"children\":[]},{\"title\":\"3. පරිබ්බාජකවග්ගො\",\"children\":[]},{\"title\":\"4. රාජවග්ගො\",\"children\":[]},{\"title\":\"5. බ්‍රාහ්මණවග්ගො\",\"children\":[]}]},{\"title\":\"උපරිපණ්ණාසපාළි\",\"children\":[{\"title\":\"1. දෙවදහවග්ගො\",\"children\":[]},{\"title\":\"2. අනුපදවග්ගො\",\"children\":[]},{\"title\":\"3. සුඤ්ඤතවග්ගො\",\"children\":[]},{\"title\":\"4. විභඞ්ගවග්ගො\",\"children\":[]},{\"title\":\"5. සළායතනවග්ගො\",\"children\":[]}]}]},{\"title\":\"සංයුත්තනිකාය\",\"children\":[{\"title\":\"සගාථාවග්ගපාළි\",\"children\":[]},{\"title\":\"නිදානවග්ගපාළි\",\"children\":[]},{\"title\":\"ඛන්ධවග්ගපාළි\",\"children\":[]},{\"title\":\"සළායතනවග්ගපාළි\",\"children\":[]},{\"title\":\"මහාවග්ගපාළි\",\"children\":[]}]},{\"title\":\"අඞ්ගුත්තරනිකාය\",\"children\":[{\"title\":\"එකකනිපාතපාළි\",\"children\":[]},{\"title\":\"දුකනිපාතපාළි\",\"children\":[]},{\"title\":\"තිකනිපාතපාළි\",\"children\":[]},{\"title\":\"චතුක්කනිපාතපාළි\",\"children\":[]},{\"title\":\"පඤ්චකනිපාතපාළි\",\"children\":[]},{\"title\":\"ඡක්කනිපාතපාළි\",\"children\":[]},{\"title\":\"සත්තකනිපාතපාළි\",\"children\":[]},{\"title\":\"අට්ඨකාදිනිපාතපාළි\",\"children\":[]},{\"title\":\"නවකනිපාතපාළි\",\"children\":[]},{\"title\":\"දසකනිපාතපාළි\",\"children\":[]},{\"title\":\"එකාදසකනිපාතපාළි\",\"children\":[]}]},{\"title\":\"ඛුද්දකනිකාය\",\"children\":[{\"title\":\"ඛුද්දකපාඨපාළි\",\"children\":[]},{\"title\":\"ධම්මපදපාළි\",\"children\":[]},{\"title\":\"උදානපාළි\",\"children\":[]},{\"title\":\"ඉතිවුත්තකපාළි\",\"children\":[]},{\"title\":\"සුත්තනිපාතපාළි\",\"children\":[]},{\"title\":\"විමානවත්ථුපාළි\",\"children\":[]},{\"title\":\"පෙතවත්ථුපාළි\",\"children\":[]},{\"title\":\"ථෙරගාථාපාළි\",\"children\":[]},{\"title\":\"ථෙරීගාථාපාළි\",\"children\":[]},{\"title\":\"අපදානපාළි-1\",\"children\":[]},{\"title\":\"අපදානපාළි-2\",\"children\":[]},{\"title\":\"බුද්ධවංසපාළි\",\"children\":[]},{\"title\":\"චරියාපිටකපාළි\",\"children\":[]},{\"title\":\"ජාතකපාළි-1\",\"children\":[]},{\"title\":\"ජාතකපාළි-2\",\"children\":[]},{\"title\":\"මහානිද්දෙසපාළි\",\"children\":[]},{\"title\":\"චූළනිද්දෙසපාළි\",\"children\":[]},{\"title\":\"පටිසම්භිදාමග්ගපාළි\",\"children\":[]},{\"title\":\"නෙත්තිප්පකරණපාළි\",\"children\":[]},{\"title\":\"මිලින්දපඤ්හපාළි\",\"children\":[]},{\"title\":\"පෙටකොපදෙසපාළි\",\"children\":[]}]}]},{\"title\":\"අභිධම්මපිටක\",\"children\":[]}]";

    private View mRightDrawerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, WRITE_EXTERNAL_STO_CODE);
            } else {
                jsonStringCreate js = new jsonStringCreate();
                js.write();
            }
        } else {
            jsonStringCreate js = new jsonStringCreate();
            js.write();
        }



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

        ListView listV = findViewById(R.id.listV);
        ListView listV2 = findViewById(R.id.listV2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tableList);
        listV.setAdapter(adapter);

        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dataList);
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
                } catch (Exception e) {
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


        final Spinner lanuage = findViewById(R.id.language);
        final Spinner dictionary = findViewById(R.id.dictionary);

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

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        /////////

        mRightDrawerView = findViewById(R.id.Right_drawer);


        /////////


    }

    @Override
    protected void onStart() {
        super.onStart();
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
                            } catch (Exception e) {
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

        Cursor res = mDBHelper.search(searchBar4.getText().toString());

        if (res.getCount() == 0) {
            //no data
            dataList.add("No Data");

            return;
        } else {

            while (res.moveToNext()) {
                if (!res.getString(0).contains("_")) {
                    dataList.add(res.getString(0));
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
                if (!res.getString(0).contains("_")) {
                    tableList2.add(res.getString(0));
                }


            }

        }


    }

    private static final int WRITE_EXTERNAL_STO_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STO_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    jsonStringCreate js = new jsonStringCreate();
                    js.write();
                } else {
                    Toast.makeText(this, "Enable permission to save image", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
