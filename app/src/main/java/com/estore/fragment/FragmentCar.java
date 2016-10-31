package com.estore.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.ProOrderActivity;
import com.estore.activity.R;
import com.estore.httputils.CommonAdapter;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.MapSerializable;
import com.estore.httputils.ViewHolder;
import com.estore.pojo.Cart;
import com.estore.pojo.Product;
import com.estore.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import swipetodismiss.SwipeMenu;
import swipetodismiss.SwipeMenuCreator;
import swipetodismiss.SwipeMenuItem;
import swipetodismiss.SwipeMenuListView;


/**
 * Created by Administrator on 2016/9/19.
 */
public class FragmentCar extends Fragment {
    private SharedPreferences sp;
    private User user = new User();
    //    private ListView cartListView;
    private List<Cart> cartlist = new ArrayList<>();//
    private CartAapater cartAdapter;
    private int totalPrice;
    private TextView cart_buy_money;//
    private TextView cart_jiesuan;
    private Button btn_jia;
    private Button btn_jian;
    private CheckBox check_all;
    private SwipeMenuListView cartListView;
    private int number = 0;
    private List<Integer> cartIdLists=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_shoping_cart, null);
//        cartListView = (ListView) view.findViewById(R.id.cart_listview);

        cartListView = (SwipeMenuListView) view.findViewById(R.id.cart_listview);
        cart_buy_money = (TextView) view.findViewById(R.id.cart_buy_money);
        check_all = (CheckBox) view.findViewById(R.id.checkall);
        cart_jiesuan = (TextView) view.findViewById(R.id.cart_jiesuan);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp = getActivity().getSharedPreferences("User", getActivity().MODE_APPEND);
        ;
        user.setUserId(sp.getInt("userId", 0));
        getData();
        //全选
        check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setChecked(true);
                    for (int i = 0; i < cartAdapter.getCount(); i++) {
                        cartAdapter.getCheckStatus().put(i, true);
                        cartAdapter.notifyDataSetChanged();
                    }
                    cartAdapter.setNumberSeclected(cartAdapter.getCount());
                    Log.e("FragmentCar", "选中数" + cartAdapter.getNumberSeclected() + "");
                    totalPrice = 0;
                    //总价格显示
                    for (int i = 0; i < cartlist.size(); i++) {
                        double eachPrice = cartAdapter.getCar_numbers().get(i) * cartlist.get(i).getProduct().estoreprice;
                        totalPrice += eachPrice;
                        //改变合计tv的值
                        cart_buy_money.setText("￥" + totalPrice);//有bug
                    }
                } else {
                    buttonView.setChecked(false);
                    for (int i = 0; i < cartAdapter.getCount(); i++) {
                        cartAdapter.getCheckStatus().put(i, false);
                        cartAdapter.notifyDataSetChanged();
                    }
                    cartAdapter.setNumberSeclected(0);
                    Log.e("FragmentCar", "选中数" + cartAdapter.getNumberSeclected() + "");

                    //总价格显示

                    // cart_buy_money.setText("￥"+0);//有bug
                }

            }


        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // 设置删除框
                SwipeMenuItem deleteitem = new SwipeMenuItem(getActivity());
                deleteitem.setBackground(new ColorDrawable(Color.RED));
                deleteitem.setWidth(dp2px(90));
                deleteitem.setTitle("删除");
                deleteitem.setTitleSize(15);
                deleteitem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteitem);
            }
        };
        cartListView.setMenuCreator(creator);
        cartListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                Log.i("FragmentCar", "position" + position + "");
                Log.i("FragmentCar", "gggggggggg" + cartlist.size());
                Integer cartId = cartlist.get(position).getCartId();
                Log.i("FragmentCar", "=============" + cartId + "");
                String url = HttpUrlUtils.HTTP_URL + "deleteCartServlet";
                RequestParams requestParams = new RequestParams(url);
                requestParams.addQueryStringParameter("cartId", cartId + "");
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        cartlist.remove(position);
                        cartAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                return false;
            }
        });


        cart_jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到订单界面
                Map<Integer, Boolean> mapstus = cartAdapter.getCheckStatus();//获取选中状态
                Set<Map.Entry<Integer, Boolean>> set = mapstus.entrySet();
                Map<Product.Products, Integer> proInfo = new HashMap<Product.Products, Integer>();//封装购物车中的信息
                for (Map.Entry<Integer, Boolean> me :
                        set) {
                    Integer position = me.getKey();
                    Boolean stus = me.getValue();
                    if (stus) {
                        //获取选中商品的信息
                        Product.Products pro = cartlist.get(position).getProduct();
                        cartIdLists.add(cartlist.get(position).getCartId());

                        Log.e("FragmentCar", "product" + pro.toString());
                        //选中购物车商品数量
                        int number = cartAdapter.getCar_numbers().get(position);
                        proInfo.put(pro, number);
                    }

                }
                if (proInfo.size() > 0) {
                    Intent intent = new Intent(getActivity(), ProOrderActivity.class);

                    MapSerializable OrderInfo = new MapSerializable();
                    OrderInfo.setPro(proInfo);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("OrderInfo", OrderInfo);
                    bundle.putSerializable("cartIdLists", (Serializable) cartIdLists);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "亲，请勾选你要结算的宝贝", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getData() {
        RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "queryCartServlet?userId=" + user.getUserId());

        x.http().get(rp, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Log.e("FragmentCar", "result" + result);
                Gson gson = new Gson();
                List<Cart> list = gson.fromJson(result, new TypeToken<List<Cart>>() {

                }.getType());
                cartlist.clear();
                cartlist.addAll(list);
                Log.e("FragmentCar", "list------" + list.toString());
                if (cartAdapter == null) {
                    cartAdapter = new CartAapater(getActivity(), cartlist, R.layout.cart_item);
                } else {
                    cartAdapter.notifyDataSetChanged();
                }
                cartListView.setAdapter(cartAdapter);

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

    public class CartAapater extends CommonAdapter<Cart> {
        Map<Integer, Boolean> checkstus = new HashMap<>();//j记录每个item checkBox 状态
        Map<Integer, Integer> car_numbers = new HashMap<>();//j记录每个位置收藏商品最新数量

        public Map<Integer, Integer> getCar_numbers() {
            return car_numbers;
        }

        public void setCar_numbers(Map<Integer, Integer> car_numbers) {
            this.car_numbers = car_numbers;
        }

        private int numberSeclected;//记录当前选中个数

        public int getNumberSeclected() {
            return numberSeclected;
        }

        public void setNumberSeclected(int numberSeclected) {
            this.numberSeclected = numberSeclected;
        }

        public Map<Integer, Boolean> getCheckStatus() {
            return checkstus;
        }

        public void setCheckStatus(Map<Integer, Boolean> checkStatus) {
            this.checkstus = checkStatus;
        }

        public CartAapater(Context context, List<Cart> list, int layoutId) {
            super(context, list, layoutId);
            for (int i = 0; i < list.size(); i++) {
                checkstus.put(i, false);//默认都没有 选中
            }
            //初始化商品数量
            for (int i = 0; i < list.size(); i++) {
                car_numbers.put(i, list.get(i).getCollectNumber());
            }

        }

        @Override
        public void convert(ViewHolder viewHolder, Cart cart, final int position) {
            final TextView tv_price = viewHolder.getViewById(R.id.cart_item_prod_price);
            final TextView tv_num = viewHolder.getViewById(R.id.cart_item_number);
            TextView tv_bianji = viewHolder.getViewById(R.id.tv_bianji);
            final TextView tv_pronum = viewHolder.getViewById(R.id.tv_num);
            TextView tv_name = viewHolder.getViewById(R.id.cart_item_prod_des);
            final LinearLayout cart_item_jiajian = viewHolder.getViewById(R.id.cart_item_jiajian);

            ImageView iv = viewHolder.getViewById(R.id.cart_item_prod_img);
            cart = cartlist.get(position);
            Log.e("FragmentCar", "cart" + cart.toString());
            if (cart != null)
                tv_price.setText("￥" + cart.getProduct().estoreprice);
            tv_num.setText(car_numbers.get(position) + "");//
            tv_pronum.setText(car_numbers.get(position) + "件");

            tv_name.setText(cart.getProduct().name);
            String[] imgurl = cart.getProduct().imgurl.split("=");
            x.image().bind(iv, HttpUrlUtils.HTTP_URL + imgurl[0]);
            //checkBox 选择 价钱改变
            final CheckBox cb_check = viewHolder.getViewById(R.id.check_item);
            cb_check.setTag(position);
            cb_check.setChecked(checkstus.get(position));
            //加 减
            btn_jia = viewHolder.getViewById(R.id.cart_item_jia);
            btn_jia.setTag(position);
            final Cart finalCart = cart;
            btn_jia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    number = Integer.parseInt(tv_num.getText().toString()) + 1;
                    tv_num.setText(number + "");
                    tv_pronum.setText(number + "件");
                    //map 中的收藏数量发上改变
                    car_numbers.put((int) v.getTag(), number);
                    //获取该位置的checked的状态
                    if (checkstus.get((int) v.getTag())) {
                        Double eachPrice = finalCart.getProduct().estoreprice * number;
                        totalPrice += finalCart.getProduct().estoreprice;
                        cart_buy_money.setText("￥" + totalPrice);

                    }
                }
            });
            tv_bianji.setOnClickListener(new View.OnClickListener() {
                //编辑
                @Override
                public void onClick(View v) {
                    if (cart_item_jiajian.getVisibility() == View.INVISIBLE) {
                        cart_item_jiajian.setVisibility(View.VISIBLE);
                    } else {
                        cart_item_jiajian.setVisibility(View.INVISIBLE);
                    }
                }
            });
            btn_jian = viewHolder.getViewById(R.id.cart_item_jian);
            btn_jian.setTag(position);
            final Cart finalCart1 = cart;
            btn_jian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Integer.parseInt(tv_num.getText().toString()) > 1) {
                        number = number - 1;
                        tv_num.setText(number + "");
                        tv_pronum.setText(number + "件");
                    } else {
                        tv_num.setText("1");
                        tv_pronum.setText(1 + "件");
                    }
                    car_numbers.put((int) v.getTag(), number);
                    if (checkstus.get((int) v.getTag())) {
                        Double eachPrice = finalCart1.getProduct().estoreprice * number;
                        if (number > 0) {
                            totalPrice -= finalCart1.getProduct().estoreprice;
                        }
                        //有bug
                        cart_buy_money.setText("￥" + totalPrice);
                    }
                }
            });


            cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.e("ShopingCartFragment", "oncheckedchanged" + position + "getTag:" + cb_check.getTag());
                    if (isChecked && position == (int) buttonView.getTag() && !check_all.isChecked()) {
                        numberSeclected++;
                        Log.e("ShopingCartFragment", "checked");
                        checkstus.put((Integer) buttonView.getTag(), true);
                        Double eachPrice = finalCart1.getProduct().estoreprice * number;
                        totalPrice += eachPrice;
                        cart_buy_money.setText(totalPrice + "￥");
                        Log.e("ShopingCartFragment", "选中个数" + cartAdapter.getNumberSeclected());
                        if (numberSeclected == cartAdapter.getCount()) {//判端当前选中个数书否是总个数，是让全选按钮选中

                            check_all.setChecked(true);
                        }
                    } else if (!isChecked && position == (int) buttonView.getTag()) {
                        Log.e("ShopingCartFragment", "notchecked");
                        numberSeclected--;
                        checkstus.put((Integer) buttonView.getTag(), false);
                        Double eachPrice = finalCart1.getProduct().estoreprice * number;
                        totalPrice -= eachPrice;
                        cart_buy_money.setText("￥" + totalPrice);
                        if (check_all.isChecked()) {
                            check_all.setChecked(false);
                        }
                    }


                }
            });

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

            //隐藏
            check_all.setChecked(false);

            //初始化checkStatus：默认所有checkbox未选中状态
            for (int i = 0; i < cartlist.size(); i++) {
                cartAdapter.getCheckStatus().put(i, false);
            }

            //  totalPrice=0;总价格重新赋值


        } else {
            //显示：再次获取网络数据
            cartAdapter = null;


            getData();
        }
    }

    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
        cart_buy_money.setText("");

    }
}
