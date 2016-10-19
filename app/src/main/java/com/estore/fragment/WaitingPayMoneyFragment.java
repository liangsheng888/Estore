package com.estore.fragment;

/*
我的订单---待付款页面
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.activity.R;
import com.estore.activity.myappliction.MyApplication;
import com.estore.httputils.CommonAdapter;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.ViewHolder;
import com.estore.pojo.GoodsOrderState;
import com.estore.pojo.Order;
import com.estore.pojo.OrderDetail;
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
 * A simple {@link Fragment} subclass.
 */
public class WaitingPayMoneyFragment extends Fragment {
    List<Order> orders=new ArrayList<>();//从服务器获取的订单信息

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
    private ListView lv_unPay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_waiting_pay_money,null);
        lv_unPay = ((ListView) view.findViewById(R.id.lv_unPay));

        return  view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getData();

        super.onActivityCreated(savedInstanceState);
    }
    public void getData(){
        Log.i("WaitingPayMoneyFragment", "getOrderData: ");
        String userId=((MyApplication)getActivity().getApplication()).getUser().getUserId()+"";
        //
        RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"orderQueryServlet?userId="+userId+"&orderStatusId=1");
        //传参数：user_id,order_id
     /*   requestParams.addQueryStringParameter("userId",((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");
        Log.i("OrderAllFragment", "userId: "+((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");

        requestParams.addQueryStringParameter("orderStatusId",0+"");*/

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("WaitingPayMoneyFragment", "onSuccess: "+result);
                Gson gson=new Gson();
                Type type=new TypeToken<List<Order>>(){}.getType();
                List<Order> newOrders=gson.fromJson(result,type);
                orders.clear();
                orders.addAll(newOrders);
                //设置listview的adapter
                Log.i("WaitingPayMoneyFragment", "orders++++"+orders.size());
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
                                Log.i("WaitingPayMoneyFragment", "initItemView:productAdapter==null ");
                                Log.i("WaitingPayMoneyFragment", "initItemView:productAdapter==null "+order.getOrderDetails());

                                productAdapter=new CommonAdapter<OrderDetail>(getActivity(),order.getOrderDetails(),R.layout.order_product_item) {
                                    @Override
                                    public void convert(ViewHolder viewHolder,OrderDetail orderDetail, int position) {

                                        Log.i("WaitingPayMoneyFragment", "convert: "+orderDetail.getProduct());
                                        TextView tvName= viewHolder.getViewById(R.id.order_item_info_des);//商品名称
                                        ImageView imageView= viewHolder.getViewById(R.id.order_item_iv_left);//图片
                                        TextView order_item_info_price= viewHolder.getViewById(R.id.order_item_info_price);//商品价格

                                        TextView order_item_info_buynum= viewHolder.getViewById(R.id.order_item_info_buynum);//商品数量
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
                            List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
                            orderDetails =order.getOrderDetails();

                            int totalNum=0;//订单中商品的总数量
                            Log.i("WaitingPayMoneyFragment", "orderDetails"+orderDetails.toString());
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

                                default:
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
                                            Log.i("WaitingPayMoneyFragment", "取消订单");
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
                                            //付款
                                            Log.i("WaitingPayMoneyFragment", "onClick: ");
                                            break;
                                        case UNRECEIVE:
                                            //确认收货，
                                            Log.i("WaitingPayMoneyFragment", "确认收货");
                                            changeState(order.getGoodsOrderId(),UNREMARK,"待评价",position);
                                            break;
                                        case UNSEND:
                                            //发货，
                                            Log.i("WaitingPayMoneyFragment", "卖家发货");
                                            changeState(order.getGoodsOrderId(),UNRECEIVE,"已发货",position);
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
                                    Log.i("WaitingPayMoneyFragment", "更新界面onSuccess: "+result);
                                    //更新界面
                                    orders.get(position).setGoodsOrderState(new GoodsOrderState(newStateId,newStateName));
                                    orderApater.notifyDataSetChanged();//更新界面

                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    Log.i("WaitingPayMoneyFragment", "更新界面fail: ");

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
                    lv_unPay.setAdapter(orderApater);
                }else{
                    orderApater.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.i("WaitingPayMoneyFragment", "onError: "+ex.getMessage());
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
