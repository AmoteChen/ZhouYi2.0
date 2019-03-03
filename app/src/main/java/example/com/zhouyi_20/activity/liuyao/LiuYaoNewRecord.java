package example.com.zhouyi_20.activity.liuyao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.liuyao.LiuYaoJinqiangua;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class LiuYaoNewRecord extends AppCompatActivity implements View.OnClickListener {
    Button btn_ok;
    Button btn_cancel;
    Spinner yongshen;
    EditText shiyou;
    public static String yongshen_selected;
    public static String shiyou_string;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liuyao_new_record);

        initial();

        btn_ok=(Button)findViewById(R.id.liuyao_new_record_ok_btn);
        btn_ok.setOnClickListener(this);
        btn_cancel=(Button)findViewById(R.id.liuyao_new_record_cancel_btn);
        btn_cancel.setOnClickListener(this);
        yongshen=(Spinner)findViewById(R.id.yongshen_spinner);
        shiyou=(EditText)findViewById(R.id.liuyao_new_record_shiyou);
        spinner_init();

    }

    private void initial(){
        EditText bugua_date_text=(EditText)findViewById(R.id.liuyao_new_record_bugua_date);

        Calendar cal= Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year= String.valueOf(cal.get(Calendar.YEAR));
        String month= String.valueOf(cal.get(Calendar.MONTH)+1);
        String day= String.valueOf(cal.get(Calendar.DATE));
        String date=year+"年"+month+"月"+day+"日";
        bugua_date_text.setText(date);
    }

    private void spinner_init(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("父母");
        list.add("兄弟");
        list.add("官鬼");
        list.add("子孙");
        list.add("妻财");
        list.add("世");
        list.add("应");
        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.yongshen_item, R.id.yongshen_textview,list);
        yongshen.setAdapter(adapter2);
        yongshen.setOnItemSelectedListener(new spinner2Listener());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.liuyao_new_record_cancel_btn:
                finish();
                break;
            case R.id.liuyao_new_record_ok_btn:
                getShiyou();
                Intent to_LiuyaoJinqiangua=new Intent(this,LiuYaoJinqiangua.class);
                startActivity(to_LiuyaoJinqiangua);
                break;
            default:
                break;
        }
    }

    class spinner2Listener implements android.widget.AdapterView.OnItemSelectedListener{


        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
            yongshen_selected=selected;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    public String getShiyou(){
        shiyou_string = shiyou.getText().toString();
        return shiyou_string;
    }
}
