package com.pitaka.app.pitaka;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "dictionary.sqlite";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        if (mDataBase != null) {
            Toast.makeText(mContext, "Done!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
        }
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public Cursor getData(String table){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+table,null);
        return res;

    }

    public Cursor getTableNameList(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        //Cursor result=sqLiteDatabase.rawQuery("select name from sqlite_master where type='table' like '%\"+searchText+\"%' ",null);
        Cursor result=sqLiteDatabase.rawQuery("select name from sqlite_master where type='table'",null);
        return result;
    }

    public Cursor search(String searchText){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("select * from pwbhimi where Sinhala like '%"+searchText+"%'",null);
        return result;
    }

}
