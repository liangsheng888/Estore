package com.estore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.activity.ProductInfoActivity;
import com.estore.activity.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.estore.view.LoadListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.LinkedList;

public class SchoolsFragment extends Fragment implements View.OnClickListener,LoadListView.ILoadListener {

    private LoadListView schools;
    private BaseAdapter mAdapter;
    private LinkedList<Product.Products> mListItems=new LinkedList<>();
    Integer page=0;
//    private ListView actualListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_schools, null);
        schools = ((LoadListView) view.findViewById(R.id.lv_schools));
        schools.setInterface(this);
        getSameCityProductInfo();
//        mAdapter=new MyAdapter();
//        mAdapter.notifyDataSetChanged();
//        sameCity.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        schools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void getSameCityProductInfo() {
        String url= HttpUrlUtils.HTTP_URL+"getSchoolProducts";
        Integer orderFlag=0;
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

                schools.setAdapter(mAdapter);

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

    }

    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getSameCityProductInfo();

                schools.loadComplete();
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getSameCityProductInfo();
    }
}
