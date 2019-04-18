package example.com.zhouyi_20.activity.liuyao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

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
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;
import example.com.zhouyi_20.tool.Birth;
import example.com.zhouyi_20.tool.TianGanDiZhi;

/**
 * Created by ChenSiyuan on 2019/1/17.
 * The name of the class is not accurate.
 * This class is the second fragment in the bottom of the FINAL RESULT.
 */

public class liuyao_fragment_2 extends Fragment {
    private View view;
    private Integer liuyaoIntegerData[];
    private String postStr;
    private final String urlStr="http://120.76.128.110:8081/table/huGua";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.liuyao_fra_layout_2,container,false);
        //空表的表头绘制（将其背景设置为黑色）
        String str = "空";
        SpannableStringBuilder style=new SpannableStringBuilder(str);
        style.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.black)),0,str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        // 填写空表表头内容
        TextView textView= (TextView)view.findViewById(R.id.liuyao_kong_title);
        textView.setText(style);

        try{
            //从bundle中获取数据并调用各个方法填上各个表项
            //此处的bundle是从Suangua_Result中发送回来的
        Bundle bundle = getArguments();
        printDayTable(bundle.getString("day_string"));
        printQinTable(bundle.getStringArrayList("qin_string"));
        printBianTable(bundle.getStringArrayList("bian_table"));
        printRightBengui(bundle.getString("ben_string"));
        printRightBiangua(bundle.getString("bian_string"));
        printKongTable(bundle.getString("kong_table"));
        printShougua(bundle.getString("shou_string"));
        printShenGua(bundle.getString("shen_gua"));
        printDate();
        }catch (Exception e){
            e.printStackTrace();
        }
        //单独拿取起卦中的卦象，发送至后端用于获取“互卦”的结果
        Intent intent=getActivity().getIntent();
        liuyaoIntegerData=(Integer[]) intent.getSerializableExtra("LiuYaoData");
        Collections.reverse(Arrays.asList(liuyaoIntegerData));
        //将卦象整理为字符串发送
        postStr="["+liuyaoIntegerData[5];
        for (int i=4;i>-1;i--)
            postStr+=","+liuyaoIntegerData[i];
        postStr+="]";
        Log.e("AAAAAA",postStr);

        HttpsConnect.sendRequest(urlStr, "POST", postStr, new HttpsListener() {
            @Override
            public void success(String response) {
                catchResponse(response);
            }

            @Override
            public void failed(Exception exception) {
                exception.printStackTrace();
            }
        });


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
    //填上变表
    private void printBianTable(List biantable){
        TextView textView;

        textView = (TextView)view.findViewById(R.id.liuyaoresult_biantable_1);
        if(biantable.get(0).toString()!="null"){textView.setText(biantable.get(0).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_biantable_2);
        if(biantable.get(1).toString()!="null"){textView.setText(biantable.get(1).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_biantable_3);
        if(biantable.get(2).toString()!="null"){textView.setText(biantable.get(2).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_biantable_4);
        if(biantable.get(3).toString()!="null"){textView.setText(biantable.get(3).toString());}
        else{textView.setText("");}

        textView = (TextView)view.findViewById(R.id.liuyaoresult_biantable_5);
        if(biantable.get(4).toString()!="null"){textView.setText(biantable.get(4).toString());}
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
    //Shougua
    private void printShougua(String shougua){
        TextView textView = (TextView)view.findViewById(R.id.liuyaoresult_right_shougua);
        textView.setText(shougua);
    }

    //填上空表
    private void printKongTable(String kong_table){
        TextView textView = (TextView)view.findViewById(R.id.frg2_kong_table);
        textView.setText(kong_table);
    }
    //填上身卦
    private void printShenGua(String shen_gua){
        TextView textView = (TextView)view.findViewById(R.id.shen_gua);
        textView.setText(shen_gua);
    }

    //日期等
    private void printDate() throws ParseException {
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        Intent intent = getActivity().getIntent();
        String time = intent.getStringExtra("date");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = formatter.parse(time);

        cal.setTime(date);

        TextView textView;



        // 农历日月年干支用Birth计算
        //五行用TianGanDiZhi计算


        TianGanDiZhi ganzhi=new TianGanDiZhi(cal,date);

        Birth birth = new Birth(cal);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
//        Map lunar = birth.toLunar();
        String dayTime = sdf.format(date);
        Map map = birth.horoscope(dayTime);
        String ganzhi_year=map.get("cY").toString();
        String ganzhi_month=map.get("cM").toString();
        String ganzhi_day=map.get("cD").toString();

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

        textView = (TextView)view.findViewById(R.id.liuyaoresult_richong);
        if(ganzhi_day.substring(1,2).equals("子")){
            textView.setText("午");
        }
        if(ganzhi_day.substring(1,2).equals("丑")){
            textView.setText("未");
        }
        if(ganzhi_day.substring(1,2).equals("寅")){
            textView.setText("申");
        }
        if(ganzhi_day.substring(1,2).equals("卯")){
            textView.setText("酉");
        }
        if(ganzhi_day.substring(1,2).equals("辰")){
            textView.setText("戌");
        }
        if(ganzhi_day.substring(1,2).equals("巳")){
            textView.setText("亥");
        }
        if(ganzhi_day.substring(1,2).equals("午")){
            textView.setText("子");
        }
        if(ganzhi_day.substring(1,2).equals("未")){
            textView.setText("丑");
        }
        if(ganzhi_day.substring(1,2).equals("申")){
            textView.setText("寅");
        }
        if(ganzhi_day.substring(1,2).equals("酉")){
            textView.setText("卯");
        }
        if(ganzhi_day.substring(1,2).equals("戌")){
            textView.setText("辰");
        }
        if(ganzhi_day.substring(1,2).equals("亥")){
            textView.setText("巳");
        }

        textView = (TextView)view.findViewById(R.id.liuyaoresult_yuepo);
        if(ganzhi_month.substring(1,2).equals("子")){
            textView.setText("午");
        }
        if(ganzhi_month.substring(1,2).equals("丑")){
            textView.setText("未");
        }
        if(ganzhi_month.substring(1,2).equals("寅")){
            textView.setText("申");
        }
        if(ganzhi_month.substring(1,2).equals("卯")){
            textView.setText("酉");
        }
        if(ganzhi_month.substring(1,2).equals("辰")){
            textView.setText("戌");
        }
        if(ganzhi_month.substring(1,2).equals("巳")){
            textView.setText("亥");
        }
        if(ganzhi_month.substring(1,2).equals("午")){
            textView.setText("子");
        }
        if(ganzhi_month.substring(1,2).equals("未")){
            textView.setText("丑");
        }
        if(ganzhi_month.substring(1,2).equals("申")){
            textView.setText("寅");
        }
        if(ganzhi_month.substring(1,2).equals("酉")){
            textView.setText("卯");
        }
        if(ganzhi_month.substring(1,2).equals("戌")){
            textView.setText("辰");
        }
        if(ganzhi_month.substring(1,2).equals("亥")){
            textView.setText("巳");
        }

    }
    private void catchResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
            try{
                JSONObject RootJsonObject = new JSONObject(response);
                JSONObject data = RootJsonObject.getJSONObject("data");
                Log.e("TTTTT",data.toString());
                String Hugua_string = data.getString("content");
                Log.e("content_LLLL",Hugua_string);
                TextView textView = view.findViewById(R.id.liuyaoresult_right_hugua);
                textView.setText(Hugua_string);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}

}
