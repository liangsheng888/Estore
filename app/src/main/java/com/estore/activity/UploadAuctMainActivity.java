package com.estore.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.estore.httputils.HttpUrlUtils;

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

public class UploadAuctMainActivity extends AppCompatActivity {


    private NumberPicker np_zuct_picker;
    int minprice = 1;
    int maxprice = 1;
     private  static List<Bitmap> imglist=new ArrayList<>();
    //头像的存储完整路径
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + getPhotoFileName());

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private ImageView iv_auct_modle_phote1;
    private EditText et_upload_aucttitle;
    private EditText et_auct_miaoshu;
    private Spinner sp_city;
    private EditText et_auct_price;
    private NumberPicker np_auct_bidprice;
    private Spinner sp_auct_type;
    private GridView gv_img;
    Integer bidprice;
    String auct_type;
    String address;
    //    np_zuct_picker
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_upload_main);
        initview();
        initdata();

    }


    private void initview() {
        np_zuct_picker = ((NumberPicker) findViewById(R.id.np_auct_bidprice));
        //iv_auct_modle_phote1 = ((ImageView) findViewById(R.id.iv_auct_modle_phote1));
        gv_img = ((GridView) findViewById(R.id.gv_img));

    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        System.out.println("============" + UUID.randomUUID());
        return sdf.format(date) + "_" + UUID.randomUUID() + ".png";
    }

    private void initdata() {
        //设置numberpack的属性
        np_zuct_picker.setMinValue(1 / 10);
        np_zuct_picker.setMaxValue(10);
//        np_zuct_picker.setDisplayedValues(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "20", "50", "100"});
        np_zuct_picker.setValue(minprice);
        np_zuct_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minprice = newVal;

            }
        });

    }
    public class PhoteAdapter extends BaseAdapter {
        @Override
        public int getCount () {
            return imglist.size();
        }

        @Override
        public Object getItem ( int position){
            return null;
        }

        @Override
        public long getItemId ( int position){
            return 0;
        }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent){
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageBitmap(imglist.get(position));

            return imageView;
        }
    }
    public void simpleDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择上传方式");
        //builder.setMessage("你确定？");

        builder.setIcon(R.drawable.emoji_81);
        EditText tv = new EditText(UploadAuctMainActivity.this);
        builder.setItems(new String[]{"拍照上传", "上传本地"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UploadAuctMainActivity.this, "你点击了第" + which + "个按钮：", Toast.LENGTH_SHORT).show();


                if (which == 0) {
                    getPicFromCamera();
                } else {
                    getPicFromPhoto();
                }

            }

        });

        builder.show();

    }

    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        System.out.println("getPicFromCamera===========" + file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);

    }


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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    }

    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        System.out.println("CAMERA_REQUEST" + file.getAbsolutePath());
                        if (file.exists()) {
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
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        imglist.add(photo);
                        gv_img.setAdapter(new PhoteAdapter());

                        saveImageToGallery(getApplication(), photo);//保存bitmap到本地

//                        iv_auct_modle_phote1.setImageBitmap(photo);


                    }
                }
                break;
            default:
                break;
        }

    }

    public void uploadauct(View view) {
        sendMsgandImg();//图片和其他输入信息

    }

    private void sendMsgandImg() {
        et_upload_aucttitle = ((EditText) findViewById(R.id.et_upload_aucttitle));
        et_auct_miaoshu = ((EditText) findViewById(R.id.et_auct_miaoshu));
        sp_city = ((Spinner) findViewById(R.id.sp_city));
        et_auct_price = ((EditText) findViewById(R.id.et_auct_price));
        np_auct_bidprice = ((NumberPicker) findViewById(R.id.np_auct_bidprice));
        sp_auct_type = ((Spinner) findViewById(R.id.sp_auct_type));
        //获得spinner中的item
        address = sp_city.getSelectedItem().toString();
        auct_type=sp_auct_type.getSelectedItem().toString();
        bidprice=np_auct_bidprice.getValue();



         String proName=et_upload_aucttitle.getText().toString().trim();
         String proDescription= et_auct_miaoshu.getText().toString().trim();
         String price= et_auct_price.getText().toString().trim();


        RequestParams params = new RequestParams(HttpUrlUtils.HTTP_URL +"addpaimaibycilent");//upload 是你要访问的servlet


        try {
            params.addBodyParameter("proName", URLEncoder.encode(proName,"utf-8"));
            params.addBodyParameter("proDescription",URLEncoder.encode(proDescription,"utf-8"));
            params.addBodyParameter("category",URLEncoder.encode(auct_type,"utf-8"));
            params.addBodyParameter("address",URLEncoder.encode(address,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        params.addBodyParameter("proPrice", price);
//        params.addBodyParameter("category",auct_type);
        params.addBodyParameter("bidprice", bidprice+"");
        params.addBodyParameter("file", file);
        System.out.println("-----file------"+file+"/n"+"----proName-------"+proName
                +"/n"+"--------proDescription-------"+proDescription+"/n"
                +"---------proDescription---------"+proDescription
                +"/n"+"---------address-------"+address
                +"/n"+"--------price---------"+price
                +"/n"+"---------auct_type-------"+auct_type
                +"/n"+"--------bidprice---------"+bidprice);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=--------result----------------"+result);
            }





            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex+"---------------------------------------------");
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



