package example.com.zhouyi_20.activity.liuyao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.Suangua_Result;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;
import example.com.zhouyi_20.object.User;
import example.com.zhouyi_20.util.DensityUtil;

import java.util.Arrays;
import java.util.Random;

public class LiuYaoJinqiangua extends AppCompatActivity implements View.OnClickListener {
    Button btn_ok;
    Button btn_cancel;
    Integer jinqiangua_Result[];

    Button btn_random_6;
    Button btn_random_5;
    Button btn_random_4;
    Button btn_random_3;
    Button btn_random_2;
    Button btn_random_1;

    ConstraintLayout linearLayout_6;
    ConstraintLayout linearLayout_5;
    ConstraintLayout linearLayout_4;
    ConstraintLayout linearLayout_3;
    ConstraintLayout linearLayout_2;
    ConstraintLayout linearLayout_1;

    SensorManager sensorManager;
    final float ACCELERATION_SENSOR_RING_VALUE=25;
    boolean ring_flag=false;

    // 点击后弹框，再次点击弹框内的行，设置外层窗口相应行的值
    AlertDialog alertDialog;
    int settingRow;
    int settingCase;

    //所有的与后端交互的数据
    private String userid;
    private String date;
    private String name;
    private String reason;
    private String note;
    private String way;
    private String yongshen;
    private int[] guaxiang = new int[6];

