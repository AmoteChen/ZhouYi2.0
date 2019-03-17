package example.com.zhouyi_20.activity.liuyao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.tool.Birth;
import example.com.zhouyi_20.tool.TianGanDiZhi;

/**
 * Created by ChenSiyuan on 2019/1/17.
 */

public class liuyao_fragment_2 extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.liuyao_fra_layout_2,container,false);
        try{
        Bundle bundle = getArguments();
        printDayTable(bundle.getString("day_string"));
        printQinTable(bundle.getStringArrayList("qin_string"));
        printRightBengui(bundle.getString("ben_string"));
        printRightBiangua(bundle.getString("bian_string"));
        printDate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;

    }

    //填上日表
    private void printDayTable(String daytable){
        //标题
        TextView textView;

        String temp = daytable.substring(0,1);
        textView = (TextView)view.findViewById(R.id.liuyaoresult_daytable_1);
        textView.setText(temp);

        temp = daytable.substring(1,2);
        textView = (TextView)view.findViewById(R.id.liuyaoresult_daytable_2);
        textView.setText(temp);

        temp = daytable.substring(2,3);
        textView = (TextView)view.findViewById(R.id.liuyaoresult_daytable_3);
        textView.setText(temp);

        temp = daytable.substring(3,4);
        textView = (TextView)view.findViewById(R.id.liuyaoresult_daytable_4);
        textView.setText(temp);

        temp = daytable.substring(4);
        textView = (TextView)view.findViewById(R.id.liuyaoresult_daytable_5);
        textView.setText(temp);
    }
    //填上亲表
    private void printQinTable(List qintable){

        TextView textView;

        textView = (TextView)view.findViewById(R.id.liuyaoresult_qintable_1);
        if(qintable.get(0).toString()!="null"){textView.setText(qintable.get(0).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_qintable_2);
        if(qintable.get(1).toString()!="null"){textView.setText(qintable.get(1).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_qintable_3);
        if(qintable.get(2).toString()!="null"){textView.setText(qintable.get(2).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_qintable_4);
        if(qintable.get(3).toString()!="null"){textView.setText(qintable.get(3).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_qintable_5);
        if(qintable.get(4).toString()!="null"){textView.setText(qintable.get(4).toString());}
        else{textView.setText("");}

    }
    //Bengui
    private void printRightBengui(String bengui){
        TextView textView = (TextView)view.findViewById(R.id.liuyaoresult_right_bengui);
        textView.setText(bengui);
    }
    //Biangua
    private void printRightBiangua(String biangua){
        TextView textView = (TextView)view.findViewById(R.id.liuyaoresult_right_biangua);
        textView.setText(biangua);
    }

    //日期等
    private void printDate() throws ParseException {
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date= new Date();

        TextView textView;



        // 农历日月年干支用Birth计算
        //五行用TianGanDiZhi计算

        TianGanDiZhi ganzhi=new TianGanDiZhi(cal);
        cal.setTime(date);
        Birth birth = new Birth(cal);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
//        Map lunar = birth.toLunar();
        String dayTime = sdf.format(date);
        Map map = birth.horoscope(dayTime);
        String ganzhi_year=map.get("cY").toString();
        String ganzhi_month=map.get("cM").toString();
        String ganzhi_day=map.get("cD").toString();

//        textView=(TextView)findViewById(R.id.liuyaoresult_ganzhi_day);
//        textView.setText(ganzhi_day);
//        textView=(TextView)findViewById(R.id.liuyaoresult_ganzhi_month);
//        textView.setText(ganzhi_month);
//        textView=(TextView)findViewById(R.id.liuyaoresult_ganzhi_year);
//        textView.setText(ganzhi_year);
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month);
        textView.setText(ganzhi_month.substring(1,2));

        // 金木水火土与旺相死囚生
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_1_2);
        textView.setText("旺");
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_2_2);
        textView.setText("相");
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_3_2);
        textView.setText("死");
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_4_2);
        textView.setText("囚");
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_5_2);
        textView.setText("休");

        String zhi_month_wuxing[]=ganzhi.getWuXing();
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_1_1);
        textView.setText(zhi_month_wuxing[0]);
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_2_1);
        textView.setText(zhi_month_wuxing[1]);
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_3_1);
        textView.setText(zhi_month_wuxing[2]);
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_4_1);
        textView.setText(zhi_month_wuxing[3]);
        textView=(TextView)view.findViewById(R.id.liuyaoresult_zhi_month_5_1);
        textView.setText(zhi_month_wuxing[4]);
    }

}
