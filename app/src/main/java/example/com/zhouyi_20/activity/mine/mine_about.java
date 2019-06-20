package example.com.zhouyi_20.activity.mine;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.content.*;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.Login;
import example.com.zhouyi_20.object.User;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;

/**
 * Created by ChenSiyuan on 2018/10/21.
 * 2019/4/24
 */

public class mine_about extends AppCompatActivity implements View.OnClickListener {

    private View updateBtn;

    private AlertDialog.Builder updateDlg;
    private AlertDialog updateDlg1;
    private AlertDialog.Builder dontUpdateDlg;
    private AlertDialog.Builder permission;
    
    private TextView banbenhao;

    private DownloadManager updateDownload;
    private long downloadId;
    private String address1;
    private boolean isNew;//判断是否有更新
    private MyContentObserver observer;
    private CompleteReceiver completeReceiver;
    private ProgressDialog downloadingDlg;
    public static final int INSTALL_APK_REQUESTCODE = 1;
    public static final int UPDATE_UI = 2;

    public static final String versionCode = "1.1.8";//181行改了下载包的命名，以解决覆盖的问题,247行改了打开安装包的文件名，有问题再改
    public static final String nextVersionCode="1.1.9";
    public static final String checkAddress = "http://120.76.128.110:12510/manage/CheckVersion";
    public static final String downloadAddress = "http://120.76.128.110:12510/AndroidDeployment/android.apk";
    public static final String ttttt = "http://http://120.76.128.110:12510/AndroidDeployment/android.apk";

    /**
     * 一个内容观察器
     */
    public class MyContentObserver extends ContentObserver {
        private Handler handler;

        public MyContentObserver(Handler handler) {
            super(handler);
            this.handler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            int[] bytesAndStatus = getBytesAndStatus(downloadId);
            int currentSize = bytesAndStatus[0];//当前大小
            int totalSize = bytesAndStatus[1];//总大小
            int status = bytesAndStatus[2];//下载状态
            float percent = (float) currentSize / (float) totalSize;
            float progress = (float) Math.floor(percent * 100);
            downloadingDlg.setMessage(currentSize / 8 / 1024 + "KB" + "/" + totalSize / 8 / 1024 + "KB" + "     " + progress + "%");
            if (progress == 100)
                downloadingDlg.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        updateBtn = findViewById(R.id.about_refresh);
        banbenhao = findViewById(R.id.banbenhao);
        banbenhao.setText(versionCode);
        updateBtn.setOnClickListener(this);
        //初始化并注册内容观察器和完成接收器
        observer = new MyContentObserver(null);
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, observer);
        completeReceiver = new CompleteReceiver();
        registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销内容观察器和完成接收器
        unregisterReceiver(completeReceiver);
        getContentResolver().unregisterContentObserver(observer);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_refresh:
                checkUpdate();
                break;
            case R.id.about_help:
                break;
            case R.id.about_website:
                break;
        }
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {
        /*先检查是否有新版本*/
        HttpsConnect.sendRequest(checkAddress, "POST", getJsonData(), new HttpsListener() {
            @Override
            public void success(String response) {

                isNew = catchResponse(response);
                //不可以在子线程中更新UI所以
                Message message = new Message();
                message.what = UPDATE_UI;
                handler.sendMessage(message);

            }

            @Override
            public void failed(Exception exception) {
                exception.printStackTrace();
            }
        });
    }


    /**
     * @param address 下载更新
     */
    private void downloadUpdate(String address) {
        updateDlg1.dismiss();

        updateDownload = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(address));

        //目录: Android -> data -> com.app -> files -> Download ->
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, nextVersionCode+".apk");

        request.setTitle("正在下载");
        request.setDescription("下载中……");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        downloadId = updateDownload.enqueue(request);


        downloadingDlg = new ProgressDialog(this);
        downloadingDlg.setTitle("正在下载");
        downloadingDlg.setCancelable(false);
        downloadingDlg.show();
    }

    /**
     * @param downloadId
     * @return 根据下载项的id，查询下载项的下载进度状态并返回“当前已下载字节”、“总字节”、“当前下载状态”
     */
    public int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{-1, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = updateDownload.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }

    /**
     * 监听下载成功就启动安装过程
     */
    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            installProcess();
        }
    }

    /**
     * 申请权限回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == RESULT_OK) {
            installProcess();
        }
    }

    /**
     * 打开Apk文件
     */
    private void openApkFile() {
        String apkPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File apk = new File(apkPath, nextVersionCode+".apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        } else {//Android7.0之后获取uri要用contentProvider

            Uri uri = getUriFromFile(this, apk);

            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getBaseContext().startActivity(intent);//////////////

    }

    /**
     * 安装过程：从检查权限到安装
     */
    private void installProcess() {

        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("安装应用需要打开未知来源权限，请去设置中开启权限");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startInstallPermissionSettingActivity();
                        }
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return;
            }
        }
        //有权限，开始安装应用程序
        openApkFile();
    }

    /**
     * 申请权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10086);
    }

    /**
     * 打开Apk时会用到，适应安卓N的特性
     */
    public static Uri getUriFromFile(Context context, File file) {
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(context, "example.com.zhouyi_20.fileprovider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(file);
        }
        return imageUri;
    }

    /**
     * 封装版本号JSON
     */
    private JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("versionNow", versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * @param response false为有更新
     */
    private boolean catchResponse(final String response) {
        boolean newVersion = true;
        try {
            JSONObject jsonObject = new JSONObject(response);
            newVersion = jsonObject.getBoolean("new");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newVersion;
    }

    /**
     * 更新UI
     */
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    if (!isNew) {
                        updateDlg = new AlertDialog.Builder(mine_about.this);
                        updateDlg.setTitle("发现新版本");
                        updateDlg.setMessage("要下载新版本吗？");
                        updateDlg.setCancelable(false);
                        updateDlg.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadUpdate(downloadAddress);
                            }
                        });
                        updateDlg.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        updateDlg1 = updateDlg.show();
                    }
                    else 
                    {
                        updateDlg = new AlertDialog.Builder(mine_about.this);
                        updateDlg.setTitle("当前版本已是最新版本");
                        updateDlg.setMessage("");
                        updateDlg.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                
                            }
                        });
                        updateDlg.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        updateDlg.show();
                    }
                    break;
            }
        }
    };
}
