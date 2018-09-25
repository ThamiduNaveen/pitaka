package com.pitaka.app.pitaka;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private DatabaseHelper2 mDBHelper2;
    private SQLiteDatabase mDb,mDb2;

    private static final int WRITE_EXTERNAL_STO_CODE = 1;

    public static String tableString="";

    public static Boolean isUpdated=false;
    //data lists for Sinhala1 fragment
    public static List<String> listDataHeader = new ArrayList<String>();
    public static List<String> listDataItems = new ArrayList<String>();
    //data lists for Paali fragment
    public static List<String> listData2Header = new ArrayList<String>();
    public static List<String> listData2Items = new ArrayList<String>();
    //data lists for Sinhala2 fragment
    public static List<String> listData3Header = new ArrayList<String>();
    public static List<String> listData3Items = new ArrayList<String>();

    //for search
    List<String> tableList = new ArrayList<String>();
    List<String> tableList2 = new ArrayList<String>();

    //for dictionary
    List<String> dataList = new ArrayList<String>();
    List<String> meanList = new ArrayList<String>();

    //dictionary setting
    List<String> dicList = new ArrayList<String>();
    public int selection1=0;
    public int selection2=0;

    ViewPager viewPager;
    DrawerLayout drawer;

    //search
    EditText searchBar,searchBar3;
    TextView searchBar2,searchBar4;

    //Navdrawer
    List<NLevelItem> list;
    ListView listView;


    //Navdrawer data
    String jsonStringList = "[{\"title\":\"විනයපිටක\",\"children\":[]},{\"title\":\"සුත්තපිටක\",\"children\":[{\"title\":\"දීඝනිකාය\",\"children\":[{\"title\":\"සීලක්ඛන්ධවග්ගපාළි\",\"children\":[{\"title\":\"බ්\u200Dරහ්මජාලසුත්තං\",\"children\":[]},{\"title\":\"සාමඤ්ඤඵලසුත්තං\",\"children\":[]},{\"title\":\"අම්බට්ඨසුත්තං\",\"children\":[]},{\"title\":\"සොණදණ්ඩසුත්තං\",\"children\":[]},{\"title\":\"කූටදන්තසුත්තං\",\"children\":[]},{\"title\":\"මහාලිසුත්තං\",\"children\":[]},{\"title\":\"ජාලියසුත්තං\",\"children\":[]},{\"title\":\"මහාසීහනාදසුත්තං\",\"children\":[]},{\"title\":\"පොට්ඨපාදසුත්තං\",\"children\":[]},{\"title\":\"සුභසුත්තං\",\"children\":[]},{\"title\":\"කෙවට්ටසුත්තං\",\"children\":[]},{\"title\":\"ලොහිච්චසුත්තං\",\"children\":[]},{\"title\":\"තෙවිජ්ජසුත්තං\",\"children\":[]}]},{\"title\":\"මහාවග්ගපාළි\",\"children\":[{\"title\":\"මහාපදානසුත්තං\",\"children\":[]},{\"title\":\"මහානිදානසුත්තං\",\"children\":[]},{\"title\":\"මහාපරිනිබ්බානසුත්තං\",\"children\":[]},{\"title\":\"මහාසුදස්සනසුත්තං\",\"children\":[]},{\"title\":\"ජනවසභසුත්තං\",\"children\":[]},{\"title\":\"මහාගොවින්දසුත්තං\",\"children\":[]},{\"title\":\"මහාසමයසුත්තං\",\"children\":[]},{\"title\":\"සක්කපඤ්හසුත්තං\",\"children\":[]},{\"title\":\"මහාසතිපට්ඨානසුත්තං\",\"children\":[]},{\"title\":\"පායාසිසුත්තං\",\"children\":[]}]},{\"title\":\"පාථිකවග්ගපාළි\",\"children\":[{\"title\":\"1. පාථිකසුත්තං\",\"children\":[]},{\"title\":\"2. උදුම්බරිකසුත්තං\",\"children\":[]},{\"title\":\"3. චක්කවත්තිසුත්තං\",\"children\":[]},{\"title\":\"4. අග්ගඤ්ඤසුත්තං\",\"children\":[]},{\"title\":\"5. සම්පසාදනීයසුත්තං\",\"children\":[]},{\"title\":\"6. පාසාදිකසුත්තං\",\"children\":[]},{\"title\":\"7. ලක්ඛණසුත්තං\",\"children\":[]},{\"title\":\"8. සිඞ්ගාලසුත්තං\",\"children\":[]},{\"title\":\"9. ආටානාටියසුත්තං\",\"children\":[]},{\"title\":\"10. සඞ්ගීතිසුත්තං\",\"children\":[]},{\"title\":\"11. දසුත්තරසුත්තං\",\"children\":[]}]}]},{\"title\":\"මජ්ඣිමනිකාය\",\"children\":[{\"title\":\"මූලපණ්ණාසපාළි\",\"children\":[{\"title\":\"1. මූලපරියායවග්ගො\",\"children\":[]},{\"title\":\"2. සීහනාදවග්ගො\",\"children\":[]},{\"title\":\"3. ඔපම්මවග්ගො\",\"children\":[]},{\"title\":\"4. මහායමකවග්ගො\",\"children\":[]},{\"title\":\"5. චූළයමකවග්ගො\",\"children\":[]}]},{\"title\":\"මජ්ඣිමපණ්ණාසපාළි\",\"children\":[{\"title\":\"1. ගහපතිවග්ගො\",\"children\":[]},{\"title\":\"2. භික්ඛුවග්ගො\",\"children\":[]},{\"title\":\"3. පරිබ්බාජකවග්ගො\",\"children\":[]},{\"title\":\"4. රාජවග්ගො\",\"children\":[]},{\"title\":\"5. බ්‍රාහ්මණවග්ගො\",\"children\":[]}]},{\"title\":\"උපරිපණ්ණාසපාළි\",\"children\":[{\"title\":\"1. දෙවදහවග්ගො\",\"children\":[]},{\"title\":\"2. අනුපදවග්ගො\",\"children\":[]},{\"title\":\"3. සුඤ්ඤතවග්ගො\",\"children\":[]},{\"title\":\"4. විභඞ්ගවග්ගො\",\"children\":[]},{\"title\":\"5. සළායතනවග්ගො\",\"children\":[]}]}]},{\"title\":\"සංයුත්තනිකාය\",\"children\":[{\"title\":\"සගාථාවග්ගපාළි\",\"children\":[]},{\"title\":\"නිදානවග්ගපාළි\",\"children\":[]},{\"title\":\"ඛන්ධවග්ගපාළි\",\"children\":[]},{\"title\":\"සළායතනවග්ගපාළි\",\"children\":[]},{\"title\":\"මහාවග්ගපාළි\",\"children\":[]}]},{\"title\":\"අඞ්ගුත්තරනිකාය\",\"children\":[{\"title\":\"එකකනිපාතපාළි\",\"children\":[]},{\"title\":\"දුකනිපාතපාළි\",\"children\":[]},{\"title\":\"තිකනිපාතපාළි\",\"children\":[]},{\"title\":\"චතුක්කනිපාතපාළි\",\"children\":[]},{\"title\":\"පඤ්චකනිපාතපාළි\",\"children\":[]},{\"title\":\"ඡක්කනිපාතපාළි\",\"children\":[]},{\"title\":\"සත්තකනිපාතපාළි\",\"children\":[]},{\"title\":\"අට්ඨකාදිනිපාතපාළි\",\"children\":[]},{\"title\":\"නවකනිපාතපාළි\",\"children\":[]},{\"title\":\"දසකනිපාතපාළි\",\"children\":[]},{\"title\":\"එකාදසකනිපාතපාළි\",\"children\":[]}]},{\"title\":\"ඛුද්දකනිකාය\",\"children\":[{\"title\":\"ඛුද්දකපාඨපාළි\",\"children\":[]},{\"title\":\"ධම්මපදපාළි\",\"children\":[]},{\"title\":\"උදානපාළි\",\"children\":[]},{\"title\":\"ඉතිවුත්තකපාළි\",\"children\":[]},{\"title\":\"සුත්තනිපාතපාළි\",\"children\":[]},{\"title\":\"විමානවත්ථුපාළි\",\"children\":[]},{\"title\":\"පෙතවත්ථුපාළි\",\"children\":[]},{\"title\":\"ථෙරගාථාපාළි\",\"children\":[]},{\"title\":\"ථෙරීගාථාපාළි\",\"children\":[]},{\"title\":\"අපදානපාළි-1\",\"children\":[]},{\"title\":\"අපදානපාළි-2\",\"children\":[]},{\"title\":\"බුද්ධවංසපාළි\",\"children\":[]},{\"title\":\"චරියාපිටකපාළි\",\"children\":[]},{\"title\":\"ජාතකපාළි-1\",\"children\":[]},{\"title\":\"ජාතකපාළි-2\",\"children\":[]},{\"title\":\"මහානිද්දෙසපාළි\",\"children\":[]},{\"title\":\"චූළනිද්දෙසපාළි\",\"children\":[]},{\"title\":\"පටිසම්භිදාමග්ගපාළි\",\"children\":[]},{\"title\":\"නෙත්තිප්පකරණපාළි\",\"children\":[]},{\"title\":\"මිලින්දපඤ්හපාළි\",\"children\":[]},{\"title\":\"පෙටකොපදෙසපාළි\",\"children\":[]}]}]},{\"title\":\"අභිධම්මපිටක\",\"children\":[]}]";



    private View mRightDrawerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect dictionary database

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

        //connect suthra database

        mDBHelper2 = new DatabaseHelper2(this);

        try {
            mDBHelper2.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb2 = mDBHelper2.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        mDBHelper2.openDataBase();

        //Uncomment following section to create jsonString and save in external storage.

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                requestPermissions(permission, WRITE_EXTERNAL_STO_CODE);
//            } else {
//                jsonStringCreate js = new jsonStringCreate();
//                js.write();
//            }
//        } else {
//            jsonStringCreate js = new jsonStringCreate();
//            js.write();
//        }


        //tab initializing
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("සිංහල 1"));
        tabLayout.addTab(tabLayout.newTab().setText("පාලි"));
        tabLayout.addTab(tabLayout.newTab().setText("සිංහල 2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ListView listV=findViewById(R.id.listV);
        final ListView listV2=findViewById(R.id.listV2);

        //search
        final LayoutInflater mInflater2 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tableList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View row;

                if (null == convertView) {
                    row = mInflater2.inflate(R.layout.support_simple_spinner_dropdown_item, null);
                } else {
                    row = convertView;
                }

                TextView tv = (TextView) row.findViewById(android.R.id.text1);
                tv.setSingleLine(false);
                tv.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                tv.setBackgroundResource(R.drawable.back);
                tv.setTextColor(Color.WHITE);
                tv.setPadding(25,25,25,25);
                tv.setText(getItem(position));

                return row;
            }
        };
        listV.setAdapter(adapter);

        //dictionary
        final LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final ArrayAdapter<String> adapterr = new ArrayAdapter<String>(this, R.layout.dictionary_item, dataList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View row;

                if (null == convertView) {
                    row = mInflater.inflate(R.layout.support_simple_spinner_dropdown_item, null);
                } else {
                    row = convertView;
                }

                TextView tv = (TextView) row.findViewById(android.R.id.text1);
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_account_circle_white_24dp, 0, 0, 0);
                tv.setSingleLine(false);
                tv.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                tv.setBackgroundResource(R.drawable.back);
                tv.setPadding(25,25,25,25);
                tv.setText(Html.fromHtml(getItem(position)));
                //tv.setText(getItem(position));

                return row;
            }
        };
        listV2.setAdapter(adapterr);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    listDataHeader.clear();
                    listDataItems.clear();
                    listData2Header.clear();
                    listData2Items.clear();
                    listData3Header.clear();
                    listData3Items.clear();
                    getSupportActionBar().setTitle(tableList.get(i));
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


        //Action bar settings
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("kcthripitaka");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        listView = (ListView) findViewById(R.id.listView1);

        searchBar = findViewById(R.id.searchBar);//search
        searchBar2 = findViewById(R.id.searchBar2);

        searchBar3 = findViewById(R.id.searchBar3);//dictionary
        searchBar4 = findViewById(R.id.searchBar4);

        final Button serchSuthraBT = (Button) findViewById(R.id.search_Button_navigation);
        final Button thripitakaBT = (Button) findViewById(R.id.thripitaka_Button_navigation);
        thripitakaBT.setVisibility(View.GONE);
        searchBar.setVisibility(View.GONE);
        searchBar2.setVisibility(View.GONE);

        //toggle views in left nav drawer
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

        //SEARCHING ACTION
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
                    adapter.notifyDataSetChanged();



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        final Spinner lanuage = findViewById(R.id.language);//conversion type selection

        dicList.add("ශබ්ද කෝෂය 1");
        dicList.add("ශබ්ද කෝෂය 2");
        dicList.add("ශබ්ද කෝෂය 3");


        final Button paaliBT = (Button) findViewById(R.id.paaliBT);
        final Button sinhalaBT = (Button) findViewById(R.id.sinhalaBT);
        paaliBT.setVisibility(View.GONE);

        paaliBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paaliBT.setVisibility(View.GONE);
                sinhalaBT.setVisibility(View.VISIBLE);
                selection1=0;
                searching();
                adapterr.notifyDataSetChanged();

            }
        });

        sinhalaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinhalaBT.setVisibility(View.GONE);
                paaliBT.setVisibility(View.VISIBLE);
                selection1=1;
                searching();
                adapterr.notifyDataSetChanged();
            }
        });




        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.simple_spinner, dicList);


        lanuage.setAdapter(adapter2);

        lanuage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    selection1=0;
                }
                else if(i==1){
                    selection1=1;
                }
                else if(i==2){
                    selection1=2;
                }
                searching();
                adapterr.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //searching();
        //dictionary searching
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
                    tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_format_align_justify_white_24dp, 0, 0, 0);
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

                            listDataHeader.clear();
                            listDataItems.clear();
                            listData2Header.clear();
                            listData2Items.clear();
                            listData3Header.clear();
                            listData3Items.clear();
                            try {
                                getSupportActionBar().setTitle(Title);
                                createVerseList(Title.toString());
                                //Toast.makeText(MainActivity.this, Title, Toast.LENGTH_SHORT).show();
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

    //suthra list for search
    public void createVerseList(String verse) {
        Cursor res = mDBHelper2.getData(verse);


        if (res.getCount() == 0) {
            return;
        } else {

            while (res.moveToNext()) {

                //Sinhala1
                listDataHeader.add(res.getString(0));
                listDataItems.add(res.getString(2));

                //Paali
                listData2Header.add(res.getString(1));
                listData2Items.add(res.getString(3));

                //Sinhala2
                listData3Header.add(res.getString(0));
                listData3Items.add(res.getString(3));

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

            case R.id.search_content:
                Intent contentSearchActivity= new Intent(MainActivity.this,ContentSearch.class);
                startActivity(contentSearchActivity);


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    //dictionary searching method
    public void searching() {
        dataList.clear();
        meanList.clear();
        Cursor res;
        try{
            if(selection1==0&&selection2==0){
                res = mDBHelper.searchDictionary1(searchBar4.getText().toString());
            }
            else if(selection1==1&&selection2==0){
                res = mDBHelper.searchDictionary2(searchBar4.getText().toString());
            }
            else if(selection1==0&&selection2==1){
                res = mDBHelper.searchDictionary3(searchBar4.getText().toString());
            }
            else if(selection1==1&&selection2==1){
                res = mDBHelper.searchDictionary4(searchBar4.getText().toString());
            }
            else if(selection1==0&&selection2==2){
                res = mDBHelper.searchDictionary5(searchBar4.getText().toString());
            }
            else{
                res = mDBHelper.searchDictionary6(searchBar4.getText().toString());
            }

            if (res.getCount() == 0) {
                //no data
                dataList.add("No Matches Found!");
                return;
            } else {

                while (res.moveToNext()) {
                    if(!res.getString(0).contains("_")){
                        String htmlColourStr = "<font color=#FD7E7E>" + res.getString(0) + "</font> <font color=#3BFF00>" +" | "+ res.getString(1)+" | " + "</font>" + "<font color=#ffffff>" + res.getString(2) + "</font>";
                        dataList.add(htmlColourStr);
                        meanList.add(res.getString(1));
                    }
                }
            }
        }
        catch (Exception e){
           // dataList.add("Dictionary Not Found!");
            Toast.makeText(this, "Dictionary Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    //suthra list
    public void createTableNameList() {

        Cursor res = mDBHelper2.getTableNameList();

        if (res.getCount() == 0) {
            //no data

            return;
        } else {

            while (res.moveToNext()) {
                if(!res.getString(0).contains("_")){
                    tableList2.add(res.getString(0));
                    tableString=tableString+" union "+res.getString(0);
                }
            }
        }
    }

    //Uncomment following section to create jsonString and save in external storage.

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case WRITE_EXTERNAL_STO_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    jsonStringCreate js = new jsonStringCreate();
//                    js.write();
//                } else {
//                    Toast.makeText(this, "Enable permission to save image", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }

}
