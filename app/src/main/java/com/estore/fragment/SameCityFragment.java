package com.estore.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.estore.activity.ProductInfoActivity;
import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.estore.view.LoadListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SameCityFragment extends Fragment implements View.OnClickListener,LoadListView.ILoadListener {

    private LoadListView sameCity;
    private BaseAdapter mAdapter;
    private LinkedList<Product.Products> mListItems=new LinkedList<>();
    Integer page=0;
    private TextView phone;
    private TextView computer;
    private TextView computertext;
    private TextView others;
    private ImageView prosort;

    Integer orderFlag=0;
    private TextView all;
    private Button up;
    private Button down;
    Integer time=0;
    List<String> popContents=new ArrayList<String>();

    //    private ListView actualListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_same_city, null);
        sameCity = ((LoadListView) view.findViewById(R.id.lv_same_city));
        all = ((TextView) view.findViewById(R.id.tv_all));
        phone = ((TextView) view.findViewById(R.id.tv_phone));
        computer = ((TextView) view.findViewById(R.id.tv_computer));
        computertext = ((TextView) view.findViewById(R.id.tv_computertext));
        others = ((TextView) view.findViewById(R.id.tv_others));
        prosort = ((ImageView) view.findViewById(R.id.iv_sort));
        sameCity.setInterface(this);
        getSameCityProductInfo();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        popContents.add("价格从高到低");
        popContents.add("价格从低到高");
        all.setOnClickListener(this);
        phone.setOnClickListener(this);
        computer.setOnClickListener(this);
        computertext.setOnClickListener(this);
        others.setOnClickListener(this);
        prosort.setOnClickListener(this);
        sameCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product.Products pp=mListItems.get(i);
                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("pp",pp);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void getSameCityProductInfo() {
        String url= HttpUrlUtils.HTTP_URL+"getSameCityProducts";
        RequestParams requestParams=new RequestParams(url);
        requestParams.addQueryStringParameter("orderFlag",orderFlag+"");
        requestParams.addQueryStringParameter("page",page+1+"");
        Log.i("cc",url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("cc",result);
                Gson gson=new Gson();
                Product product=gson.fromJson(result,Product.class);
                mListItems.clear();
                mListItems.addAll(product.list);
                if(mAdapter==null){
                    mAdapter=new MyAdapter();
                }else{
                    mAdapter.notifyDataSetChanged();
                }
                sameCity.setAdapter(mAdapter);
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



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                orderFlag=0;
                break;
            case R.id.tv_phone:
                orderFlag=1;
                break;
            case R.id.tv_computer:
                orderFlag=2;
                break;
            case R.id.tv_computertext:
                orderFlag=3;
                break;
            case R.id.tv_others:
                orderFlag=4;
                break;
            case R.id.iv_sort:
                initPopupWindow(prosort);
                break;
        }
        Log.i("cc",orderFlag+"");
        getSameCityProductInfo();
    }

    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getSameCityProductInfo();

                sameCity.loadComplete();
            }
        }, 2000);
    }

    public class MyAdapter extends  BaseAdapter{
        private ImageView productPhoto;
        private TextView productDetail;
        private TextView productKind;
        private TextView cityAddress;
        private TextView schoolAddress;
        private TextView productPrice;
        private TextView productNum;

        @Override
        public int getCount() {
            return mListItems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=View.inflate(getActivity(),R.layout.eh_item,null);
            productPhoto = ((ImageView) view.findViewById(R.id.iv_project_photo));
            productDetail = ((TextView) view.findViewById(R.id.tv_project_detail));
            productKind = ((TextView) view.findViewById(R.id.tv_product_kind));
            cityAddress = ((TextView) view.findViewById(R.id.tv_eh_cityaddress));
            schoolAddress = ((TextView) view.findViewById(R.id.tv_eh_schooladdress));
            productPrice = ((TextView) view.findViewById(R.id.tv_project_price));
            productNum = ((TextView) view.findViewById(R.id.tv_product_number));
            Product.Products list=mListItems.get(i);
            String[] imgurl=list.imgurl.split("=");
            x.image().bind(productPhoto,HttpUrlUtils.HTTP_URL+imgurl[0]);
            productDetail.setText(list.description);
            productKind.setText(list.category);
            cityAddress.setText(list.proaddress);
            schoolAddress.setText(list.schoolname);
            productPrice.setText(list.estoreprice+"");
            productNum.setText("总共"+list.pnum+"件");
            return view;
        }
    }
    public void initPopupWindow(View v){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_time_item,null);
        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,200);
        ListView lv= (ListView) view.findViewById(R.id.lv_zonghe_paixu);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),R.layout.popupwindow_listview_item,popContents);
        lv.setAdapter(arrayAdapter);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(v);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();

                if(position==0){
                    orderFlag=5;
                }else if(position==1){
                    orderFlag=6;
                }

                getSameCityProductInfo();
            }
        });
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getSameCityProductInfo();
    }
}
