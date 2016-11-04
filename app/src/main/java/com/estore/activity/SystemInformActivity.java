package com.estore.activity;
/*
系统通知页面
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawuyang on 2016-10-28.
 */
public class SystemInformActivity extends Activity {
    private ImageView informReturn;
    private ListView lv;
   final List<Message> messagelist=new ArrayList<Message>();
    private BaseAdapter myAdapter=new Myaddapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_inform);
        initView();
        initEvent();
        myAdapter.notifyDataSetChanged();
        getMessageInfo();
    }


    private void initView() {
        informReturn = ((ImageView) findViewById(R.id.iv_informreturn));
        lv=((ListView) findViewById(R.id.lv));

    }
    private void initEvent() {
        informReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private class Myaddapter extends BaseAdapter {

        private TextView title;
        private TextView content;
        private TextView time;

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
            title = ((TextView) view.findViewById(R.id.tv_title));
            content = ((TextView) view.findViewById(R.id.tv_content));
            time = ((TextView) view.findViewById(R.id.tv_time));
            Message message=messagelist.get(position);

            Log.i("cc","message======"+message);
            title.setText(message.getTitle());
            content.setText(message.getContent());
            time.setText(message.getTime());

            return view;
        }
    }
    private void getMessageInfo() {
        final String url= HttpUrlUtils.HTTP_URL+"/findallmessageservlet";
        Log.i("cc", "url: "+url);
        RequestParams request=new RequestParams(url);
        x.http().get(request, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                Log.i("SystemInform=======","result"+result);
                Gson gson=new Gson();
                List<Message> mm=gson.fromJson(result,new TypeToken<List<Message>>(){}.getType());
                messagelist.addAll(mm);
                Log.i("cc","messageList========"+messagelist);
                if(myAdapter==null){
                    myAdapter=new Myaddapter();
                }else {
                 myAdapter.notifyDataSetChanged();
                }
                lv.setAdapter(myAdapter);

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
