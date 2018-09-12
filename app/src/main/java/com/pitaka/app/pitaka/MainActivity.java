package com.pitaka.app.pitaka;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Typeface;
//import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    private SQLiteDatabase mDb;
    public static StringBuffer stringBuffer;
    ViewPager viewPager;
    DrawerLayout drawer;


    List<NLevelItem> list;
    ListView listView;

    String jsonStringList = "[{\"title\":\"winyapitaka\",\"children\":[]},{\"title\":\"suthrapitaka\",\"children\":[{\"title\":\"deeganikaya\",\"children\":[{\"title\":\"seelakkandaWaggapali\",\"children\":[{\"title\":\"1.BrahmajalaSuttan\",\"children\":[]},{\"title\":\"2.SaamanchapalaSuththan\",\"children\":[]},{\"title\":\"3.Ambattasuththan\",\"children\":[]}]},{\"title\":\"mahawaggapaali\",\"children\":[]}]},{\"title\":\"majjimanikaya\",\"children\":[]},{\"title\":\"sanukthanikaya\",\"children\":[]}]},{\"title\":\"abhidammapitaka\",\"children\":[]}]";
    //String jsonStringList = "[{\"title\":\"Root 1\",\"children\":[{\"title\":\"Child 11\",\"children\":[{\"title\":\"Extended Child 111\",\"children\":[{\"title\":\"Super Extended Child 1111\",\"children\":[{\"title\":\"Super Extended Child 1111\",\"children\":[{\"title\":\"Super Extended Child 1111\",\"children\":[]}]}]}]},{\"title\":\"Extended Child 112\",\"children\":[]},{\"title\":\"Extended Child 113\",\"children\":[]}]},{\"title\":\"Child 12\",\"children\":[{\"title\":\"Extended Child 121\",\"children\":[]},{\"title\":\"Extended Child 122\",\"children\":[]}]},{\"title\":\"Child 13\",\"children\":[]}]},{\"title\":\"Root 2\",\"children\":[{\"title\":\"Child 21\",\"children\":[{\"title\":\"Extended Child 211\",\"children\":[]},{\"title\":\"Extended Child 212\",\"children\":[]},{\"title\":\"Extended Child 213\",\"children\":[]}]},{\"title\":\"Child 22\",\"children\":[{\"title\":\"Extended Child 221\",\"children\":[]},{\"title\":\"Extended Child 222\",\"children\":[]}]},{\"title\":\"Child 23\",\"children\":[]}]},{\"title\":\"Root 1\",\"children\":[]}]";

    private View mRightDrawerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Sinhala"));
        tabLayout.addTab(tabLayout.newTab().setText("Paali"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


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
        final Button serchSuthraBT = (Button) findViewById(R.id.search_Button_navigation);
        final Button thripitakaBT = (Button) findViewById(R.id.thripitaka_Button_navigation);
        serchSuthraBT.setVisibility(View.GONE);
        serchSuthraBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.GONE);
                serchSuthraBT.setVisibility(View.GONE);
                thripitakaBT.setVisibility(View.VISIBLE);

            }
        });

        thripitakaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.VISIBLE);
                thripitakaBT.setVisibility(View.GONE);
                serchSuthraBT.setVisibility(View.VISIBLE);
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
                            // Toast.makeText(MainActivity.this, "Clicked on: "+Title, Toast.LENGTH_SHORT).show();
                            //createVerseList(Title);
                            createVerseList("suthra1");
                            viewPager.getAdapter().notifyDataSetChanged();
                            drawer.closeDrawer(GravityCompat.START);

                        }
                    });
                }

                return view;
            }
        });

        return superChild;
    }

    public void createVerseList(String verse) {
        Cursor res = mDBHelper.getVerse(verse);

        if (res.getCount() == 0) {
            //no


            return;
        } else {
            stringBuffer = new StringBuffer();

            while (res.moveToNext()) {
                stringBuffer.append(res.getString(0) + "\n");
                Toast.makeText(this, res.getString(0), Toast.LENGTH_SHORT).show();

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

}
