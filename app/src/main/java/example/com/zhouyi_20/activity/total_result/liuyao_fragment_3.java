package example.com.zhouyi_20.activity.total_result;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.tool.Birth;

/**
 * Created by ChenSiyuan on 2019/1/17.
 */
//整个逻辑写得非常的暴力，因为工期实在是太赶了，为后来者埋了坑，实在抱歉。
//至于整个程序中变量名的问题，肯定会被吐槽，没有办法，祖传代码，经历了无数代完全不同地乱取。。祝顺利
public class liuyao_fragment_3 extends Fragment {
    //神煞那个选择下拉列表
    private Spinner spinner_shensha;
    //神煞的名称（灰色字体部分）
    private TextView shensha_total, shensha_1, shensha_2, shensha_3, shensha_4, shensha_5, shensha_6, shensha_7, shensha_8;
    private TextView shensha_9, shensha_10, shensha_11, shensha_12, shensha_13, shensha_14, shensha_15, shensha_16;
    //神煞对应的地支（黑色字体部分）
    private TextView dizhi_1, dizhi_2, dizhi_3, dizhi_4, dizhi_5, dizhi_6, dizhi_7, dizhi_8, dizhi_9,
            dizhi_10, dizhi_11, dizhi_12, dizhi_13, dizhi_14, dizhi_15, dizhi_16;
    //名称与地支的对应关系（用HashMap处理）
    private HashMap TaoHua_map, YiMa_map, JieSha_map, WangShen_map, GuaSu_map, GuChen_map, HongLuan_map, TianXi_map, YangRen_map, GuiRen_map;
    //日期的天干
    private String ganzhi_day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liuyao_fra_layout_3, container, false);
        //初始化（没有包装起来是因为view这里做的一个局部变量，这个可以处理一下，当时太赶了没有弄）
        spinner_shensha = (Spinner) view.findViewById(R.id.spinner_shensha);
        spinner_shensha.setOnItemSelectedListener(new spinnerListener());

        shensha_total = (TextView) view.findViewById(R.id.shensha_total);
        shensha_1 = (TextView) view.findViewById(R.id.shensha_text_1);
        shensha_2 = (TextView) view.findViewById(R.id.shensha_text_2);
        shensha_3 = (TextView) view.findViewById(R.id.shensha_text_3);
        shensha_4 = (TextView) view.findViewById(R.id.shensha_text_4);
        shensha_5 = (TextView) view.findViewById(R.id.shensha_text_5);
        shensha_6 = (TextView) view.findViewById(R.id.shensha_text_6);
        shensha_7 = (TextView) view.findViewById(R.id.shensha_text_7);
        shensha_8 = (TextView) view.findViewById(R.id.shensha_text_8);

        shensha_9 = (TextView) view.findViewById(R.id.shensha_text_9);
        shensha_10 = (TextView) view.findViewById(R.id.shensha_text_10);
        shensha_11 = (TextView) view.findViewById(R.id.shensha_text_11);
        shensha_12 = (TextView) view.findViewById(R.id.shensha_text_12);
        shensha_13 = (TextView) view.findViewById(R.id.shensha_text_13);
        shensha_14 = (TextView) view.findViewById(R.id.shensha_text_14);
        shensha_15 = (TextView) view.findViewById(R.id.shensha_text_15);
        shensha_16 = (TextView) view.findViewById(R.id.shensha_text_16);

        dizhi_1 = (TextView) view.findViewById(R.id.shensha_dizhi_1);
        dizhi_2 = (TextView) view.findViewById(R.id.shensha_dizhi_2);
        dizhi_3 = (TextView) view.findViewById(R.id.shensha_dizhi_3);
        dizhi_4 = (TextView) view.findViewById(R.id.shensha_dizhi_4);
        dizhi_5 = (TextView) view.findViewById(R.id.shensha_dizhi_5);
        dizhi_6 = (TextView) view.findViewById(R.id.shensha_dizhi_6);
        dizhi_7 = (TextView) view.findViewById(R.id.shensha_dizhi_7);
        dizhi_8 = (TextView) view.findViewById(R.id.shensha_dizhi_8);

        dizhi_9 = (TextView) view.findViewById(R.id.shensha_dizhi_9);
        dizhi_10 = (TextView) view.findViewById(R.id.shensha_dizhi_10);
        dizhi_11 = (TextView) view.findViewById(R.id.shensha_dizhi_11);
        dizhi_12 = (TextView) view.findViewById(R.id.shensha_dizhi_12);
        dizhi_13 = (TextView) view.findViewById(R.id.shensha_dizhi_13);
        dizhi_14 = (TextView) view.findViewById(R.id.shensha_dizhi_14);
        dizhi_15 = (TextView) view.findViewById(R.id.shensha_dizhi_15);
        dizhi_16 = (TextView) view.findViewById(R.id.shensha_dizhi_16);

        try {
            BuildMaps();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return view;

    }

    //建立各种对应关系
    private void BuildMaps() throws ParseException {
        //获取天干
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = new Date();
        cal.setTime(date);
        Birth birth = new Birth(cal);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
//        Map lunar = birth.toLunar();
        String dayTime = sdf.format(date);
        Map map = birth.horoscope(dayTime);
        ganzhi_day = map.get("cD").toString();

        TaoHua_map = new HashMap();
        TaoHua_map.put("子", "酉");
        TaoHua_map.put("丑", "午");
        TaoHua_map.put("寅", "卯");
        TaoHua_map.put("卯", "子");
        TaoHua_map.put("辰", "酉");
        TaoHua_map.put("巳", "午");
        TaoHua_map.put("午", "卯");
        TaoHua_map.put("未", "子");
        TaoHua_map.put("申", "酉");
        TaoHua_map.put("酉", "午");
        TaoHua_map.put("戌", "卯");
        TaoHua_map.put("亥", "子");

        YiMa_map = new HashMap();
        YiMa_map.put("子", "寅");
        YiMa_map.put("丑", "亥");
        YiMa_map.put("寅", "申");
        YiMa_map.put("卯", "巳");
        YiMa_map.put("辰", "寅");
        YiMa_map.put("巳", "亥");
        YiMa_map.put("午", "申");
        YiMa_map.put("未", "巳");
        YiMa_map.put("申", "寅");
        YiMa_map.put("酉", "亥");
        YiMa_map.put("戌", "申");
        YiMa_map.put("亥", "巳");

        JieSha_map = new HashMap();
        JieSha_map.put("子", "巳");
        JieSha_map.put("丑", "寅");
        JieSha_map.put("寅", "亥");
        JieSha_map.put("卯", "申");
        JieSha_map.put("辰", "巳");
        JieSha_map.put("巳", "寅");
        JieSha_map.put("午", "亥");
        JieSha_map.put("未", "申");
        JieSha_map.put("申", "巳");
        JieSha_map.put("酉", "寅");
        JieSha_map.put("戌", "亥");
        JieSha_map.put("亥", "申");

        WangShen_map = new HashMap();
        WangShen_map.put("子", "亥");
        WangShen_map.put("丑", "申");
        WangShen_map.put("寅", "巳");
        WangShen_map.put("卯", "寅");
        WangShen_map.put("辰", "亥");
        WangShen_map.put("巳", "申");
        WangShen_map.put("午", "巳");
        WangShen_map.put("未", "寅");
        WangShen_map.put("申", "亥");
        WangShen_map.put("酉", "申");
        WangShen_map.put("戌", "巳");
        WangShen_map.put("亥", "寅");

        GuaSu_map = new HashMap();
        GuaSu_map.put("子", "戌");
        GuaSu_map.put("丑", "戌");
        GuaSu_map.put("寅", "丑");
        GuaSu_map.put("卯", "丑");
        GuaSu_map.put("辰", "丑");
        GuaSu_map.put("巳", "辰");
        GuaSu_map.put("午", "辰");
        GuaSu_map.put("未", "辰");
        GuaSu_map.put("申", "未");
        GuaSu_map.put("酉", "未");
        GuaSu_map.put("戌", "未");
        GuaSu_map.put("亥", "戌");

        GuChen_map = new HashMap();
        GuChen_map.put("子", "寅");
        GuChen_map.put("丑", "寅");
        GuChen_map.put("寅", "巳");
        GuChen_map.put("卯", "巳");
        GuChen_map.put("辰", "巳");
        GuChen_map.put("巳", "申");
        GuChen_map.put("午", "申");
        GuChen_map.put("未", "申");
        GuChen_map.put("申", "亥");
        GuChen_map.put("酉", "亥");
        GuChen_map.put("戌", "亥");
        GuChen_map.put("亥", "寅");

        HongLuan_map = new HashMap();
        HongLuan_map.put("子", "卯");
        HongLuan_map.put("丑", "寅");
        HongLuan_map.put("寅", "丑");
        HongLuan_map.put("卯", "子");
        HongLuan_map.put("辰", "亥");
        HongLuan_map.put("巳", "戌");
        HongLuan_map.put("午", "酉");
        HongLuan_map.put("未", "申");
        HongLuan_map.put("申", "未");
        HongLuan_map.put("酉", "午");
        HongLuan_map.put("戌", "巳");
        HongLuan_map.put("亥", "辰");

        TianXi_map = new HashMap();
        TianXi_map.put("子", "酉");
        TianXi_map.put("丑", "申");
        TianXi_map.put("寅", "未");
        TianXi_map.put("卯", "午");
        TianXi_map.put("辰", "巳");
        TianXi_map.put("巳", "辰");
        TianXi_map.put("午", "卯");
        TianXi_map.put("未", "寅");
        TianXi_map.put("申", "丑");
        TianXi_map.put("酉", "子");
        TianXi_map.put("戌", "亥");
        TianXi_map.put("亥", "戌");

        YangRen_map = new HashMap();
        YangRen_map.put("甲", "卯");
        YangRen_map.put("乙", "辰");
        YangRen_map.put("丙", "午");
        YangRen_map.put("丁", "未");
        YangRen_map.put("戊", "午");
        YangRen_map.put("己", "未");
        YangRen_map.put("庚", "酉");
        YangRen_map.put("辛", "戌");
        YangRen_map.put("壬", "子");
        YangRen_map.put("癸", "丑");

        GuiRen_map = new HashMap();
        GuiRen_map.put("甲", "丑未");
        GuiRen_map.put("乙", "子申");
        GuiRen_map.put("丙", "亥酉");
        GuiRen_map.put("丁", "亥酉");
        GuiRen_map.put("戊", "丑未");
        GuiRen_map.put("己", "子申");
        GuiRen_map.put("庚", "午寅");
        GuiRen_map.put("辛", "午寅");
        GuiRen_map.put("壬", "卯巳");
        GuiRen_map.put("癸", "卯巳");


    }

    //下拉列表的对应显示，具体要求见业务逻辑文档
    class spinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            switch (position) {
                case 0:
                    shensha_total.setText("常用神煞");
                    shensha_1.setText("干禄");
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

                    dizhi_1.setText("");
                    dizhi_2.setText("");
                    dizhi_3.setText(TianXi_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_4.setText(JieSha_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_5.setText(YangRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_6.setText(TaoHua_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_7.setText(YiMa_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_8.setText(GuiRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_9.setText("");
                    dizhi_10.setText("");
                    dizhi_11.setText("");
                    dizhi_12.setText("");
                    dizhi_13.setText("");
                    dizhi_14.setText("");
                    dizhi_15.setText("");
                    dizhi_16.setText("");
                    break;
                case 1:
                    shensha_total.setText("占感情");
                    shensha_1.setText("灾煞");
                    shensha_2.setText("劫煞");
                    shensha_3.setText("寡宿");
                    shensha_4.setText("孤辰");
                    shensha_5.setText("桃花");
                    shensha_6.setText("红鸾");
                    shensha_7.setText("天喜");
                    shensha_8.setText("羊刃");
                    shensha_9.setText("驿马");
                    shensha_10.setText("月将");
                    shensha_11.setText("日德");
                    shensha_12.setText("贵人");
                    shensha_13.setText("");
                    shensha_14.setText("");
                    shensha_15.setText("");
                    shensha_16.setText("");

                    dizhi_1.setText("");
                    dizhi_2.setText(JieSha_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_3.setText(GuaSu_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_4.setText(GuChen_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_5.setText(TaoHua_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_6.setText(HongLuan_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_7.setText(TianXi_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_8.setText(YangRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_9.setText(YiMa_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_10.setText("");
                    dizhi_11.setText("");
                    dizhi_12.setText(GuiRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_13.setText("");
                    dizhi_14.setText("");
                    dizhi_15.setText("");
                    dizhi_16.setText("");
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

                    dizhi_1.setText("");
                    dizhi_2.setText(YiMa_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_3.setText("");
                    dizhi_4.setText("");
                    dizhi_5.setText(GuiRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_6.setText("");
                    dizhi_7.setText("");
                    dizhi_8.setText("");
                    dizhi_9.setText("");
                    dizhi_10.setText("");
                    dizhi_11.setText("");
                    dizhi_12.setText("");
                    dizhi_13.setText("");
                    dizhi_14.setText("");
                    dizhi_15.setText("");
                    dizhi_16.setText("");
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

                    dizhi_1.setText(YangRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_2.setText("");
                    dizhi_3.setText("");
                    dizhi_4.setText(TianXi_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_5.setText(YiMa_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_6.setText("");
                    dizhi_7.setText("");
                    dizhi_8.setText("");
                    dizhi_9.setText("");
                    dizhi_10.setText("");
                    dizhi_11.setText("");
                    dizhi_12.setText("");
                    dizhi_13.setText("");
                    dizhi_14.setText("");
                    dizhi_15.setText("");
                    dizhi_16.setText("");
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

                    dizhi_1.setText(TianXi_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_2.setText(YangRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_3.setText(JieSha_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_4.setText(WangShen_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_5.setText(GuiRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_6.setText(YiMa_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_7.setText("");
                    dizhi_8.setText("");
                    dizhi_9.setText("");
                    dizhi_10.setText("");
                    dizhi_11.setText("");
                    dizhi_12.setText("");
                    dizhi_13.setText("");
                    dizhi_14.setText("");
                    dizhi_15.setText("");
                    dizhi_16.setText("");
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

                    dizhi_1.setText(GuaSu_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_2.setText(GuChen_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_3.setText("");
                    dizhi_4.setText(WangShen_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_5.setText(JieSha_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_6.setText(YangRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_7.setText(HongLuan_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_8.setText(TianXi_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_9.setText("");
                    dizhi_10.setText("");
                    dizhi_11.setText("");
                    dizhi_12.setText("");
                    dizhi_13.setText("");
                    dizhi_14.setText("");
                    dizhi_15.setText("");
                    dizhi_16.setText("");
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

                    dizhi_1.setText(GuiRen_map.get(ganzhi_day.substring(0, 1)).toString());
                    dizhi_2.setText(YiMa_map.get(ganzhi_day.substring(1, 2)).toString());
                    dizhi_3.setText("");
                    dizhi_4.setText("");
                    dizhi_5.setText("");
                    dizhi_6.setText("");
                    dizhi_7.setText("");
                    dizhi_8.setText("");
                    dizhi_9.setText("");
                    dizhi_10.setText("");
                    dizhi_11.setText("");
                    dizhi_12.setText("");
                    dizhi_13.setText("");
                    dizhi_14.setText("");
                    dizhi_15.setText("");
                    dizhi_16.setText("");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }


    }
}


