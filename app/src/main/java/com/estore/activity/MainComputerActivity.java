package com.estore.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.jude.rollviewpager.RollPagerView;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/19.
 */
public class MainComputerActivity extends AppCompatActivity{

    private List<Product.Products> productsList=new ArrayList<>();
    private ListView lv_jingpin;
    private MyAdapter adapter;
    List<ImageView> images = null;
    private String[] imgurls;
    private GridView gv_jingpin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_computer);
        lv_jingpin = (ListView) findViewById(R.id.lv_computer);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Log.i("cc",bundle+"");
        productsList= (List<Product.Products>) bundle.getSerializable("proList");
        if(adapter==null){
            adapter=new MyAdapter();
        }else{
            adapter.notifyDataSetChanged();
        }
        lv_jingpin.setAdapter(adapter);

        Log.i("cc",productsList+"");


        lv_jingpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product.Products pp = productsList.get(position);
                Intent intent = new Intent(getApplicationContext(), ProductInfoActivity.class);
                // intent.putExtra("pp",pp);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pp", pp);
                intent.putExtras(bundle);
                Log.e("FragmentHome", pp.toString());
                startActivity(intent);
            }
        });

    }



    public class MyAdapter extends BaseAdapter {
        private ImageView iv;
        @Override
        public int getCount() {
            return productsList.size();
        }

        @Override
        public Object getItem(int position) {
            return productsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.list_item, null);
            gv_jingpin = (GridView) view.findViewById(R.id.gv_jingpin);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_jin_proname);
            TextView tv_jingpin_desc = (TextView) view.findViewById(R.id.tv_jingpin_desc);
            TextView tv_username = (TextView) view.findViewById(R.id.tv_username);
            TextView tv_estoreprice = (TextView) view.findViewById(R.id.tv_jingpin_price);
            TextView tv_jingpin_address = (TextView) view.findViewById(R.id.tv_jingpin_address);

            Product.Products pp = productsList.get(position);//根据当前位置获得pp
            ImageOptions.Builder io = new ImageOptions.Builder();
            imgurls=pp.imgurl.split("=");//将拿到的图片路径分割成字符串数组
            x.image().bind(iv, HttpUrlUtils.HTTP_URL + imgurls[0]);
            // iv.setImageUrl(HttpUrlUtils.HTTP_URL+ pp.imgurl.trim(), R.drawable.sj, R.drawable.sj);
            Log.e("MainActivity", HttpUrlUtils.HTTP_URL +imgurls[0]);
            tv_name.setText(pp.name);
            tv_estoreprice.setText("￥"+pp.estoreprice);
            tv_jingpin_desc.setText(pp.description);
            tv_jingpin_address.setText(pp.proaddress);

            gv_jingpin.setBackground(new BitmapDrawable());//
            // viewHolder.gv_jingpin.setLayoutParams(new LinearLayout.LayoutParams(200,400));
            gv_jingpin.setAdapter(new Adapter(imgurls));
            view.setBackgroundColor(Color.WHITE);

            gv_jingpin.setClickable(false);
            gv_jingpin.setPressed(false);
            gv_jingpin.setEnabled(false);

            return view;
        }
    }


    public class Adapter extends BaseAdapter{
        private String[] imgurls;
        public Adapter(String[] imgurls){
            this.imgurls=imgurls;
        }


        @Override
        public int getCount() {
            return imgurls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=View.inflate(getApplicationContext(),R.layout.layout_fra_pro_item,null);}
            ImageView iv=(ImageView) convertView.findViewById(R.id.iv_pro);
            x.image().bind(iv,HttpUrlUtils.HTTP_URL+imgurls[position]);
            return convertView;
        }

    }


}
