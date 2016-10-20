package com.estore.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class SchoolsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SchoolsFragment";
    private PullToRefreshListView lv_schools;
    private BaseAdapter adapter;
    private ListView actualListView;
    private LinkedList<Product.Products> mListItems;
    List<Product.Products> productList=new ArrayList<Product.Products>();
    private Button schoolSortPhone;
    private Button schoolSortComputer;
    private Button schoolSortMatch;
    private Button schoolSortOthers;
    private Button schoolSortPriceUp;
    private Button schoolSortPriceDown;
    private Integer orderFlag=0;
    private Integer page=1;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_schools,null);

        lv_schools = ((PullToRefreshListView) view.findViewById(R.id.lv_schools));
        schoolSortPhone = ((Button) view.findViewById(R.id.btn_schoolsort_phone));
        schoolSortComputer = ((Button) view.findViewById(R.id.btn_schoolsort_computer));
        schoolSortMatch = ((Button) view.findViewById(R.id.btn_schoolsort_match));
        schoolSortOthers = ((Button) view.findViewById(R.id.btn_schoolsort_others));
        schoolSortPriceUp = ((Button) view.findViewById(R.id.btn_schoolsort_price_up));
        schoolSortPriceDown = ((Button) view.findViewById(R.id.btn_sort_schoolprice_down));

        getSchoolList();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        schoolSortPhone.setOnClickListener(this);
        schoolSortComputer.setOnClickListener(this);
        schoolSortMatch.setOnClickListener(this);
        schoolSortOthers.setOnClickListener(this);
        schoolSortPriceUp.setOnClickListener(this);
        schoolSortPriceDown.setOnClickListener(this);

//        lvSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Product.Products pp=productList.get(i);
//                Log.i(TAG,i+"");
//                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("pp",pp);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        actualListView=lv_schools.getRefreshableView();
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product.Products pp=productList.get(i);
                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("pp",pp);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lv_schools.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

              //  new GetDataTaskListView(lv_schools,adapter,mListItems,orderFlag,url).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                new GetDataTaskListView(lv_schools,adapter,mListItems,orderFlag,url).execute();
            }
        });
        new GetDataTaskListView(lv_schools,adapter,mListItems,orderFlag,url).execute();

        actualListView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_schoolsort_phone:
                orderFlag=1;
                break;
            case R.id.btn_schoolsort_computer:
                orderFlag=2;
                break;
            case R.id.btn_schoolsort_match:
                orderFlag=3;
                break;
            case R.id.btn_schoolsort_others:
                orderFlag=4;
                break;
            case R.id.btn_schoolsort_price_up:
                orderFlag=5;
                break;
            case R.id.btn_sort_schoolprice_down:
                orderFlag=6;
                break;
        }
        getSchoolList();

    }


//    private void initView() {
//        initPTRListView();
//        initListView();
//    }
//
//    private void initListView() {
//        lv_schools.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
//                new GetDataTaskListView(lv_schools, adapter,mListItems).execute();
//            }
//        });
//
//        // 添加滑动到底部的监听器
//        lv_schools.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//
//            @Override
//            public void onLastItemVisible() {
//                Toast.makeText(getActivity(), "已经到底了", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //mPullRefreshListView.isScrollingWhileRefreshingEnabled();//看刷新时是否允许滑动
//        //在刷新时允许继续滑动
//        lv_schools.setScrollingWhileRefreshingEnabled(true);
//        //mPullRefreshListView.getMode();//得到模式
//        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
//        lv_schools.setMode(PullToRefreshBase.Mode.BOTH);
//
//    }
//
//    private void initPTRListView() {
//        actualListView=lv_schools.getRefreshableView();
//        mListItems=new LinkedList<Product.Products>();
//        mListItems.addAll(productList);
//        actualListView.setAdapter(adapter);
//        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("SchoolsFragment","position="+position+"");
//                int curposition=position-1;
//                Product.Products pp=productList.get(curposition);
//                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("pp",pp);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//    }
//
//
    public class MyAdapter extends BaseAdapter{
        private TextView tv_project_description;
    private TextView tvProductKind;

    @Override
        public int getCount() {
            return productList.size();
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
            ImageView iv_project_photo = ((ImageView) view.findViewById(R.id.iv_project_photo));
            TextView tv_project_name = ((TextView) view.findViewById(R.id.tv_project_name));
            TextView tv_project_price = ((TextView) view.findViewById(R.id.tv_project_price));
            tv_project_description = ((TextView) view.findViewById(R.id.tv_project_description));
            tvProductKind = ((TextView) view.findViewById(R.id.tv_product_kind));
            Product.Products list = productList.get(position);
            tv_project_name.setText(list.name);
            tv_project_price.setText(list.estoreprice + "");
            tv_project_description.setText(list.description);
            tvProductKind.setText(list.category);
            String[] imgurl=list.imgurl.split("=");
            x.image().bind(iv_project_photo, HttpUrlUtils.HTTP_URL+imgurl[0]);
            return view;
        }

    };
    public void getSchoolList(){

        url=HttpUrlUtils.HTTP_URL+"/getSchoolProducts";
        RequestParams params=new RequestParams(url);
        params.addQueryStringParameter("orderFlag",orderFlag+"");
        params.addQueryStringParameter("page",page+"");
        Log.i(TAG,url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG,result+"==========");
                Gson gson=new Gson();
                Product project=gson.fromJson(result,Product.class);
                productList.clear();
                productList.addAll(project.list);
                if(adapter==null) {
                    adapter = new MyAdapter();
                }else if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }
                actualListView.setAdapter(adapter);
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
}
