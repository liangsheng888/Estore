package com.estore.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.MainActivity;
import com.estore.activity.MainComputerActivity;
import com.estore.activity.PaimaiMainActivity;
import com.estore.activity.PersonComputerActivity;
import com.estore.activity.PhoneActivity;
import com.estore.activity.ProductInfoActivity;
import com.estore.activity.R;
import com.estore.activity.WatchActivity;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.xUtilsImageUtils;
import com.estore.pojo.Product;
import com.estore.view.GridViewWithHeaderAndFooter;
import com.estore.view.LoadListView;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/19.
 */
public class FragmentHome extends Fragment implements LoadListView.ILoadListener, View.OnClickListener {
    private static final int HOME =2 ;
    private static final int SAME_CITY = 3;
    private static final int HIGH_SCHOOL =4 ;
    private LinkedList<Product.Products> list=new LinkedList<>();
    private LoadListView lv_jingpin;
   // PullToRefreshGridView prg;
    GridViewWithHeaderAndFooter gridViewWithHeaderAndFooter;
   // GridView gridView;
    private MyAdapter adapter;
  //  private AutoScrollViewPager autoScrollViewPager;
    List<ImageView> images = null;
    private int page=1;
    private Button school;
    private Button city;
    private Button auction;
    private String[] imgurls;
    private RollPagerView mRollViewPager;
    private RelativeLayout rl_header;
    private RelativeLayout computer;
    private RelativeLayout computerText;
    private LinearLayout ll_school;
    private LinearLayout ll_city;
    private LinearLayout ll_auct;

    Integer orderFlag;
    List<Product.Products> proList=new ArrayList<Product.Products>();
    private String url;
    private RelativeLayout phone;
    private RelativeLayout watch;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fra_home, null);
        lv_jingpin = (LoadListView) view.findViewById(R.id.lv_jingpin);
       // rl_header= (RelativeLayout) view.findViewById(R.id.rl_header);
        lv_jingpin.setInterface(this);

        //gridViewWithHeaderAndFooter = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gridViewWithHeaderAndFooter);
      //  prg = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);


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
        String url = HttpUrlUtils.HTTP_URL+"getAllProducts?page="+page;


        final RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                page++;
                Gson gson = new Gson();
                Product pro = gson.fromJson(result, Product.class);

                Log.e("MainActivity", "pro------"+pro.toString());

                if(pro.list.size()<=0){
                    View view=View.inflate(getActivity(),R.layout.footer_layout,null);
                    ((LinearLayout)view.findViewById(R.id.load_layout)).setVisibility(View.GONE);
                    ((LinearLayout)view.findViewById(R.id.load_nothing)).setVisibility(View.VISIBLE);


                }
                //list.clear();
                list.addAll(pro.list);
                Log.e("MainActivity", "list------"+list.toString());
                if(adapter==null){
                    Log.e("MainActivity", "adapter==null");
                    adapter = new MyAdapter();
                    lv_jingpin.setSelection(0);
                }else {

                    Log.e("MainActivity", "adapter!=null");
                    adapter.notifyDataSetChanged();
                    lv_jingpin.setSelection(list.size()-1);
                }
               // gridView.setAdapter(adapter);

                lv_jingpin.setAdapter(adapter);

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

        Log.e("home", "onActivityCreated");
        getData();//网络拿数据
        //为GridView设置点击事件
        Log.e("home", "为GridView设置点击事件");
        lv_jingpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //long i = parent.getItemIdAtPosition(position);
                Log.e("home", "点击事件");
                Product.Products pp = list.get(position-1);
                //Toast.makeText(getActivity(), id + "", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ProductInfoActivity.class);
                // intent.putExtra("pp",pp);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pp", pp);
                intent.putExtras(bundle);
                Log.e("FragmentHome", pp.toString());
                startActivityForResult(intent,HOME);
            }
        });

        View view=View.inflate(getActivity(),R.layout.fra_home_add_header,null);

        ll_school = (LinearLayout) view.findViewById(R.id.ll_school);

        ll_city = (LinearLayout) view.findViewById(R.id.ll_city);
        ll_auct = (LinearLayout) view.findViewById(R.id.ll_auct);
      //  auction = (Button) view.findViewById(R.id.btn_auction_GridView_home);
        computer = ((RelativeLayout) view.findViewById(R.id.rl_first_computer));
        computerText = ((RelativeLayout) view.findViewById(R.id.rl_first_computertext));
        phone = ((RelativeLayout) view.findViewById(R.id.rl_first_phone));
        watch = ((RelativeLayout) view.findViewById(R.id.rl_first_watch));
        computer.setOnClickListener(this);
        computerText.setOnClickListener(this);
        phone.setOnClickListener(this);
        watch.setOnClickListener(this);

        ll_school.setOnClickListener(this);
        ll_city.setOnClickListener(this);
        ll_auct.setOnClickListener(this);
        PictureRoll(view);

         // ll_head.addView(view, layoutParams);
        //为GrildView 添加头
        lv_jingpin.addHeaderView(view);
        // lv_fr_home.addHeaderView(ll_head);


        super.onActivityCreated(savedInstanceState);
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
            case R.id.ll_auct:
                //跳转到拍卖
                Toast.makeText(getActivity(), "PaimaiMainActivity", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),PaimaiMainActivity.class));
                break;
            case R.id.ll_city:
                //跳转到同城
                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("direct",SAME_CITY);

                startActivity(intent);

                break;
            case R.id.ll_school:
                //跳转到高校
                Intent intent2= new Intent(getActivity(), MainActivity.class);
                intent2.putExtra("direct",HIGH_SCHOOL);

                startActivity(intent2);


                break;
            case R.id.rl_first_computer://电脑
                orderFlag=1;
                getProInfoByCategory(orderFlag,MainComputerActivity.class);

                break;
            case R.id.rl_first_computertext://笔记本
                orderFlag=2;
                getProInfoByCategory(orderFlag, PersonComputerActivity.class);
                break;
            case R.id.rl_first_phone://手机
                orderFlag=3;
                getProInfoByCategory(orderFlag, PhoneActivity.class);
                break;
            case R.id.rl_first_watch://手表
                orderFlag=4;
                getProInfoByCategory(orderFlag, WatchActivity.class);

                break;
        }

    }

    public void getProInfoByCategory(Integer orderFlag, Class<?> activityClass) {
         Intent intent=new Intent(getActivity(),activityClass);
         intent.putExtra("orderFlag",orderFlag);
         startActivityForResult(intent,3);

    }

    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取更多数据

                getLoadData();
