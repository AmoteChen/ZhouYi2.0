package example.com.zhouyi_20.view;
/**
 *启动页面
 */
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.Main;

import static java.lang.Thread.sleep;

public class LunchView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.lunch_layout);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goMainActivity();
                }
            }, 1500);//界面显示时间

        }
        private void goMainActivity(){
            Intent intent = new Intent(getApplicationContext(),Main.class);
            //设置跳出动画
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LunchView.this).toBundle());
            LunchView.this.finish();
    }

}
