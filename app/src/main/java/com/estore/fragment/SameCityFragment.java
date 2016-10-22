package com.estore.fragment;


import android.app.Fragment;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.GetDataTaskListView;
import com.estore.activity.MainActivity;
import com.estore.activity.ProductInfoActivity;
import com.estore.activity.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SameCityFragment extends Fragment implements View.OnClickListener{
    private ListView lvSameCity;
    Integer orderFlag=0;
    private TextView sortPhone;
    private TextView sortComputer;
    private TextView sortMatch;
    private TextView sortOthers;
    private TextView sortPriceUp;
    private TextView sortPriceDown;

    private static final String TAG = "SameCityFragment";
    private PullToRefreshListView lv_same_city;
    private BaseAdapter adapter;
    private ImageView iv_project_photo;
//    List<Product.Products> productList=new ArrayList<Product.Products>();
    private ListView actualListView;
    private LinkedList<Product.Products> mListItems=new LinkedList<>();
    private String url;
//    private static Integer page;
    Integer page=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_same_city,null);
        lv_same_city = ((PullToRefreshListView) view.findViewById(R.id.lv_same_city));
        sortPhone = ((TextView) view.findViewById(R.id.btn_sort_phone));
        sortComputer = ((TextView) view.findViewById(R.id.btn_sort_computer));
        sortMatch = ((TextView) view.findViewById(R.id.btn_sort_match));
        sortOthers = ((TextView) view.findViewById(R.id.btn_sort_others));
        sortPriceUp = ((TextView) view.findViewById(R.id.btn_sort_price_up));
        sortPriceDown = ((TextView) view.findViewById(R.id.btn_sort_price_down));
        getSameCityList();
        return view;



    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sortPhone.setOnClickListener(this);
        sortComputer.setOnClickListener(this);
        sortMatch.setOnClickListener(this);
        sortOthers.setOnClickListener(this);
        sortPriceUp.setOnClickListener(this);
        sortPriceDown.setOnClickListener(this);
//        initView();
        actualListView=lv_same_city.getRefreshableView();
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product.Products pp=mListItems.get(i);
                Log.i(TAG,i+"");
                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("pp",pp);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        lv_same_city.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {


                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

////                try {
////                    Thread.sleep(3000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//
////                new GetDataTaskListView(lv_same_city,adapter,mListItems,orderFlag,url,page).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=((MainActivity)getActivity()).getPage();
                orderFlag=((MainActivity)getActivity()).getOrderFlag();
                page++;
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new GetDataTaskListView(lv_same_city,adapter,mListItems,orderFlag,url,page).execute();
            }
        });

        lv_same_city.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "已经到底了", Toast.LENGTH_SHORT).show();
//                lv_same_city.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
            }
        });
        lv_same_city.setScrollingWhileRefreshingEnabled(true);
        lv_same_city.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        new GetDataTaskListView(lv_same_city,adapter,mListItems,orderFlag,url,page).execute();
//        lv_same_city.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                String label = DateUtils.formatDateTime(
//                        getActivity(),
//                        System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME
//                                | DateUtils.FORMAT_SHOW_DATE
//                                | DateUtils.FORMAT_ABBREV_ALL);
//                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//
//                new GetDataTaskListView(lv_same_city,adapter,mListItems,orderFlag,url).execute();
//
//            }
//
//
//        });
//        new GetDataTaskListView(lv_same_city,adapter,mListItems,orderFlag,url).execute();
        actualListView.setAdapter(adapter);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            sortPhone.setBackgroundColor(Color.WHITE);
            sortComputer.setBackgroundColor(Color.WHITE);
            sortMatch.setBackgroundColor(Color.WHITE);
            sortOthers.setBackgroundColor(Color.WHITE);
            sortPriceUp.setBackgroundColor(Color.WHITE);
            sortPriceDown.setBackgroundColor(Color.WHITE);
            getSameCityList();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_sort_phone:
                ((MainActivity)getActivity()).setOrderFlag(1);
                sortPhone.setBackgroundColor(Color.RED);
                sortComputer.setBackgroundColor(Color.WHITE);
                sortMatch.setBackgroundColor(Color.WHITE);
                sortOthers.setBackgroundColor(Color.WHITE);
                sortPriceUp.setBackgroundColor(Color.WHITE);
                sortPriceDown.setBackgroundColor(Color.WHITE);
                break;
            case R.id.btn_sort_computer:
                ((MainActivity)getActivity()).setOrderFlag(2);
                sortPhone.setBackgroundColor(Color.WHITE);
                sortComputer.setBackgroundColor(Color.RED);
                sortMatch.setBackgroundColor(Color.WHITE);
                sortOthers.setBackgroundColor(Color.WHITE);
                sortPriceUp.setBackgroundColor(Color.WHITE);
                sortPriceDown.setBackgroundColor(Color.WHITE);
                break;
            case R.id.btn_sort_match:
                ((MainActivity)getActivity()).setOrderFlag(3);
                sortPhone.setBackgroundColor(Color.WHITE);
                sortComputer.setBackgroundColor(Color.WHITE);
                sortMatch.setBackgroundColor(Color.RED);
                sortOthers.setBackgroundColor(Color.WHITE);
                sortPriceUp.setBackgroundColor(Color.WHITE);
                sortPriceDown.setBackgroundColor(Color.WHITE);
                break;
            case R.id.btn_sort_others:
                ((MainActivity)getActivity()).setOrderFlag(4);
                sortPhone.setBackgroundColor(Color.WHITE);
                sortComputer.setBackgroundColor(Color.WHITE);
                sortMatch.setBackgroundColor(Color.WHITE);
                sortOthers.setBackgroundColor(Color.RED);
                sortPriceUp.setBackgroundColor(Color.WHITE);
                sortPriceDown.setBackgroundColor(Color.WHITE);
                break;
            case R.id.btn_sort_price_up:
                ((MainActivity)getActivity()).setOrderFlag(5);
                sortPhone.setBackgroundColor(Color.WHITE);
                sortComputer.setBackgroundColor(Color.WHITE);
                sortMatch.setBackgroundColor(Color.WHITE);
                sortOthers.setBackgroundColor(Color.WHITE);
                sortPriceUp.setBackgroundColor(Color.RED);
                sortPriceDown.setBackgroundColor(Color.WHITE);
                break;
            case R.id.btn_sort_price_down:
                ((MainActivity)getActivity()).setOrderFlag(6);
                sortPhone.setBackgroundColor(Color.WHITE);
                sortComputer.setBackgroundColor(Color.WHITE);
                sortMatch.setBackgroundColor(Color.WHITE);
                sortOthers.setBackgroundColor(Color.WHITE);
                sortPriceUp.setBackgroundColor(Color.WHITE);
                sortPriceDown.setBackgroundColor(Color.RED);
                break;
        }

        ((MainActivity)getActivity()).setPage(1);

        getSameCityList();


    }


    //    private void initView() {
