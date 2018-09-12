package com.pitaka.app.pitaka;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.pitaka.app.pitaka.MainActivity.stringBuffer;
import static java.lang.System.err;



public class Sinhala extends Fragment {


    public TextView tv;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), stringBuffer, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sinhala, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        tv = view.findViewById(R.id.sampleView);
        tv.setText(stringBuffer);

    }

    @Override
    public void onResume() {
        super.onResume();

    }



    public void setData(String data){

        try{
            Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.d("error",data);
        }
    }
}
