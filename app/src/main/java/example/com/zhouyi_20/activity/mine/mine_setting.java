package example.com.zhouyi_20.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import example.com.zhouyi_20.R;

public class mine_setting extends AppCompatActivity {
    private TextView jiemianyuyan;
    private TextView xianshisheding;
    private TextView bugua;
    private TextView wannianli;
    private TextView ziliaoguanli;
    
    private View zaoyezi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting);
        jiemianyuyan = (TextView) findViewById(R.id.jiamianyuyan);
        xianshisheding = (TextView) findViewById(R.id.xianshisheding);
        bugua = (TextView) findViewById(R.id.bugua);
        wannianli = (TextView) findViewById(R.id.wannianli);
        ziliaoguanli = (TextView) findViewById(R.id.ziliaoguanli);
        zaoyezi = (View) findViewById(R.id.zaoyezi); 
        
        jiemianyuyan.setBackgroundResource(R.color.hui);
        xianshisheding.setBackgroundResource(R.color.hui);
        bugua.setBackgroundResource(R.color.hui);
        wannianli.setBackgroundResource(R.color.hui);
        ziliaoguanli.setBackgroundResource(R.color.hui);
    }
}
