package example.com.zhouyi_20.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.NewRecord;
import example.com.zhouyi_20.object.Divination;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;

/**
 * Created by ChenSiyuan on 2019/3/3.
 * 用于管理历史纪录的Divination
 */

public class HistoryAdapter extends BaseRecyclerViewAdapter<Divination> {
    private List<Divination> divinations;
    private final String delete_address = "http://120.76.128.110:12510/app/deleteRecord";
    private Context context;
    private OnDeleteClickListener mDeleteClickListener;

    public HistoryAdapter(Context context, List<Divination> data){
        super(context,data, R.layout.item_history);
        this.context = context;
        divinations = data;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, Divination history,int position){
        final View delet_view = holder.getView(R.id.item_hi_delete);
        View total_view = holder.getView(R.id.item_hi_total);

        total_view.setTag(position);
        delet_view.setTag(position);

        if(!delet_view.hasOnClickListeners()){
            delet_view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if ( mDeleteClickListener != null) {
                        HttpsConnect.sendRequest(delete_address, "POST", getJsonData((Integer) v.getTag()), new HttpsListener() {
                            @Override
                            public void success(String response) {
                                catchResponse(response);
                            }

                            @Override
                            public void failed(Exception exception) {
                                exception.printStackTrace();
                            }
                        });
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        ((TextView) holder.getView(R.id.item_hi_shiyou)).setText(history.getReason());
        ((TextView) holder.getView(R.id.item_hi_date)).setText(history.getTime());
        ((TextView) holder.getView(R.id.item_hi_name)).setText(history.getName());
        ((TextView) holder.getView(R.id.item_hi_xingqi)).setText(history.getXingqi());

        total_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer)v.getTag();
                Divination divination = divinations.get(position);
                Intent to_record = new Intent(v.getContext(), NewRecord.class);
                Bundle bundle=new Bundle();
                JSONArray jsonArray = divination.getGuaxiang();
                Integer guaxiang []= new Integer[6];
                for (int i = 0;i<6;i++){
                    try {
                        guaxiang[i]= (Integer) jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                bundle.putSerializable("LiuYaoData",guaxiang);
                to_record.putExtras(bundle);
                to_record.putExtra("_id",divination.get_id());
                to_record.putExtra("id", divination.getId());
                to_record.putExtra("time",divination.getTime());
                to_record.putExtra("reason", divination.getReason());
                to_record.putExtra("way",divination.getway());
                to_record.putExtra("name",divination.getName());
                to_record.putExtra("note",divination.getNote());
                to_record.putExtra("yongshen",divination.getYongshen());
                to_record.putExtra("from","history");

                v.getContext().startActivity(to_record);
            }
        });
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position);
    }

    private JSONObject getJsonData(int position){
        JSONObject jsonObject = new JSONObject();
        Divination divination = divinations.get(position);

        try {
            jsonObject.put("id",divination.get_id());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponse(final String response)  {
        JSONObject object = null;
        try {
            object = new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = object.optString("result");
        String reason = object.optString("reason");
        if(result.compareTo("success")==0){
            Toast.makeText(context,"纪录删除成功！",Toast.LENGTH_SHORT).show();
        }

    }


    }



