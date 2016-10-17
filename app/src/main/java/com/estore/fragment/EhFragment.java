package com.estore.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.estore.activity.MainActivity;
import com.estore.activity.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EhFragment extends Fragment {
    private RadioGroup rg_eh_tab;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment oldFragment;
    private Fragment newFragment;
    private ImageView ehSort;

    List<String> popContents=new ArrayList<String>();
    int orderFlag=0;
    List<Product.Products> products=new ArrayList<Product.Products>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_eh,null);
        rg_eh_tab = ((RadioGroup) view.findViewById(R.id.rg_eh_tab));
        ehSort = ((ImageView) view.findViewById(R.id.iv_eh_sort));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        switchFragment(new SameCityFragment());
        //orderFlag=0默认显示按照最新发布时间排序的全部商品
        popContents.add("手机");//orderFlag=1
        popContents.add("电脑");//orderFlag=2
        popContents.add("手表");//orderFlag=3
        popContents.add("其他");//orderFlag=4
        popContents.add("价格 ↑");//orderFlag=5
        popContents.add("价格 ↓");//orderFlag=6
        ehSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopupWindow(view);
            }
        });

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

    public void initPopupWindow(View v){
        //content
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.lv_zonghe_paixu,null);
        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,200);

        //listview设置数据源
        ListView lv= (ListView) view.findViewById(R.id.lv_zonghe_paixu);

        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),R.layout.lv_item_zonghe_paixu,popContents);
        lv.setAdapter(arrayAdapter);

        //popupwiondow外面点击，popupwindow消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //显示在v的下面
        popupWindow.showAsDropDown(v);

        //listview的item点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupwidow
                popupWindow.dismiss();
                //排序
                if(position==0){
                    orderFlag=1;
                }else if(position==1){
                    orderFlag=2;
                }else if(position==2){
                    orderFlag=3;
                }else if(position==3) {
                    orderFlag = 4;
                }else if(position==2) {
                    orderFlag = 4;
                }else if(position==2) {
                    orderFlag = 5;
                }else if(position==5) {
                    orderFlag = 6;
                }
                //重新获取数据源，按价格排序

                String url= HttpUrlUtils.HTTP_URL+"queryProductBean";
                RequestParams requestParams=new RequestParams(url);
                requestParams.addQueryStringParameter("orderFlag",orderFlag+"");
                requestParams.addQueryStringParameter("prowhere",((MainActivity)getActivity()).getProwhere()+"");
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson=new Gson();
                        Type type=new TypeToken<List<Product>>(){}.getType();
                        List<Product.Products> newList=new ArrayList<Product.Products>();
                        newList=gson.fromJson(result,type);
                        products.clear();
                        products.addAll(newList);
                        ((MainActivity)getActivity()).setProductList(products);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
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
