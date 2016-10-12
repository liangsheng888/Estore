package com.estore.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.estore.activity.R;

public class EhFragment extends Fragment {
    private RadioGroup rg_eh_tab;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment oldFragment;
    private Fragment newFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_eh,null);
        rg_eh_tab = ((RadioGroup) view.findViewById(R.id.rg_eh_tab));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        switchFragment(new SameCityFragment());
        rg_eh_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment=null;
                switch (checkedId){

                    case R.id.rb_samecity:
                        if (fragment1==null)
                            fragment1=new SameCityFragment();
                        newFragment=fragment1;
                        break;
                    case R.id.rb_schools:
                        if (fragment2==null)
                            fragment2=new SchoolsFragment();
                        newFragment=fragment2;
                }
                switchFragment(newFragment);

            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void switchFragment(Fragment fragment) {
        if (fragment==null) {
            fragment1=fragment=new SameCityFragment();
        }
        FragmentManager fm=getChildFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
//        ft.replace(R.id.fl_eh_tab,fragment);

        if(oldFragment!=null && !oldFragment.isHidden() && oldFragment.isAdded()){
            ft.hide(oldFragment);
        }
        if(fragment!=null && fragment.isAdded() && fragment.isHidden()){
            ft.show(fragment);
        }else {
            //fragment1
            ft.add(R.id.fl_eh_tab,fragment);
            ft.addToBackStack(null);
        }
        oldFragment = fragment;
        ft.commit();
    }


}
