package com.estore.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.PublishEstoreDetialItemActivity;
import com.estore.activity.R;
import com.estore.activity.myappliction.MyApplication;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.MyPublishActivityBean;
import com.estore.pojo.User;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishEStoreFragment extends Fragment {
    //fragment生命周期
    private TextView tv_btestore;
    private ListView lv_publishest;
    final List<MyPublishActivityBean.ProImag> prolist=new ArrayList<MyPublishActivityBean.ProImag>();
    private BaseAdapter  adapter;
    User user=new User();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication my = (MyApplication)getActivity().getApplication();

        user = my.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getProduct();
        View view=inflater.inflate(R.layout.fragment_publish_estore,null);
        lv_publishest= ((ListView) view.findViewById(R.id.lv_publishest));
        //跳到详细页
        lv_publishest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPublishActivityBean.ProImag estoreimage=prolist.get(position);
                Intent intent=new Intent(getActivity(), PublishEstoreDetialItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("estoreimage",estoreimage);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    //显示
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //adapter=new myAdapter();

    }
    //适配器
    public class myAdapter extends BaseAdapter{
        private ImageView iv_pubestorepic;
        private TextView tv_pubestoreprice;
        private TextView tv_pubestorewhere;
        private TextView tv_pubestorerepresent;
        private TextView tv_pubestorewhere1;

        @Override
        public int getCount() {
            return prolist.size();
        }

        @Override
        public Object getItem(int position) {
            return prolist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("PublishEStoreFragment","getView");
            //得到listview
            View view = View.inflate(getActivity(), R.layout.items_publishestore, null);
            //得到listview的item
            iv_pubestorepic = ((ImageView) view.findViewById(R.id.iv_pubestorepic));
            tv_pubestoreprice = ((TextView) view.findViewById(R.id.tv_pubestoreprice));
            tv_pubestorewhere = ((TextView) view.findViewById(R.id.tv_pubestorewhere));//同城
            tv_pubestorerepresent = ((TextView) view.findViewById(R.id.tv_pubestorerepresent));
            tv_pubestorewhere1 = ((TextView) view.findViewById(R.id.tv_pubestorewhere1));//高校
            MyPublishActivityBean.ProImag  pro=prolist.get(position);
            Log.e("PublishEStoreFragment","getView:"+pro.toString());
            x.image().bind(iv_pubestorepic,HttpUrlUtils.HTTP_URL +pro.imgurl);
            Log.i("TAG",HttpUrlUtils.HTTP_URL +pro.imgurl);
            tv_pubestoreprice.setText(pro.estoreprice+"");
            tv_pubestorerepresent.setText(pro.description+"");
            //判断同城高校
            // Toast.makeText(getActivity(),pro.heighschool+"",Toast.LENGTH_LONG).show();
            if (pro.prowhere==1) {//高校
                tv_pubestorewhere.setText(pro.samecity);
            }
            if(pro.prowhere==0) {//同城高校
                tv_pubestorewhere.setText(pro.samecity);
                tv_pubestorewhere1.setText(pro.heighschool);

            }
            return view;

        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    //重新开始
    @Override
    public void onResume() {
        super.onResume();
    }
    //暂时停止
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
    //请求数据
    private void getProduct() {
        //RequestParams requestParams=new RequestParams("http://10.40.5.18:8080/EStore/getmypublishservlet?email=491830643@qq.com");
        String url = HttpUrlUtils.HTTP_URL + "myAddProductsServlet?user_id="+user.getUserId();//访问网络的url
        RequestParams requestParams = new RequestParams(url);//请求参数url
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("PublishEStoreFragment","result"+result);
                Gson gson=new Gson();
                MyPublishActivityBean  probean=gson.fromJson(result,MyPublishActivityBean.class);
                prolist.addAll(probean.list);
                Log.e("PublishEStoreFragment",prolist.toString());
                if(adapter==null){
                    Log.e("PublishEStoreFragment","adapter=null");
                    adapter=new myAdapter();
                    lv_publishest.setAdapter(adapter);
                }else{
                    Log.e("PublishEStoreFragment","adapter!=null");
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("PublishEStoreFragment","fail"+ex.getMessage());

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
