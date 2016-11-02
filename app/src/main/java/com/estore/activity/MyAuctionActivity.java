package com.estore.activity;
/*
我的拍卖--我拍到商品
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.ListMyAuctionActivityBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyAuctionActivity extends AppCompatActivity {
    private static final String TAG ="SellActivity";
    private ImageView iv_auctionreturn;
    final List<ListMyAuctionActivityBean.ProImag> auctionProList=new ArrayList<ListMyAuctionActivityBean.ProImag>();
    private BaseAdapter  adapter;
    private ListView lv_acutionitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auction);
        //得到listview
        lv_acutionitem = ((ListView) findViewById(R.id.lv_acutionitem));
        //返回我的拍卖
        iv_auctionreturn = ((ImageView) findViewById(R.id.iv_auctionreturn));
        iv_auctionreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(MyAuctionActivity.this,MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });
        //跳到listview详细页面
        lv_acutionitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListMyAuctionActivityBean.ProImag auctiondetial = auctionProList.get(position);
                Log.e(TAG, auctiondetial+"");
                Intent intent = new Intent(MyAuctionActivity.this,AuctionDetialItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("auctiondetial", auctiondetial);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //从服务器上拿数据---M，给listview设置适配器
        adapter =new BaseAdapter() {
            private ImageView iv_auctionpic;
            private TextView tv_auctionprice;
            private TextView tv_auctiontime;
            private TextView tv_auctionrepresent;

            @Override
            public int getCount() {
                return auctionProList.size();
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
                //得到listview
                View view = View.inflate(MyAuctionActivity.this, R.layout.items_auction, null);
                //得到listview的item
                iv_auctionpic = ((ImageView) view.findViewById(R.id.iv_auctionpic));
                tv_auctionprice = ((TextView) view.findViewById(R.id.tv_auctionprice));
                tv_auctiontime = ((TextView) view.findViewById(R.id.tv_auctiontime));
                tv_auctionrepresent = ((TextView) view.findViewById(R.id.tv_auctionrepresent));
                //得到位置设置内容
                ListMyAuctionActivityBean.ProImag pro=auctionProList.get(position);
                x.image().bind(iv_auctionpic, HttpUrlUtils.HTTP_URL +pro.auct_imgurl);
                Log.e("TAG",HttpUrlUtils.HTTP_URL +pro.auct_imgurl);
                tv_auctionrepresent.setText(pro.auct_description);
                tv_auctionprice.setText(pro.auct_minprice+"");
                tv_auctiontime.setText(pro.auct_end+"");
                return view;
            }

        };
        //放入listview
        lv_acutionitem.setAdapter(adapter);
        //获取网络数据，显示在listview上
        getAuctPrionoImage();
    }

    private void getAuctPrionoImage() {
        //界面初始化数据：listview显示数据
        //xutils获取网络数据
       // RequestParams params=new RequestParams("http://10.40.5.18:8080/EStore/myPaiMaiServlet?email=2238265450@qq.com");
        //xutils获取网络数据
        String url = HttpUrlUtils.HTTP_URL + "myPaiMaiServlet?email=978188219@qq.com";//访问网络的url
        RequestParams requestParams = new RequestParams(url);//请求参数url
        x.http().get(requestParams,new Callback.CommonCallback<String>(){

            @Override
            public void onSuccess(String result) {
                Log.i("MyAuctionActivity",result);
                Gson gson=new Gson();
                //将从服务器得到的JSONObject变成javabean-ListMyAuctionActivityBean
                ListMyAuctionActivityBean auction=gson.fromJson(result,ListMyAuctionActivityBean.class);
                //更新数据
                Log.e("MyAuctionActivity",auction.list.toString());
                auctionProList.addAll(auction.list);
                Log.e("MyAuctionActivity",auctionProList.toString());
                //通过adapter通知服务器更新数据
                adapter.notifyDataSetChanged();
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
        });
    }
    }


