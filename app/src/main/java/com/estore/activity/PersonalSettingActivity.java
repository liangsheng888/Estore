package com.estore.activity;
/*
个人资料设置页面
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.activity.myappliction.MyApplication;
import com.estore.fragment.MyHomePageFragment;
import com.estore.httputils.GetUserIdByNet;

import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.xUtilsImageUtils;
import com.estore.pojo.User;
import com.estore.pojo.UserBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PersonalSettingActivity extends AppCompatActivity {
    private UserBean  user = new UserBean();
    private ImageView iv_perreturn;
    private Dialog dialog = null;
    private TextView tv_nicknamecontent;
    private TextView tv_persexcontent;
    private TextView tv_phonenumber;
    private TextView tv_deliveryadress;
    private Button rb_tuichu;
    private SharedPreferences sp;
    private AlertDialog.Builder builder;
    //弹框
    private RelativeLayout rl_photorl;
    private RelativeLayout rl_sexrl;
    private RelativeLayout rl_nickname;
    private RelativeLayout rl_phonenumrl;
    private RelativeLayout rl_adressrl;
    private TextView rl_save;

    private static final String TAG = "PersonalSettingActivity";
    String sex[] = {"男", "女"};

    private  File file =null;
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private List<File> imageFileLists=new ArrayList<File>();
    private ImageView user_photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        sp = getSharedPreferences("User", MODE_APPEND);
        user.setUserId(sp.getInt("userId", 0));
        Log.i("PersonalSettingActivity", "userId:" + user.getUserId());


        //请求数据
        getUserIfo();
        initView();
        initDate();
        initEvent();

    }

    private void initView() {
        //返回个人资料
        iv_perreturn = ((ImageView) findViewById(R.id.iv_perreturn));
        rb_tuichu = ((Button) findViewById(R.id.rb_tuichu));
        rl_photorl = ((RelativeLayout) findViewById(R.id.rl_photorl));
        rl_nickname = ((RelativeLayout) findViewById(R.id.rl_nicknamerl));
        rl_sexrl = ((RelativeLayout) findViewById(R.id.rl_sexrl));
        rl_phonenumrl = ((RelativeLayout) findViewById(R.id.rl_phonenumrl));
        tv_phonenumber = ((TextView) findViewById(R.id.tv_phonenumber));//手机号
        rl_adressrl = ((RelativeLayout) findViewById(R.id.rl_adressrl));
        tv_persexcontent = ((TextView) findViewById(R.id.tv_persexcontent));//性别
        rl_save = ((TextView) findViewById(R.id.rl_saverl));
        user_photo=(ImageView)findViewById(R.id.user_photo);
        tv_deliveryadress = ((TextView) findViewById(R.id.tv_deliveryadress));
        tv_nicknamecontent = ((TextView) findViewById(R.id.tv_nicknamecontent));

    }

    private void initEvent() {


        //返回
        iv_perreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rb_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalSettingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
         /*
        String nickname, String userSex, String user_address,  String user_phone, String userPhoto
        */
        //修改头像
        rl_photorl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoicePhoto();
            }

        });
        //修改昵称
        rl_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RelativeLayout modify_myNickname = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_name_activity, null);
                AlertDialog builder = new AlertDialog.Builder(PersonalSettingActivity.this).setView(modify_myNickname).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_name = ((EditText) modify_myNickname.findViewById(R.id.et_edit_name));
                        String name = et_name.getText().toString();
                        tv_nicknamecontent.setText(name);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        //设置性别
        rl_sexrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(PersonalSettingActivity.this);
                alert.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                tv_persexcontent.setText("男");
                                break;
                            case 1:
                                tv_persexcontent.setText("女");
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
        //地址
        rl_adressrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RelativeLayout modify_myadress = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_daress_activity, null);
                new AlertDialog.Builder(PersonalSettingActivity.this).setView(modify_myadress).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_adress = ((EditText) modify_myadress.findViewById(R.id.et_edit_adress));
                        String adress = et_adress.getText().toString();
                        tv_deliveryadress.setText(adress);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        //设置电话号码
        rl_phonenumrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view2=  getLayoutInflater().inflate(R.layout.activity_modify_my_telphone_activity, null);
                new AlertDialog.Builder(PersonalSettingActivity.this).setView(view2).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_edit_telnumber = ((EditText) view2.findViewById(R.id.et_edit_telnumber));
                        String tel =et_edit_telnumber.getText().toString();
                        Log.e("tel",tel+"");

                        tv_phonenumber.setText(tel);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        //保存
         /*
        String nickname, String userSex, String user_address,Integer userId, String userPhoto, String user_phone
        */
        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String nickname, String userSex, String user_address,Integer userId,String userPhoto ,String user_phone
                uploadImage();
                String nickname=tv_nicknamecontent.getText().toString();
                String  userSex= tv_persexcontent.getText().toString();
                String  useraddress=tv_deliveryadress.getText().toString();

                String userphoto="";
                if(file!=null){
                    userphoto="upload/"+file.getName();//图片路径
                    System.out.println(userphoto);
                }
                String phonenmber=tv_phonenumber.getText().toString();
                UserBean userModify=new UserBean(nickname,userSex,useraddress,user.getUserId(),userphoto,phonenmber);
                Gson gson=new Gson();
                String usergson=gson.toJson(userModify);
                RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"/modifyuserservlet");
                requestParams.addBodyParameter("user", usergson);//用户id

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i(TAG,"onClick"+result);
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
                Intent intent=new Intent(PersonalSettingActivity.this,MainActivity.class);
                intent.putExtra("direct",5);
                startActivity(intent);

            }



        });
    }
    //上传服务器
    public  void uploadImage() {
        System.out.println("111");
        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL+ "uploadimagesservlet");
        requestParams.setMultipart(true);
        if(file!=null){
            requestParams.addBodyParameter("file", file);
        }
        // System.out.println(file.getName());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ModifyPersonInfo", "onSuccess: ");
                System.out.println("success" + result);
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
    private void showChoicePhoto() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("选择打开方式");
        View view=View.inflate(this,R.layout.layout_choice_getphoto,null);
        ((ImageView) view.findViewById(R.id.tv_camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" +
                        getPhotoFileName());
                getPictureByCamera(file);
                dialog.dismiss();
            }
        });
        ((ImageView) view.findViewById(R.id.tv_photo)).setOnClickListener(new View.OnClickListener() {
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

    private void initDate() {


    }

    public void getUserIfo() {
        String url = HttpUrlUtils.HTTP_URL + "/findUserServlet?userId=" + user.getUserId();
        Log.i("PersonalSettingActivity", user.getUserId() + "userId");
        RequestParams repuestparams = new RequestParams(url);

        x.http().get(repuestparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("PersonalSettingActivity", "result"+result);
                Gson gson = new Gson();
                UserBean userinfo = gson.fromJson(result, UserBean.class);
                Log.i("PersonalSettingActivity","userinfo" +userinfo.toString());
/*
String nickname, String userSex, String user_address,  String user_phone, String userPhoto

 */
                tv_nicknamecontent = ((TextView) findViewById(R.id.tv_nicknamecontent));//昵称
                tv_nicknamecontent.setText(userinfo.getNickname());
                tv_persexcontent = ((TextView) findViewById(R.id.tv_persexcontent));//性别
                tv_persexcontent.setText(userinfo.getUserSex().equals("0") ? "男" : "女");
                tv_deliveryadress = ((TextView) findViewById(R.id.tv_deliveryadress));//地址
                tv_deliveryadress.setText(userinfo.getUser_address());
                tv_phonenumber = ((TextView) findViewById(R.id.tv_phonenumber));//手机号
                tv_phonenumber.setText(userinfo.getUser_phone());
                user_photo = ((ImageView) findViewById(R.id.user_photo));//头像
                String[] photo=userinfo.getUserPhoto().split("=");
                xUtilsImageUtils.display(user_photo,HttpUrlUtils.HTTP_URL +photo[0],true);

                Log.i("PersonalSettingActivity", userinfo.getUser_phone() + "---" + userinfo.getUser_address());

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
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        System.out.println("============" + UUID.randomUUID());
        Log.e("UUID:",UUID.randomUUID()+"");
        return sdf.format(date) + "_" + UUID.randomUUID() + ".png";
    }

    private void getPictureByCamera(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        System.out.println("getPicFromCamera===========" + file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    //选择相册
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
                        user_photo.setImageBitmap(photo);//设置图片

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
}