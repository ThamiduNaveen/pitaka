package com.pitaka.app.pitaka;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOException;

import static com.pitaka.app.pitaka.MainActivity.tableString;

public class ContentSearch extends AppCompatActivity {

    private DatabaseHelper2 mDBHelper3;
    private SQLiteDatabase mDb3;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_search);
        Toolbar toolbar=findViewById(R.id.toolbar);

        tv=findViewById(R.id.search);

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
                searchContent(searchT.getText().toString(),tableString);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void searchContent(String text,String tables) {

        Cursor res = mDBHelper3.searchContentSinhala(text,tables);

        if (res.getCount() == 0) {
            //no data

            return;
        } else {

            while (res.moveToNext()) {
                    tv.setText(res.getString(2));
            }
        }
    }
}
