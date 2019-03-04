package example.com.zhouyi_20.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sun.corba.se.impl.oa.toa.TOA;

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
    private SearchView mSearchView;
    private List<Divination> divinations = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private HistoryAdapter searchAdapter;



    private List<Divination> search_divinations = new ArrayList<>();

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        dividantion_init();
        View view = inflater.inflate(R.layout.history_fragment,container,false);
        slideRecyclerView = (SlideRecyclerView)view.findViewById(R.id.history_list);
        slideRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_inset));
        slideRecyclerView.addItemDecoration(itemDecoration);
        mSearchView=(SearchView)view.findViewById(R.id.history_searchview);
        mSearchView.setSubmitButtonEnabled(true);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(TextUtils.isEmpty(query)){
                    Toast.makeText(getContext(),"请输入查找内容", Toast.LENGTH_SHORT).show();
                }
                else {
                    search_divinations.clear();
                    for (int i = 0;i<divinations.size();i++){
                        Divination temp_divination = divinations.get(i);
                        if(temp_divination.getway().equals(query)||temp_divination.getId().equals(query)||temp_divination.getReason().equals(query)
                                ||temp_divination.getTime().equals(query)||temp_divination.getXingqi().equals(query))
                        {
                            search_divinations.add(temp_divination);
                            break;
                        }
                        if(search_divinations.size()==0){
                            Toast.makeText(getContext(),"无匹配记录", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"匹配成功", Toast.LENGTH_SHORT).show();
                            searchAdapter = new HistoryAdapter(getContext(),search_divinations);
                            slideRecyclerView.setAdapter(searchAdapter);
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    slideRecyclerView.setAdapter(historyAdapter);
                }
                else
                {
                    search_divinations.clear();
                    for(int i = 0; i < divinations.size(); i++)
                    {
                        Divination temp_divination = divinations.get(i);
                        if(temp_divination.getway().contains(newText)||temp_divination.getId().contains(newText)||temp_divination.getReason().contains(newText)
                                ||temp_divination.getTime().contains(newText)||temp_divination.getXingqi().contains(newText))
                        {
                            search_divinations.add(temp_divination);
                        }
                    }
                    searchAdapter = new HistoryAdapter(getContext(), search_divinations);
                    searchAdapter.notifyDataSetChanged();
                    slideRecyclerView.setAdapter(searchAdapter);
                }
                return true;
            }

        });

        historyAdapter = new HistoryAdapter(getContext(),divinations);
        slideRecyclerView.setAdapter(historyAdapter);
        historyAdapter.setOnDeleteClickListener(new HistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                divinations.remove(position);
                historyAdapter.notifyDataSetChanged();
                slideRecyclerView.closeMenu();
            }
        });
        return view;
    }

    public void dividantion_init(){
        divinations.add(new Divination("戊戌年3,25日阮再敏问事业婚姻儿子","2018/3/25","星期日","六爻"));
        divinations.add(new Divination( "邵小姐问家庭关系","2018/3/24","星期六","六爻"));
        divinations.add(new Divination( "老林问事业去处68年11月24日","2018/3/24","星期二","自定"));
        divinations.add(new Divination( "建生67年阳历617","2018/3/3","星期六","六爻"));
        divinations.add(new Divination("潘跃华77年11月21日","2018/3/3","星期六","六爻"));
        divinations.add(new Divination( "潘孝生问合作财运","2018/3/3","星期六","自定"));
        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻"));
        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定"));
        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻"));
        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻"));
        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定"));
        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻"));
        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻"));
        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定"));
        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻"));
        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻"));
        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定"));
        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻"));
    }
}
