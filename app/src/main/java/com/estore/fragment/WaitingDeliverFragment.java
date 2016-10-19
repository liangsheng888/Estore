package com.estore.fragment;
/*
我的订单---待发货页面
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estore.activity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingDeliverFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_waiting_deliver, null);
    return view;
    }

}
