package com.estore.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.ModifyMyAddProductActivity;
import com.estore.activity.PublishEstoreDetialItemActivity;
import com.estore.R;
import com.estore.activity.myappliction.MyApplication;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.MyPublishActivityBean;
import com.estore.pojo.User;
import com.estore.view.LoadListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import swipetodismiss.SwipeMenu;
import swipetodismiss.SwipeMenuCreator;
import swipetodismiss.SwipeMenuItem;
import swipetodismiss.SwipeMenuListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishEStoreFragment extends Fragment implements LoadListView.ILoadListener {
    //fragment生命周期
    MyPublishActivityBean.ProImag  pro;
    private TextView tv_btestore;
    private LoadListView lv_publishest;
    final LinkedList<MyPublishActivityBean.ProImag> prolist=new LinkedList<MyPublishActivityBean.ProImag>();
    private BaseAdapter  adapter=new myAdapter() ;
    User user=new User();
    Integer page=1;
    private SharedPreferences sp;
    private LinearLayout ll_jiazai_fabu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getActivity().getSharedPreferences("User",getActivity().MODE_APPEND);
        user.setUserId(sp.getInt("userId",0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_publish_estore,null);
        ll_jiazai_fabu = ((LinearLayout) view.findViewById(R.id.ll_jiazai_fabu));
        lv_publishest= ((LoadListView) view.findViewById(R.id.lv_publishest));
        lv_publishest.setInterface(this);
        lv_publishest.setAdapter(adapter);
        lv_publishest.setLayoutAnimation(getAnimationController());
        //跳到详细页
        lv_publishest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPublishActivityBean.ProImag estoreimage=prolist.get(position);
                Log.i("pub", "estoreimage "+estoreimage);
                Intent intent=new Intent(getActivity(), PublishEstoreDetialItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("estoreimage",estoreimage);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private LayoutAnimationController getAnimationController() {
        int duration=300;
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }

    //显示
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getProduct();
        //adapter=new myAdapter();



    }
//上拉加载
    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getProduct();
                lv_publishest.loadComplete();
            }
        }, 1000);
    }

    

    //适配器
    public class myAdapter extends BaseAdapter{
        private ImageView iv_pubestorepic;
        private TextView tv_pubestoreprice;
        private TextView tv_pubestorewhere;
        private TextView tv_pubestorerepresent;
        private TextView  iv_erdetial_propic;
        private TextView  tv_youfei ;
        private TextView tv_pubestorewhere1;

        @Override
        public int getCount() {
            return prolist.size();
        }

        @Override
        public Object getItem(int position) {
            return prolist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("PublishEStoreFragment","getView");
            //得到listview
            View view = View.inflate(getActivity(), R.layout.items_publishestore, null);
            //得到listview的item
            iv_pubestorepic = ((ImageView) view.findViewById(R.id.iv_pubestorepic));//图片
            tv_pubestoreprice = ((TextView) view.findViewById(R.id.tv_pubestoreprice));//价格
            tv_pubestorewhere = ((TextView) view.findViewById(R.id.tv_pubestorewhere));//同城
            tv_pubestorerepresent = ((TextView) view.findViewById(R.id.tv_pubestorerepresent));//描述
            tv_pubestorewhere1 = ((TextView) view.findViewById(R.id.tv_pubestorewhere1));//高校




            MyPublishActivityBean.ProImag  pro=prolist.get(position);
            Log.e("PublishEStoreFragment","getView:"+pro.toString());
            String[] imgurl=pro.imgurl.split("=");
            x.image().bind(iv_pubestorepic,HttpUrlUtils.HTTP_URL +imgurl[0]);
            Log.i("TAG",HttpUrlUtils.HTTP_URL +pro.imgurl);
            tv_pubestoreprice.setText(pro.estoreprice+"");
            tv_pubestorerepresent.setText(pro.description+"");
            //判断同城高校
            // Toast.makeText(getActivity(),pro.heighschool+"",Toast.LENGTH_LONG).show();
            if (pro.prowhere==0) {//高校
                tv_pubestorewhere.setText(pro.schoolname);
            }
            if(pro.prowhere==1) {//同城高校
                tv_pubestorewhere.setText(pro.proaddress);
                tv_pubestorewhere1.setText(pro.schoolname);

            }
            return view;

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        getProduct();
    }
    //重新开始
    @Override
    public void onResume() {
        super.onResume();
    }
    //暂时停止
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        getProduct();

        super.onHiddenChanged(hidden);
    }
    //请求数据
    private void getProduct() {
        //RequestParams requestParams=new RequestParams("http://10.40.5.18:8080/EStore/getmypublishservlet?email=491830643@qq.com");
        String url = HttpUrlUtils.HTTP_URL + "myAddProductsServlet?user_id="+user.getUserId();//访问网络的url
        Log.i("cc",user.getUserId()+"");
        Log.i("cc",url);
        RequestParams requestParams = new RequestParams(url);//请求参数url
        requestParams.addQueryStringParameter("page",page+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ll_jiazai_fabu.setVisibility(View.GONE);
                page++;
                Log.e("PublishEStoreFragment","result"+result);
                Gson gson=new Gson();
                MyPublishActivityBean  probean=gson.fromJson(result,MyPublishActivityBean.class);
                Log.i("cc","probean"+probean+"");
                if(probean.list.size()<=0){
                    Toast.makeText(getActivity(),"亲！没有更多数据了",Toast.LENGTH_LONG).show();
                    return;
                }
                prolist.clear();
                prolist.addAll(probean.list);
                Log.e("PublishEStoreFragment",prolist.toString());
                if(adapter==null){
                    Log.e("PublishEStoreFragment","adapter=null");
                    adapter=new myAdapter();
                    lv_publishest.setAdapter(adapter);
                }else{
                    Log.e("PublishEStoreFragment","adapter!=null");
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("PublishEStoreFragment","fail"+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }



}
