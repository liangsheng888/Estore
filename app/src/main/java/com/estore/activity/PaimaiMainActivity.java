package com.estore.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.fragment.PaiMai12Fragment;
import com.estore.fragment.PaiMai16Fragment;
import com.estore.fragment.PaiMai20Fragment;
import com.estore.fragment.PaiMai8Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaimaiMainActivity extends AppCompatActivity implements View.OnClickListener {
    List<Fragment> lists = new ArrayList<Fragment>();

    private ViewPager paimai_fragment_viewpager;
    private TextView tv_paimai_hande1;
    private TextView tv_paimai_hande2;
    private TextView tv_paimai_hande3;
    private TextView tv_paimai_hande4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_paimai_main);
        initView();
        initEven();
        ininData();
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
    }

    private void initEven() {
        tv_paimai_hande1.setOnClickListener(this);
        tv_paimai_hande2.setOnClickListener(this);
        tv_paimai_hande3.setOnClickListener(this);
        tv_paimai_hande4.setOnClickListener(this);
    }

    private void ininData() {
        lists.add(new PaiMai8Fragment());
        lists.add(new PaiMai12Fragment());
        lists.add(new PaiMai16Fragment());
        lists.add(new PaiMai20Fragment());
        Date date = new Date(System.currentTimeMillis());
        String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        System.out.println(timeStr);
        Integer time = Integer.valueOf(timeStr.substring(8, 10));
        System.out.println("p拍卖时间time" + time);
        if (time < 12) {
            paimai_fragment_viewpager.setCurrentItem(0);


        } else if (time > 12) {
            System.out.println("进入12点场");
            paimai_fragment_viewpager.setCurrentItem(3);
        } else if (time > 16) {
            paimai_fragment_viewpager.setCurrentItem(2);
        } else if (time > 20) {
            paimai_fragment_viewpager.setCurrentItem(3);
        }

//        FragmentPagerAdapter(getSupportFragmentManager())

        paimai_fragment_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_paimai_hande1.setBackgroundColor(Color.RED);
                        tv_paimai_hande2.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande3.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande4.setBackgroundColor(Color.parseColor("#4f965c"));

                        break;
                    case 1:
                        tv_paimai_hande2.setBackgroundColor(Color.RED);
                        tv_paimai_hande1.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande3.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande4.setBackgroundColor(Color.parseColor("#4f965c"));

                        break;
                    case 2:
                        tv_paimai_hande3.setBackgroundColor(Color.RED);
                        tv_paimai_hande2.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande1.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande4.setBackgroundColor(Color.parseColor("#4f965c"));

                        break;
                    case 3:
                        tv_paimai_hande4.setBackgroundColor(Color.RED);
                        tv_paimai_hande2.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande3.setBackgroundColor(Color.parseColor("#4f965c"));
                        tv_paimai_hande1.setBackgroundColor(Color.parseColor("#4f965c"));

                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        paimai_fragment_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return lists.size();
            }

            @Override
            public Fragment getItem(int position) {
                return lists.get(position);

            }

        });

    }

    private void initView() {
        paimai_fragment_viewpager = ((ViewPager) findViewById(R.id.paimai_fragment_viewpager));
        tv_paimai_hande1 = ((TextView) findViewById(R.id.tv_paimai_hande1));
        tv_paimai_hande2 = ((TextView) findViewById(R.id.tv_paimai_hande2));
        tv_paimai_hande3 = ((TextView) findViewById(R.id.tv_paimai_hande3));
        tv_paimai_hande4 = ((TextView) findViewById(R.id.tv_paimai_hande4));
        tv_paimai_hande1.setBackgroundColor(Color.RED);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_paimai_hande1:
                paimai_fragment_viewpager.setCurrentItem(0);
                tv_paimai_hande1.setBackgroundColor(Color.RED);
                tv_paimai_hande2.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande3.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande4.setBackgroundColor(Color.parseColor("#4f965c"));


                break;
            case R.id.tv_paimai_hande2:
                paimai_fragment_viewpager.setCurrentItem(1);
                tv_paimai_hande2.setBackgroundColor(Color.RED);
                tv_paimai_hande1.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande3.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande4.setBackgroundColor(Color.parseColor("#4f965c"));
                break;
            case R.id.tv_paimai_hande3:
                paimai_fragment_viewpager.setCurrentItem(2);
                tv_paimai_hande3.setBackgroundColor(Color.RED);
                tv_paimai_hande2.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande1.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande4.setBackgroundColor(Color.parseColor("#4f965c"));
                break;
            case R.id.tv_paimai_hande4:
                paimai_fragment_viewpager.setCurrentItem(3);
                tv_paimai_hande4.setBackgroundColor(Color.RED);
                tv_paimai_hande2.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande3.setBackgroundColor(Color.parseColor("#4f965c"));
                tv_paimai_hande1.setBackgroundColor(Color.parseColor("#4f965c"));
                break;
        }
    }
}
