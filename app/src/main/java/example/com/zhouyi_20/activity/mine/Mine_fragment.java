package example.com.zhouyi_20.activity.mine;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.History_fragment;
import example.com.zhouyi_20.activity.Login;
import example.com.zhouyi_20.activity.Main;
import example.com.zhouyi_20.activity.mine.util.FileStorage;
import example.com.zhouyi_20.activity.mine.util.PermissionsActivity;
import example.com.zhouyi_20.activity.mine.util.PermissionsChecker;
import example.com.zhouyi_20.object.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static example.com.zhouyi_20.activity.Main.setFragment_set;

/**
 * Created by ChenSiyuan on 2018/9/20.
 */

public class Mine_fragment extends Fragment implements View.OnClickListener {
    private ImageView blurImageView;
    private ImageView avatarImageView;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private item_view info;
    private item_view about;
    private item_view offline;
    private item_view setting;
    private Button logout;

    private static Bitmap user_header;
    private static boolean title_changed = false;



    private static final int CODE_GALLERY_REQUEST = 0xa0;//相册选取
    private static final int CODE_CAMERA_REQUEST = 0xa1; //拍照
    private static final int CODE_RESULT_REQUEST = 0xa2; //剪裁图片
    private static final int REQUEST_PERMISSION = 0xa5;  //权限请求

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private Uri imageUri;//原图保存地址
    private String imagePath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment,container,false);
        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) view.findViewById(R.id.iv_avatar);
        avatarImageView.setOnClickListener(this);

        info = (item_view) view.findViewById(R.id.info_item_view);
        info.setOnClickListener(this);
        about = (item_view) view.findViewById(R.id.about_item_view);
        about.setOnClickListener(this);
        offline =(item_view) view.findViewById(R.id.offline_item_view);
        offline.setOnClickListener(this);
        logout = (Button)view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        setting = (item_view) view.findViewById(R.id.setting_item_view);
        setting.setOnClickListener(this);

        mPermissionsChecker = new PermissionsChecker(getActivity());

        item_view.setState(User.getState());
        if(User.getState()) {
            Glide.with(this).load(R.drawable.login_title)
                    .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                    .into(blurImageView);

            Glide.with(this).load(R.drawable.login_title)
                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(avatarImageView);
        }
        else {
            Glide.with(this).load(R.drawable.defult_head)
                    .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                    .into(blurImageView);

            Glide.with(this).load(R.drawable.defult_head)
                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(avatarImageView);
        }
        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        if(User.getState()){
            info.setVisibility(View.VISIBLE);
            offline.setVisibility(View.VISIBLE);
            if(!title_changed){
                Glide.with(this).load(R.drawable.login_title)
                        .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                        .into(blurImageView);

                Glide.with(this).load(R.drawable.login_title)
                        .bitmapTransform(new CropCircleTransformation(getActivity()))
                        .into(avatarImageView);
            }
            else {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                user_header.compress(Bitmap.CompressFormat.PNG, 100, stream);

                Glide.with(this)
                        .load(stream.toByteArray())
                        .error(R.drawable.login_title)
                        .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                        .into(blurImageView);

                Glide.with(this)
                        .load(stream.toByteArray())
                        .error(R.drawable.login_title)
                        .bitmapTransform(new CropCircleTransformation(getActivity()))
                        .into(avatarImageView);
            }
        }
        else {
            info.setVisibility(View.GONE);
            offline.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);

            Glide.with(this).load(R.drawable.defult_head)
                    .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                    .into(blurImageView);

            Glide.with(this).load(R.drawable.defult_head)
                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(avatarImageView);
        }
    }
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_avatar:
                if(User.getState()){
                    chooseDialog();
                }
                else{
                    Intent toLogin=new Intent(getContext(), Login.class);
                    startActivity(toLogin);}
                break;
            case R.id.info_item_view:
                Intent toInfo=new Intent(getActivity(),info_main.class);
                startActivity(toInfo);
                break;
            case R.id.about_item_view:
                Intent toAbout=new Intent(getActivity(),mine_about.class);
                startActivity(toAbout);
                break;
            case R.id.setting_item_view:
                Intent toSetting = new Intent(getActivity(), mine_setting.class);
                startActivity(toSetting);
                break;
            case R.id.logout:
                if(User.getState()){
                    sp=getActivity().getSharedPreferences(User.getAccount(), Context.MODE_PRIVATE);
                    editor=sp.edit();
                    editor.putBoolean("state",false);
                    editor.commit();

                    sp=getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
                    editor=sp.edit();
                    editor.putBoolean("state",false);
                    editor.commit();

                    User.setState(false);
                    Toast.makeText(getActivity(),"当前账户已注销",Toast.LENGTH_SHORT).show();
                    restartApplication();

                }
                break;

        }
    }


    private void chooseDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("选择头像")//

                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                                startPermissionsActivity();
                            } else {
                                selectFromAlbum();
                            }
                        } else {
                            selectFromAlbum();
                        }
                    }
                }).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_GALLERY_REQUEST: // 相册
                if (Build.VERSION.SDK_INT >= 19) {
                    handleImageOnKitKat(data);
                } else {
                    handleImageBeforeKitKat(data);
                }
                break;

            case CODE_RESULT_REQUEST://相册里生成bitmap
                try {
                    user_header = BitmapFactory.decodeFile(imagePath);
                    User.setHead(bitmapToBase64(user_header));
                    title_changed=true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_PERMISSION://权限请求
                if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
//                    finish();
                } else {
                        selectFromAlbum();
                    }
                break;
        }
    }



    /**
     * 从相册选择
     */
    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }

    /**
     * 裁剪
     */
    private void cropPhoto() {
        File file = new FileStorage().createCropFile();
        Uri outputUri = Uri.fromFile(file);//缩略图保存地址
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(imageUri, "image/*");
//        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        imageUri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), imageUri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(imageUri, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = imageUri.getPath();
        }

        cropPhoto();
    }

    private void handleImageBeforeKitKat(Intent intent) {
        imageUri = intent.getData();
        imagePath = getImagePath(imageUri, null);
        cropPhoto();
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(getActivity(), REQUEST_PERMISSION,
                PERMISSIONS);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * bitmap 转 base64
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    /**
     * base64 转 bitmap
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    private void restartApplication() {
        final Intent intent =  new Intent();

        intent.setClass(getContext(),Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }







}