    private final String address ="http://120.76.128.110:12510/app/newRecord";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liuyao_new_record);

        btn_ok=(Button)findViewById(R.id.liuyao_jinqiangua_ok_btn);
        btn_ok.setOnClickListener(this);
        btn_cancel=(Button)findViewById(R.id.jinqiangua_cancel_btn);
        btn_cancel.setOnClickListener(this);

        btn_random_6=(Button)findViewById(R.id.jinqiangua_random_btn_6);
        btn_random_5=(Button)findViewById(R.id.jinqiangua_random_btn_5);
        btn_random_4=(Button)findViewById(R.id.jinqiangua_random_btn_4);
        btn_random_3=(Button)findViewById(R.id.jinqiangua_random_btn_3);
        btn_random_2=(Button)findViewById(R.id.jinqiangua_random_btn_2);
        btn_random_1=(Button)findViewById(R.id.jinqiangua_random_btn_1);
        btn_random_6.setOnClickListener(this);
        btn_random_5.setOnClickListener(this);
        btn_random_4.setOnClickListener(this);
        btn_random_3.setOnClickListener(this);
        btn_random_2.setOnClickListener(this);
        btn_random_1.setOnClickListener(this);

        linearLayout_6=(ConstraintLayout)findViewById(R.id.liuyao_jinqiangua_layout_6);
        linearLayout_5=(ConstraintLayout)findViewById(R.id.liuyao_jinqiangua_layout_5);
        linearLayout_4=(ConstraintLayout)findViewById(R.id.liuyao_jinqiangua_layout_4);
        linearLayout_3=(ConstraintLayout)findViewById(R.id.liuyao_jinqiangua_layout_3);
        linearLayout_2=(ConstraintLayout)findViewById(R.id.liuyao_jinqiangua_layout_2);
        linearLayout_1=(ConstraintLayout)findViewById(R.id.liuyao_jinqiangua_layout_1);
        linearLayout_6.setOnClickListener(this);
        linearLayout_5.setOnClickListener(this);
        linearLayout_4.setOnClickListener(this);
        linearLayout_3.setOnClickListener(this);
        linearLayout_2.setOnClickListener(this);
        linearLayout_1.setOnClickListener(this);

        jinqiangua_Result=new Integer[6];

        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerationSensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerationSensor==null)
            Toast.makeText(this,"注册传感器监听器失败，请给予权限!", Toast.LENGTH_LONG).show();

        // 注册加速度传感器监听函数
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x=sensorEvent.values[0];
                float y=sensorEvent.values[1];
                float z=sensorEvent.values[2];
                if (Math.abs(x)+ Math.abs(y)+ Math.abs(z)>ACCELERATION_SENSOR_RING_VALUE&&!ring_flag){
                    ring_flag=true;
                    randomSetJinqiangua();
                    ring_flag=false;
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        },accelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //随机设置卦象
        randomSetJinqiangua();
        
    }

    // 结束当前活动，结束活动的时候把起卦界面的信息包装发送
    private void finishJinqiangua(){
        Intent to_liuyao_result=new Intent(this,Suangua_Result.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("LiuYaoData",jinqiangua_Result);
        to_liuyao_result.putExtras(bundle);
        to_liuyao_result.putExtra("date",date);
        to_liuyao_result.putExtra("way",way);
        to_liuyao_result.putExtra("reason",reason);
        to_liuyao_result.putExtra("name",name);
        to_liuyao_result.putExtra("note",note);
        to_liuyao_result.putExtra("yongshen",yongshen);
        startActivity(to_liuyao_result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.jinqiangua_cancel_btn:
                finish();
                break;
            case R.id.liuyao_jinqiangua_ok_btn:
                getData();
                finishJinqiangua();
                for(int i = 0;i<6;i++){
                guaxiang[i]=jinqiangua_Result[i].intValue();
                }
                HttpsConnect.sendRequest(address, "POST", getJsonData(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchResponse(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
                break;

            // 下面6个按钮用于随机选择对应行的金钱卦

            case R.id.jinqiangua_random_btn_6:
                randomSetJinqianguaSingle(6);
                break;
            case R.id.jinqiangua_random_btn_5:
                randomSetJinqianguaSingle(5);
                break;
            case R.id.jinqiangua_random_btn_4:
                randomSetJinqianguaSingle(4);
                break;
            case R.id.jinqiangua_random_btn_3:
                randomSetJinqianguaSingle(3);
                break;
            case R.id.jinqiangua_random_btn_2:
                randomSetJinqianguaSingle(2);
                break;
            case R.id.jinqiangua_random_btn_1:
                randomSetJinqianguaSingle(1);
                break;

            // 下面6个点击事件用于用户自己设置对应行的金钱卦

            case R.id.liuyao_jinqiangua_layout_6:
                userChooseJinqianguaSingle(6);
                break;
            case R.id.liuyao_jinqiangua_layout_5:
                userChooseJinqianguaSingle(5);
                break;
            case R.id.liuyao_jinqiangua_layout_4:
                userChooseJinqianguaSingle(4);
                break;
            case R.id.liuyao_jinqiangua_layout_3:
                userChooseJinqianguaSingle(3);
                break;
            case R.id.liuyao_jinqiangua_layout_2:
                userChooseJinqianguaSingle(2);
                break;
            case R.id.liuyao_jinqiangua_layout_1:
                userChooseJinqianguaSingle(1);
                break;

            // 下面4个点击事件是弹框选择

            case R.id.liuyao_jinqiangua_choose_layout_1:
                settingCase=1;
                setJinqianguaSingle(settingRow);
                alertDialog.dismiss();
                break;
            case R.id.liuyao_jinqiangua_choose_layout_2:
                settingCase=2;
                setJinqianguaSingle(settingRow);
                alertDialog.dismiss();
                break;
            case R.id.liuyao_jinqiangua_choose_layout_3:
                settingCase=3;
                setJinqianguaSingle(settingRow);
                alertDialog.dismiss();
                break;
            case R.id.liuyao_jinqiangua_choose_layout_4:
                settingCase=4;
                setJinqianguaSingle(settingRow);
                alertDialog.dismiss();
                break;

            default:
                break;
        }
    }

    // 下面几个函数中涉及的row都是从下往上数的，从1开始，6结束

    // 用户自己选择金钱卦
    private void userChooseJinqianguaSingle(final int row){
        settingRow=row;

        TableLayout choose=(TableLayout) getLayoutInflater().inflate(R.layout.liuyao_jinqiangua_choose,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this, R.style.Widget_AppCompat_ButtonBar_AlertDialog);
        builder.setView(choose);


        // 设置居中的标题
        TextView title=new TextView(this);
        title.setText("请选择封爻");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(32);
        title.setBackgroundColor(Color.parseColor("#e0eeee"));
        builder.setCustomTitle(title);

        alertDialog=builder.create();

        // 设置监听事件
        ConstraintLayout layout_1=(ConstraintLayout)choose.findViewById(R.id.liuyao_jinqiangua_choose_layout_1);
        ConstraintLayout layout_2=(ConstraintLayout)choose.findViewById(R.id.liuyao_jinqiangua_choose_layout_2);
        ConstraintLayout layout_3=(ConstraintLayout)choose.findViewById(R.id.liuyao_jinqiangua_choose_layout_3);
        ConstraintLayout layout_4=(ConstraintLayout)choose.findViewById(R.id.liuyao_jinqiangua_choose_layout_4);

        layout_1.setOnClickListener(this);
        layout_2.setOnClickListener(this);
        layout_3.setOnClickListener(this);
        layout_4.setOnClickListener(this);

        alertDialog.show();
        alertDialog.getWindow().setLayout(DensityUtil.dip2px(this,300), TableLayout.LayoutParams.WRAP_CONTENT);
    }

    // 设置指定行的金钱卦
    private void setJinqianguaSingle(int row){
        int result=0;
        int imageIDList[]=new int[4];
        String text=new String();

        switch (settingCase){
            case 1:
                result=6;
                imageIDList[0]=imageIDList[1]=imageIDList[2]= R.drawable.white_circle;
                imageIDList[3]= R.drawable.g6;
                text="阴变阳";
                break;
            case 2:
                result=7;
                imageIDList[0]=imageIDList[1]= R.drawable.white_circle;
                imageIDList[2]= R.drawable.black_circle;
                imageIDList[3]= R.drawable.g7;
                text="阳爻";
                break;
            case 3:
                result=8;
                imageIDList[0]= R.drawable.white_circle;
                imageIDList[1]=imageIDList[2]= R.drawable.black_circle;
                imageIDList[3]= R.drawable.g8;
                text="阴爻";
                break;
            case 4:
                result=9;
                imageIDList[0]=imageIDList[1]=imageIDList[2]= R.drawable.black_circle;
                imageIDList[3]= R.drawable.g9;
                text="阳变阴";
                break;
        }

        for (int i=0;i<4;++i){
            ImageView imageView=(ImageView)findViewById(returnViewID(row,i+1));
            imageView.setImageResource(imageIDList[i]);
        }

        ((TextView)findViewById(returnViewID(row,5))).setText(text);

        jinqiangua_Result[row-1]=result;
    }

    // 随机设置指定行的金钱卦
    private void randomSetJinqianguaSingle(int row){
        Random random=new Random();
        int randomList[]=new int[3];
        int result=0;
        for (int i=0;i<3;++i){
            // 随机产生2或3
            randomList[i]=random.nextInt(2)+2;

            result+=randomList[i];
        }

        Arrays.sort(randomList);

        for (int i=0;i<3;++i){
            // 根据随机产生的结果加载图片
            ImageView imageView=(ImageView)findViewById(returnViewID(row,i+1));
            int imageID=randomList[i]==2? R.drawable.white_circle: R.drawable.black_circle;
            imageView.setImageResource(imageID);
        }

        // 保存结果并加载图片
        jinqiangua_Result[row-1]=result;
        ((ImageView)findViewById(returnViewID(row,4))).setImageResource(returnImageID(result));

        // 根据结果决定显示的文字
        ((TextView)findViewById(returnViewID(row,5))).setText(returnResultText(result));
    }

    // 随机设置所有行的金钱卦
    private void randomSetJinqiangua(){
        for (int i=1;i<=6;++i){
            randomSetJinqianguaSingle(i);
        }
    }

    private int returnViewID(int row,int index){
        switch (row){
            case 6:
                switch (index){
                    case 1:
                        return R.id.jinqiangua_imageview_6_1;
                    case 2:
                        return R.id.jinqiangua_imageview_6_2;
                    case 3:
                        return R.id.jinqiangua_imageview_6_3;
                    case 4:
                        return R.id.jinqiangua_imageview_6_4;
                    case 5:
                        return R.id.jinqiangua_textview_6;
                    default:
                        return 0;
                }
            case 5:
                switch (index){
                    case 1:
                        return R.id.jinqiangua_imageview_5_1;
                    case 2:
                        return R.id.jinqiangua_imageview_5_2;
                    case 3:
                        return R.id.jinqiangua_imageview_5_3;
                    case 4:
                        return R.id.jinqiangua_imageview_5_4;
                    case 5:
                        return R.id.jinqiangua_textview_5;
                    default:
                        return 0;
                }
            case 4:
                switch (index){
                    case 1:
                        return R.id.jinqiangua_imageview_4_1;
                    case 2:
                        return R.id.jinqiangua_imageview_4_2;
                    case 3:
                        return R.id.jinqiangua_imageview_4_3;
                    case 4:
                        return R.id.jinqiangua_imageview_4_4;
                    case 5:
                        return R.id.jinqiangua_textview_4;
                    default:
                        return 0;
                }
            case 3:
                switch (index){
                    case 1:
                        return R.id.jinqiangua_imageview_3_1;
                    case 2:
                        return R.id.jinqiangua_imageview_3_2;
                    case 3:
                        return R.id.jinqiangua_imageview_3_3;
                    case 4:
                        return R.id.jinqiangua_imageview_3_4;
                    case 5:
                        return R.id.jinqiangua_textview_3;
                    default:
                        return 0;
                }
            case 2:
                switch (index){
                    case 1:
                        return R.id.jinqiangua_imageview_2_1;
                    case 2:
                        return R.id.jinqiangua_imageview_2_2;
                    case 3:
                        return R.id.jinqiangua_imageview_2_3;
                    case 4:
                        return R.id.jinqiangua_imageview_2_4;
                    case 5:
                        return R.id.jinqiangua_textview_2;
                    default:
                        return 0;
                }
            case 1:
                switch (index){
                    case 1:
                        return R.id.jinqiangua_imageview_1_1;
                    case 2:
                        return R.id.jinqiangua_imageview_1_2;
                    case 3:
                        return R.id.jinqiangua_imageview_1_3;
                    case 4:
                        return R.id.jinqiangua_imageview_1_4;
                    case 5:
                        return R.id.jinqiangua_textview_1;
                    default:
                        return 0;
                }
            default:
                return 0;
        }
    }

    private int returnImageID(int index){
        switch (index){
            case 6:
                return R.drawable.g6;
            case 7:
                return R.drawable.g7;
            case 8:
                return R.drawable.g8;
            case 9:
                return R.drawable.g9;
            default:
                return 0;
        }
    }

    private String returnResultText(int index){
        switch (index){
            case 6:
                return "阴变阳";
            case 7:
                return "阳爻";
            case 8:
                return "阴爻";
            case 9:
                return "阳变阴";
            default:
                return "";
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

    private JSONObject getJsonData(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<6;i++){
            jsonArray.put(guaxiang[i]);
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

    private void catchResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.compareTo("success")==0){
                        Toast.makeText(LiuYaoJinqiangua.this,"记录存储成功！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}