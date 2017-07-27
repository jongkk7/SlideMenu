package com.yjk.ydrawanimationmenu;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yjk on 2017. 6. 23..
 */

public class Fragment02 extends Fragment {
    View view;
    AppCompatActivity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment02, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = (AppCompatActivity) context;
        super.onAttach(context);
    }



}
