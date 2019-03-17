package example.com.zhouyi_20.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.mine.item_view;
import example.com.zhouyi_20.object.HttpsConnect;
import example.com.zhouyi_20.object.HttpsListener;
import example.com.zhouyi_20.object.User;

import org.json.JSONObject;

public class Zhouyi_fragment extends Fragment implements View.OnClickListener {

    private ImageView bt_guayao;
    private ImageView bt_ziding;




    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhouyi_fragment,container,false);

        bt_guayao=(ImageView)view.findViewById(R.id.main_bt_guayao);
        bt_guayao.setOnClickListener(this);
        bt_ziding=(ImageView)view.findViewById(R.id.main_bt_zidingyi);
        bt_ziding.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bt_guayao:
                Intent to_LiuyaoNewRecord=new Intent(getActivity(),NewRecord.class);
                to_LiuyaoNewRecord.putExtra("way", "六爻");
                to_LiuyaoNewRecord.putExtra("from","liuyao");
                startActivity(to_LiuyaoNewRecord);
                break;
            case R.id.main_bt_zidingyi:
                Intent to_ZidingNewRecord=new Intent(getActivity(),NewRecord.class);
                to_ZidingNewRecord.putExtra("way","自定");
                to_ZidingNewRecord.putExtra("from","ziding");
                startActivity(to_ZidingNewRecord);
            default:
                break;
        }
    }



}
