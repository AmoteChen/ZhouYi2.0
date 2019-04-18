package example.com.zhouyi_20.activity.total_result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.object.User;

/**
 * Created by ChenSiyuan on 2019/1/17.
 * The name of the class is not accurate.
 * This class is the first fragment in the bottom of the FINAL RESULT.
 */

public class liuyao_fragment_1 extends Fragment {
    private View view;
    private String date;
    private String name;
    private String reason;
    private String note;
    private String yongshen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.liuyao_fra_layout_1,container,false);
        getData();
        //控件初始化及相应内容填写
        TextView textView = (TextView)view.findViewById(R.id.liuyao_result_frg1_date);
        textView.setText(date);
        textView = (TextView)view.findViewById(R.id.liuyao_result_frg1_name);
        textView.setText(name);
        textView = (TextView)view.findViewById(R.id.liuyao_result_frg1_reason);
        textView.setText(reason);
        textView = (TextView)view.findViewById(R.id.liuyao_result_frg1_note);
        textView.setText(note);
        textView = (TextView)view.findViewById(R.id.liuyao_result_frg1_yongshen);
        textView.setText(yongshen);
        return view;
    }
    //从起卦页面获取信息
    private void getData(){
        Intent intent = getActivity().getIntent();
        date = intent.getStringExtra("date");
        name = intent.getStringExtra("name");
        reason = intent.getStringExtra("reason");
        note = intent.getStringExtra("note");
        yongshen = intent.getStringExtra("yongshen");
    }
}
