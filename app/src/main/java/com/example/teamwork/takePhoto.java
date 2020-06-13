package com.example.teamwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.IOException;

public class takePhoto extends AppCompatActivity implements View.OnClickListener {
    private Button goBack, takePh, choosePh;//定义返回、拍照、选择照片按钮按钮
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public ImageView picture;//定义存放图片框架元素
    public Uri imageUri; //获取照片的路径

    public void initElement() {//初始化函数
        goBack = findViewById(R.id.goBack);
        takePh = findViewById(R.id.takePh);
        choosePh = findViewById(R.id.choosePh);
        picture = findViewById(R.id.iv_picture);
        //接下来事件注册
        goBack.setOnClickListener(this);
        takePh.setOnClickListener(this);
        choosePh.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        initElement();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goBack:
                Log.e("AppCompatActivity", "点击返回功能");
                Intent intent2 = new Intent(takePhoto.this, NavBar.class);
                startActivity(intent2);
                break;
            case R.id.takePh:
                takePhoto();
                Log.e("AppCompatActivity", "点击拍照功能");
                break;
            case R.id.choosePh:
                Log.e("AppCompatActivity", "点击选照功能");
                setChoosePh();
                break;
            default:
                Log.e("AppCompatActivity", "本次操作失败");
        }
    }

    public void takePhoto() {//拍照程序
        //创建File对象，用于存储拍照后的对象
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(takePhoto.this, "com.example.teamwork.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    public void setChoosePh() {
        if (ContextCompat.checkSelfPermission(takePhoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(takePhoto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        System.out.println(bitmap + "************************************************");
                        ;
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);

        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 4.4及以上的系统使用这个方法处理图片
     *
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
detectText()
    }

    public String detectText(Bitmap bitmap) {

        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String path = "src/main/res/data/eng.traineddata"; //训练数据路径

        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "eng"); //eng为识别语言
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"); // 识别白名单
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?"); // 识别黑名单
        tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);//设置识别模式
        tessBaseAPI.setImage(bitmap); //设置需要识别图片的bitmap
        String inspection = tessBaseAPI.getHOCRText(0);
        tessBaseAPI.end();
        return inspection;
    }

}