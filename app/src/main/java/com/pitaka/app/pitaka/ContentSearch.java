package com.pitaka.app.pitaka;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toolbar;

import java.io.IOException;


public class ContentSearch extends AppCompatActivity {

    private DatabaseHelper2 mDBHelper3;
    private SQLiteDatabase mDb3;



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

        searchT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SinglishTranslator st = new SinglishTranslator();
                String msg = st.convertText(searchT.getText().toString());
                searchContentS(msg);  //Sinhala Search
                searchContentP(msg);  //Paali Search


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
                //listDataHeader.add(res.getString(0));
                //listDataItems.add(res.getString(2));

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
                //listDataHeader.add(res.getString(0));
                //listDataItems.add(res.getString(2));

            }
        }
    }
}
