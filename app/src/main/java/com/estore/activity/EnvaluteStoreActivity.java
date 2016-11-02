package com.estore.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.xUtilsImageUtils;
import com.estore.pojo.Envalute;
import com.estore.view.LoadListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class EnvaluteStoreActivity extends AppCompatActivity implements LoadListView.ILoadListener {
    private List<Envalute> envaluteList=new ArrayList<>();
    private LoadListView list;
    int page=1;
    public RemarkAdapter remarkAdapter;
    private int userId;
    private ImageView iv_en_store_fanhui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_envalute_store);
        remarkAdapter=new RemarkAdapter();
        iv_en_store_fanhui=(ImageView)findViewById(R.id.iv_en_store_fanhui);
        Intent intent=getIntent();
        userId= intent.getIntExtra("userid",-1);
        Log.e("@@@@@","userId"+userId);
        list = ((LoadListView) findViewById(R.id.lv_envalute));
        list.setInterface(this);
        iv_en_store_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getEnvaluteInfoByNet(userId);


    }

    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取更多数据

//                //更新listview显示；
//                showListView(apk_list);
//                //通知listview加载完毕
                getEnvaluteInfoByNet(userId);
                list.loadComplete();
            }
        }, 2000);

    }

    public class RemarkAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return envaluteList.size();
        }

        @Override
        public Object getItem(int position) {
            return envaluteList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= View.inflate(EnvaluteStoreActivity.this,R.layout.pro_envalute_item,null);
            ImageView ivuserphoto=(ImageView) view.findViewById(R.id.iv_evt_photo);
            TextView tv_username=(TextView) view.findViewById(R.id.tv_evt_usernick);
            TextView tv_evcontent=(TextView)view.findViewById(R.id.tv_evcontent);
            TextView tv_evttime=(TextView) view.findViewById(R.id.tv_evttime);
            ImageView  iv_evtimg=(ImageView) view.findViewById(R.id. iv_evtimg);

            Envalute envalute=envaluteList.get(position);
            Log.e("@@@@@","评价"+envalute.toString());
            xUtilsImageUtils.display(ivuserphoto, HttpUrlUtils.HTTP_URL+envalute.getUser().getUserPhoto(),true);
            xUtilsImageUtils.display(iv_evtimg,HttpUrlUtils.HTTP_URL+envalute.getEvt_imgurl());
            tv_username.setText(envalute.getUser().getNickname());
            tv_evttime.setText(envalute.getEvt_time());
            tv_evcontent.setText(envalute.getEvt_msg());
            return view;
        }
    }
    public List<Envalute> getEnvaluteInfoByNet(int userId){

        RequestParams rp=new RequestParams(HttpUrlUtils.HTTP_URL+"queryEnvaluteServlet");
        rp.addBodyParameter("page",page+"");
        rp.addBodyParameter("userId",userId+"");
        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("@@@@@","result"+result);
                page++;
                Gson gson=new Gson();

                //envaluteList.clear();
                List<Envalute>  en= gson.fromJson(result,new TypeToken<List<Envalute>>(){}.getType());
                Log.e("@@@@@","en"+en.toString());
                envaluteList.addAll(en);
                Log.e("@@@@@","envaluteList"+envaluteList.toString());
             
                if(envaluteList.size()==0){
                    Log.e("@@@@@","envaluteList==null");
                    remarkAdapter=new RemarkAdapter();

                }else {
                    Log.e("@@@@@","envaluteList!=null");
                    remarkAdapter.notifyDataSetChanged();
                }
                list.setAdapter(remarkAdapter);

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

        return envaluteList;
    }
}
