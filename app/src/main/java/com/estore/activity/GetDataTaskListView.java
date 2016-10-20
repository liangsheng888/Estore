package com.estore.activity;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.estore.fragment.SameCityFragment;
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

/**
 * Created by 执行Z on 2016/9/25.
 */
public  class GetDataTaskListView extends AsyncTask<Void, ArrayList<Product.Products>, Void> {
    private static Integer page=1;
    private ArrayList<Product.Products> lists=new ArrayList<>();
    private PullToRefreshListView pullToRefreshListView;//lv_same_city
    private BaseAdapter adapter;
    private LinkedList<Product.Products> mListItems;
    Product product;
    private  Integer orderFlag;
    private String url;

    public GetDataTaskListView(PullToRefreshListView pullToRefreshListView /*listview*/,
                               BaseAdapter adapter, LinkedList<Product.Products> mListItems /*listItems*/,
                               int orderFlag /*分类标识符*/, String url /*请求链接*/) {
        // TODO 自动生成的构造函数存根
        this.pullToRefreshListView = pullToRefreshListView;
        this.adapter = adapter;
        this.mListItems = mListItems;
        this.orderFlag=orderFlag;
        this.url=url;
    }

    @Override
    protected void onProgressUpdate(ArrayList<Product.Products>... values) {
        super.onProgressUpdate(values);
        PullToRefreshBase.Mode mode = pullToRefreshListView.getCurrentMode();

        for (Product.Products products : values[0]) {
            if(mode==PullToRefreshBase.Mode.PULL_FROM_START){

            }else {
                mListItems.add(products);
            }
        }
        if(adapter!=null) {
            adapter.notifyDataSetChanged();
        }
        pullToRefreshListView.onRefreshComplete();
    }

    @Override
    protected Void doInBackground(Void... params) {
//        String url= HttpUrlUtils.HTTP_URL+"getSameCityProducts";
        RequestParams requestParams=new RequestParams(url);
        requestParams.addQueryStringParameter("orderFlag",orderFlag+"");
        requestParams.addQueryStringParameter("page",page+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                page++;
                Gson gson=new Gson();
                Product product=gson.fromJson(result,Product.class);
                for (Product.Products products : product.list) {
                    lists.add(products);
                }
                publishProgress(lists);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                page--;
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        return null;
    }

//    @Override
//    protected void onPostExecute() {
//        // TODO 自动生成的方法存根
//        super.onPostExecute(result);
//        //得到当前的模式
//        PullToRefreshBase.Mode mode = lv_same_city.getCurrentMode();
//        if (mode == PullToRefreshBase.Mode.PULL_FROM_START) {
////            mListItems.addFirst("这是刷新出来的数据");
//
//        } else {
////            mListItems.addLast("这是加载出来的数据");
//        }
//        // 通知数据改变了
//        adapter.notifyDataSetChanged();
//        // 加载完成后停止刷新
//        lv_same_city.onRefreshComplete();
//
//    }
}
