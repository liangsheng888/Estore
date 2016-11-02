package com.estore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.estore.R;


public class LoadListView extends ListView implements OnScrollListener {
    View footer;// 底部布局；
//
    int totalItemCount;// 总数量；
    int lastVisibleItem;// 最后一个可见的item；
    boolean isLoading;// 正在加载；
    ILoadListener iLoadListener;
    private Integer mpostiiton;
    private int mLastY;
    private int mTopPosition;
    private int mBottomPosition;
    public LoadListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyle) {
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
       // footer.findViewById(R.id.load_nothing).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);

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
        mpostiiton = view.getFirstVisiblePosition();

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
        this.setSelection(mpostiiton);
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

  /*  @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        final int y = (int) ev.getRawY();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                final boolean isHandled = mOnScrollOverListener.onMotionDown(ev);
                if (isHandled) {
                    mLastY = y;
                    return isHandled;
                }
                break;


            case MotionEvent.ACTION_MOVE:
                final int childCount = getChildCount();
                if(childCount == 0) return super.onTouchEvent(ev);

                final int itemCount = getAdapter().getCount() - mBottomPosition;

                final int deltaY = y - mLastY;
                //DLog.d("lastY=%d y=%d", mLastY, y);

                final int firstTop = getChildAt(0).getTop();
                final int listPadding = getListPaddingTop();

                final int lastBottom = getChildAt(childCount - 1).getBottom();
                final int end = getHeight() - getPaddingBottom();

                final int firstVisiblePosition = getFirstVisiblePosition();

                final boolean isHandleMotionMove = mOnScrollOverListener.onMotionMove(ev, deltaY);

                if(isHandleMotionMove){
                    mLastY = y;
                    return true;
                }

                //DLog.d("firstVisiblePosition=%d firstTop=%d listPaddingTop=%d deltaY=%d", firstVisiblePosition, firstTop, listPadding, deltaY);
                if (firstVisiblePosition <= mTopPosition && firstTop >= listPadding && deltaY > 0) {
                    final boolean isHandleOnListViewTopAndPullDown;
                    isHandleOnListViewTopAndPullDown = mOnScrollOverListener.onListViewTopAndPullDown(deltaY);
                    if(isHandleOnListViewTopAndPullDown){
                        mLastY = y;
                        return true;
                    }
                }

                // DLog.d("lastBottom=%d end=%d deltaY=%d", lastBottom, end, deltaY);
                if (firstVisiblePosition + childCount >= itemCount && lastBottom <= end && deltaY < 0) {
                    final boolean isHandleOnListViewBottomAndPullDown;
                    isHandleOnListViewBottomAndPullDown = mOnScrollOverListener.onListViewBottomAndPullUp(deltaY);
                    if(isHandleOnListViewBottomAndPullDown){
                        mLastY = y;
                        return true;
                    }
                }
                break;


            case MotionEvent.ACTION_UP:
                final boolean isHandlerMotionUp = mOnScrollOverListener.onMotionUp(ev);
                if (isHandlerMotionUp) {
                    mLastY = y;
                    return true;
                }
                break;
            }


        mLastY = y;
        return super.onTouchEvent(ev);
    }*/


    }

