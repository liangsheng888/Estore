package com.estore.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estore.activity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingEvaluateFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_waiting_evaluate, null);
    return  view;
    }

}
