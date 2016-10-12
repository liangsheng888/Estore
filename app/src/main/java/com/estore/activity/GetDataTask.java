package com.estore.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by 执行Z on 2016/9/25.
 */
public  class GetDataTask extends AsyncTask<Void,ArrayList<Product.Products>, Void> {
    private static Integer page=1;
    private ArrayList<Product.Products> lists=new ArrayList<>();
    Product pro;

    //private PullToRefreshListView mPullRefreshListView;
    private PullToRefreshGridView mPullRefreshListView;
    private BaseAdapter mAdapter;
    private LinkedList<Product.Products> mListItems;


    public GetDataTask(PullToRefreshGridView mPullRefreshListView, BaseAdapter mAdapter, LinkedList<Product.Products> mListItems) {
        this.mPullRefreshListView = mPullRefreshListView;
        this.mAdapter = mAdapter;
        this.mListItems = mListItems;
    }

    @Override
    protected void onProgressUpdate(ArrayList<Product.Products>... values) {
        //得到当前的模式
        PullToRefreshBase.Mode mode = mPullRefreshListView.getCurrentMode();
        for (Product.Products p:values[0]) {
            if (mode == PullToRefreshBase.Mode.PULL_FROM_START) {
                Log.e("TAG","pfirst:"+ p.toString());

            } else {

                Log.e("TAG","plast:"+ p.toString());
                mListItems.addLast(p);

            }

        }

        // 通知数据改变了
        mAdapter.notifyDataSetChanged();
        // 加载完成后停止刷新
        mPullRefreshListView.onRefreshComplete();
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //模拟请求
        String url = HttpUrlUtils.HTTP_URL + "getAllProducts?page="+(page+1);
        Log.e("TAG",url);

        RequestParams param = new RequestParams(url);
        x.http().get(param, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                page++;
                Log.e("TAG",page+"");
                Gson gson=new Gson();
                Product pro=gson.fromJson(result,Product.class);
                    for(Product.Products pp:pro.list){
                        lists.add(pp);
                    }

                publishProgress(lists);

                Log.e("TAG",pro.toString());
                 // mListItems.addAll(pro.list);
                Log.e("TAG",pro.list.toString());

                Log.e("TAG",lists+"lll");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

              //  Toast.makeText(getApplicationContext.this,"网络超时，请重新登录",Toast.LENGTH_SHORT).show();
                page=page-1;
                Log.e("TAG",page+"");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

       return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}
