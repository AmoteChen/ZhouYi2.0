package example.com.zhouyi_20.activity.liuyao;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import example.com.zhouyi_20.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    private FragmentAdapter fragmentAdapter;
    private liuyao_fragment_1 liuyao_fragment_1;
    private liuyao_fragment_2 liuyao_fragment_2;
    private liuyao_fragment_3 liuyao_fragment_3;

    private LinearLayout point_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liuyao_result);
        point_position = (LinearLayout)findViewById(R.id.point_position);
        fragment_init();
        fragment_manage();
    }

    private void fragment_init(){
        fragmentList.clear();
        viewPager=(ViewPager)findViewById(R.id.liuyao_bottom);
        liuyao_fragment_1 = new liuyao_fragment_1();
        liuyao_fragment_2 = new liuyao_fragment_2();
        liuyao_fragment_3 = new liuyao_fragment_3();
        fragmentList.add(liuyao_fragment_1);
        fragmentList.add(liuyao_fragment_2);
        fragmentList.add(liuyao_fragment_3);

        //初始时刻生成三个点
        int page = fragmentList.size();
        for (int i = 0; i < page; i++) {
            Change_Point point = new Change_Point(MainActivity.this);
            //将第二个点变为选中颜色
            if (i == 1) {
                point.setSelected(true);
            } else {
                point.setSelected(false);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
            params.leftMargin = 0;
            params.topMargin = 0;
            point_position.addView(point, params);
        }
    }
    private void fragment_manage(){
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //在每次选中更改的时候，清空
                point_position.removeAllViews();
                //清空后画点
                int page = fragmentList.size();
                for (int i = 0; i < page; i++) {
                    Change_Point point = new Change_Point(MainActivity.this);
                    if (i == position) {
                        point.setSelected(true);
                    } else {
                        point.setSelected(false);
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
                    params.leftMargin = 0;
                    params.topMargin = 0;
                    point_position.addView(point, params);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void point_draw(){

    }
}
