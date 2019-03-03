package example.com.zhouyi_20.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.object.Divination;

/**
 * Created by ChenSiyuan on 2019/3/3.
 */

public class HistoryAdapter extends BaseRecyclerViewAdapter<Divination> {

    private OnDeleteClickListener mDeleteClickListener;

    public HistoryAdapter(Context context, List<Divination> data){
        super(context,data, R.layout.item_history);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, Divination history,int position){
        View view = holder.getView(R.id.item_hi_delete);
        view.setTag(position);
        if(!view.hasOnClickListeners()){
            view.setOnClickListener(new View.OnClickListener(){
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

    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position);
    }
}