//                //更新listview显示；
//                showListView(apk_list);
//                //通知listview加载完毕
                lv_jingpin.loadComplete();
            }
        }, 2000);

    }
    //获取加载数据
    public void getLoadData() {
        getJinPinList();
    }

    private void getJinPinList() {
        getData();


    }
    public static class ViewHolder {
        TextView tv_name,tv_username;
        TextView tv_estoreprice,tv_jingpin_desc,tv_jingpin_address;
        ImageView iv;
        //ViewPager vp_jingpin;
        GridView gv_jingpin;
        public TextView tv_time;
        public TextView tv_xingnum;
        public ImageView iv_jing_userphoto;
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

                //viewHolder.vp_jingpin = (ViewPager) convertView.findViewById(R.id.vp_jingpin);
                viewHolder.gv_jingpin = (GridView) convertView.findViewById(R.id.gv_jingpin);
                viewHolder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id. tv_jin_proname);
                viewHolder. tv_jingpin_desc = (TextView) convertView.findViewById(R.id.tv_jingpin_desc);
                viewHolder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
                viewHolder.tv_xingnum= (TextView) convertView.findViewById(R.id.tv_xingnum);
                viewHolder.iv_jing_userphoto= (ImageView) convertView.findViewById(R.id.iv_jing_userphoto);
               // xUtilsImageUtils.display();
                
                
                viewHolder.tv_estoreprice = (TextView) convertView.findViewById(R.id.tv_jingpin_price);
                viewHolder.tv_jingpin_address = (TextView) convertView.findViewById(R.id.tv_jingpin_address);


               // viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_userPhoto);

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
            viewHolder.tv_estoreprice.setText("￥"+pp.estoreprice);
            viewHolder. tv_jingpin_desc.setText(pp.description);
            viewHolder.tv_jingpin_address.setText(pp.proaddress);
            viewHolder.tv_time.setText("发布时间"+pp.time);
            viewHolder.tv_xingnum.setText(pp.xingCount+"");
            viewHolder.tv_username.setText(pp.userNick);
            xUtilsImageUtils.display(viewHolder.iv_jing_userphoto,pp.userPhoto,true);
           // x.image().bind(viewHolder.iv_jing_userphoto,pp.userPhoto);

            //viewHolder.gv_jingpin.setBackground(new BitmapDrawable());//
           // viewHolder.gv_jingpin.setBackgroundColor(Color.WHITE);
            viewHolder.gv_jingpin.setClickable(false);
            viewHolder.gv_jingpin.setPressed(false);
            viewHolder.gv_jingpin.setEnabled(false);

           // viewHolder.gv_jingpin.setLayoutParams(new LinearLayout.LayoutParams(200,400));
            viewHolder.gv_jingpin.setAdapter(new Adapter(imgurls));
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

    public class Adapter extends BaseAdapter{
        private String[] imgurls;
        public Adapter(String[] imgurls){
            this.imgurls=imgurls;
        }


        @Override
        public int getCount() {
            return imgurls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
               if(convertView==null){
                 convertView=View.inflate(getActivity(),R.layout.layout_fra_pro_item,null);}
                ImageView iv=(ImageView) convertView.findViewById(R.id.iv_pro);
                x.image().bind(iv,HttpUrlUtils.HTTP_URL+imgurls[position]);
                convertView.setBackgroundColor(Color.WHITE);
            return convertView;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
}
