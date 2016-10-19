package com.estore.activity;
/*
我的订单页面
 */

        import android.content.Intent;
        import android.graphics.Color;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;

        import com.estore.fragment.AllOrdersFragment;
        import com.estore.fragment.WaitingDeliverFragment;
        import com.estore.fragment.WaitingEvaluateFragment;
        import com.estore.fragment.WaitingPayMoneyFragment;
        import com.estore.fragment.WaitingReceiveGoodsFragment;

public class MyOrderActivity extends AppCompatActivity {
    AllOrdersFragment allOrdersFragment;
    WaitingReceiveGoodsFragment waitingReceiveGoodsFragment;
    WaitingDeliverFragment waitingDeliverFragment;
    WaitingEvaluateFragment waitingEvaluateFragment;
    WaitingPayMoneyFragment waitingPayMoneyFragment;
    private Fragment[] fragments;
    private RadioButton rb_allorder;
    private RadioButton rb_delivergoods;
    private RadioButton rb_paymoney;
    private RadioButton rb_waitingevaluate;
    private RadioButton rb_receivegoods;
    private RadioGroup rg_grouporder;
    private ImageView iv_orderreturn;
    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    private Button[] buttons=new Button[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        //所有fragment的数组
        allOrdersFragment=new AllOrdersFragment();
        waitingReceiveGoodsFragment = new WaitingReceiveGoodsFragment();
        waitingDeliverFragment= new WaitingDeliverFragment();
        waitingEvaluateFragment = new WaitingEvaluateFragment();
        waitingPayMoneyFragment= new WaitingPayMoneyFragment();
        fragments=new Fragment[]{allOrdersFragment,waitingReceiveGoodsFragment,waitingDeliverFragment,waitingEvaluateFragment,waitingPayMoneyFragment};
        //跳转到Fragment
        rb_allorder = ((RadioButton) findViewById(R.id.rb_allorder));
        rb_delivergoods = ((RadioButton) findViewById(R.id.rb_delivergoods));
        rb_paymoney = ((RadioButton) findViewById(R.id.rb_paymoney));
        rb_waitingevaluate = ((RadioButton) findViewById(R.id.rb_waitingevaluate));
        rb_receivegoods = ((RadioButton) findViewById(R.id.rb_receivegoods));
        rg_grouporder= ((RadioGroup) findViewById(R.id.rg_grouporder));
        //界面初始显示第一个fragment;添加第一个fragment
        buttons[0]=rb_allorder;
        buttons[1]=rb_receivegoods;
        buttons[2]=rb_delivergoods;
        buttons[3]=rb_waitingevaluate;
        buttons[4]=rb_paymoney;


        getSupportFragmentManager().beginTransaction().add(R.id.fl_orders, fragments[0]).commit();

        //初始时，按钮1选中
        buttons[0].setSelected(true);
        buttons[0].setBackgroundColor(Color.RED);

        //返回我的订单
        iv_orderreturn = ((ImageView) findViewById(R.id.iv_orderreturn));
        iv_orderreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MyOrderActivity.this,MainActivity.class);
                //startActivity(intent);
                finish();
            }
        });
        rg_grouporder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_allorder:
                        Log.i("MyOrderActivity", "全部 ");
                        newIndex=0;//

                        break;
                    case R.id.rb_receivegoods:
                        Log.i("MyOrderActivity", "待收货 ");
                        newIndex=1;//

                        break;
                    case R.id.rb_delivergoods:
                        Log.i("MyOrderActivity", "代发货 ");
                        newIndex=2;//

                        break;
                    case R.id.rb_waitingevaluate:
                        Log.i("MyOrderActivity", "待评价 ");
                        newIndex=3;//

                        break;
                    case R.id.rb_paymoney:
                        Log.i("MyOrderActivity", "代付款 ");
                        newIndex=4;//

                        break;
                }

                FragmentTransaction transaction;
                //如果选择的项不是当前选中项，则替换；否则，不做操作
                if(newIndex!=oldIndex){

                    transaction=getSupportFragmentManager().beginTransaction();

                    transaction.hide(fragments[oldIndex]);//隐藏当前显示项


                    //如果选中项没有加过，则添加
                    if(!fragments[newIndex].isAdded()){
                        //添加fragment
                        transaction.add(R.id.fl_orders,fragments[newIndex]);
                    }
                    //显示当前选择项
                    transaction.show(fragments[newIndex]).commit();
                }
                //之前选中的项，取消选中
                buttons[oldIndex].setSelected(false);
                //当前选择项，按钮被选中
                buttons[newIndex].setSelected(true);
                buttons[oldIndex].setBackgroundColor(Color.WHITE);
                buttons[newIndex].setBackgroundColor(Color.RED);



                //当前选择项变为选中项
                oldIndex=newIndex;


            }
        });


    }

}