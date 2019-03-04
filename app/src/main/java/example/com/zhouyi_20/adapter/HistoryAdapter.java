package example.com.zhouyi_20.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.activity.DivinationInfo;
import example.com.zhouyi_20.activity.NewRecord;
import example.com.zhouyi_20.object.Divination;

/**
 * Created by ChenSiyuan on 2019/3/3.
 * 用于管理历史纪录的Divination
 */

public class HistoryAdapter extends BaseRecyclerViewAdapter<Divination> {
    private List<Divination> divinations;

    private OnDeleteClickListener mDeleteClickListener;

    public HistoryAdapter(Context context, List<Divination> data){
        super(context,data, R.layout.item_history);
        divinations = data;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, Divination history,int position){
        View delet_view = holder.getView(R.id.item_hi_delete);
        View total_view = holder.getView(R.id.item_hi_total);

        total_view.setTag(position);
        delet_view.setTag(position);

        if(!delet_view.hasOnClickListeners()){
            delet_view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if ( mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        ((TextView) holder.getView(R.id.item_hi_shiyou)).setText(history.getReason());
        ((TextView) holder.getView(R.id.item_hi_date)).setText(history.getTime());
        ((TextView) holder.getView(R.id.item_hi_way)).setText(history.getway());
        ((TextView) holder.getView(R.id.item_hi_xingqi)).setText(history.getXingqi());

        total_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer)v.getTag();
                Divination divination = divinations.get(position);
                Intent to_record = new Intent(v.getContext(), NewRecord.class);
                to_record.putExtra("id", divination.getId());
                to_record.putExtra("time",divination.getTime());
                to_record.putExtra("reason", divination.getReason());
                to_record.putExtra("way",divination.getway());
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
}
