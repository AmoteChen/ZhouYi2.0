package example.com.zhouyi_20.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.liuyao.FragmentAdapter;
import example.com.zhouyi_20.activity.liuyao.LiuYaoNewRecord;
import example.com.zhouyi_20.activity.liuyao.liuyao_fragment_1;
import example.com.zhouyi_20.activity.liuyao.liuyao_fragment_2;
import example.com.zhouyi_20.activity.liuyao.liuyao_fragment_3;
import example.com.zhouyi_20.activity.mine.Mine_fragment;
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

    private Button bt_zhouyi;
    private Button bt_history;
    private Button bt_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        fragment_init();
        fragment_manage();
        bt_zhouyi = (Button)findViewById(R.id.main_bt_zhouyi);
        bt_zhouyi.setOnClickListener(this);
        bt_history = (Button)findViewById(R.id.main_bt_history);
        bt_history.setTextColor(Color.parseColor("#6393d5"));
        bt_history.setOnClickListener(this);
        bt_mine = (Button)findViewById(R.id.main_bt_mine);
        bt_mine.setOnClickListener(this);
    }

    private void fragment_init() {
        fragmentList.clear();
        viewPager = (CustomViewPager) findViewById(R.id.main_top_layout);
        //禁止滑动
        viewPager.setScanScroll(false);
        history_fragment = new History_fragment();
        mine_fragment = new Mine_fragment();
        zhouyi_fragment = new Zhouyi_fragment();
        fragmentList.add(zhouyi_fragment);
        fragmentList.add(history_fragment);
        fragmentList.add(mine_fragment);

    }
    private void fragment_manage(){
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bt_zhouyi:
                viewPager.setCurrentItem(0);
                bt_zhouyi.setTextColor(Color.parseColor("#6393d5"));
                bt_history.setTextColor(Color.parseColor("#000000"));
                bt_mine.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.main_bt_history:
                viewPager.setCurrentItem(1);
                bt_zhouyi.setTextColor(Color.parseColor("#000000"));
                bt_history.setTextColor(Color.parseColor("#6393d5"));
                bt_mine.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.main_bt_mine:
                viewPager.setCurrentItem(2);
                bt_zhouyi.setTextColor(Color.parseColor("#000000"));
                bt_history.setTextColor(Color.parseColor("#000000"));
                bt_mine.setTextColor(Color.parseColor("#6393d5"));
                break;
            default:
                break;
        }
    }
}
