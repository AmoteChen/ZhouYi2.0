package example.com.zhouyi_20.activity.Ziding;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.total_result.Suangua_Result;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;
import example.com.zhouyi_20.object.User;

/**
 * Created by ChenSiyuan on 2019/2/26.
 */

public class Zidinggua extends AppCompatActivity implements View.OnClickListener {
    Integer ziding_Result[] = new Integer[6];

    private RadioGroup total_select;
    private RadioGroup sg_select_1;
    private RadioGroup sg_select_2;
    private RadioGroup xg_select_1;
    private RadioGroup xg_select_2;

    private RadioButton zd_total_1;
    private RadioButton zd_total_2;

    private Button button_ok;
    private Button button_back;

    //1为本卦，2为变卦,3什么都没选
    private int bg_selected = 3;

    private String userid;
    private String date;
    private String name;
    private String reason;
    private String note;
    private String way;
    private String yongshen;


    List<Integer> sg_temp = new ArrayList<>();
    List<Integer> xg_temp = new ArrayList<>();


    private int[] send_list1 = new int[6];
    private int[] send_list2 = new int[6];
    private boolean sg_selected = false;
    private boolean xg_selected = false;

    private final String getInput ="http://120.76.128.110:8081/table/origin";
    private final String address ="http://120.76.128.110:12510/app/newRecord";

