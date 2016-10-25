package com.estore.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.ProductAll;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaiMaiAll extends AppCompatActivity {
    BaseAdapter adapter;
    BaseAdapter lvadapter;
    final ArrayList<ProductAll> auctList = new ArrayList<>();
    List<ProductAll> newlist = new ArrayList<ProductAll>();
    String bidTime = "12";
    private ListView lv_paimai_all_1;
    private ListView lv_paimai_all_2;
    private ListView lv_paimai_all_3;
    private ListView lv_paimai_all_4;
    Map<String, List> listPaiMai = new HashMap<>();
    List<String> imgurlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pai_mai_all);
        toolbar();
        initView();
        initEven();
        initData();

    }

    private void toolbar() {
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
                    Toast.makeText(PaiMaiAll.this, "R.string.item_01", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(PaiMaiAll.this, "R.string.item_02", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item3) {
                    Toast.makeText(PaiMaiAll.this, "R.string.item_03", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item4) {
                    Toast.makeText(PaiMaiAll.this, "R.string.item_04", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
    }

    private void initView() {

        lv_paimai_all_1 = ((ListView) findViewById(R.id.lv_paimai_all_1));
        lv_paimai_all_2 = ((ListView) findViewById(R.id.lv_paimai_all_2));
        lv_paimai_all_3 = ((ListView) findViewById(R.id.lv_paimai_all_3));
        lv_paimai_all_4 = ((ListView) findViewById(R.id.lv_paimai_all_4));
    }

    private void initEven() {


    }

    private void initData() {
        listPaiMai = new HashMap<>();

//        listPaiMai.put(bidTime="12",auctList);
//        listPaiMai.put(bidTime="16",auctList);
//        listPaiMai.put(bidTime="20",auctList);
        new Thread(new Runnable() {
            @Override
            public void run() {
                listPaiMai.put(bidTime="08",auctList);
                bidTime = "08";
                getAdapter();
                lv_paimai_all_1.setAdapter(adapter);
                getPaiMaiAllData();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                bidTime = "12";
                getAdapter();
                lv_paimai_all_2.setAdapter(adapter);
                getPaiMaiAllData();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bidTime = "16";
                getAdapter();
                lv_paimai_all_3.setAdapter(adapter);
                getPaiMaiAllData();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bidTime = "20";
                getAdapter();
                lv_paimai_all_4.setAdapter(adapter);
                getPaiMaiAllData();
            }
        }).start();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//                bidTime = "08";
//                getAdapter();
//                bidTime = "16";
//                getAdapter();
//                bidTime = "20";
//                getAdapter();
//            }
//        }, 2000);
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                bidTime = "08";
//                getAdapter();
//            }
//        }, 100);
//
    }

    public void getAdapter() {
        System.out.println("进入listview的适配器+listPaiMai" + listPaiMai);
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listPaiMai.size();
            }

            @Override
            public Object getItem(int i) {
                return listPaiMai.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {


                ViewHodle viewHodle = null;
                if (viewHodle == null) {
                    viewHodle = new ViewHodle();
                    view = View.inflate(PaiMaiAll.this, R.layout.paimai_all_list_item, null);
                    viewHodle.gv_paimai_all = ((GridView) view.findViewById(R.id.gv_paimai_all));
                    viewHodle.tv_paimai_all_collet = ((TextView) view.findViewById(R.id.tv_paimai_all_collet));
                    view.setTag(view);
                } else {
                    view = (View) view.getTag();
                }
//                ProductAll product = auctList.get(i);
//                imgurlList.add(auctList.get(i).getImgurl());

                viewHodle.gv_paimai_all.setAdapter(new PhoteAdapter(imgurlList));

                return view;
            }

        };

    }


    private static class ViewHodle {
        GridView gv_paimai_all;
        TextView tv_paimai_all_collet;
    }

    public class PhoteAdapter extends BaseAdapter {

        private List<String> imgurlList;

        public PhoteAdapter(List<String> imgurlList) {
            this.imgurlList = imgurlList;
        }

        @Override
        public int getCount() {
            return imgurlList.size();
        }

        @Override
        public Object getItem(int position) {
            return imgurlList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(getApplicationContext());
//            ProductAll productAll=auctList.get(position);
//            imgurlList.add(productAll.imgurl);
//            System.out.println("imgurlListimgurlListimgurlListimgurlList"+imgurlList);
            x.image().bind(imageView, HttpUrlUtils.HTTP_URL + imgurlList.get(position));
            return imageView;
        }
    }

    public void getPaiMaiAllData() {
        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "getPaiMaiAllServler");
        requestParams.addBodyParameter("bidTime", bidTime);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("paiMaiALL拍卖首页数据" + result);
                Gson gson = new Gson();
                newlist = new ArrayList<ProductAll>();
                Type type = new TypeToken<List<ProductAll>>() {
                }.getType();
                newlist = gson.fromJson(result, type);
                auctList.clear();
                auctList.addAll(newlist);
                for (int i=0;i<auctList.size();i++){
                    imgurlList.add(auctList.get(i).imgurl);
                }
                listPaiMai.put(bidTime, auctList);
                System.out.println("PaiMaiAllnewlist-----------" + auctList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("paiMaiALL拍卖首页数据的错误" + ex.getMessage());
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
