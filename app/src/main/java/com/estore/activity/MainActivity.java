package com.estore.activity;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;


import com.estore.fragment.FragmentCar;
import com.estore.fragment.FragmentHome;
import com.estore.fragment.MyHomePageFragment;
import com.estore.fragment.EhFragment;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private SharedPreferences sp;
    private ListView lv;

    public LinkedList<Product.Products> getList() {
        return list;
    }

    private final LinkedList<Product.Products> list =new LinkedList<Product.Products>();

    private Fragment[] fragments;
    Button[] buttons=new Button[5];
    private RadioGroup rg;
    private ListView lv_sild;//侧滑菜单
    private Fragment fr_home;
    private Fragment fr_old;
    private Fragment fr_car;
    private Fragment fr_add;
    private Fragment fr_mine;
    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    private boolean firstIn = true;//
    String[] keys = {"img", "name"};
    private String menuData[] = {"新闻", "订阅", "图片", "视频", "跟帖", "投票"};
    private int[] id = {R.id.iv_name, R.id.tv_name};
    private int[] imgs = {R.drawable.add_red, R.drawable.car_red, R.drawable.car_red, R.drawable.car_red, R.drawable.car_red, R.drawable.car_red};
    private List<Map<String, Object>> mapList;//策划菜单数据
    public   Integer page=1;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer orderFlag=0;

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("User",MODE_APPEND);


        mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (int i = 0; i < imgs.length; i++) {
            map = new HashMap<String, Object>();
            map.put("img", imgs[i]);
            map.put("name", menuData[i]);
            mapList.add(map);
        }
        initView();
        initData();}

    private void initView() {
        rg = ((RadioGroup) this.findViewById(R.id.rg_bottom_driection));
        lv_sild = (ListView) this.findViewById(R.id.lv_slidMenu);
        buttons[0]= ((RadioButton) this.findViewById(R.id.rb_home));
        //buttons[1]=((RadioButton) this.findViewById(R.id.rb_fabu));
        buttons[2]=((RadioButton) this.findViewById(R.id.rb_cat));
        buttons[1]=((RadioButton) this.findViewById(R.id.rb_old));
        buttons[3]=((RadioButton) this.findViewById(R.id.rb_mine));
        fr_home = new FragmentHome();
        fr_car = new FragmentCar();
        fr_add = new EhFragment();
        fr_mine = new MyHomePageFragment();

        fragments=new Fragment[]{fr_home,fr_add,fr_car,fr_mine};
        getFragmentManager().beginTransaction().add(R.id.fl_main, fragments[0]).commit();

        //初始时，按钮1选中
        buttons[0].setSelected(true);




    }
//
    private void initData() {

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Toast.makeText(MainActivity.this, checkedId+"", Toast.LENGTH_SHORT).show();

                switch (checkedId) {
                    case R.id.rb_home:
                        newIndex=0;
                        changeFragment( newIndex);
                        break;
                    case R.id.rb_fabu:
                        showAdd();
                        break;
                    case R.id.rb_cat:

                        if(sp.getString("username",null)==null){
                            showDialog();

                            return;
                        }

                        newIndex=2;
                        changeFragment( newIndex);

                        break;
                    case R.id.rb_old:
                        page=1;
                        newIndex=1;
                        orderFlag=0;
                        changeFragment( newIndex);


                        break;
                    case R.id.rb_mine:
                        newIndex=3;
                        changeFragment( newIndex);

                        break;
                }


            }
        });

        lv_sild.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,menuData));
        //设置点击事件
        lv_sild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lv_sild.setAdapter(new SimpleAdapter(MainActivity.this, mapList, R.layout.layout, keys, id));
    }

    private void showAdd() {


//        PopupWindow pop=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
//        pop.setTouchable(true);//设置可点击
//        pop.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//        //背景必须设置
//////
//        pop.setBackgroundDrawable(getResources().getDrawable(R.drawable.ground));
//        pop.showAsDropDown(view,-400,400);
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        final Dialog dialog=builder.create();
        View view=View.inflate(this,R.layout.layout_add_item,null);
        RadioGroup rg=(RadioGroup)view.findViewById(R.id.rg_addPro);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_fabupro:
                        Intent intent =new Intent(MainActivity.this,AddProActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rb_paimaipro:
                        Intent intent2 =new Intent(MainActivity.this,UploadAuctMainActivity.class);
                        startActivity(intent2);
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.show();
//
//
//
    }
//
    //切换模块
    public void changeFragment(int newIndex) {

        FragmentTransaction transaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if(newIndex!=oldIndex){

            transaction=getFragmentManager().beginTransaction();

            transaction.hide(fragments[oldIndex]);//隐藏当前显示项


            //如果选中项没有加过，则添加
            if(!fragments[newIndex].isAdded()){
                //添加fragment
                transaction.add(R.id.fl_main,fragments[newIndex]);
            }
            //显示当前选择项
            transaction.show(fragments[newIndex]).commit();
        }
        //之前选中的项，取消选中
        buttons[oldIndex].setSelected(false);
        //当前选择项，按钮被选中
        buttons[newIndex].setSelected(true);
        buttons[oldIndex].setBackgroundColor(Color.WHITE);
        //当前选择项变为选中项
        oldIndex=newIndex;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intentMine= getIntent();
        int direct= intentMine.getIntExtra("direct",0);
        if(direct==1){
            Log.i("MainActivity","direct"+direct);

            changeFragment(0);
            ((RadioButton) findViewById(R.id.rb_home)).setChecked(true);
            return ;

        }else if(direct==2){
            Log.i("MainActivity","direct"+direct);

            changeFragment(2);
            ((RadioButton) findViewById(R.id.rb_cat)).setChecked(true);
            return;

        }else if(direct==3){
            Log.i("MainActivity","direct"+direct);

            changeFragment(1);
            ((RadioButton) findViewById(R.id.rb_old)).setChecked(true);
            return;

        }else if(direct==4){
            Log.i("MainActivity","direct"+direct);

            changeFragment(1);
            ((RadioButton) findViewById(R.id.rb_old)).setChecked(true);
            return;

        }else if(direct==5){
            changeFragment(3);
            ((RadioButton) findViewById(R.id.rb_mine)).setChecked(true);
            return;


        }


    }
    private void showDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("亲！你没有登录账号，请登录？");
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(MainActivity.this,LoginOther.class);
                startActivity(intent);


            }
        });
        builder.setNegativeButton("注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        builder.show();
    }
    }