//        initPTRListView();
//        initListView();
//    }
//
//
//    private void initPTRListView() {
//        lv_same_city.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                //设置下拉时显示的日期和时间
//                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//
//                // 更新显示的label
//                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//                // 开始执行异步任务，传入适配器来进行数据改变
//                new GetDataTaskListView(lv_same_city, adapter,mListItems).execute();
//            }
//        });
//
//        // 添加滑动到底部的监听器
//        lv_same_city.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//
//            @Override
//            public void onLastItemVisible() {
//                Toast.makeText(getActivity(), "已经到底了", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //mPullRefreshListView.isScrollingWhileRefreshingEnabled();//看刷新时是否允许滑动
//        //在刷新时允许继续滑动
//        lv_same_city.setScrollingWhileRefreshingEnabled(true);
//        //mPullRefreshListView.getMode();//得到模式
//        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
//        lv_same_city.setMode(PullToRefreshBase.Mode.BOTH);
//
//    }
//
//    private void initListView() {
////通过getRefreshableView()来得到一个listview对象
//        actualListView = lv_same_city.getRefreshableView();
//        mListItems = new LinkedList<Product.Products>();
//        //把string数组中的string添加到链表中
//        mListItems.addAll(productList);
//        actualListView.setAdapter(adapter);
//        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int position_=position-1;
//                Product.Products pp=productList.get(position_);
//                Log.i(TAG,position_+"");
//                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("pp",pp);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//    }
    public class MyAdapter extends BaseAdapter{

        private TextView tv_product_kind;
        private TextView tv_project_description;

        @Override
        public int getCount() {
            return mListItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.eh_item, null);
            iv_project_photo = ((ImageView) view.findViewById(R.id.iv_project_photo));
            TextView tv_project_name = ((TextView) view.findViewById(R.id.tv_project_name));
            TextView tv_project_price = ((TextView) view.findViewById(R.id.tv_project_price));
            tv_project_description = ((TextView) view.findViewById(R.id.tv_project_description));
            tv_product_kind = ((TextView) view.findViewById(R.id.tv_product_kind));
            Product.Products list = mListItems.get(position);
            tv_project_name.setText(list.name);
            tv_project_price.setText(list.estoreprice + "");
            tv_project_description.setText(list.description);
            tv_product_kind.setText(list.category);
            String[] imgurl=list.imgurl.split("=");
            x.image().bind(iv_project_photo, HttpUrlUtils.HTTP_URL+imgurl[0]);
            return view;
        }
    }
    //
    public void getSameCityList(){
        page=((MainActivity)getActivity()).getPage();
        orderFlag=((MainActivity)getActivity()).getOrderFlag();
        url=HttpUrlUtils.HTTP_URL+"getSameCityProducts";
        RequestParams params=new RequestParams(url);
        Log.i(TAG,HttpUrlUtils.HTTP_URL+"imgurl");
        params.addQueryStringParameter("orderFlag",orderFlag+"");
        params.addQueryStringParameter("page",page+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG,result+"==========");
                Gson gson=new Gson();
                Product project=gson.fromJson(result,Product.class);

                mListItems.clear();
                mListItems.addAll(project.list);
                if(adapter==null) {
                    adapter = new MyAdapter();
                }else if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }
                actualListView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG,"fail"+"==========");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
