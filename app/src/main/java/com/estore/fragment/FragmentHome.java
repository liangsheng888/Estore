package com.estore.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.GetDataTask;
import com.estore.activity.MainActivity;
import com.estore.activity.PaimaiMainActivity;
import com.estore.activity.ProductInfoActivity;
import com.estore.activity.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


/**
 * Created by Administrator on 2016/9/19.
 */
public class FragmentHome extends Fragment implements View.OnClickListener {
    private static final int HOME =2 ;
    private static final int SAME_CITY = 3;
    private static final int HIGH_SCHOOL =4 ;
    private LinkedList<Product.Products> list=new LinkedList<>();
    //private ListView lv_fr_home;
    PullToRefreshGridView prg;
    //vate GridViewWithHeaderAndFooter gridView;
    GridView gridView;
    private MyAdapter adapter;
    private AutoScrollViewPager autoScrollViewPager;
    List<ImageView> images = null;
    private Button school;
    private Button city;
    private Button auction;
    private String[] imgurls;
    private RollPagerView mRollViewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fra_home, null);
        PictureRoll(view);

        //lv_fr_home = (ListView) view.findViewById(R.id.lv_fr_home);
        //gridView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_home);
        prg = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
        school = (Button) view.findViewById(R.id.btn_school_GridView_home);

        city = (Button) view.findViewById(R.id.btn_city_GridView_home);
        auction = (Button) view.findViewById(R.id.btn_auction_GridView_home);

        //autoScrollViewPager = (AutoScrollViewPager) view.findViewById(R.id.autoScrollViewPager);//找到AutoScrollViewPager
       // initViewPager();//

        //gv_fr_home = (GridView) view.findViewById(R.id.lv_fr_home);

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //刷新
        if(!hidden){
            getData();


    }}

    private void getData() {
        String url = HttpUrlUtils.HTTP_URL+"getAllProducts?page=1";


        final RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Product pro = gson.fromJson(result, Product.class);
                Log.e("MainActivity", "pro------"+pro.toString());
                list.clear();
                list.addAll(pro.list);
                Log.e("MainActivity", "list------"+list.toString());
                if(adapter==null){
                    adapter = new MyAdapter();
                }else {
                    adapter.notifyDataSetChanged();
                }
                gridView.setAdapter(adapter);


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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        gridView = prg.getRefreshableView();
        gridView.setNumColumns(3);
       // initPicture();
        Log.e("home", "onActivityCreated");
        getData();//网络拿数据
        //为GridView设置点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long i = parent.getItemIdAtPosition(position);
                Product.Products pp = list.get(position);
                Toast.makeText(getActivity(), id + "", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ProductInfoActivity.class);
                // intent.putExtra("pp",pp);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pp", pp);
                intent.putExtras(bundle);
                Log.e("FragmentHome", pp.toString());
                startActivityForResult(intent,HOME);
            }
        });

        prg.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh"); // Do work to
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);


                new GetDataTask(prg, adapter, list).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh");
                new GetDataTask(prg, adapter, list).execute();
            }
        });
        new GetDataTask(prg, adapter, list).execute();
        /*LinearLayout ll_head = new LinearLayout(getActivity());
        //设置布局长度充满屏幕
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        View view = View.inflate(getActivity(), R.layout.header_listview_home, null);
        Button school = (Button) view.findViewById(R.id.btn_school_GridView_home);

        Button city = (Button) view.findViewById(R.id.btn_city_GridView_home);
        Button auction = (Button) view.findViewById(R.id.btn_auction_GridView_home);*/
        school.setOnClickListener(this);
        city.setOnClickListener(this);
        auction.setOnClickListener(this);
        // autoScrollViewPager = (AutoScrollViewPager) view.findViewById(R.id.autoScrollViewPager);//找到AutoScrollViewPager


        // ll_head.addView(view, layoutParams);
        //为GrildView 添加头
        // gridView.addHeaderView(ll_head);
        // lv_fr_home.addHeaderView(ll_head);


        super.onActivityCreated(savedInstanceState);
    }

    private void initViewPager() {

        autoScrollViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView((View) images.get(position % images.size()));
                return images.get(position % images.size());
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }


        });
       /* //设置滚动时延时时间

        autoScrollViewPager.setInterval(2000);
        //AutoScrollViewPager.RIGHT/AutoScrollViewPager.LEFT;
        //设置轮播的方向
        autoScrollViewPager.setDirection(AutoScrollViewPager.RIGHT);
        //设置是否自动循环轮播，默认为true
//注意：一旦设为true，则不能和ViewPagerIndicator一起使用
        autoScrollViewPager.setCycle(true);
//设置切换动画的时长
        autoScrollViewPager.setScrollDurationFactor(10);
//设置当滑动到最后一个或者第一个时，如何切换下一张
        //设置当滑动到最后一个或者第一个时，如何切换下一张
*//**
 * SLIDE_BORDER_MODE_NONE：不能再滑动
 * SLIDE_BORDER_MODE_TO_PARENT：移动父视图的Pager
 * SLIDE_BORDER_MODE_CYCLE：循环
 * 默认为SLIDE_BORDER_MODE_NONE
 *//*
        autoScrollViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);

        //当触摸的时候，停止轮播
        autoScrollViewPager.setStopScrollWhenTouch(true);*/
    }

    // 加载图片
    private void initPicture() {

        /*images = new ArrayList();
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.a);
        images.add(imageView);
        imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.b);
        images.add(imageView);
        imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.c);
        images.add(imageView);
        imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.d);
        images.add(imageView);*/

    }


    @Override
    public void onDestroyView() {
        Log.e("home", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //开启自动轮播，延时时间为getInterval()
        //autoScrollViewPager.startAutoScroll();
        //开启自动轮播，并设置开始滚动的延时
        //auto_view_pager.startAutoScroll(4000);
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止轮播
        //autoScrollViewPager.stopAutoScroll();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_auction_GridView_home:
                //跳转到拍卖
                Toast.makeText(getActivity(), "PaimaiMainActivity", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),PaimaiMainActivity.class));
                break;
            case R.id.btn_city_GridView_home:
                //跳转到同城
                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("direct",SAME_CITY);

                startActivity(intent);

                break;
            case R.id.btn_school_GridView_home:
                //跳转到高校
                Intent intent2= new Intent(getActivity(), MainActivity.class);
                intent2.putExtra("direct",HIGH_SCHOOL);

                startActivity(intent2);


                break;
        }

    }

    public static class ViewHolder {
        TextView tv_name;
        TextView tv_estoreprice;
        ImageView iv;
    }


    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //View view = null;
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.list_item, null);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

                viewHolder.tv_estoreprice = (TextView) convertView.findViewById(R.id.tv_estoreprice);


                viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_userPhoto);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                //view = convertView;
            }
            Product.Products pp = list.get(position);//根据当前位置获得pp
            ImageOptions.Builder io = new ImageOptions.Builder();
            imgurls=pp.imgurl.split("=");//将拿到的图片路径分割成字符串数组
            x.image().bind(viewHolder.iv, HttpUrlUtils.HTTP_URL + imgurls[0]);
            // iv.setImageUrl(HttpUrlUtils.HTTP_URL+ pp.imgurl.trim(), R.drawable.sj, R.drawable.sj);
            Log.e("MainActivity", HttpUrlUtils.HTTP_URL +imgurls[0]);
            viewHolder.tv_name.setText(pp.name);
            viewHolder.tv_estoreprice.setText(pp.estoreprice + "");

            convertView.setBackgroundColor(Color.WHITE);

            return convertView;
        }
    }
    public void PictureRoll(View view){
        mRollViewPager = (RollPagerView)view.findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW,Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);

        // Toolbar

    }
    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.guanggao,
                R.drawable.guanggao2,
                R.drawable.guanggao3,
                R.drawable.guanggao1,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }

}
