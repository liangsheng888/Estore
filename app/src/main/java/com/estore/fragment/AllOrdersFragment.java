package com.estore.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.R;

import com.estore.activity.EnvaluteActivity;
import com.estore.activity.PayActivity;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * 全部订单
 */

/**
 * Created by Administrator on 2016/10/13.
 */
public class AllOrdersFragment extends Fragment{

    private ListView frag_allorders_listview;
    List<Order> orders=new ArrayList<>();//从服务器获取的订单信息
    private SharedPreferences sp;
    private User user=new User();
    List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
    List<OrderDetail> orderInfo=new ArrayList<OrderDetail>();

    CommonAdapter<Order> orderApater;//适配器
 /*	 1 待付款
                                2	已付款
                                3	待发货
                                4	待收货
                                5	待评价
                                6	已评价
                                7	交易关闭*/


    public static final int UNPAY=1;//没有付款
    public static final int UNRECEIVE=4;//待收货
    public static final int UNSEND=3;  //代发货
    public static final int REMARK=6;    //已评价
    public static final int UNREMARK=5;//待评价
    public static final int CANCEL=7;//取消订单
    private LinearLayout ll_jiazai_all;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_all_orders,null);
        ll_jiazai_all = ((LinearLayout) view.findViewById(R.id.ll_jiazai_all));
        frag_allorders_listview=(ListView) view.findViewById(R.id.frag_allorders_listview);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        sp=getActivity().getSharedPreferences("User",getActivity().MODE_APPEND);
        user.setUserId(sp.getInt("userId",0));
        getData();

        super.onActivityCreated(savedInstanceState);
    }
    public void getData(){
        Log.i("AllOrdersFragment", "getOrderData: ");
      //   String userId= GetUserIdByNet.getUserIdByNet(getActivity())+""+"";
        //
        RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"orderQueryServlet?userId="+user.getUserId() +"&orderStatusId=0");
        //传参数：user_id,order_id
     /*   requestParams.addQueryStringParameter("userId",((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");
        Log.i("OrderAllFragment", "userId: "+((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");

        requestParams.addQueryStringParameter("orderStatusId",0+"");*/

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ll_jiazai_all.setVisibility(View.GONE);
                Log.i("AllOrdersFragment", "onSuccess: "+result);
                Gson gson=new Gson();
                Type type=new TypeToken<List<Order>>(){}.getType();
                List<Order> newOrders=gson.fromJson(result,type);
                orders.clear();
                orders.addAll(newOrders);

                //设置listview的adapter
                Log.i("OrderAllFragment", "orders++++"+orders.size());
                if(orderApater==null){
                    orderApater=new CommonAdapter<Order>(getActivity(),orders,R.layout.frag_allorders_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, Order order, int position) {
                            order=orders.get(position);
                            Log.i("OrderAllFragment", "order++++"+order.toString());


                            //设置item中控件的取值
                            //initItemView(viewHolder,order,position);
                            TextView frag_allorders_item_time=  viewHolder.getViewById(R.id.frag_allorders_item_time);//订单时间
                            TextView frag_allorders_item_trade=  viewHolder.getViewById(R.id.frag_allorders_item_trade);//交易关闭

                            TextView frag_allorders_item_buynum=  viewHolder.getViewById(R.id.frag_allorders_item_buynum);//商品数量
                            TextView frag_allorders_item_money=  viewHolder.getViewById(R.id.frag_allorders_item_money);//总价格
                            Button btnLeft=viewHolder.getViewById(R.id.btn_item_left);
                            Button btnRight=viewHolder.getViewById(R.id.btn_item_right);
                            NoScrollListview noScrollListview=viewHolder.getViewById(R.id.frag_allorders_item_listView);

                            CommonAdapter<OrderDetail> productAdapter=null;//显示商品详情的adapter
                            //设置商品详情显示数据
                            if(productAdapter==null) {
                                Log.i("AllOrdersFragment", "initItemView:productAdapter==null ");
                                Log.i("AllOrdersFragment", "initItemView:productAdapter==null "+order.getOrderDetails());

                                productAdapter=new CommonAdapter<OrderDetail>(getActivity(),order.getOrderDetails(),R.layout.order_product_item) {
                                    @Override
                                    public void convert(ViewHolder viewHolder,OrderDetail orderDetail, int position) {

                                        Log.i("AllOrdersFragment", "convert: "+orderDetail.getProduct());
                                        TextView tvName= viewHolder.getViewById(R.id.order_item_info_des);//商品名称
                                        ImageView imageView= viewHolder.getViewById(R.id.order_item_iv_left);//图片
                                        TextView order_item_info_price= viewHolder.getViewById(R.id.order_item_info_price);//商品价格

                                        TextView order_item_info_buynum= viewHolder.getViewById(R.id.order_item_info_buynum);//商品数量
                                        ImageView proPhoto=viewHolder.getViewById(R.id.order_item_iv_left);
                                        String[] imgurl=orderDetail.getProduct().imgurl.split("=");
                                        x.image().bind(proPhoto,HttpUrlUtils.HTTP_URL + imgurl[0]);
                                        order_item_info_buynum.setText(orderDetail.getGoodsNum()+"");
                                        order_item_info_price.setText(orderDetail.getGoodsPrice()+"");

                                        tvName.setText(orderDetail.getProduct().name);
                                    }
                                };
                                noScrollListview.setAdapter(productAdapter);

                            }else{
                                Log.i("AllOrdersFragment", "initItemView:productAdapte!=null ");
                                productAdapter.notifyDataSetChanged();
                            }


                            //控件赋值
                            frag_allorders_item_time.setText("订单时间："+order.getGoodsCreateTime());
                            frag_allorders_item_trade.setText(order.getGoodsOrderState().getGoodsOrderStates());//订单状态
                            //详情的listview显示商品详情

                            //订单购买数量
                            orderDetails.clear();
                            orderDetails.addAll(order.getOrderDetails());
                            orderInfo.addAll(order.getOrderDetails());

                            int totalNum=0;//订单中商品的总数量
                            Log.i("AllOrdersFragment", "orderDetails"+orderDetails.toString());
                            for(OrderDetail orderDetail:orderDetails){
                                totalNum+= orderDetail.getGoodsNum();//便利每个详情中商品的数量
                            }


                            frag_allorders_item_buynum.setText("共"+totalNum+"件");//设置总数量
                            frag_allorders_item_money.setText("实付￥"+order.getGoodsTotalPrice());

                            //设置按钮的初始显示内容
                            btnShow(order.getGoodsOrderState().getGoodsOrderStateId(),btnLeft,btnRight);

                            //按钮点击事件
                            btnClick(order,position,btnLeft,btnRight);

                        }


                        //根据订单状态，判断按钮是否显示，按钮的文本，按钮的点击事件
                        public void btnShow(int orderStateId,Button btnLeft,Button btnRight){
                            switch (orderStateId){
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
                                    btnLeft.setVisibility(View.GONE);
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnLeft.setText("");
                                    btnRight.setText("评论");
                                    break;
                                case REMARK:
                                    btnLeft.setVisibility(View.GONE);
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnLeft.setText("");
                                    btnRight.setText("删除订单");
                                    break;
                                case CANCEL:
                                    btnLeft.setVisibility(View.GONE);
                                    btnRight.setVisibility(View.VISIBLE);
                                    btnLeft.setText("");
                                    btnRight.setText("删除订单");
                                    break;
                            }

                        }

                        //按钮点击事件
                        public void btnClick(final Order order, final int position,Button btnLeft, Button btnRight){
                            btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //判断订单状态
                                    switch (order.getGoodsOrderState().getGoodsOrderStateId()){
                                        case UNPAY:
                                            Log.i("AllOrdersFragment", "取消订单");
                                            //取消订单(更新订单状态，更新界面)
                                            changeState(order.getGoodsOrderId(),CANCEL,"取消订单",position);
                                            break;
                                    }

                                }
                            });

                            btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (order.getGoodsOrderState().getGoodsOrderStateId()){
                                        case UNPAY:
                                            Log.i("WaitingPayMoneyFragment", "付款");
                                            //付款 付款成功后该商品数量减1
                                            Log.i("WaitingPayMoneyFragment", "onClick: ");
                                            Intent intent=new Intent(getActivity(),PayActivity.class);
                                            Log.i("WaitingPayMoneyFragment", "onClick: position "+position);

                                            // String proName=order.getOrderDetails().get(position).getProduct().name;
                                            String proName=orderInfo.get(position).getProduct().name;
                                            String description=orderInfo.get(position).getProduct().description;
                                            Double price=order.getGoodsTotalPrice();


                                            intent.putExtra("proName",proName);
                                            intent.putExtra("description",description);
                                            intent.putExtra("price",price);
                                            intent.putExtra("product_id",orderInfo.get(position).getProduct().id);
                                            intent.putExtra("pronum",orderInfo.get(position).getGoodsNum());
                                            intent.putExtra("orderId",order.getGoodsOrderId());
                                            intent.putExtra("position",position);

                                            startActivity(intent);

                                            /*subProduct(orderInfo.get(position).getProduct().id,orderInfo.get(position).getGoodsNum());
                                            //更新订单状态，卖家显示已付款，卖家显示发货
                                             changeState(order.getGoodsOrderId(),UNRECEIVE,"待收货",position);*/

                                            break;
                                        case UNRECEIVE:
                                            //确认收货，
                                            Log.i("OrderAllFragment", "确认收货");
                                            changeState(order.getGoodsOrderId(),UNREMARK,"待评价",position);
                                            break;
                                        case UNSEND:
                                            //发货，
                                            Log.i("OrderAllFragment", "卖家发货");
                                            changeState(order.getGoodsOrderId(),UNRECEIVE,"已发货",position);
                                            break;
                                        case UNREMARK:
                                            //评论，
                                            Log.i("OrderAllFragment", "评论");
                                            Log.e("********","订单详情"+orderDetails.toString()+"");
                                            Log.e("********","position"+ position+"");
                                            Log.e("********",orderInfo.get(position).getProduct().toString());
                                            Log.i("WaitingDeliverFragment", "评论");
                                            Intent intent2 = new Intent(getActivity(), EnvaluteActivity.class);
                                            intent2.putExtra("orderId", order.getGoodsOrderId());
                                            intent2.putExtra("productId", orderInfo.get(position).getProduct().id);
                                            intent2.putExtra("position",position);

                                            intent2.putExtra("estore_id",orderInfo.get(position).getProduct().user_id);//商家Id
                                            startActivity(intent2);
                                            //评论，
                                            break;
                                        case REMARK://删除订单
                                            Log.i("OrderAllFragment", "删除订单");
                                            orders.remove(position);

                                            deleteOrder(order.getGoodsOrderId());
                                            orderApater.notifyDataSetChanged();
                                            break;
                                        case CANCEL://交易关闭
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
                        public void changeState(int orderId, final int newStateId, final String newStateName,final int position){

                            RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"orderUpdateServlet");
                            requestParams.addBodyParameter("orderId",orderId+"");
                            requestParams.addBodyParameter("newStateId",newStateId+"");
                            //更新订单，更新界面
                            x.http().post(requestParams, new Callback.CommonCallback<String>() {

                                @Override
                                public void onSuccess(String result) {
                                    Log.i("AllOrdersFragment", "更新界面onSuccess: "+result);
                                    //更新界面
                                    orders.get(position).setGoodsOrderState(new GoodsOrderState(newStateId,newStateName));
                                    orderApater.notifyDataSetChanged();//更新界面

                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    Log.i("AllOrdersFragment", "更新界面fail: ");

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
                    frag_allorders_listview.setAdapter(orderApater);
                }else{
                    orderApater.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.i("AllOrdersFragment", "onError: "+ex.getMessage());
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
        RequestParams rp=new RequestParams(HttpUrlUtils.HTTP_URL+"deleteOrderServlet");
        Log.i("WaitingDeliverFragment", "删除订单orderId: "+goodsOrderId);

        rp.addBodyParameter("orderId",goodsOrderId+"");
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            getData();
        }
    }
    private void subProduct(int productId, int goodsNum) {
        RequestParams rp=new RequestParams(HttpUrlUtils.HTTP_URL+"subProductServlet");
        rp.addBodyParameter("productId",productId+"");
        rp.addBodyParameter("goodsNum",goodsNum+"");
        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("WaitingPayMoneyFragment", "更新商品数量成功 ");
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
    public void onStart() {
        getData();
        super.onStart();
    }
}
