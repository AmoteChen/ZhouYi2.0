package example.com.zhouyi_20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.Ziding.Zidinggua;
import example.com.zhouyi_20.activity.liuyao.LiuYaoJinqiangua;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class NewRecord extends AppCompatActivity implements View.OnClickListener {
    Button btn_ok;
    Button btn_cancel;
    Spinner yongshen_spinner;
    EditText shiyou_edit;
    EditText name_edit;
    EditText note_edit;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record);

        btn_ok=(Button)findViewById(R.id.liuyao_new_record_ok_btn);
        btn_ok.setOnClickListener(this);
        btn_cancel=(Button)findViewById(R.id.liuyao_new_record_cancel_btn);
        btn_cancel.setOnClickListener(this);

        yongshen_spinner =(Spinner)findViewById(R.id.yongshen_spinner);
        shiyou_edit =(EditText)findViewById(R.id.liuyao_new_record_shiyou);
        name_edit= (EditText)findViewById(R.id.liuyao_new_record_name);
        note_edit=(EditText)findViewById(R.id.liuyao_new_record_note);

        textView_way=(TextView)findViewById(R.id.liuyao_new_record_way);
        bugua_date_text=(EditText)findViewById(R.id.liuyao_new_record_bugua_date);

        Intent intent = getIntent();
        way = intent.getStringExtra("way");
        from = intent.getStringExtra("from");

        if(intent.getStringExtra("from").equals("liuyao")||intent.getStringExtra("from").equals("ziding")){
            initial();
            way_init(way);

            spinner_init();
        }
        if(intent.getStringExtra("from").equals("history")){
            String way = intent.getStringExtra("way").toString();
            String time = intent.getStringExtra("time").toString();
            String reason = intent.getStringExtra("reason").toString();
            String name = intent.getStringExtra("name").toString();
            String note = intent.getStringExtra("note").toString();
            spinner_init();
            history_init(way,time,reason,name,note);
        }

    }

    private void initial(){

        Calendar cal= Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year= String.valueOf(cal.get(Calendar.YEAR));
        String month= String.valueOf(cal.get(Calendar.MONTH)+1);
        String day= String.valueOf(cal.get(Calendar.DATE));
        date=year+"年"+month+"月"+day+"日";
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
        yongshen_spinner.setAdapter(adapter2);
        yongshen_spinner.setOnItemSelectedListener(new spinner2Listener());

    }

    private void way_init(String way){
        textView_way.setText(way);
    }
    private void history_init(String way,String time,String reason,String name,String note){
        textView_way.setText(way);
        bugua_date_text.setText(time);
        shiyou_edit.setText(reason);
        name_edit.setText(name);
        note_edit.setText(note);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.liuyao_new_record_cancel_btn:
                restartApplication();
                break;
            case R.id.liuyao_new_record_ok_btn:
                if(getShiyou_edit().isEmpty()||getName().isEmpty()) {
                    Toast.makeText(NewRecord.this,"事由或姓名不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(way.equals("六爻")&&from.equals("liuyao")){
                    BindDate();
                    Intent to_LiuyaoJinqiangua=new Intent(this,LiuYaoJinqiangua.class);
                    to_LiuyaoJinqiangua.putExtra("date",date);
                    to_LiuyaoJinqiangua.putExtra("way",way);
                    to_LiuyaoJinqiangua.putExtra("reason",reason);
                    to_LiuyaoJinqiangua.putExtra("name",name);
                    to_LiuyaoJinqiangua.putExtra("note",note);
                    to_LiuyaoJinqiangua.putExtra("yongshen",yongshen_selected);
                    startActivity(to_LiuyaoJinqiangua);}

                if(way.equals("自定")&&from.equals("ziding")){
                    BindDate();
                    Intent to_Zidinggua=new Intent(this,Zidinggua.class);
                    to_Zidinggua.putExtra("date",date);
                    to_Zidinggua.putExtra("way",way);
                    to_Zidinggua.putExtra("reason",reason);
                    to_Zidinggua.putExtra("name",name);
                    to_Zidinggua.putExtra("note",note);
                    to_Zidinggua.putExtra("yongshen",yongshen_selected);
                    startActivity(to_Zidinggua);
                }
                if(from.equals("history")){
                    Intent to_result=new Intent(this,Suangua_Result.class);
                    startActivity(to_result);
                }
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

    public String getShiyou_edit(){
        shiyou_string = shiyou_edit.getText().toString();
        return shiyou_string;
    }
    public String getName(){
        name_string = name_edit.getText().toString();
        return name_string;
    }

    private void BindDate(){
        name = name_edit.getText().toString();
        reason = shiyou_edit.getText().toString();
        note = note_edit.getText().toString();
    }

    private void restartApplication() {
        final Intent intent =  new Intent();

        intent.setClass(this,Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            Toast.makeText(this,"请点击取消按钮以返回",Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }
}
