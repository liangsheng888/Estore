package com.estore.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.fragment.FragmentHome;
import com.estore.httputils.HttpUrlUtils;

import org.w3c.dom.Text;
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

/***
 * 发布商品
 */
public class AddProActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_choice_photo;
    private TextView tv_school;
    private TextView tv_youfei;//邮费
    private Button btn_add_pro;
    private ImageView iv_showPhoto;
    private EditText et_proName;
    private EditText et_proNum;
    private EditText et_proPrice;
    private EditText et_proDescription;
    private GridView gv_showphoto;
    private RadioGroup rgIsSchool;
    private RadioGroup rgIsBaoYou;

    private Spinner sp_proAddress;
    private Spinner sp_proCategory;
    private ImageView iv_addOne;
    private ImageView iv_subOne;
    private String proAddress;//商品地址
    private int pnum;//商品数量
    private AlertDialog.Builder builder;
    private Dialog dialog = null;
    private String proCategory;
    private String[] schoolData={"北京大学","清华大学","郑州大学","苏州大学","南阳师院","洛阳师院","周口师院"};
    private int flag=0;//0代表同城，1代表城市
    private List<Bitmap> imageLists=new ArrayList<Bitmap>();
    private List<File> imageFileLists=new ArrayList<File>();
    private int youfei=0;
    private MyAdapterShowPhoto adapter;
    //头像的存储完整路径
    private  File file =null;

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private static final String TAG = "AddProActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro);
        initView();
        initData();
    }

    private void initData() {
        et_proNum.setText("0");
        btn_add_pro.setOnClickListener(this);
        tv_choice_photo.setOnClickListener(this);

        iv_addOne.setOnClickListener(this);
        iv_subOne.setOnClickListener(this);
        rgIsBaoYou.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_baoyou_yes:

                        tv_youfei.setText("");
                        tv_youfei.setVisibility(View.GONE);

                        break;
                    case R.id.rb_baoyou_no:
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
                                RadioButton radioButton=(RadioButton)findViewById(R.id.rb_baoyou_yes);
                                radioButton.setChecked(true);

                            }
                        });
                        builder.show();
                        break;

                }
            }
        });
        rgIsSchool.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_no:
                        flag=0;
                        tv_school.setText("");
                        break;
                    case R.id.rb_yes:
                        AlertDialog.Builder builder=new AlertDialog.Builder(AddProActivity.this);
                        builder.setTitle("选择学校");
                        builder.setSingleChoiceItems(schoolData, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
        //为 spinner 设置点击事件
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
        sp_proCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                proCategory=(String)parent.getSelectedItem();//得到选择的内容
                Log.e(TAG, "你选择了" + proCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initView() {
        tv_choice_photo = (TextView) findViewById(R.id.tv_choicephoto);
        btn_add_pro = (Button) findViewById(R.id.btn_add_pro);
        //iv_showPhoto = (ImageView) findViewById(R.id.iv_showPhoto);
        et_proName = (EditText) findViewById(R.id.et_proName);
        et_proNum = (EditText) findViewById(R.id.et_pronum);
        et_proPrice = (EditText) findViewById(R.id.et_proPrice);
        et_proDescription = (EditText) findViewById(R.id.et_proDescription);
        iv_addOne = (ImageView) findViewById(R.id.iv_addOne);
        iv_subOne = (ImageView) findViewById(R.id.iv_subOne);
        sp_proAddress = (Spinner) findViewById(R.id.sp_proAddress);
        sp_proCategory=(Spinner)findViewById(R.id.sp_category);
        rgIsSchool=(RadioGroup)findViewById(R.id.rg_isSchool);
        rgIsBaoYou=(RadioGroup)findViewById(R.id.rg_baoyou);
        tv_school=(TextView)findViewById(R.id.tv_school_name);
        gv_showphoto=(GridView) this.findViewById(R.id.gv_showPhoto);
        tv_youfei=(TextView)findViewById(R.id.tv_youfei);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_choicephoto:
                showChoicePhoto();

                break;
            case R.id.btn_add_pro://添加商品
                Log.e(TAG, "添加商品");
                addProducts();
                break;
            case R.id.iv_addOne://商品数量加1
                if(TextUtils.isEmpty(et_proNum.getText().toString().trim())){
                    pnum=0;
                }
                pnum = (Integer.parseInt(et_proNum.getText().toString().trim()) + 1);
                et_proNum.setText(pnum + "");
                break;
            case R.id.iv_subOne://商品数量减1
                if (pnum > 1)
                    pnum = (Integer.parseInt(et_proNum.getText().toString().trim()) - 1);
                et_proNum.setText(pnum + "");
                break;

        }
    }

    private void showChoicePhoto() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("选择打开方式");
        View view = View.inflate(this, R.layout.layout_choice_getphoto, null);


        ((TextView) view.findViewById(R.id.tv_camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "打开相机");
               file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" +
                        getPhotoFileName());

                getPictureByCamera(file);
                dialog.dismiss();

            }
        });
        ((TextView) view.findViewById(R.id.tv_photo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "选择本地照片");
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" +
                        getPhotoFileName());
                getPicFromPhoto();
                dialog.dismiss();


            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void addProducts() {

        //数据上传服务器

        String proName = et_proName.getText().toString();
        Log.e("AddProActivity",proName);
        String proNum = et_proNum.getText().toString().trim();
        Log.e("AddProActivity",proNum+"");
        String proPrice = et_proPrice.getText().toString();
        Log.e("AddProActivity",proPrice);
        String proDescription = et_proDescription.getText().toString();
        Log.e("AddProActivity",proDescription);
        String address=proAddress;
        Log.e("AddProActivity",address);
        String schoolname=tv_school.getText().toString().trim();

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
            }

        params.addBodyParameter("proName",URLEncoder.encode(proName,"utf-8"));
        params.addBodyParameter("proNum",proNum);
            params.addBodyParameter("youfei",youfei+"");
        params.addBodyParameter("proPrice",proPrice);
        params.addBodyParameter("category",URLEncoder.encode(proCategory,"utf-8"));
        params.addBodyParameter("proDescription",URLEncoder.encode(proDescription,"utf-8"));
            params.addBodyParameter("address",URLEncoder.encode(address,"utf-8"));
            params.addBodyParameter("prowhere",flag+"");
            params.addBodyParameter("schooladdress",schoolname);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("email","978188219@qq.com");
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
                        Intent intent = new Intent(AddProActivity.this, SellActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    public void getPictureByCamera(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        System.out.println("getPicFromCamera===========" + file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);

    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        System.out.println("============" + UUID.randomUUID());
        Log.e("UUID:",UUID.randomUUID()+"");
        return sdf.format(date) + "_" + UUID.randomUUID() + ".png";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        System.out.println("CAMERA_REQUEST" + file.getAbsolutePath());
                        if (file.exists()) {
                            //剪切图片
                            photoClip(Uri.fromFile(file));
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());

                }
                break;
            case PHOTO_CLIP:
                //保存图片到本地
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        imageLists.add(photo);//将bitmap添加到集合
                        Log.e("AddProActivty",imageLists.toString());
                        adapter=new MyAdapterShowPhoto();
                        adapter.notifyDataSetChanged();
                        gv_showphoto.setAdapter(adapter);

                        saveImageToGallery(this, photo);//保存bitmap到本地

                        //iv_showPhoto.setImageBitmap(photo);
                    }
                }
                break;

        }
    }

    //对图片进行剪切
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
//        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);


            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        imageFileLists.add(file);
        Log.e("FileUrl:",imageFileLists.toString());
    }

    public class MyAdapterShowPhoto extends BaseAdapter{

        @Override
        public int getCount() {
            return imageLists.size();
        }

        @Override
        public Object getItem(int position) {
            return imageLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            if(convertView!=null){
                view=convertView;
            }else{
                view=  View.inflate(getApplicationContext(),R.layout.addproactivity_addphoto_item,null);
                ImageView iv=(ImageView)view.findViewById(R.id.iv_showphotos);
                iv.setImageBitmap(imageLists.get(position));
            }
            return view;
        }
    }

}
