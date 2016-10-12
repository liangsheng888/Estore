package com.estore.activity;

import android.os.AsyncTask;
import android.widget.BaseAdapter;


import com.estore.pojo.Product;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.LinkedList;

/**
 * Created by 执行Z on 2016/9/25.
 */
public  class GetDataTaskListView extends AsyncTask<Void, Void, Void> {

    private PullToRefreshListView lv_same_city;
    private BaseAdapter adapter;
    private LinkedList<Product.Products> mListItems;

    public GetDataTaskListView(PullToRefreshListView listView,
                               BaseAdapter adapter, LinkedList<Product.Products> listItems) {
        // TODO 自动生成的构造函数存根
        lv_same_city = listView;
        this.adapter = adapter;
        mListItems = listItems;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //模拟请求
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO 自动生成的方法存根
        super.onPostExecute(result);
        //得到当前的模式
        PullToRefreshBase.Mode mode = lv_same_city.getCurrentMode();
        if (mode == PullToRefreshBase.Mode.PULL_FROM_START) {
//            mListItems.addFirst("这是刷新出来的数据");

        } else {
//            mListItems.addLast("这是加载出来的数据");
        }
        // 通知数据改变了
        adapter.notifyDataSetChanged();
        // 加载完成后停止刷新
        lv_same_city.onRefreshComplete();

    }
}
