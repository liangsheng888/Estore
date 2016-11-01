package com.estore.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.estore.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ListViewActivity extends AppCompatActivity {
    private ListView lv_item;
    private String[] data={"张三","李四","赵武","张三","李四","赵武","张三","李四","赵武","666"};
    private int[] id={R.id.iv_name, R.id.tv_name};
    private int[] picture={R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user};
    private List<HashMap<String,Object>> map=new ArrayList<HashMap<String,Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv_item=((ListView) findViewById(R.id.lv_item));
        ImageView view=new ImageView(this);
        view.setImageResource(R.drawable.user);

        lv_item.addHeaderView(view);
        HashMap<String,Object> hashMap=null;
        for(int i=0;i<10;i++) {
            hashMap=new HashMap<String,Object>();
            hashMap.put("picture1", picture[i]);
            hashMap.put("name", data[i]);
            map.add(hashMap);
        }
        String[] key={"picture1","name"};
       // lv_item.setAdapter(new ArrayAdapter<String>(ListViewActivity.this,R.layout.item,R.id.tv_test,data));
       lv_item.setAdapter(new SimpleAdapter(ListViewActivity.this,map,R.layout.layout,key,id));
    }
}
