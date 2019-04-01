package example.com.zhouyi_20.activity.liuyao;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.zhouyi_20.R;

/**
 * Created by ChenSiyuan on 2019/1/17.
 */

public class liuyao_fragment_3 extends Fragment {
    private Spinner spinner_shensha;
    private TextView shensha_total,shensha_1,shensha_2,shensha_3,shensha_4,shensha_5,shensha_6,shensha_7,shensha_8;
    private TextView shensha_9,shensha_10,shensha_11,shensha_12,shensha_13,shensha_14,shensha_15,shensha_16;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.liuyao_fra_layout_3,container,false);
        spinner_shensha = (Spinner)view.findViewById(R.id.spinner_shensha);
        spinner_shensha.setOnItemSelectedListener(new spinnerListener());

        shensha_total = (TextView)view.findViewById(R.id.shensha_total);
        shensha_1 = (TextView)view.findViewById(R.id.shensha_text_1);
        shensha_2 = (TextView)view.findViewById(R.id.shensha_text_2);
        shensha_3 = (TextView)view.findViewById(R.id.shensha_text_3);
        shensha_4 = (TextView)view.findViewById(R.id.shensha_text_4);
        shensha_5 = (TextView)view.findViewById(R.id.shensha_text_5);
        shensha_6 = (TextView)view.findViewById(R.id.shensha_text_6);
        shensha_7 = (TextView)view.findViewById(R.id.shensha_text_7);
        shensha_8 = (TextView)view.findViewById(R.id.shensha_text_8);

        shensha_9 = (TextView)view.findViewById(R.id.shensha_text_9);
        shensha_10 = (TextView)view.findViewById(R.id.shensha_text_10);
        shensha_11 = (TextView)view.findViewById(R.id.shensha_text_11);
        shensha_12 = (TextView)view.findViewById(R.id.shensha_text_12);
        shensha_13 = (TextView)view.findViewById(R.id.shensha_text_13);
        shensha_14 = (TextView)view.findViewById(R.id.shensha_text_14);
        shensha_15 = (TextView)view.findViewById(R.id.shensha_text_15);
        shensha_16 = (TextView)view.findViewById(R.id.shensha_text_16);


        return view;

    }

    class spinnerListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            switch(position){
                case 0:
                    shensha_total.setText("常用神煞");
                    shensha_1.setText("千禄");
                    shensha_2.setText("往亡");
                    shensha_3.setText("天喜");
                    shensha_4.setText("劫煞");
                    shensha_5.setText("羊刃");
                    shensha_6.setText("桃花");
                    shensha_7.setText("驿马");
                    shensha_8.setText("贵人");
                    shensha_9.setText("");
                    shensha_10.setText("");
                    shensha_11.setText("");
                    shensha_12.setText("");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");
                    break;
                case 1:
                    shensha_total.setText("占感情");
                    shensha_1.setText("灾煞 驿马");
                    shensha_2.setText("劫煞 月将");
                    shensha_3.setText("寡宿 日德");
                    shensha_4.setText("孤辰 贵人");
                    shensha_5.setText("桃花");
                    shensha_6.setText("红鸾");
                    shensha_7.setText("天喜");
                    shensha_8.setText("羊刃");
                    shensha_9.setText("");
                    shensha_10.setText("");
                    shensha_11.setText("");
                    shensha_12.setText("");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");
                    break;
                case 2:
                    shensha_total.setText("占疾病");
                    shensha_1.setText("驿马");
                    shensha_2.setText("月将");
                    shensha_3.setText("日德");
                    shensha_4.setText("贵人");
                    shensha_5.setText("吊客");
                    shensha_6.setText("丧门");
                    shensha_7.setText("地医");
                    shensha_8.setText("天医");
                    shensha_9.setText("");
                    shensha_10.setText("");
                    shensha_11.setText("");
                    shensha_12.setText("");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");
                    break;
                case 3:
                    shensha_total.setText("占官禄");
                    shensha_1.setText("羊刃");
                    shensha_2.setText("月将");
                    shensha_3.setText("日德");
                    shensha_4.setText("天喜");
                    shensha_5.setText("驿马");
                    shensha_6.setText("日德");
                    shensha_7.setText("文昌");
                    shensha_8.setText("贵人");
                    shensha_9.setText("");
                    shensha_10.setText("");
                    shensha_11.setText("");
                    shensha_12.setText("");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");
                    break;
                case 4:
                    shensha_total.setText("占求财");
                    shensha_1.setText("天喜");
                    shensha_2.setText("羊刃");
                    shensha_3.setText("劫煞");
                    shensha_4.setText("亡神");
                    shensha_5.setText("贵人");
                    shensha_6.setText("驿马");
                    shensha_7.setText("");
                    shensha_8.setText("");
                    shensha_9.setText("");
                    shensha_10.setText("");
                    shensha_11.setText("");
                    shensha_12.setText("");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");
                    break;
                case 5:
                    shensha_total.setText("占胎孕");
                    shensha_1.setText("寡宿");
                    shensha_2.setText("孤辰");
                    shensha_3.setText("灾煞");
                    shensha_4.setText("亡神");
                    shensha_5.setText("劫煞");
                    shensha_6.setText("羊刃");
                    shensha_7.setText("红鸾");
                    shensha_8.setText("天喜");
                    shensha_9.setText("");
                    shensha_10.setText("");
                    shensha_11.setText("");
                    shensha_12.setText("");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");
                    break;
                case 6:
                    shensha_total.setText("占官非");
                    shensha_1.setText("贵人");
                    shensha_2.setText("驿马");
                    shensha_3.setText("月将");
                    shensha_4.setText("日德");
                    shensha_5.setText("");
                    shensha_6.setText("");
                    shensha_7.setText("");
                    shensha_8.setText("");
                    shensha_9.setText("");
                    shensha_10.setText("");
                    shensha_11.setText("");
                    shensha_12.setText("");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }


        }
    }


