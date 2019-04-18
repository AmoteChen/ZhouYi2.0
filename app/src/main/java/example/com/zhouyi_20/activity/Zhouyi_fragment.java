package example.com.zhouyi_20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import example.com.zhouyi_20.R;

public class Zhouyi_fragment extends Fragment implements View.OnClickListener {

    private ImageView bt_guayao;
    private ImageView bt_ziding;
    private ImageView bt_number;




    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhouyi_fragment,container,false);

        bt_guayao=(ImageView)view.findViewById(R.id.main_bt_guayao);
        bt_guayao.setOnClickListener(this);
        bt_number=(ImageView)view.findViewById(R.id.main_bt_shuzi);
        bt_number.setOnClickListener(this);
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
            case R.id.main_bt_shuzi:
                Intent to_NumberNewRecord=new Intent(getActivity(),NewRecord.class);
                to_NumberNewRecord.putExtra("way","数字");
                to_NumberNewRecord.putExtra("from","number");
                startActivity(to_NumberNewRecord);
                break;
            case R.id.main_bt_zidingyi:
                Intent to_ZidingNewRecord=new Intent(getActivity(),NewRecord.class);
                to_ZidingNewRecord.putExtra("way","自定");
                to_ZidingNewRecord.putExtra("from","ziding");
                startActivity(to_ZidingNewRecord);
                break;
            default:
                break;
        }
    }



}
