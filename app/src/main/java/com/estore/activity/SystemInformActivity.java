package com.estore.activity;
/*
系统通知页面
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Message;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by mawuyang on 2016-10-28.
 */
public class SystemInformActivity extends Activity {
    private TextView tv_title;
    private TextView tv_content;
    private ListView lv;
    final List<Message> messagelist=new ArrayList<Message>();
    private BaseAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_inform);

        tv_title = ((TextView) findViewById(R.id.tv_title));
        tv_content = ((TextView) findViewById(R.id.tv_content));

//        TextView tv = new TextView(this);
//        tv.setText("用户自定义打开的Activity");
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null){
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            if(tv_title!=null){
            tv_title.setText("Title : " + title);}
            if(tv_content!=null){
            tv_content.setText("Content : " + content);}}
        }
//        addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        initView();
        initDate();
        initEvent();
        getMessageInfo();
    }


    private void initDate() {
    }

    private void initView() {

    }
    private void initEvent() {

    }
    private class Myaddapter extends BaseAdapter {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_time;

        @Override
        public int getCount() {
            return messagelist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view=View.inflate(SystemInformActivity.this,R.layout.layout_inform_item,null);
            tv_title = ((TextView) view.findViewById(R.id.tv_title));
            tv_content = ((TextView) view.findViewById(R.id.tv_content));
            tv_time = ((TextView) view.findViewById(R.id.tv_time));
             Message  mList=  messagelist.get(position);
            tv_title.setText(mList.getTitle());
            //tv_time.setText(mList.getTime());
            tv_content.setText(mList.getContent());

            return view;
        }
    }
    private void getMessageInfo() {
        final String url= HttpUrlUtils.HTTP_URL+"/findallmessageservlet";
        RequestParams request=new RequestParams(url);
        x.http().get(request, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                Log.i("SystemInform=======","result"+result);
                Gson gson=new Gson();
               Message mm=gson.fromJson(result,Message.class);
               // messagelist.addAll(mm.list);
                if(myAdapter==null){
                    myAdapter=new Myaddapter();
                }else {
                    myAdapter.notifyDataSetChanged();
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
        });
    }

}
