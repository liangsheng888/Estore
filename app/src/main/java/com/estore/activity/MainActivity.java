package com.estore.activity;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;


import com.estore.fragment.FragmentCar;
import com.estore.fragment.FragmentHome;
import com.estore.fragment.MyHomePageFragment;
import com.estore.fragment.EhFragment;
import com.estore.pojo.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private ListView lv;
   /* public List<Product.Products> getList() {
        return list;
    }*/
    public LinkedList<Product.Products> getList() {
        return list;
    }

    private final LinkedList<Product.Products> list =new LinkedList<Product.Products>();
    //private final List<Product.Products> list =new ArrayList<Product.Products>();
    private RadioGroup rg;
    private ListView lv_sild;//侧滑菜单
    private Fragment fr_home;
    private Fragment fr_old;
    private Fragment fr_car;
    private Fragment fr_add;
    private Fragment fr_mine;


    private Fragment fr_used;
    private Fragment fr_now;
    private boolean firstIn = true;//

    String[] keys = {"img", "name"};
    private String menuData[] = {"新闻", "订阅", "图片", "视频", "跟帖", "投票"};
    private int[] id = {R.id.iv_name, R.id.tv_name};
    private int[] imgs = {R.drawable.add_red, R.drawable.car_red, R.drawable.car_red, R.drawable.car_red, R.drawable.car_red, R.drawable.car_red};
    private List<Map<String, Object>> mapList;//策划菜单数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (int i = 0; i < imgs.length; i++) {
            map = new HashMap<String, Object>();
            map.put("img", imgs[i]);
            map.put("name", menuData[i]);
            mapList.add(map);
        }
        initView();
        initData();


        //拿到服务器的数据
        Intent intent = this.getIntent();
        String json = intent.getStringExtra("json");
        System.out.println(json);
        Log.e("MainActivity", json);
        //将json转化为Product对象
        Gson gson = new Gson();
        Product pro = gson.fromJson(json, Product.class);
        Log.e("MainActivity", pro.toString());
        list.addAll(pro.list);

        Log.e("MainActivity", list.toString());
    }

    private void initView() {
        rg = ((RadioGroup) this.findViewById(R.id.rg_bottom_driection));
        lv_sild = (ListView) this.findViewById(R.id.lv_slidMenu);
    }

    private void initData() {
        //默认是首页
        changeFragment(null);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Toast.makeText(MainActivity.this, checkedId+"", Toast.LENGTH_SHORT).show();

                switch (checkedId) {
                    case R.id.rb_home:

                        // Toast.makeText(MainActivity.this, (checkedId == R.id.rb_home) + "", Toast.LENGTH_SHORT).show();
                        if (fr_home == null)
                            fr_home = new FragmentHome();
                            fr_now = fr_home;
                        changeFragment(fr_now);
                        break;
                    case R.id.rb_fabu:
                        showAdd();
//                        if (fr_add == null)
//                            fr_add = new FragmentAdd();
//                        fr_now = fr_add;
                        break;
                    case R.id.rb_cat:
                        if (fr_car == null)
                            fr_car = new FragmentCar();
                        fr_now = fr_car;
                        changeFragment(fr_now);
                        break;
                    case R.id.rb_old:
                        if (fr_add == null)
                            fr_add = new EhFragment();
                        fr_now = fr_add;
                        changeFragment(fr_now);
                        break;
                    case R.id.rb_mine:
                        if (fr_mine == null)
                            fr_mine = new MyHomePageFragment();
                        fr_now = fr_mine;
                        changeFragment(fr_now);
                        break;
                }





            }
        });                firstIn = false;


        //为侧滑菜单添加适配器
        //lv_sild.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,menuData));
        //设置点击事件
        lv_sild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lv_sild.setAdapter(new SimpleAdapter(MainActivity.this, mapList, R.layout.layout, keys, id));
    }
   //fabu

    private void showAdd() {


//        PopupWindow pop=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
//        pop.setTouchable(true);//设置可点击
//        pop.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//        //背景必须设置
//
//        pop.setBackgroundDrawable(getResources().getDrawable(R.drawable.ground));
//        pop.showAsDropDown(view,-400,400);
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
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
            }
        });
        builder.setView(view);
        builder.show();



    }

    //切换模块
    private void changeFragment(Fragment fg) {
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.e("!firstIn",!firstIn+"");
        if (fg == null) {
            fr_home = fg=new FragmentHome();
        }

        if (fr_used != null && !fr_used.isHidden() && fr_used.isAdded() && (!firstIn )) {
            //由于home界面默认显示，再点击还应该显示

            ft.hide(fr_used);
        }

        if (fg != null && fg.isHidden() && fg.isAdded()) {
            ft.show(fg);
        }
        if(fg!=null&&!fg.isAdded()){
            ft.add(R.id.fl_main, fg);
            ft.addToBackStack(null);//把碎片添加到返回栈中

        }
        fr_used = fg;

        //ft.replace(R.id.fl_main, fg);
        ft.commit();
    }


}


