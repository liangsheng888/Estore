package com.estore.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.GetUserIdByNet;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.MapSerializable;
import com.estore.httputils.ShowLoginDialogUtils;
import com.estore.httputils.xUtilsImageUtils;
import com.estore.pojo.Cart;
import com.estore.pojo.Envalute;
import com.estore.pojo.Product;
import com.estore.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

/***
 * 商品详情页
 */
public class ProductInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ProductInfoActivity";
    private ImageView iv_project_detail_back;
    private ViewPager vp_show_photo;
    private TextView tv_product_detail;
    private TextView tv_project_detail_price;
    private TextView prod_info_tv_des;//商品名称
    private TextView prod_info_tv_pnum;//商品数量
    private TextView prod_info_tv_baoyou;
    private TextView tv_youfei;//邮费

    private Button btn_touch_seller;

    private TextView tv_product_detail_city;
    private TextView tv_product_detail_schools;
    private RelativeLayout title_bar_rl_cartview;
    private TextView edt;
    private TextView title_bar_reddot;//购物车图标显示数量
    private CheckBox cb_guanzhu;
    private Button btn_addcart;//加入购物车
    private Button btn_buy_now;//立即购买
    private Button addbt;
    private Button subbt;
    private Dialog dialog;
    private AlertDialog.Builder builder;

    Map<Product.Products, Integer> mapPro = new HashMap<>();//购买商品，及数量
    private List<Envalute> envaLists = new ArrayList<>();
    private User user = new User();
    private RemarkAdapter remarkAdapter;


    private int carNumber;//购物车商品数量
    private String[] photourl;
    private Product.Products pp;
    private int prePosition = 0;
    private int[] id = {R.id.iv_quan1, R.id.iv_quan2, R.id.iv_quan3};
    private SharedPreferences sp;
    private ListView lv_user_remark;//评价
    private TextView prod_info_tv_prod_comment;//更多
    private int page = 1;
    private TextView prod_info_tv_prod_record;
    private TextView prod_info_tv_detail;
     private TextView prod_info_tv_buynum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        sp = getSharedPreferences("User", Context.MODE_APPEND);
        initView();
        initData();
        getDataCartNumber();


    }

    private void initData() {

        pp = getProDetailInfo();//
        getEnvaluteInfoByNet(pp.user_id);

        if (pp.youfei > 0) {
            prod_info_tv_baoyou.setVisibility(View.GONE);
            tv_youfei.setText("邮费￥" + pp.youfei);
        }
        photourl = pp.imgurl.split("=");//
        Log.e(TAG, photourl[0]);
        tv_project_detail_price.setText("￥" + pp.estoreprice);
        prod_info_tv_des.setText(pp.name);
//        tv_product_detail.setText(pp.description);
        prod_info_tv_pnum.setText("库存:" + pp.pnum);
        tv_product_detail_city.setText(pp.proaddress);
        prod_info_tv_detail.setText(pp.description);
        if (pp.schoolname != null) {
            tv_product_detail_schools.setText(pp.schoolname);
        }
        //fanhui
        iv_project_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        vp_show_photo.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(ProductInfoActivity.this, R.layout.vp_item, null);
                ImageView iv_vp = ((ImageView) view.findViewById(R.id.iv_vp));
                x.image().bind(iv_vp, HttpUrlUtils.HTTP_URL + photourl[position]);
                container.addView(view);//容易忘记
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(((View) object));
            }

            @Override
            public int getCount() {
                return photourl.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        vp_show_photo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((ImageView) findViewById(id[position])).setImageResource(R.drawable.point_red);
                ((ImageView) findViewById(id[prePosition])).setImageResource(R.drawable.point_gray);
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //

        //加入购物车
        btn_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ProductInfoActivity", "加入购物车");
                String username = sp.getString("username", null);
                if (username == null) {
                    ShowLoginDialogUtils.showDialogLogin(ProductInfoActivity.this);
                    return;
                }
                GetUserIdByNet.getUserIdByNet(ProductInfoActivity.this);

                carNumber += Integer.parseInt(edt.getText().toString().trim());
                title_bar_reddot.setVisibility(View.VISIBLE);
                title_bar_reddot.setText(carNumber + "");
                //动画
                TranslateAnimation ta = (TranslateAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                title_bar_rl_cartview.startAnimation(ta);


                //添加都服务器
                RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "insertCartServlet");
                Log.e("ProductInfoActivity", pp.toString());
                // Cart cart=new Cart(pp,user.getUserId(),Integer.parseInt(edt.getText().toString().trim()),new Timestamp(System.currentTimeMillis()));
                // Gson gson=new Gson();
                //String json= gson.toJson(cart);
                rp.addBodyParameter("product_id", pp.id + "");
                rp.addBodyParameter("user_id", sp.getInt("userId", 0) + "");
                rp.addBodyParameter("cart_num", Integer.parseInt(edt.getText().toString().trim()) + "");
                rp.addBodyParameter("create_time", new Timestamp(System.currentTimeMillis()).toString());
                //rp.addBodyParameter("cartInfo",json);
                x.http().post(rp, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("ProductInfoActivity", "加入购物车" + result);
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
        });
        //立即购买
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ProductInfoActivity", "立即购买");
                String username = sp.getString("username", null);
                if (username == null) {
                    ShowLoginDialogUtils.showDialogLogin(ProductInfoActivity.this);
                    return;
                }
                GetUserIdByNet.getUserIdByNet(ProductInfoActivity.this);
                user.setUserName(sp.getString("username", null));
                MapSerializable ms = new MapSerializable();
                mapPro.put(pp, Integer.parseInt((edt.getText().toString())));
                ms.setPro(mapPro);
                Log.i("ProductInfoActivity", ms.toString());
                List<Integer> cartIdLists = new ArrayList<Integer>();
                Intent intent = new Intent(ProductInfoActivity.this, ProOrderActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("OrderInfo", ms);
                bundle.putSerializable("cartIdLists", (Serializable) cartIdLists);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        //跳转到购物车
        title_bar_rl_cartview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getString("username", null) == null) {
                    ShowLoginDialogUtils.showDialogLogin(ProductInfoActivity.this);//登录
                    return;
                }
                GetUserIdByNet.getUserIdByNet(ProductInfoActivity.this);

                Intent intent = new Intent(ProductInfoActivity.this, MainActivity.class);
                intent.putExtra("direct", 2);
                startActivity(intent);
                //finish();

            }
        });


    }

    private void initView() {
        edt = (TextView) findViewById(R.id.edt);
        edt.setText("0");
        iv_project_detail_back = (ImageView) this.findViewById(R.id.iv_project_detail_back);
        vp_show_photo = (ViewPager) this.findViewById(R.id.vp_show_photo);
        //tv_product_detail=(TextView) this.findViewById(R.id.tv_product_detail);
        prod_info_tv_pnum = (TextView) findViewById(R.id.prod_info_tv_pnum);
        prod_info_tv_buynum=(TextView)findViewById(R.id.prod_info_tv_buynum);
       // prod_info_tv_buynum.setText("0");
        tv_project_detail_price = (TextView) this.findViewById(R.id.tv_project_detail_price);
        btn_touch_seller = (Button) this.findViewById(R.id.btn_touch_seller);
        tv_product_detail_city = (TextView) findViewById(R.id.tv_product_detail_city);
        tv_product_detail_schools = (TextView) findViewById(R.id.tv_product_detail_schools);
        title_bar_reddot = (TextView) findViewById(R.id.title_bar_reddot);
        prod_info_tv_detail = ((TextView) findViewById(R.id.prod_info_tv_detail));
//        cb_guanzhu=(CheckBox) this.findViewById(R.id.cb_guanzhu);
        btn_addcart = (Button) this.findViewById(R.id.btn_addcart);
        btn_buy_now = (Button) this.findViewById(R.id.btn_buy_now);
        prod_info_tv_des = (TextView) findViewById(R.id.prod_info_tv_des);
        title_bar_rl_cartview = (RelativeLayout) findViewById(R.id.title_bar_rl_cartview);
        lv_user_remark = (ListView) findViewById(R.id.lv_user_remark);
        prod_info_tv_prod_comment = (TextView) findViewById(R.id.prod_info_tv_prod_comment);
        tv_youfei = (TextView) findViewById(R.id.tv_youfei);
        prod_info_tv_baoyou = (TextView) findViewById(R.id.pro_info_tv_baoyou);
        prod_info_tv_prod_record = (TextView) findViewById(R.id.prod_info_tv_prod_record);

        addbt = (Button) findViewById(R.id.addbt);
        subbt = (Button) findViewById(R.id.subbt);
        addbt.setOnClickListener(this);
        subbt.setOnClickListener(this);
        btn_touch_seller.setOnClickListener(this);
        prod_info_tv_prod_comment.setOnClickListener(this);


        showDialog();


    }

    private void showDialog() {
        builder = new AlertDialog.Builder(ProductInfoActivity.this);
        dialog = builder.create();
        builder.setTitle("亲！你没有登录账号，请登录？");
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(ProductInfoActivity.this, LoginOther.class);
                startActivity(intent);


            }
        });
        builder.setNegativeButton("注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(ProductInfoActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    //接收上个界面传递的商品信息
    public Product.Products getProDetailInfo() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Product.Products pp = (Product.Products) bundle.getSerializable("pp");
        Log.e(TAG, pp.toString());
        return pp;
    }

    public void getDataCartNumber() {
        if (sp.getString("username", null) != null) {
            user.setUserName(sp.getString("username", null));
            //获取网络数据，
            //显示加入购物车的数量
            //queryCartServlet
            RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "queryCartServlet?userId=" + sp.getInt("userId", 0));
            //Log.e("ProductInfoActivity","url："+HttpUrlUtils.HTTP_URL+"queryCartServlet?userId="+new MyApplication().getUser().getUserId());
            x.http().get(rp, new Callback.CacheCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    Log.e("ProductInfoActivity", result);
                    Gson gson = new Gson();
                    List<Cart> listcart = gson.fromJson(result, new TypeToken<List<Cart>>() {
                    }.getType());
                    Log.e("ProductInfoActivity", listcart.toString());
                    if (listcart.size() > 0) {
                        //获取购物车所有数量
                        for (Cart car : listcart) {
                            carNumber += car.getCollectNumber();
                        }
                        ;
                        //显示在图标上
                        title_bar_reddot.setVisibility(View.VISIBLE);
                        title_bar_reddot.setText(carNumber + "");

                    } else {
                        title_bar_reddot.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("ProductInfoActivity", "访问购物车数据失败");

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
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbt://商品数加1
                if (Integer.parseInt(edt.getText().toString().trim()) < pp.pnum) {
                    edt.setText((Integer.parseInt(edt.getText().toString().trim()) + 1) + "");
                } else {
                    edt.setText(pp.pnum + "");
                }
                break;
            case R.id.subbt://商品数减一
                if (Integer.parseInt(edt.getText().toString().trim()) > 1) {
                    edt.setText((Integer.parseInt(edt.getText().toString().trim()) - 1) + "");
                    btn_addcart.setEnabled(true);
                    btn_buy_now.setEnabled(true);
                } else {
                    edt.setText(0+"");
                    btn_addcart.setEnabled(false);
                    btn_buy_now.setEnabled(false);
                }
                break;
            case R.id.prod_info_tv_prod_comment://更多评价
                if (envaLists.size() > 1) {
                    Intent intent = new Intent(ProductInfoActivity.this, EnvaluteStoreActivity.class);
                    intent.putExtra("userid", pp.user_id);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductInfoActivity.this, "亲！只有一条评论", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_touch_seller:
                String username = sp.getString("username", null);
                if (username == null) {
                    ShowLoginDialogUtils.showDialogLogin(ProductInfoActivity.this);//登录
                    return;
                }
                GetUserIdByNet.getUserIdByNet(ProductInfoActivity.this);

//                SharedPreferences sp1=getSharedPreferences("user",MODE_APPEND);
//                String token=sp1.getString("token","");
//                Log.i("cc", "onCreate: "+token);
//                RongIM.connect(token, new RongIMClient.ConnectCallback() {
//                    @Override
//                    public void onTokenIncorrect() {
//
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        Log.i("cc", "——onSuccess—-" + s);
//
////                startActivity(new Intent(ProductInfoActivity.this,MyFriendsActivity.class));
//
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.i("cc","--onError--"+errorCode);
//
//                    }
//                });

                Log.i("cc", "==userId==" + pp.user_id);
                Log.i("cc", "==userNick==" + pp.userNick);
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(ProductInfoActivity.this, pp.user_id + "", pp.userNick);
                }

                break;

        }

    }

    public class RemarkAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return envaLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(ProductInfoActivity.this, R.layout.pro_envalute_item, null);
            ImageView ivuserphoto = (ImageView) view.findViewById(R.id.iv_evt_photo);
            TextView tv_username = (TextView) view.findViewById(R.id.tv_evt_usernick);
            TextView tv_evcontent = (TextView) view.findViewById(R.id.tv_evcontent);
            TextView tv_evttime = (TextView) view.findViewById(R.id.tv_evttime);
            ImageView iv_evtimg = (ImageView) view.findViewById(R.id.iv_evtimg);
            if (envaLists.size() > 0) {
                Envalute envalute = envaLists.get(position);
                Log.e("ProductInfoActivity", "评价" + envalute.toString());
                xUtilsImageUtils.display(ivuserphoto, HttpUrlUtils.HTTP_URL + envalute.getUser().getUserPhoto(), true);
                xUtilsImageUtils.display(iv_evtimg, HttpUrlUtils.HTTP_URL + envalute.getEvt_imgurl());
                tv_username.setText(envalute.getUser().getNickname());
                tv_evttime.setText(envalute.getEvt_time());
                tv_evcontent.setText(envalute.getEvt_msg());
            }
            return view;
        }
    }

    public List<Envalute> getEnvaluteInfoByNet(int userId) {

        RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "queryEnvaluteServlet");
        rp.addBodyParameter("page", page + "");
        rp.addBodyParameter("userId", userId + "");
        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                page++;
                Gson gson = new Gson();

                envaLists.clear();
                envaLists = gson.fromJson(result, new TypeToken<List<Envalute>>() {
                }.getType());
                prod_info_tv_prod_record.setText("宝贝评价（" + envaLists.size() + ")");
                if (remarkAdapter == null) {
                    remarkAdapter = new RemarkAdapter();
                } else {
                    remarkAdapter.notifyDataSetChanged();
                }
                lv_user_remark.setAdapter(remarkAdapter);

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

        return envaLists;
    }
}
