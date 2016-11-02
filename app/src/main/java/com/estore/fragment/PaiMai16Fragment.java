package com.estore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estore.activity.PaimaiMain_infoActivity;
import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.AuctListActivityBean;
import com.estore.view.LoadListViewPaiMAI;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;


public class PaiMai16Fragment extends Fragment implements LoadListViewPaiMAI.ILoadListener {
    PaiMai16Fragment.MyAuctAdapter auctAdapter;
    final ArrayList<AuctListActivityBean.Auct> auctList = new ArrayList<AuctListActivityBean.Auct>();
    private String[] imgurls;
    private LoadListViewPaiMAI lv_list_paimai;
    int page = 0;
    String searchFlag="0";//搜索条件标志位
    String bidTime = "16";//获取拍卖标志位
    TextView tv_paimai_hande_search1;
    TextView tv_paimai_hande_search2;
    TextView tv_paimai_hande_search3;
    TextView tv_paimai_hande_search4;
    TextView tv_paimai_hande_search5;
    private LinearLayout ll_pai_sousuo;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pai_mai_changci, null);
        lv_list_paimai = ((LoadListViewPaiMAI) view.findViewById(R.id.lv_list_paimai));
        lv_list_paimai.setInterface(this);
        initView(view);
        return view;

    }

    private void initView(View view) {

        tv_paimai_hande_search1 = ((TextView) view.findViewById(R.id.tv_paimai_hande_search1));
        tv_paimai_hande_search2 = ((TextView) view.findViewById(R.id.tv_paimai_hande_search2));
        tv_paimai_hande_search3 = ((TextView) view.findViewById(R.id.tv_paimai_hande_search3));
        tv_paimai_hande_search4 = ((TextView) view.findViewById(R.id.tv_paimai_hande_search4));
        tv_paimai_hande_search5 = ((TextView) view.findViewById(R.id.tv_paimai_hande_search5));
        tv_paimai_hande_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("获取全部");
                searchFlag = "0";
                getAuctList();
            }
        });
        tv_paimai_hande_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlag = "1";
                getAuctList();
            }
        });
        tv_paimai_hande_search3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlag = "2";
                getAuctList();
            }
        });
        tv_paimai_hande_search4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlag = "3";
                getAuctList();
            }
        });
        tv_paimai_hande_search5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlag = "4";
                getAuctList();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("进入碎片");
        getAuctList();
    }

    private static class ViewHodle {
        TextView tv_auct_name;
        TextView tv_username;
        ImageView iv_auct_imgurl;
        TextView tv_auct_minprice;//
        TextView tv_endbidprice;
        TextView tv_auct_begin;
    }

    @Override
    public void onLoad() {
        ll_pai_sousuo = ((LinearLayout) getActivity().findViewById(R.id.ll_pai_sousuo));
        ll_pai_sousuo.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取更多数据

                getLoadData();
                //adapter();
                auctAdapter = new PaiMai16Fragment.MyAuctAdapter();
                lv_list_paimai.setAdapter(auctAdapter);

//                //更新listview显示；
//                showListView(apk_list);
//                //通知listview加载完毕
                lv_list_paimai.loadComplete();
            }
        }, 2000);
    }

    public void getLoadData() {

        getAuctList();
    }

    private void getAuctList() {
        final RequestParams params = new RequestParams(HttpUrlUtils.HTTP_URL + "getPaiMaiProducts");
        params.addBodyParameter("page", page + 1 + "");
        params.addBodyParameter("bidTime", bidTime + "");
        params.addBodyParameter("searchFlag", searchFlag + "");
        System.out.println("进入getAuctList" + params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("========result========" + result + "-------------------------------------");
                Gson gson = new Gson();
                AuctListActivityBean bean = gson.fromJson(result, AuctListActivityBean.class);
                auctList.clear();
                auctList.addAll(bean.list);

                if (auctAdapter == null) {
                    System.out.println("----=-=-==-adapter==null==-=-==");
                    auctAdapter = new PaiMai16Fragment.MyAuctAdapter();
                    //  adapter();
                } else {
                    System.out.println("----=-=-==-adapter!null==-=-==");
                    auctAdapter.notifyDataSetChanged();
                }
                lv_list_paimai.setAdapter(auctAdapter);
                System.out.println("----=-=-==-acutlist==-=-==" + auctList + "----=-=-==-acutlist==-=-==");
//获得listview的点击事件

                lv_list_paimai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //                        items = new ArrayList<>();
//                        adapter = (BaseAdapter) parent.getAdapter();
//                        for (int i = 0; i < adapter.getCount(); i++) {
//
//                            String data = (adapter.getItem(i))
//                            items.add(data);
//                        }
                        AuctListActivityBean.Auct auct = auctList.get(position);
                        System.out.println(auct + "---------------auct----------------------------");

                        Intent intent = new Intent();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("auct", auct);
                        intent.putExtras(bundle);


                        intent.setClass(getActivity(), PaimaiMain_infoActivity.class);
                        startActivity(intent);

                    }
                });

                //通知listview更新界面
                auctAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.getMessage() + "--------------er-----------------------");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public class MyAuctAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return auctList.size();
        }


        @Override
        public Object getItem(int position) {
            return auctList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PaiMai16Fragment.ViewHodle viewHodle = null;
            if (viewHodle == null) {
                viewHodle = new PaiMai16Fragment.ViewHodle();
                convertView = View.inflate(getActivity(), R.layout.paimai_list_item, null);
                viewHodle.tv_auct_name = ((TextView) convertView.findViewById(R.id.tv_auct_name));
//                    viewHodle.tv_username = ((TextView) convertView.findViewById(R.id.tv_username));
                viewHodle.iv_auct_imgurl = ((ImageView) convertView.findViewById(R.id.iv_auct_imgurl));
                viewHodle.tv_auct_minprice = ((TextView) convertView.findViewById(R.id.tv_auct_minprice));
                viewHodle.tv_auct_begin = ((TextView) convertView.findViewById(R.id.tv_auct_begin));
                viewHodle.tv_endbidprice = ((TextView) convertView.findViewById(R.id.tv_endbidprice));

                convertView.setTag(viewHodle);//缓存对象
            } else {
                viewHodle = (PaiMai16Fragment.ViewHodle) convertView.getTag();
            }

            AuctListActivityBean.Auct auct = auctList.get(position);

            //获得数据
            viewHodle.tv_auct_begin.setText(auct.auct_begin);
            viewHodle.tv_auct_name.setText(auct.auct_name);
//                viewHodle.tv_username.setText(auct.user_id);
            viewHodle.tv_endbidprice.setText("￥" + auct.auct_minprice + "");
            imgurls = auct.auct_imgurl.split("=");//将拿到的图片路径分割成字符串数组
            x.image().bind(viewHodle.iv_auct_imgurl, HttpUrlUtils.HTTP_URL + imgurls[0]);
//            x.image().bind(viewHodle.iv_auct_imgurl, HttpUrlUtils.HTTP_URL + auct.auct_imgurl);
            System.out.println("http://10.40.5.6:8080/EStore/" + auct.auct_imgurl);
            System.out.println("changdu" + auctList.size());


//                iv_auct_imgurl.setImageResource();
//                tv_endbidprice.setText(auct.endBidPrice+"");
//                auct_begin.setText(auct.auct_begin+"");
            return convertView;

        }
    }

    ;


}