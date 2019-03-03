package example.com.zhouyi_20.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.adapter.DivinationAdapter;
import example.com.zhouyi_20.adapter.HistoryAdapter;
import example.com.zhouyi_20.object.Divination;
import example.com.zhouyi_20.view.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class History_fragment extends Fragment {

    SlideRecyclerView slideRecyclerView;
    LinearLayoutManager layoutManager;
    private List<Divination> divinations = new ArrayList<>();

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment,container,false);

        slideRecyclerView = (SlideRecyclerView)view.findViewById(R.id.history_list);
        slideRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_inset));
        slideRecyclerView.addItemDecoration(itemDecoration);


        divinations.add(new Divination("戊戌年3,25日阮再敏问事业婚姻儿子","2018/3/25","星期日","六爻"));
        divinations.add(new Divination( "邵小姐问家庭关系","2018/3/24","星期六","六爻"));
        divinations.add(new Divination( "老林问事业去处68年11月24日","2018/3/24","星期二","自定"));
        divinations.add(new Divination( "建生67年阳历617","2018/3/3","星期六","六爻"));
        divinations.add(new Divination("潘跃华77年11月21日","2018/3/3","星期六","六爻"));
        divinations.add(new Divination( "潘孝生问合作财运","2018/3/3","星期六","自定"));
        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻"));
        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定"));
        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻"));
        final HistoryAdapter adapter = new HistoryAdapter(getContext(),divinations);
        slideRecyclerView.setAdapter(adapter);
        adapter.setOnDeleteClickListener(new HistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                divinations.remove(position);
                adapter.notifyDataSetChanged();
                slideRecyclerView.closeMenu();
            }
        });
        return view;
    }
}
