package example.com.zhouyi_20.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.total_result.FragmentAdapter;
import example.com.zhouyi_20.activity.mine.Mine_fragment;
import example.com.zhouyi_20.activity.mine.item_view;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;
import example.com.zhouyi_20.object.User;
import example.com.zhouyi_20.tool.CustomViewPager;

/**
 * Created by ChenSiyuan on 2019/2/25.
 */

public class Main extends AppCompatActivity implements View.OnClickListener{
    private CustomViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    private FragmentAdapter fragmentAdapter;
    private Zhouyi_fragment zhouyi_fragment;
    private History_fragment history_fragment;
    private Mine_fragment mine_fragment;

    private ImageView bt_zhouyi;
    private ImageView bt_history;
    private ImageView bt_mine;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private String name;
    private String account;
    private String password;
    private String token;
    private String id;
    private String reason;
    private String realname;
    private String birthYM;
    private boolean state;

    private static boolean fragment_set = false;

    private String check_address = "http://120.76.128.110:12510/user/CheckLogin";
    private String address = "http://120.76.128.110:12510/user/UserLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAGG","Main_onCreate");
        //先加载碎片内的信息再进行布局初始化，以防止布局已经设置后，信息不能刷新
        if(fragment_init()){

            //设置页面打开动画
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            init_fade();

            setContentView(R.layout.main);

            Viewmanager();
            Log.e("TAGG","layout_seted");

            bt_zhouyi =(ImageView)findViewById(R.id.main_bt_zhouyi);
            bt_zhouyi.setOnClickListener(this);
            bt_history = (ImageView)findViewById(R.id.main_bt_history);
            bt_history.setImageResource(R.drawable.bottom_lishi_yes);
            bt_history.setOnClickListener(this);
            bt_mine = (ImageView)findViewById(R.id.main_bt_mine);
            bt_mine.setOnClickListener(this);

            sp = getSharedPreferences("user", Context.MODE_PRIVATE);
            name = sp.getString("name", "");
            account = sp.getString("account", "");
            password = sp.getString("password", "");
            token = sp.getString("token", "");
            id = sp.getString("id", "-1");
            state = sp.getBoolean("state", false);


            User.setName(name);
            User.setAccount(account);
            User.setPassword(password);
            User.setToken(token);
            User.setId(id);
            User.setState(state);


            if (!token.equals("no token")) {
                //Toast.makeText(this, "checking", Toast.LENGTH_SHORT).show();
                HttpsConnect.sendRequest(check_address, "POST", getCheckJsonData(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchCheckResponse(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            } else {
                //Toast.makeText(this, "no token", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //旧版本
        //bt_zhouyi.setTextColor(Color.parseColor("#000000"));
        //bt_history.setTextColor(Color.parseColor("#6393d5"));
        //bt_mine.setTextColor(Color.parseColor("#000000"));
        bt_zhouyi.setImageResource(R.drawable.bottom_zhanbu_no);
        bt_history.setImageResource(R.drawable.bottom_lishi_yes);
        bt_mine.setImageResource(R.drawable.bottom_wode_no);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.e("TAGG","Main_onRestart");

        //先加载碎片内的信息再进行布局初始化，以防止布局已经设置后，信息不能刷新
        if(fragment_init()){

            Viewmanager();
            Log.e("TAGG","layout_seted");


            sp = getSharedPreferences("user", Context.MODE_PRIVATE);
            name = sp.getString("name", "");
            account = sp.getString("account", "");
            password = sp.getString("password", "");
            token = sp.getString("token", "");
            id = sp.getString("id", "-1");
            state = sp.getBoolean("state", false);


            User.setName(name);
            User.setAccount(account);
            User.setPassword(password);
            User.setToken(token);
            User.setId(id);
            User.setState(state);


            if (!token.equals("no token")) {
                //Toast.makeText(this, "checking", Toast.LENGTH_SHORT).show();
                HttpsConnect.sendRequest(check_address, "POST", getCheckJsonData(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchCheckResponse(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            } else {
                //Toast.makeText(this, "no token", Toast.LENGTH_SHORT).show();
            }

        }

    }

    /**
     * @return
     * 布局初始化
     */
    private boolean fragment_init() {
        fragmentList.clear();
        history_fragment = new History_fragment();
        mine_fragment = new Mine_fragment();
        zhouyi_fragment = new Zhouyi_fragment();
        fragmentList.add(zhouyi_fragment);
        fragmentList.add(history_fragment);
        fragmentList.add(mine_fragment);
        Log.e("TAGG","fragment_seted");
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);

        return true;
    }

    /**
     * 页面打开动画
     *
     */
    private void init_fade() {
        Transition transition = new Fade().setDuration(200);
        getWindow().setEnterTransition(transition);
        getWindow().setExitTransition(transition);
    }

    /**
     * ViewPager的处理
     */
    private void Viewmanager(){
        viewPager = (CustomViewPager) findViewById(R.id.main_top_layout);
        //禁止滑动
        viewPager.setScanScroll(false);

        viewPager.invalidate();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        Log.e("TAGG",fragmentList.toString());
        viewPager.setCurrentItem(1);
        Log.e("TAGG","viewer_seted");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bt_zhouyi:
                viewPager.setCurrentItem(0);
                bt_zhouyi.setImageResource(R.drawable.bottom_zhanbu_yes);
                bt_history.setImageResource(R.drawable.bottom_lishi_no);
                bt_mine.setImageResource(R.drawable.bottom_wode_no);
                break;
            case R.id.main_bt_history:
                viewPager.setCurrentItem(1);
                bt_zhouyi.setImageResource(R.drawable.bottom_zhanbu_no);
                bt_history.setImageResource(R.drawable.bottom_lishi_yes);
                bt_mine.setImageResource(R.drawable.bottom_wode_no);
                break;
            case R.id.main_bt_mine:
                viewPager.setCurrentItem(2);
                bt_zhouyi.setImageResource(R.drawable.bottom_zhanbu_no);
                bt_history.setImageResource(R.drawable.bottom_lishi_no);
                bt_mine.setImageResource(R.drawable.bottom_wode_yes);
                break;
            default:
                break;
        }
    }
    JSONObject getCheckJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", User.getToken());
            jsonObject.put("id", User.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchCheckResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String reason = jsonObject.getString("reason");
                    if (result.equals("success")) {
                        if (state) {
                            HttpsConnect.sendRequest(address, "POST", getJsonData(), new HttpsListener() {
                                @Override
                                public void success(String response) {
                                    catchResponse(response);
                                }

                                @Override
                                public void failed(Exception exception) {
                                    exception.printStackTrace();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

        JSONObject getJsonData() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("password", password);
                jsonObject.put("phone", account);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        private void catchResponse(final String response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("result");
                        reason = jsonObject.getString("reason");
                        realname = jsonObject.getString("realname");
                        birthYM = jsonObject.getString("birthYM");
                        name = jsonObject.getString("name");
                        token = jsonObject.getString("token");
                        id = jsonObject.getString("userId");
                        if (result.compareTo("success") == 0) {
                            Toast.makeText(Main.this, name+"登陆成功", Toast.LENGTH_SHORT).show();
                            item_view.setState(true);

//                        name = jsonObject.getString("name");
//                        account = jsonObject.getString("phone");
//                        password = jsonObject.getString("password");
//                        User.setName(name);
//                        User.setAccount(account);
//                        User.setPassword(password);
                            User.setName(User.getName());
                            User.setState(true);
                            User.setToken(token);
                            User.setId(id);
                            User.setTrue_name(realname);
                            User.setBirthday(birthYM);

                            sp = getSharedPreferences(User.getAccount(), Context.MODE_PRIVATE);
                            editor = sp.edit();
                            editor.putString("name", User.getName());
//                        editor.putString("account", User.getAccount());
                            editor.putString("password", User.getPassword());
                            editor.putBoolean("state", User.getState());
                            editor.putString("token", User.getToken());
                            editor.putString("id", User.getId());
                            editor.commit();

                            sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                            editor = sp.edit();
                            editor.putString("name", User.getName());
//                        editor.putString("account", User.getAccount());
                            editor.putString("password", User.getPassword());
                            editor.putBoolean("state", User.getState());
                            editor.putString("token", User.getToken());
                            editor.putString("id", User.getId());
                            editor.commit();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public static void setFragment_set(boolean fragment_set1){
            fragment_set=fragment_set1;
        }

}
