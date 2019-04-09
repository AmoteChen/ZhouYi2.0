package example.com.zhouyi_20.activity.Number;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.Suangua_Result;
import example.com.zhouyi_20.activity.Ziding.Zidinggua;
import example.com.zhouyi_20.object.HttpsConnect;
import example.com.zhouyi_20.object.HttpsListener;
import example.com.zhouyi_20.object.User;

/**
 * Created by ChenSiyuan on 2019/4/8.
 */

public class Numbergua extends AppCompatActivity implements View.OnClickListener{

    private String userid;
    private String date;
    private String name;
    private String reason;
    private String note;
    private String way;
    private String yongshen;
    private Integer Number_Result[] = new Integer[6];

    private EditText edit_gua1;
    private EditText edit_gua2;
    private EditText edit_gua3;
    private EditText edit_gua4;
    private EditText edit_gua5;
    private EditText edit_gua6;

    private final String address ="http://120.76.128.110:12510/app/newRecord";

    private Button button_ok;
    private Button button_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_new_record);
        intinize();
        getData();

    }

    private void intinize(){
        button_ok = (Button)findViewById(R.id.number_ok);
        button_ok.setOnClickListener(this);

        button_back = (Button)findViewById(R.id.number_back);
        button_back.setOnClickListener(this);

        edit_gua1 = (EditText)findViewById(R.id.number_gua_1);
        edit_gua2 = (EditText)findViewById(R.id.number_gua_2);
        edit_gua3 = (EditText)findViewById(R.id.number_gua_3);
        edit_gua4 = (EditText)findViewById(R.id.number_gua_4);
        edit_gua5 = (EditText)findViewById(R.id.number_gua_5);
        edit_gua6 = (EditText)findViewById(R.id.number_gua_6);

        edit_gua1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        edit_gua2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        edit_gua3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        edit_gua4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        edit_gua5.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        edit_gua6.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});




    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.number_ok:
                Getguaxiang();
                //储存历史记录
                HttpsConnect.sendRequest(address, "POST", getJsonData_NR(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchResponse_NR(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
                //发送获取的起卦信息
                Intent to_suangua_result = new Intent(Numbergua.this,Suangua_Result.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("LiuYaoData",Number_Result);
                to_suangua_result.putExtras(bundle);
                to_suangua_result.putExtra("from","number");
                to_suangua_result.putExtra("date",date);
                to_suangua_result.putExtra("way",way);
                to_suangua_result.putExtra("reason",reason);
                to_suangua_result.putExtra("name",name);
                to_suangua_result.putExtra("note",note);
                to_suangua_result.putExtra("yongshen",yongshen);
                startActivity(to_suangua_result);
                break;
            case R.id.number_back:
                finish();
                break;
            default:
                break;
        }
    }
    //获取起卦信息
    private void getData(){
        Intent intent = getIntent();
        userid = User.getId();
        date = intent.getStringExtra("date");
        name = intent.getStringExtra("name");
        reason = intent.getStringExtra("reason");
        note = intent.getStringExtra("note");
        way = intent.getStringExtra("way");
        yongshen = intent.getStringExtra("yongshen");
    }
    //包装历史记录中的起卦信息
    private JSONObject getJsonData_NR(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<6;i++){
            jsonArray.put(Number_Result[i]);
        }
        try{
            jsonObject.put("userid",userid);
            jsonObject.put("date",date);
            jsonObject.put("name",name);
            jsonObject.put("reason",reason);
            jsonObject.put("note",note);
            jsonObject.put("way",way);
            jsonObject.put("yongshen",yongshen);
            jsonObject.put("guaxiang",jsonArray);
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponse_NR(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.compareTo("success")==0){
                        Toast.makeText(Numbergua.this,"记录存储成功！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //获取起卦的数字
    private void Getguaxiang(){
        Number_Result[0]=Integer.parseInt(edit_gua6.getText().toString());
        Number_Result[1]=Integer.parseInt(edit_gua5.getText().toString());
        Number_Result[2]=Integer.parseInt(edit_gua4.getText().toString());
        Number_Result[3]=Integer.parseInt(edit_gua3.getText().toString());
        Number_Result[4]=Integer.parseInt(edit_gua2.getText().toString());
        Number_Result[5]=Integer.parseInt(edit_gua1.getText().toString());

    }
}
