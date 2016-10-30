package com.estore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.estore.activity.R;


public class LoadListViewPaiMAI extends ListView implements OnScrollListener {
    View footer;// 底部布局；
//
    int totalItemCount;// 总数量；
    int lastVisibleItem;// 最后一个可见的item；
    boolean isLoading;// 正在加载；
    ILoadListener iLoadListener;
//    private LinearLayout ll_pai_sousuo;

    public LoadListViewPaiMAI(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public LoadListViewPaiMAI(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public LoadListViewPaiMAI(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    /**
     * 添加底部加载提示布局到listview
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);

        footer = inflater.inflate(R.layout.footer_layout, null);
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
//       View view= inflater.inflate(R.layout.fragment_pai_mai_changci,null);
//        ll_pai_sousuo = ((LinearLayout) view.findViewById(R.id.ll_pai_sousuo));
//        this.addHeaderView(ll_pai_sousuo);

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
//        ll_pai_sousuo.setVisibility(VISIBLE);
        if (totalItemCount == lastVisibleItem
                && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                footer.findViewById(R.id.load_layout).setVisibility(
                        View.VISIBLE);
                // 加载更多
                iLoadListener.onLoad();
            }
        }
    }
    /**
     * 加载完毕
     */
    public void loadComplete(){
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(
                View.GONE);
    }

    public void setInterface(ILoadListener iLoadListener){
        this.iLoadListener = iLoadListener;
    }
    //加载更多数据的回调接口
    public interface ILoadListener{
        public void onLoad();
    }
}
