package example.com.zhouyi_20.activity.Ziding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.Main;
import example.com.zhouyi_20.activity.Suangua_Result;
import example.com.zhouyi_20.object.User;

/**
 * Created by ChenSiyuan on 2019/2/26.
 */

public class Zidinggua extends AppCompatActivity implements View.OnClickListener {
    public LinearLayout linearLayout;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ziding_new_record);
        linearLayout = (LinearLayout)findViewById(R.id.temp_total_ziding);
        linearLayout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.temp_total_ziding:
                Intent to_main = new Intent(Zidinggua.this,Main.class);
                startActivity(to_main);
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
}