    //跟踪输入是否转换完成
    private boolean input_changed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ziding_new_record);
        intinize();
        getData();

    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.zd_ok:
                if(zd_total_1.getText().length()==18&&zd_total_2.getText().length()==18) {
                    HttpsConnect.sendRequest(getInput, "POST", getJsonData(), new HttpsListener() {
                        @Override
                        public void success(String response) {
                            catchResponse(response);
                        }

                        @Override
                        public void failed(Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                }
                else {
                    Toast.makeText(this, "输入错误！请仔细检查选择卦象",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.zd_back:
                finish();
                break;
            default:
                break;
        }
    }
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

    private void intinize(){
        total_select = (RadioGroup)findViewById(R.id.top_total);

        sg_select_1 = (RadioGroup)findViewById(R.id.sg_group_1);
        sg_select_2 = (RadioGroup)findViewById(R.id.sg_group_2);
        xg_select_1 = (RadioGroup)findViewById(R.id.xg_group_1);
        xg_select_2 = (RadioGroup)findViewById(R.id.xg_group_2);

        zd_total_1 = (RadioButton)findViewById(R.id.zd_total_1);
        zd_total_2 = (RadioButton)findViewById(R.id.zd_total_2);

        button_ok = (Button)findViewById(R.id.zd_ok);
        button_ok.setOnClickListener(this);

        button_back = (Button)findViewById(R.id.zd_back);
        button_back.setOnClickListener(this);





        total_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.zd_total_1:
                        bg_selected = 1;
                        break;
                    case R.id.zd_total_2:
                        bg_selected = 2;
                        break;
                }
            }
            });
        xg_select_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.xg_1_1:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_2.clearCheck();
                        xg_temp.add(0);
                        xg_temp.add(0);
                        xg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.qian_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.qian_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.xg_1_2:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_2.clearCheck();
                        xg_temp.add(1);
                        xg_temp.add(0);
                        xg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.dui_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.dui_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.xg_1_3:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_2.clearCheck();
                        xg_temp.add(0);
                        xg_temp.add(1);
                        xg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.li_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.li_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.xg_1_4:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_2.clearCheck();
                        xg_temp.add(1);
                        xg_temp.add(1);
                        xg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.zhen_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.zhen_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;

                    default:
                        break;
                }
            }
        });
        xg_select_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.xg_2_1:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_1.clearCheck();
                        xg_temp.add(0);
                        xg_temp.add(0);
                        xg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.xun_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.xun_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.xg_2_2:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_1.clearCheck();
                        xg_temp.add(1);
                        xg_temp.add(0);
                        xg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kan_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kan_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.xg_2_3:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_1.clearCheck();
                        xg_temp.add(0);
                        xg_temp.add(1);
                        xg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.ken_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.ken_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.xg_2_4:
                        xg_temp.clear();
                        sg_temp=new ArrayList<>(sg_temp.subList(0,3));
                        xg_select_1.clearCheck();
                        xg_temp.add(1);
                        xg_temp.add(1);
                        xg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kun_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_xg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kun_path));
                        }
                        xg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    default:
                        break;
                }
            }
        });
        sg_select_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sg_1_1:
                        sg_temp.clear();
                        sg_select_2.clearCheck();
                        sg_temp.add(0);
                        sg_temp.add(0);
                        sg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.qian_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.qian_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.sg_1_2:
                        sg_temp.clear();
                        sg_select_2.clearCheck();
                        sg_temp.add(1);
                        sg_temp.add(0);
                        sg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.dui_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.dui_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.sg_1_3:
                        sg_temp.clear();
                        sg_select_2.clearCheck();
                        sg_temp.add(0);
                        sg_temp.add(1);
                        sg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.li_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.li_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.sg_1_4:
                        sg_temp.clear();
                        sg_select_2.clearCheck();
                        sg_temp.add(1);
                        sg_temp.add(1);
                        sg_temp.add(0);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.zhen_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.zhen_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;

                    default:
                        break;
                }
            }
        });
        sg_select_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sg_2_1:
                        sg_temp.clear();
                        sg_select_1.clearCheck();
                        sg_temp.add(0);
                        sg_temp.add(0);
                        sg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.xun_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.xun_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.sg_2_2:
                        sg_temp.clear();
                        sg_select_1.clearCheck();
                        sg_temp.add(1);
                        sg_temp.add(0);
                        sg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kan_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kan_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.sg_2_3:
                        sg_temp.clear();
                        sg_select_1.clearCheck();
                        sg_temp.add(0);
                        sg_temp.add(1);
                        sg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.ken_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.ken_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    case R.id.sg_2_4:
                        sg_temp.clear();
                        sg_select_1.clearCheck();
                        sg_temp.add(1);
                        sg_temp.add(1);
                        sg_temp.add(1);
                        if(bg_selected==1){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_bengua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kun_path));
                        }
                        if(bg_selected==2){
                            ImageView imageView = (ImageView)findViewById(R.id.ziding_biangua_image_sg);
                            imageView.setBackground(getResources().getDrawable(R.drawable.kun_path));
                        }
                        sg_selected = true;
                        setList(sg_selected,xg_selected,bg_selected);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setList(boolean sg,boolean xg, int bg_selected){
        List<Integer> temp = new ArrayList<>();
        if(sg&&xg&&bg_selected==1){
            sg_temp.addAll(xg_temp);
            temp.clear();
            temp.addAll(sg_temp);
            for (int i =0;i<6;i++){
                send_list1[i]=(int)temp.get(i);
            }

            String string = "";
            string="["+temp.get(0);
            for (int i=1;i<6;++i)
                string+=","+temp.get(i);
            string+="]";

            zd_total_1.setText(temp.toString());

            Ziding_Map ziding_map = new Ziding_Map();
            TextView textView = (TextView)findViewById(R.id.ziding_bengua_text);
            textView.setText(ziding_map.get(string));
        }
        if(sg&&xg&&bg_selected==2){
            sg_temp.addAll(xg_temp);
            temp.clear();
            temp.addAll(sg_temp);
            for (int i =0;i<6;i++){
                send_list2[i]=(int)temp.get(i);
            }
            zd_total_2.setText(temp.toString());

            String string = "";
            string="["+temp.get(0);
            for (int i=1;i<6;++i)
                string+=","+temp.get(i);
            string+="]";

            Ziding_Map ziding_map = new Ziding_Map();
            TextView textView = (TextView)findViewById(R.id.ziding_biangua_text);
            textView.setText(ziding_map.get(string));
        }

    }

    private JSONObject getJsonData(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray_1 = new JSONArray();
        JSONArray jsonArray_2 = new JSONArray();
        for (int i=0;i<6;++i)
        {
            jsonArray_1.put(send_list1[i]);
        }
        for (int i=0;i<6;++i)
        {
            jsonArray_2.put(send_list2[i]);
        }

        try {
            jsonObject.put("list1",jsonArray_1);
            Log.e("AAAAmessage11",jsonArray_1.toString());
            jsonObject.put("list2",jsonArray_2);
            Log.e("AAAAmessage111",jsonArray_2.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject RootJsonObject = new JSONObject(response);
                    String result = RootJsonObject.getString("message");
                    Log.e("AAAAmessage",result);
                    JSONArray jsonArray = RootJsonObject.getJSONArray("data");
                    Log.e("MMMMMFFQQ",jsonArray.toString());

                    Intent to_suangua_result = new Intent(Zidinggua.this,Suangua_Result.class);
                    Bundle bundle=new Bundle();
                    for (int i = 0;i<6;i++){
                        ziding_Result[i]=jsonArray.getInt(i);
                    }
                    //转换完成
                    input_changed=true;
                    bundle.putSerializable("LiuYaoData",ziding_Result);
                    to_suangua_result.putExtras(bundle);
                    to_suangua_result.putExtra("from","ziding");
                    to_suangua_result.putExtra("date",date);
                    to_suangua_result.putExtra("way",way);
                    to_suangua_result.putExtra("reason",reason);
                    to_suangua_result.putExtra("name",name);
                    to_suangua_result.putExtra("note",note);
                    to_suangua_result.putExtra("yongshen",yongshen);

                    if(input_changed){
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
                    }

                    startActivity(to_suangua_result);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private JSONObject getJsonData_NR(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<6;i++){
            jsonArray.put(ziding_Result[i]);
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
                        Toast.makeText(Zidinggua.this,"记录存储成功！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
