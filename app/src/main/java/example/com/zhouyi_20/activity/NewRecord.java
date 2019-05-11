package example.com.zhouyi_20.activity;

import android.content.DialogInterface;
import android.content.Intent;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.Number.Numbergua;
import example.com.zhouyi_20.activity.Ziding.Ziding_Map;
import example.com.zhouyi_20.activity.Ziding.Zidinggua;
import example.com.zhouyi_20.activity.liuyao.LiuYaoJinqiangua;
import example.com.zhouyi_20.activity.total_result.Suangua_Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class NewRecord extends AppCompatActivity implements View.OnClickListener {
    Button btn_ok;
    Button btn_cancel;
    Spinner yongshen_spinner;
    EditText shiyou_edit;
    EditText name_edit;
    EditText note_edit;

    ImageView benguaShang;
    ImageView bianguaShang;
    ImageView benguaXia;
    ImageView bianguaXia;
    TextView zuo;
    TextView you;
    //此处数据用于判断和具体的算卦处理
    public static String yongshen_selected;
    public static String shiyou_string;
    public static String name_string;

    private TextView textView_way;
    private EditText bugua_date_text;
    public String from;

    //此处数据用于向卦象页面发送数据
    public String way;
    public String date;
    public String name;
    public String reason;
    public String note;
    private Integer GuaXiang[] = new Integer[6];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhanbu_record);

        btn_ok = (Button) findViewById(R.id.liuyao_new_record_ok_btn);
        btn_ok.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.liuyao_new_record_cancel_btn);
        btn_cancel.setOnClickListener(this);

        yongshen_spinner = (Spinner) findViewById(R.id.yongshen_spinner);
        shiyou_edit = (EditText) findViewById(R.id.liuyao_new_record_shiyou);
        name_edit = (EditText) findViewById(R.id.liuyao_new_record_name);
        note_edit = (EditText) findViewById(R.id.liuyao_new_record_note);

        textView_way = (TextView) findViewById(R.id.liuyao_new_record_way);
        bugua_date_text = (EditText) findViewById(R.id.liuyao_new_record_bugua_date);


        Intent intent = getIntent();
        way = intent.getStringExtra("way");
        from = intent.getStringExtra("from");

        if (intent.getStringExtra("from").equals("liuyao") || intent.getStringExtra("from").equals("ziding") || intent.getStringExtra("from").equals("number")) {
            initial();
            way_init(way);

            spinner_init();
        }
        if (intent.getStringExtra("from").equals("history")) {
            initLiangGeTu();
            way = intent.getStringExtra("way").toString();
            date = intent.getStringExtra("time").toString();
            reason = intent.getStringExtra("reason").toString();
            name = intent.getStringExtra("name").toString();
            note = intent.getStringExtra("note").toString();
            spinner_init();
            history_init(way, date, reason, name, note);
        }

    }

    /**
     * 这是对两个图的初始化
     */
    private void initLiangGeTu() {
        //这是我新增加的本挂和变卦的imageview
        benguaShang = (ImageView) findViewById(R.id.liuyao_new_record_bengua_shang);
        bianguaShang = (ImageView) findViewById(R.id.liuyao_new_record_biangua_shang);
        bianguaXia = (ImageView) findViewById(R.id.liuyao_new_record_biangua_xia);
        benguaXia = (ImageView) findViewById(R.id.liuyao_new_record_bengua_xia);
        zuo = (TextView) findViewById(R.id.textView);
        you = (TextView) findViewById(R.id.textView3);
        //下面获取intent中的guaxiang信息
        Integer gua[] = (Integer[]) getIntent().getSerializableExtra("LiuYaoData");
        int guaxiang[] = new int[6];
        for (int i = 0; i < 6; i++) {
            guaxiang[i] = gua[i].intValue();
        }
        int bengua[] = guaxiang;
        int biangua[] = guaxiang;
        Ziding_Map ziding_map = new Ziding_Map();
        String temp;
        //处理本挂
        runFirstMethod(bengua);
        guaxiangMap01(bengua[0], bengua[1], bengua[2], 1);
        guaxiangMap01(bengua[3], bengua[4], bengua[5], 2);
        //获得下面的字
        String stringToGetText = "";
        stringToGetText = "[" + bengua[0];
        for (int i = 1; i < 6; ++i)
            stringToGetText += "," + bengua[i];
        stringToGetText += "]";
        temp = ziding_map.get(stringToGetText);
        String temparray[] = temp.split("\n", 2);

        //处理变卦
        runSecondMethod(biangua);
        guaxiangMap01(biangua[0], biangua[1], biangua[2], 3);
        guaxiangMap01(biangua[3], biangua[4], biangua[5], 4);
        stringToGetText = "[" + biangua[0];
        for (int i = 1; i < 6; ++i)
            stringToGetText += "," + biangua[i];
        stringToGetText += "]";
        temp = ziding_map.get(stringToGetText);
        String temparray2[] = temp.split("\n", 2);
        zuo.setText(temparray2[1]);
        you.setText(temparray[1]);
    }

    /**
     * @param a
     * @param b
     * @param c      这是将三个数映射到四个图中的一个
     * @param biaoji 1=本挂上 2=本挂下 3=变卦上 4=变卦下
     */
    private void guaxiangMap(int a, int b, int c, int biaoji) {
        switch (biaoji) {
            case 1://本挂上
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        benguaShang.setImageResource(R.drawable.qian_path);
                                        break;
                                    case 1://001
                                        benguaShang.setImageResource(R.drawable.xun_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        benguaShang.setImageResource(R.drawable.li_path);
                                        break;
                                    case 1://011
                                        benguaShang.setImageResource(R.drawable.ken_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        benguaShang.setImageResource(R.drawable.dui_path);
                                        break;
                                    case 1://101
                                        benguaShang.setImageResource(R.drawable.kan_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        benguaShang.setImageResource(R.drawable.zhen_path);
                                        break;
                                    case 1://111
                                        benguaShang.setImageResource(R.drawable.kun_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
            case 2:
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        benguaXia.setImageResource(R.drawable.qian_path);
                                        break;
                                    case 1://001
                                        benguaXia.setImageResource(R.drawable.xun_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        benguaXia.setImageResource(R.drawable.li_path);
                                        break;
                                    case 1://011
                                        benguaXia.setImageResource(R.drawable.ken_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        benguaXia.setImageResource(R.drawable.dui_path);
                                        break;
                                    case 1://101
                                        benguaXia.setImageResource(R.drawable.kan_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        benguaXia.setImageResource(R.drawable.zhen_path);
                                        break;
                                    case 1://111
                                        benguaXia.setImageResource(R.drawable.kun_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
            case 3:
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        bianguaShang.setImageResource(R.drawable.qian_path);
                                        break;
                                    case 1://001
                                        bianguaShang.setImageResource(R.drawable.xun_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        bianguaShang.setImageResource(R.drawable.li_path);
                                        break;
                                    case 1://011
                                        bianguaShang.setImageResource(R.drawable.ken_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        bianguaShang.setImageResource(R.drawable.dui_path);
                                        break;
                                    case 1://101
                                        bianguaShang.setImageResource(R.drawable.kan_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        bianguaShang.setImageResource(R.drawable.zhen_path);
                                        break;
                                    case 1://111
                                        bianguaShang.setImageResource(R.drawable.kun_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
            case 4:
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        bianguaXia.setImageResource(R.drawable.qian_path);
                                        break;
                                    case 1://001
                                        bianguaXia.setImageResource(R.drawable.xun_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        bianguaXia.setImageResource(R.drawable.li_path);
                                        break;
                                    case 1://011
                                        bianguaXia.setImageResource(R.drawable.ken_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        bianguaXia.setImageResource(R.drawable.dui_path);
                                        break;
                                    case 1://101
                                        bianguaXia.setImageResource(R.drawable.kan_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        bianguaXia.setImageResource(R.drawable.zhen_path);
                                        break;
                                    case 1://111
                                        bianguaXia.setImageResource(R.drawable.kun_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
        }
    }

    /**
     * @param a
     * @param b
     * @param c
     * @param biaoji 01转换
     */
    private void guaxiangMap01(int a, int b, int c, int biaoji) {
        switch (biaoji) {
            case 1://本挂上
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        benguaShang.setImageResource(R.drawable.kun_path);
                                        break;
                                    case 1://001
                                        benguaShang.setImageResource(R.drawable.zhen_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        benguaShang.setImageResource(R.drawable.kan_path);
                                        break;
                                    case 1://011
                                        benguaShang.setImageResource(R.drawable.dui_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        benguaShang.setImageResource(R.drawable.ken_path);
                                        break;
                                    case 1://101
                                        benguaShang.setImageResource(R.drawable.li_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        benguaShang.setImageResource(R.drawable.xun_path);
                                        break;
                                    case 1://111
                                        benguaShang.setImageResource(R.drawable.qian_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
            case 2:
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        benguaXia.setImageResource(R.drawable.kun_path);
                                        break;
                                    case 1://001
                                        benguaXia.setImageResource(R.drawable.zhen_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        benguaXia.setImageResource(R.drawable.kan_path);
                                        break;
                                    case 1://011
                                        benguaXia.setImageResource(R.drawable.dui_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        benguaXia.setImageResource(R.drawable.ken_path);
                                        break;
                                    case 1://101
                                        benguaXia.setImageResource(R.drawable.li_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        benguaXia.setImageResource(R.drawable.xun_path);
                                        break;
                                    case 1://111
                                        benguaXia.setImageResource(R.drawable.qian_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
            case 3:
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        bianguaShang.setImageResource(R.drawable.kun_path);
                                        break;
                                    case 1://001
                                        bianguaShang.setImageResource(R.drawable.zhen_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        bianguaShang.setImageResource(R.drawable.kan_path);
                                        break;
                                    case 1://011
                                        bianguaShang.setImageResource(R.drawable.dui_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        bianguaShang.setImageResource(R.drawable.ken_path);
                                        break;
                                    case 1://101
                                        bianguaShang.setImageResource(R.drawable.li_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        bianguaShang.setImageResource(R.drawable.xun_path);
                                        break;
                                    case 1://111
                                        bianguaShang.setImageResource(R.drawable.qian_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
            case 4:
                switch (a) {
                    case 0:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://000
                                        bianguaXia.setImageResource(R.drawable.kun_path);
                                        break;
                                    case 1://001
                                        bianguaXia.setImageResource(R.drawable.zhen_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://010
                                        bianguaXia.setImageResource(R.drawable.kan_path);
                                        break;
                                    case 1://011
                                        bianguaXia.setImageResource(R.drawable.dui_path);
                                        break;
                                }
                                break;
                        }
                        break;
                    case 1:
                        switch (b) {
                            case 0:
                                switch (c) {
                                    case 0://100
                                        bianguaXia.setImageResource(R.drawable.ken_path);
                                        break;
                                    case 1://101
                                        bianguaXia.setImageResource(R.drawable.li_path);
                                        break;
                                }
                                break;
                            case 1:
                                switch (c) {
                                    case 0://110
                                        bianguaXia.setImageResource(R.drawable.xun_path);
                                        break;
                                    case 1://111
                                        bianguaXia.setImageResource(R.drawable.qian_path);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
        }
    }

    private void initial() {

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date day = new Date();
        cal.setTime(day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date = sdf.format(day);
        bugua_date_text.setText(date);
        bugua_date_text.setOnClickListener(this);
    }

    private void spinner_init() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("用爻");
        list.add("父母");
        list.add("兄弟");
        list.add("官鬼");
        list.add("子孙");
        list.add("妻财");
        list.add("世");
        list.add("应");
        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.yongshen_item, R.id.yongshen_textview, list);
        yongshen_spinner.setAdapter(adapter2);
        yongshen_spinner.setOnItemSelectedListener(new spinner2Listener());

    }

    private void way_init(String way) {
        textView_way.setText(way);
    }

    private void history_init(String way, String time, String reason, String name, String note) {
        textView_way.setText(way);
        bugua_date_text.setText(time);
        shiyou_edit.setText(reason);
        name_edit.setText(name);
        note_edit.setText(note);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liuyao_new_record_cancel_btn:
                restartApplication();
                break;
            case R.id.liuyao_new_record_ok_btn:
                if (getShiyou_edit().isEmpty() || getName().isEmpty()) {
                    Toast.makeText(NewRecord.this, "事由或姓名不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (way.equals("六爻") && from.equals("liuyao")) {
                    BindDate();
                    Intent to_LiuyaoJinqiangua = new Intent(this, LiuYaoJinqiangua.class);
                    to_LiuyaoJinqiangua.putExtra("date", date);
                    to_LiuyaoJinqiangua.putExtra("way", way);
                    to_LiuyaoJinqiangua.putExtra("reason", reason);
                    to_LiuyaoJinqiangua.putExtra("name", name);
                    to_LiuyaoJinqiangua.putExtra("note", note);
                    to_LiuyaoJinqiangua.putExtra("yongshen", yongshen_selected);
                    startActivity(to_LiuyaoJinqiangua);
                }

                if (way.equals("自定") && from.equals("ziding")) {
                    BindDate();
                    Intent to_Zidinggua = new Intent(this, Zidinggua.class);
                    to_Zidinggua.putExtra("date", date);
                    to_Zidinggua.putExtra("way", way);
                    to_Zidinggua.putExtra("reason", reason);
                    to_Zidinggua.putExtra("name", name);
                    to_Zidinggua.putExtra("note", note);
                    to_Zidinggua.putExtra("yongshen", yongshen_selected);
                    startActivity(to_Zidinggua);
                }
                if (way.equals("数字") && from.equals("number")) {
                    BindDate();
                    Intent to_Numbergua = new Intent(this, Numbergua.class);
                    to_Numbergua.putExtra("date", date);
                    to_Numbergua.putExtra("way", way);
                    to_Numbergua.putExtra("reason", reason);
                    to_Numbergua.putExtra("name", name);
                    to_Numbergua.putExtra("note", note);
                    to_Numbergua.putExtra("yongshen", yongshen_selected);
                    startActivity(to_Numbergua);
                }

                if (from.equals("history")) {
                    Intent intent = getIntent();
                    GuaXiang = (Integer[]) intent.getSerializableExtra("LiuYaoData");
                    BindDate();
                    Intent to_result = new Intent(this, Suangua_Result.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("LiuYaoData", GuaXiang);
                    to_result.putExtras(bundle);
                    to_result.putExtra("date", date);
                    to_result.putExtra("way", way);
                    to_result.putExtra("reason", reason);
                    to_result.putExtra("name", name);
                    to_result.putExtra("note", note);
                    to_result.putExtra("yongshen", yongshen_selected);
                    startActivity(to_result);
                }
                break;
            case R.id.liuyao_new_record_bugua_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                View dialogView = View.inflate(this, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
                final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);
                timePicker.setIs24HourView(true);

                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            date = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() + " " + timePicker.getHour() + ":" + timePicker.getMinute();
                            Log.e("DDDDD", date);
                        }
                        bugua_date_text.setText(date);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();

                break;
            default:
                break;
        }
    }

    class spinner2Listener implements android.widget.AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
            yongshen_selected = selected;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    public String getShiyou_edit() {
        shiyou_string = shiyou_edit.getText().toString();
        return shiyou_string;
    }

    public String getName() {
        name_string = name_edit.getText().toString();
        return name_string;
    }

    private void BindDate() {
        name = name_edit.getText().toString();
        reason = shiyou_edit.getText().toString();
        note = note_edit.getText().toString();
    }

    private void restartApplication() {
        final Intent intent = new Intent();

        intent.setClass(this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    /**
     * @param event 点击系统返回按钮提示点击上方按钮返回
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "请点击取消按钮以返回", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    /**
     * @param input 卦象数据变本卦
     */
    public static void runFirstMethod(int[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = 1 - input[i] % 2;
        }
    }

    /**
     * @param input 卦象数据变变卦
     */
    public static void runSecondMethod(int[] input) {
        for (int i = 0; i < 4; i++) {
            if (input[i] == 6) {
                input[i] = 9;
            } else if (input[i]==9){
                input[i]=6;
            }
        }
        for (int i = 0; i < input.length; i++) {
            input[i] = 1 - input[i] % 2;
        }
    }
}