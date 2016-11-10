package com.estore.fragment;

/*
我的订单---待评价页面
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.EnvaluteActivity;
import com.estore.activity.MainActivity;
import com.estore.R;
import com.estore.activity.myappliction.MyApplication;
import com.estore.httputils.CommonAdapter;

import com.estore.httputils.GetUserIdByNet;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.ViewHolder;
import com.estore.pojo.GoodsOrderState;
import com.estore.pojo.Order;
import com.estore.pojo.OrderDetail;
import com.estore.pojo.User;
import com.estore.view.NoScrollListview;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingEvaluateFragment extends Fragment {
    List<Order> orders = new ArrayList<>();//从服务器获取的订单信息
    private List<File> imageFileLists = new ArrayList<File>();
    private EditText et_pinglun;//评价内容
    private CheckBox cb_xing1;
    private CheckBox cb_xing2;
    private CheckBox cb_xing3;
    private CheckBox cb_xing4;
    private CheckBox cb_xing5;
    private CheckBox cb_xing6;
    private CheckBox cb_xing7;
    private CheckBox cb_xing8;
    private CheckBox cb_xing9;
    MultiPickResultView ev_recycler_view;
    int evt_honest = 0;//星级
    List<OrderDetail> orderDetails=new ArrayList<>();


    CommonAdapter<Order> orderApater;//适配器
 /*	 1 待付款
                                2	已付款
                                3	待发货
                                4	待收货
                                5	待评价
                                6	已评价
                                7	交易关闭*/


    public static final int UNPAY = 1;//没有付款
    public static final int UNRECEIVE = 4;//待收货
    public static final int UNSEND = 3;  //代发货
    public static final int REMARK = 6;    //已评价
    public static final int UNREMARK = 5;//待评价
    public static final int CANCEL = 7;//取消订单
    private ListView lv_unEvaluate;
    private SharedPreferences sp;
    private User user = new User();
    private LinearLayout ll_jiazai_evalute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_waiting_evaluate, null);
        ll_jiazai_evalute = ((LinearLayout) view.findViewById(R.id.ll_jiazai_evalute));
        lv_unEvaluate = ((ListView) view.findViewById(R.id.lv_unEvaluate));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        sp = getActivity().getSharedPreferences("User", getActivity().MODE_APPEND);
        ;
        user.setUserId(sp.getInt("userId", 0));
        getData();

        super.onActivityCreated(savedInstanceState);
    }

    public void getData() {
        Log.i("WaitingDeliverFragment", "getOrderData: ");
        // String userId= GetUserIdByNet.getUserIdByNet(getActivity())+"";
        //
        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "orderQueryServlet?userId=" + user.getUserId() + "&orderStatusId=5");
        //传参数：user_id,order_id
     /*   requestParams.addQueryStringParameter("userId",((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");
        Log.i("OrderAllFragment", "userId: "+((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");

        requestParams.addQueryStringParameter("orderStatusId",0+"");*/

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ll_jiazai_evalute.setVisibility(View.GONE);
                Log.i("WaitingDeliverFragment", "onSuccess: " + result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<Order>>() {
                }.getType();
                List<Order> newOrders = gson.fromJson(result, type);
                orders.clear();
                orders.addAll(newOrders);
                //设置listview的adapter
                Log.i("WaitingDeliverFragment", "orders++++" + orders.size());
                if (orderApater == null) {
                    orderApater = new CommonAdapter<Order>(getActivity(), orders, R.layout.frag_allorders_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, Order order, int position) {
                            order = orders.get(position);
                            Log.i("WaitingDeliverFragment", "order++++" + order.toString());


                            //设置item中控件的取值
                            //initItemView(viewHolder,order,position);
                            TextView frag_allorders_item_time = viewHolder.getViewById(R.id.frag_allorders_item_time);//订单时间
                            TextView frag_allorders_item_trade = viewHolder.getViewById(R.id.frag_allorders_item_trade);//交易关闭

                            TextView frag_allorders_item_buynum = viewHolder.getViewById(R.id.frag_allorders_item_buynum);//商品数量
                            TextView frag_allorders_item_money = viewHolder.getViewById(R.id.frag_allorders_item_money);//总价格
                            Button btnLeft = viewHolder.getViewById(R.id.btn_item_left);
                            Button btnRight = viewHolder.getViewById(R.id.btn_item_right);
                            NoScrollListview noScrollListview = viewHolder.getViewById(R.id.frag_allorders_item_listView);

                            CommonAdapter<OrderDetail> productAdapter = null;//显示商品详情的adapter
                            //设置商品详情显示数据
                            if (productAdapter == null) {
                                Log.i("WaitingDeliverFragment", "initItemView:productAdapter==null ");
                                Log.i("WaitingDeliverFragment", "initItemView:productAdapter==null " + order.getOrderDetails());

                                productAdapter = new CommonAdapter<OrderDetail>(getActivity(), order.getOrderDetails(), R.layout.order_product_item) {
                                    @Override
                                    public void convert(ViewHolder viewHolder, OrderDetail orderDetail, int position) {

                                        Log.i("AllOrdersFragment", "convert: " + orderDetail.getProduct());
                                        TextView tvName = viewHolder.getViewById(R.id.order_item_info_des);//商品名称
                                        ImageView imageView = viewHolder.getViewById(R.id.order_item_iv_left);//图片
                                        TextView order_item_info_price = viewHolder.getViewById(R.id.order_item_info_price);//商品价格

                                        TextView order_item_info_buynum = viewHolder.getViewById(R.id.order_item_info_buynum);//商品数量
                                        ImageView proPhoto = viewHolder.getViewById(R.id.order_item_iv_left);
                                        String[] imgurl = orderDetail.getProduct().imgurl.split("=");
                                        x.image().bind(proPhoto, HttpUrlUtils.HTTP_URL + imgurl[0]);
                                        order_item_info_buynum.setText(orderDetail.getGoodsNum() + "");
                                        order_item_info_price.setText(orderDetail.getGoodsPrice() + "");

                                        tvName.setText(orderDetail.getProduct().name);
                                    }
                                };
                                noScrollListview.setAdapter(productAdapter);

                            } else {
                                Log.i("WaitingDeliverFragment", "initItemView:productAdapte!=null ");
                                productAdapter.notifyDataSetChanged();
                            }


                            //控件赋值
                            frag_allorders_item_time.setText("订单时间：" + order.getGoodsCreateTime());
                            frag_allorders_item_trade.setText(order.getGoodsOrderState().getGoodsOrderStates());//订单状态
                            //详情的listview显示商品详情

                            //订单购买数量
                            orderDetails.clear();
                            orderDetails.addAll(order.getOrderDetails());

                            int totalNum = 0;//订单中商品的总数量
                            Log.i("WaitingDeliverFragment", "orderDetails" + orderDetails.toString());
                            for (OrderDetail orderDetail : orderDetails) {
                                totalNum += orderDetail.getGoodsNum();//便利每个详情中商品的数量
                            }


                            frag_allorders_item_buynum.setText("共" + totalNum + "件");//设置总数量
                            frag_allorders_item_money.setText("实付￥" + order.getGoodsTotalPrice());

                            //设置按钮的初始显示内容
                            btnShow(order.getGoodsOrderState().getGoodsOrderStateId(), btnLeft, btnRight);

                            //按钮点击事件
                            btnClick(order, position, btnLeft, btnRight);

                        }


                        //根据订单状态，判断按钮是否显示，按钮的文本，按钮的点击事件
                        public void btnShow(int orderStateId, Button btnLeft, Button btnRight) {
                            switch (orderStateId) {
                                /*	 1 待付款
                                2	已付款
                                3	待发货
                                4	待收货
                                5	待评价
                                6	已评价
                                7	交易关闭*/

                                case UNPAY:
                                    //待付款:（取消订单、付款）

                                    btnLeft.setVisibility(View.VISIBLE);
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnLeft.setText("取消订单");
                                    btnRight.setText("付款");
                                    break;
                                case UNRECEIVE:
                                    //待收货:(确认收货)
                                    btnLeft.setVisibility(View.GONE);//左边按钮消失
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnRight.setText("确认收货");

                                    break;
                                case UNSEND:
                                    //待发货:
                                    btnLeft.setVisibility(View.GONE);//左边按钮消失
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnRight.setText("发货");

                                    break;
                                case UNREMARK:
                                    //待发货:
                                    btnLeft.setVisibility(View.GONE);//左边按钮消失
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnRight.setText("评价");

                                    break;

                                default:
                                    btnLeft.setVisibility(View.GONE);
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnLeft.setText("");
                                    btnRight.setText("删除订单");
                                    break;
                            }

                        }

                        //按钮点击事件
                        public void btnClick(final Order order, final int position, Button btnLeft, Button btnRight) {
                            btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //判断订单状态
                                    switch (order.getGoodsOrderState().getGoodsOrderStateId()) {
                                        case UNPAY:
                                            Log.i("WaitingDeliverFragment", "取消订单");
                                            //取消订单(更新订单状态，更新界面)
                                            changeState(order.getGoodsOrderId(), CANCEL, "取消订单", position);
                                            break;
                                    }

                                }
                            });

                            btnRight.setOnClickListener(new View.OnClickListener() {


                                @Override
                                public void onClick(View v) {
                                    switch (order.getGoodsOrderState().getGoodsOrderStateId()) {
                                        case UNREMARK:

                                            Log.e("********","订单详情"+orderDetails.toString()+"");
                                            Log.e("********","position"+ position+"");
                                            Log.e("********",orderDetails.get(position).getProduct().toString());
                                            Log.i("WaitingDeliverFragment", "评论");
                                            Intent intent = new Intent(getActivity(), EnvaluteActivity.class);
                                            intent.putExtra("orderId", order.getGoodsOrderId());
                                            intent.putExtra("productId", orderDetails.get(position).getProduct().id);
                                            intent.putExtra("position",position);

                                            intent.putExtra("estore_id",orderDetails.get(position).getProduct().user_id);//商家Id
                                            startActivity(intent);
                                            //评论，


                                           /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            final Dialog dialog = builder.create();
                                            View view = View.inflate(getActivity(), R.layout.layout_envalute, null);
                                            dialog.show();
                                            dialog.getWindow().setContentView(view);
                                            et_pinglun = (EditText) view.findViewById(R.id.et_evlaute);
                                            ev_recycler_view = (MultiPickResultView) view.findViewById(R.id.ev_recycler_view);
                                            ev_recycler_view.init(getActivity(), MultiPickResultView.ACTION_SELECT, null);
                                            // TextView tv_evt_photo=(TextView)view.findViewById(R.id.tv_evt_photo);
                                            TextView fabiao = (TextView) view.findViewById(R.id.tv_fabiao);
                                            cb_xing1 = ((CheckBox) view.findViewById(R.id.xing_gray));
                                            cb_xing2 = ((CheckBox) view.findViewById(R.id.xing_gray2));
                                            cb_xing3 = ((CheckBox) view.findViewById(R.id.xing_gray3));
                                            cb_xing4 = ((CheckBox) view.findViewById(R.id.xing_gray4));
                                            cb_xing5 = ((CheckBox) view.findViewById(R.id.xing_gray5));
                                            cb_xing6 = ((CheckBox) view.findViewById(R.id.xing_gray6));
                                            cb_xing7 = ((CheckBox) view.findViewById(R.id.xing_gray7));
                                            cb_xing8 = ((CheckBox) view.findViewById(R.id.xing_gray8));
                                            fabiao.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    if (cb_xing1.isChecked()) {
                                                        evt_honest++;
                                                    }
                                                    if (cb_xing2.isChecked()) {
                                                        evt_honest++;
                                                    }
                                                    if (cb_xing3.isChecked()) {
                                                        evt_honest++;
                                                    }
                                                    if (cb_xing4.isChecked()) {
                                                        evt_honest++;
                                                    }
                                                    if (cb_xing5.isChecked()) {
                                                        evt_honest++;
                                                    }
                                                    if (cb_xing6.isChecked()) {
                                                        evt_honest++;
                                                    }
                                                    if (cb_xing7.isChecked()) {
                                                        evt_honest++;
                                                    }
                                                    if (cb_xing8.isChecked()) {
                                                        evt_honest++;
                                                    }

                                                    //上传评论
                                                    uploadEnvalue(order.getGoodsOrderId(), order.getOrderDetails().get(position).getProduct().id, dialog);
*//*
                                                }
                                            });
                                            //*/
                                           // changeState(order.getGoodsOrderId(), REMARK, "已评论", position);
                                            break;
                                        case REMARK://删除订单
                                            Log.i("OrderAllFragment", "删除订单");
                                            orders.remove(position);

                                            deleteOrder(order.getGoodsOrderId());
                                            orderApater.notifyDataSetChanged();

                                            break;
                                    }
                                }
                            });

                        }

                        //更新订单状态，更新界面
                        public void changeState(int orderId, final int newStateId, final String newStateName, final int position) {

                            RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "orderUpdateServlet");
                            requestParams.addBodyParameter("orderId", orderId + "");
                            requestParams.addBodyParameter("newStateId", newStateId + "");


                            //更新订单，更新界面
                            x.http().post(requestParams, new Callback.CommonCallback<String>() {

                                @Override
                                public void onSuccess(String result) {
                                    Log.i("WaitingDeliverFragment", "更新界面onSuccess: " + result);
                                    //更新界面
                                    orders.get(position).setGoodsOrderState(new GoodsOrderState(newStateId, newStateName));
                                    orderApater.notifyDataSetChanged();//更新界面

                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    Log.i("WaitingDeliverFragment", "更新界面fail: ");

                                }

                                @Override
                                public void onCancelled(CancelledException cex) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });
                        }
                    };
                    lv_unEvaluate.setAdapter(orderApater);
                } else {
                    orderApater.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.i("WaitingDeliverFragment", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void deleteOrder(Integer goodsOrderId) {
        RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "deleteOrderServlet");
        Log.i("WaitingDeliverFragment", "删除订单orderId: " + goodsOrderId);

        rp.addBodyParameter("orderId", goodsOrderId + "");


        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

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


    private void uploadEnvalue(Integer goodsOrderId, int product_id, final Dialog dialog) {
        RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "envaluteServlet");
        if (TextUtils.isEmpty(et_pinglun.getText().toString())) {
            Toast.makeText(getActivity(), "请输入你要评价的内容", Toast.LENGTH_LONG).show();
            return;

        }
        Log.i("WaitingEvaluateFragment", "评论 ");
        rp.addBodyParameter("user_id", sp.getInt("userId", 0) + "");
        rp.addBodyParameter("product_id", product_id + "");
        rp.addBodyParameter("order_id", goodsOrderId + "");
        rp.addBodyParameter("evt_msg", et_pinglun.getText().toString());
        rp.addBodyParameter("evt_honest", evt_honest + "");

        //params.setMultipart(true);
        for (int i = 0; i < imageFileLists.size(); i++) {
            rp.addBodyParameter("file" + i, imageFileLists.get(i));
            Log.e("file", imageFileLists.get(i) + "size" + imageFileLists.size());
        }

        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("WaitingEvaluateFragment", result);
                if ("true".equals(result)) {
                    Toast.makeText(getActivity(), "评价成功", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                   /* Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);*/
                } else {
                    return;
                }


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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
}
