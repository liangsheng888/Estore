package com.estore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.AuctListActivityBean;
import com.estore.view.LoadListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

public class PaimaiMainActivity extends AppCompatActivity implements View.OnClickListener,LoadListView.ILoadListener {

   LoadListView lv_list_paimai;
    private ArrayList items;
    BaseAdapter adapter = null;
     int page=0;
    // 一个listview对应的list是不可以变化的（引用）
    final ArrayList<AuctListActivityBean.Auct> auctList = new ArrayList<AuctListActivityBean.Auct>();
    private Button btn_paimai_bidding;
    private TextView tv_type1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_paimai_main);
        initView();
        initEven();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);//设置导航栏图标
        toolbar.setLogo(R.drawable.emoji_81);//设置app logo
        toolbar.setTitle("拍卖");//设置主标题
        toolbar.setSubtitle("正在拍卖物品");//设置子标题

        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_item1) {
                    Toast.makeText(PaimaiMainActivity.this, "R.string.item_01", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(PaimaiMainActivity.this, "R.string.item_02", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item3) {
                    Toast.makeText(PaimaiMainActivity.this, "R.string.item_03", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item4) {
                    Toast.makeText(PaimaiMainActivity.this, "R.string.item_04", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
//        //设置头图片
        lv_list_paimai = ((LoadListView) findViewById(R.id.lv_list_paimai));
        lv_list_paimai.setInterface(this);

        adapter();
        lv_list_paimai.setAdapter(adapter);

        //从服务器拿
        getAuctList();


    }

public  void  adapter(){
    adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return auctList.size();
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
            ViewHodle viewHodle = null;
            if (viewHodle == null) {
                viewHodle = new ViewHodle();
                convertView = View.inflate(PaimaiMainActivity.this, R.layout.paimai_list_item, null);
                viewHodle.tv_auct_name = ((TextView) convertView.findViewById(R.id.tv_auct_name));
//                    viewHodle.tv_username = ((TextView) convertView.findViewById(R.id.tv_username));
                viewHodle.iv_auct_imgurl = ((ImageView) convertView.findViewById(R.id.iv_auct_imgurl));
                viewHodle.tv_auct_minprice = ((TextView) convertView.findViewById(R.id.tv_auct_minprice));

                convertView.setTag(viewHodle);//缓存对象
            } else {
                viewHodle = (ViewHodle) convertView.getTag();
            }

            AuctListActivityBean.Auct auct = auctList.get(position);

            //获得数据
            viewHodle.tv_auct_name.setText(auct.auct_name);
//                viewHodle.tv_username.setText(auct.user_id);
            viewHodle.tv_auct_minprice.setText("￥" + auct.auct_minprice + "");
            x.image().bind(viewHodle.iv_auct_imgurl, HttpUrlUtils.HTTP_URL + auct.auct_imgurl);
            System.out.println("http://10.40.5.6:8080/EStore/" + auct.auct_imgurl);
//                iv_auct_imgurl.setImageResource();
//                tv_endbidprice.setText(auct.endBidPrice+"");
//                auct_begin.setText(auct.auct_begin+"");
            return convertView;

        }
    };


}
    private void initEven() {
        tv_type1.setOnClickListener(this);
    }

    private void initView() {
        tv_type1 = ((TextView) findViewById(R.id.tv_type1));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_type1:
                Intent intent=new Intent(getApplication(),PaiMaiAll.class);
                startActivity(intent);
                break;

        }
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
                adapter();
                lv_list_paimai.setAdapter(adapter);

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
    private static class ViewHodle {
        TextView tv_auct_name;
        TextView tv_username;
        ImageView iv_auct_imgurl;
        TextView tv_auct_minprice;//
    }

    private void getAuctList() {
        final RequestParams params = new RequestParams(HttpUrlUtils.HTTP_URL + "getPaiMaiProducts?page=1");
        params.addBodyParameter("page",page+1+"");
        System.out.println("进入getAuctList" + params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("========result========" + result + "-------------------------------------");
                Gson gson = new Gson();
                AuctListActivityBean bean = gson.fromJson(result, AuctListActivityBean.class);
                auctList.addAll(bean.list);
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


                        intent.setClass(getApplicationContext(), PaimaiMain_infoActivity.class);
                        startActivity(intent);

                    }
                });
                System.out.println("--------------adapter--------" + adapter);
                //通知listview更新界面
                adapter.notifyDataSetChanged();
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


}


