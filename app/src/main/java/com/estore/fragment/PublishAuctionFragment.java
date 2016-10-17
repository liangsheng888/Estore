package com.estore.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.activity.PublishAuctionDetialItemActivity;
import com.estore.activity.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.ListMyAuctionActivityBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class PublishAuctionFragment extends Fragment {
    private static final String TAG = "fragmentlife";
    private ListView lv_auctionlv;
    private TextView tv_btauction;
    private BaseAdapter adapter;
    final List<ListMyAuctionActivityBean.ProImag> pubList=new ArrayList<ListMyAuctionActivityBean.ProImag>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_publish_auction,null);
        lv_auctionlv = ((ListView) view.findViewById(R.id.lv_auctionlv));
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
        return view;

    }

    //适配器
    public  class mypubAdapter extends  BaseAdapter{
        private TextView tv_pubauctionname;
        private ImageView iv_pubauctionpic;
        private TextView tv_pubauctionprice;
        private TextView tv_pubauctiontime;
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
            View view=View.inflate(getActivity(),R.layout.items_publishauction,null);
            iv_pubauctionpic = ((ImageView) view.findViewById(R.id.iv_pubauctionpic));
            tv_pubauctionname = ((TextView) view.findViewById(R.id.tv_pubauctionname));
            tv_pubauctionprice = ((TextView) view.findViewById(R.id.tv_pubauctionprice));
            tv_pubauctiontime = ((TextView) view.findViewById(R.id.tv_pubauctiontime));
            ListMyAuctionActivityBean.ProImag pubimag=pubList.get(position);
            x.image().bind(iv_pubauctionpic, HttpUrlUtils.HTTP_URL+pubimag.auct_imgurl);
            tv_pubauctionname.setText(pubimag.auct_name);
            tv_pubauctionprice.setText(pubimag.auct_minprice+"");
            tv_pubauctiontime.setText(pubimag.auct_begin);
            return view;
        }
    }

    private void getPublishAuction() {
        //请求url获得数据
        // RequestParams rq=new RequestParams("http://10.40.5.18:8080/EStore/myPaiMaiServlet?email=2238265450@qq.com");
        String url =HttpUrlUtils.HTTP_URL+"/myPaiMaiServlet?email=978188219@qq.com";//访问网络的url
        Log.i("getPublishAuction",url);
        RequestParams requestParams = new RequestParams(url);//请求参数url
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("PublishhAuctionFragment",result);
                Gson gson=new Gson();
                ListMyAuctionActivityBean prolist=gson.fromJson(result, ListMyAuctionActivityBean.class);
                pubList.addAll(prolist.list);
                if(adapter==null){
                    adapter=new mypubAdapter();
                }else{
                    adapter.notifyDataSetChanged();
                }
                lv_auctionlv.setAdapter(adapter);
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
