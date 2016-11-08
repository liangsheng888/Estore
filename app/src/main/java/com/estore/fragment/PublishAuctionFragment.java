package com.estore.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.PublishAuctionDetialItemActivity;
import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.ListMyAuctionActivityBean;
import com.estore.pojo.User;
import com.estore.pojo.UserBean;
import com.estore.view.LoadListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class PublishAuctionFragment extends Fragment implements LoadListView.ILoadListener  {
    private static final String TAG = "fragmentlife";
    private LoadListView lv_auctionlv;
    private TextView tv_btauction;
    private BaseAdapter adapter=new mypubAdapter()
            ;
    User user=new User();
    Integer page=1;
    private LoadListView estore;
    private SharedPreferences sp;
    final LinkedList<ListMyAuctionActivityBean.ProImag> pubList=new LinkedList<ListMyAuctionActivityBean.ProImag>();
    private LinearLayout ll_jiazai_auct;

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
        View view=inflater.inflate(R.layout.fragment_publish_auction,null);
        lv_auctionlv = ((LoadListView) view.findViewById(R.id.lv_auctionlv));
        ll_jiazai_auct = ((LinearLayout) view.findViewById(R.id.ll_jiazai_auct));

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

    //上拉加载
    @Override
    public void onLoad() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getPublishAuction();
                lv_auctionlv.loadComplete();
            }
        }, 1000);
    }

    //适配器
    public  class mypubAdapter extends  BaseAdapter{
        private TextView tv_pubauctionname;
        private ImageView iv_pubauctionpic;
        private TextView tv_pubauctionprice;
        private TextView tv_pubauctiontime;
        private Button btnDelete;

        @Override
        public int getCount() {
            return pubList.size();
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
            final int index=position;
            View view=View.inflate(getActivity(),R.layout.items_publishauction,null);
            iv_pubauctionpic = ((ImageView) view.findViewById(R.id.iv_pubauctionpic));
            tv_pubauctionname = ((TextView) view.findViewById(R.id.tv_pubauctionname));
            tv_pubauctionprice = ((TextView) view.findViewById(R.id.tv_pubauctionprice));
            tv_pubauctiontime = ((TextView) view.findViewById(R.id.tv_pubauctiontime));
//            btnDelete = ((Button) view.findViewById(R.id.btn_pubauctiondelete));
            ListMyAuctionActivityBean.ProImag pubimag=pubList.get(position);
            x.image().bind(iv_pubauctionpic, HttpUrlUtils.HTTP_URL+pubimag.auct_imgurl);
            tv_pubauctionname.setText(pubimag.auct_name);
            tv_pubauctionprice.setText(pubimag.auct_minprice+"");
            tv_pubauctiontime.setText(pubimag.auct_begin+"");

//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
            return view;
        }
    }

    private void getPublishAuction() {
        //请求url获得数据
        // RequestParams rq=new RequestParams("http://10.40.5.18:8080/EStore/myPaiMaiServlet?email=2238265450@qq.com");
        String url =HttpUrlUtils.HTTP_URL+"/myPaiMaiServlet?user_id="+user.getUserId();//访问网络的url
        Log.i("getPublishAuction",url);
        RequestParams requestParams = new RequestParams(url);//请求参数url
        requestParams.addQueryStringParameter("page",page+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("PublishhAuctionFragment",result);
                ll_jiazai_auct.setVisibility(View.GONE);
                page++;
                Gson gson=new Gson();
                ListMyAuctionActivityBean prolist=gson.fromJson(result, ListMyAuctionActivityBean.class);
                if(prolist.list.size()<=0){
                    Toast.makeText(getActivity(),"亲！没有更多数据了",Toast.LENGTH_LONG).show();
                    return;
                }
                pubList.clear();
                pubList.addAll(prolist.list);
                if(adapter==null){
                    adapter=new mypubAdapter();
                    lv_auctionlv.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("PublishhAuctionFragment","onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv_auctionlv.setAdapter(adapter);
        lv_auctionlv.setInterface(this);
        lv_auctionlv.setLayoutAnimation(getAnimationController());

        //跳到详细页
        lv_auctionlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListMyAuctionActivityBean.ProImag pubimag=pubList.get(position);
                Intent intent=new Intent(getActivity(), PublishAuctionDetialItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("pubimag",pubimag);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getPublishAuction();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

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
        super.onHiddenChanged(hidden);

    }




}
