package com.estore.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.httputils.GetUserIdByNet;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.User;
import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;

/***
 * 发布商品
 */
public class AddProActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CAMERA_CODE =5 ;
    private static final int REQUEST_PREVIEW_CODE =6 ;
    private TextView tv_choice_photo;
    private TextView tv_school;
    private TextView tv_youfei;//邮费
    private Button btn_add_pro;
    private ArrayList<String> imagePaths = new ArrayList<>();
    //GridAdapter gridAdapter;


    private EditText et_proName;
    private EditText et_proNum;
    private EditText et_proPrice;
    private EditText et_proDescription;
    private GridView gv_showphoto;
    private RadioGroup rgIsSchool;
    private CheckBox cb_baoyou;
    private RelativeLayout rl_fenlei;//分类
    private TextView tv_fenlei;//分类名称
    private RelativeLayout rl_price;//价格

    private Spinner sp_proAddress;
    private ImageView iv_add_pro_last;
    //private ImageView iv_subOne;
    private String proAddress;//商品地址
    private int pnum;//商品数量
    private AlertDialog.Builder builder;
    private Dialog dialog = null;
    private String[] schoolData={"北京大学","清华大学","郑州大学","苏州大学","南阳师院","洛阳师院","周口师院"};
    private String[] proNameData={"手机","电脑","笔记本","其它"};
    private int flag=0;//0代表同城，1代表xuexiao
    private List<Bitmap> imageLists=new ArrayList<Bitmap>();
    private List<File> imageFileLists=new ArrayList<File>();
    private int youfei=0;
   // private MyAdapterShowPhoto adapter;
    MultiPickResultView recyclerView;
    //头像的存储完整路径
    private  File file =null;

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private static final String TAG = "AddProActivity";
    private SharedPreferences sp;
    private User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro);
        sp=getSharedPreferences("User",MODE_APPEND);
        ;
        user.setUserId(sp.getInt("userId",0));

        initView();
        initData();
    }

    private void initData() {
        btn_add_pro.setOnClickListener(this);
        //tv_choice_photo.setOnClickListener(this);
        rl_fenlei.setOnClickListener(this);
        //fanhui
        iv_add_pro_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cb_baoyou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if(!isChecked){
                      AlertDialog.Builder builder=new AlertDialog.Builder(AddProActivity.this);
                      builder.setTitle("请输入邮费");
                      final EditText et_youfei=new EditText(AddProActivity.this);
                      builder.setView(et_youfei);
                      builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              tv_youfei.setText("邮费  ￥"+et_youfei.getText().toString().trim());
                              youfei=Integer.parseInt(et_youfei.getText().toString().trim());
                              tv_youfei.setVisibility(View.VISIBLE);

                              dialog.dismiss();

                          }
                      });
                      builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              tv_youfei.setVisibility(View.GONE);
                              cb_baoyou.setChecked(true);

                          }
                      });
                      builder.show();

                  }else {
                      tv_youfei.setText("");
                      tv_youfei.setVisibility(View.GONE);


                  }
            }
        });
        //为 spinner 设置点击事件sp_proAddress

        sp_proAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                proAddress = (String) parent.getSelectedItem();
                Log.e(TAG, "你选择了" + proAddress);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rgIsSchool.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_city:
                        flag=0;
                        tv_school.setText("");
                        tv_school.setVisibility(View.GONE);
                        break;

                    case R.id.rb_shcool:
                        AlertDialog.Builder builder=new AlertDialog.Builder(AddProActivity.this);
                        builder.setTitle("选择学校");
                        builder.setSingleChoiceItems(schoolData, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_school.setVisibility(View.VISIBLE);
                                tv_school.setText(schoolData[which]);
                                flag=1;
                                dialog.dismiss();

                            }
                        });
                        builder.show();
                        break;
                }
            }
        });
    }

    private void initView() {
       // tv_choice_photo = (TextView) findViewById(R.id.tv_choicephoto);
        recyclerView = (MultiPickResultView) findViewById(R.id.recycler_view);
        recyclerView.init(this,MultiPickResultView.ACTION_SELECT,null);
        //onActivityResult里一行代码回调
        

        btn_add_pro = (Button) findViewById(R.id.btn_add_pro);
        tv_fenlei = (TextView) findViewById(R.id.tv_fenlei);
        et_proName = (EditText) findViewById(R.id.et_proName);
        et_proNum = (EditText) findViewById(R.id.et_pronum);
        et_proPrice = (EditText) findViewById(R.id.et_proPrice);
        et_proDescription = (EditText) findViewById(R.id.et_proDescription);
        iv_add_pro_last = (ImageView) findViewById(R.id.iv_add_pro_last);
//        iv_subOne = (ImageView) findViewById(R.id.iv_subOne);
          sp_proAddress = (Spinner) findViewById(R.id.sp_proAddress);//        sp_proCategory=(Spinner)findViewById(R.id.sp_category);
         rgIsSchool=(RadioGroup)findViewById(R.id.rgIsSchool);
        cb_baoyou=(CheckBox)findViewById(R.id.cb_baoyou);
        tv_school=(TextView)findViewById(R.id.tv_school);
        gv_showphoto=(GridView) this.findViewById(R.id.gv_showPhoto);

        rl_fenlei=(RelativeLayout) findViewById(R.id.rl_fenlei);
        tv_youfei=(TextView)findViewById(R.id.tv_youfei);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*case R.id.tv_choicephoto:
                showChoicePhoto();
                break;*/
            case R.id.btn_add_pro://添加商品
                Log.e(TAG, "添加商品");
                addProducts();
                break;
          case R.id.rl_fenlei://分类
              AlertDialog.Builder builder=new AlertDialog.Builder(AddProActivity.this);
              builder.setTitle("宝贝类别");
              builder.setSingleChoiceItems(proNameData, 0, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      tv_fenlei.setText(proNameData[which]);
                      dialog.dismiss();

                  }
              });
              builder.show();
              break;


        }
    }
    private void showDialog() {
        builder=new AlertDialog.Builder(AddProActivity.this);
        dialog=builder.create();
        builder.setTitle("亲！你没有登录账号，请登录？");
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(AddProActivity.this,LoginOther.class);
                startActivity(intent);


            }
        });
        builder.setNegativeButton("注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(AddProActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        dialog.show();
    }

    private void addProducts() {
        SharedPreferences sp=getSharedPreferences("User",Context.MODE_APPEND);
        String username=sp.getString("username",null)  ;
        if(TextUtils.isEmpty(username)){
            showDialog() ;
            return;
        }


        //数据上传服务器

        String proName = et_proName.getText().toString().trim();
        Log.e("AddProActivity",proName);
        String proNum = et_proNum.getText().toString().trim();
        Log.e("AddProActivity",proNum+"");
        String proPrice = et_proPrice.getText().toString().trim();
        Log.e("AddProActivity",proPrice);
        String proDescription = et_proDescription.getText().toString().trim();
        Log.e("AddProActivity",proDescription);
        String address=proAddress;
        Log.e("AddProActivity",address);
        String schoolname=tv_school.getText().toString().trim();
        if(TextUtils.isEmpty(proName)){
            Toast.makeText(AddProActivity.this,"亲，宝贝名字不能忘额！",Toast.LENGTH_LONG).show();
           return;
        }
        if(TextUtils.isEmpty(proNum)){
            Toast.makeText(AddProActivity.this,"亲，宝贝数量不能忘额！",Toast.LENGTH_LONG).show();

            return;
        }
        if(TextUtils.isEmpty(proPrice)){
            Toast.makeText(AddProActivity.this,"亲，宝贝价格不能忘额！",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(proDescription)){
            Toast.makeText(AddProActivity.this,"亲，宝贝描述不能忘额！",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(address)){
            Toast.makeText(AddProActivity.this,"亲，宝贝地址不能忘额！",Toast.LENGTH_LONG).show();
            return;
        }
        if(imageFileLists.size()<0){
            Toast.makeText(AddProActivity.this,"亲，宝贝照不能忘额！",Toast.LENGTH_LONG).show();
            return;
        }

        RequestParams params = new RequestParams(HttpUrlUtils.HTTP_URL+"addProductsByCilent");
       /* try {
            params = new RequestParams(HttpUrlUtils.HTTP_URL+"addProductsByCilent?file="+file+"&email=978188219@qq.com&proName="
                    + URLEncoder.encode(proName,"utf-8")+"&proNum="+proNum+"&proPrice="+proPrice+"&proCategory="+URLEncoder.encode(proCategory,"utf-8")+"&proDescription="+URLEncoder.encode(proDescription,"utf-8")+"&address="+ URLEncoder.encode(address,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        try {
        //params.setMultipart(true);
            for (int i=0;i<imageFileLists.size();i++){
                params.addBodyParameter("file"+i,imageFileLists.get(i));
                Log.e("file",imageFileLists.get(i)+"size"+imageFileLists.size());
            }

           params.addBodyParameter("proName",URLEncoder.encode(proName,"utf-8"));
           params.addBodyParameter("proNum",proNum);
            params.addBodyParameter("youfei",youfei+"");
           params.addBodyParameter("proPrice",proPrice);
           params.addBodyParameter("category",URLEncoder.encode(tv_fenlei.getText().toString(),"utf-8"));
           params.addBodyParameter("proDescription",URLEncoder.encode(proDescription,"utf-8"));
            params.addBodyParameter("address",URLEncoder.encode(address,"utf-8"));
            params.addBodyParameter("prowhere",flag+"");
            params.addBodyParameter("schoolname",URLEncoder.encode(schoolname,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //
        params.addBodyParameter("userid", user.getUserId()+"");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
            if(result.equals("true")){
                Toast.makeText(AddProActivity.this,"发布商品成功",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new  AlertDialog.Builder(AddProActivity.this);
                builder.setTitle("继续添加？");
                builder.setPositiveButton("查看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转到我的发布
                        Intent intent = new Intent(AddProActivity.this, PublishActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清空数据
                        et_proName.setText("");
                        et_proPrice.setText("");
                        et_proNum.setText("");
                        cb_baoyou.setChecked(true);
                        ((RadioButton)findViewById(R.id.rb_city)).setChecked(true);
                     /*   imageLists.clear();
                        adapter.notifyDataSetChanged();*/
                 }
                });
                builder.show();


            }else{
                Toast.makeText(AddProActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(AddProActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recyclerView.onActivityResult(requestCode,resultCode,data);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                for (String str:
                        photos) {
                    imageFileLists.add(new File(str));

                }
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(AddProActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(AddProActivity.this, "取消选择", Toast.LENGTH_LONG).show();

            }

        });
    }}

