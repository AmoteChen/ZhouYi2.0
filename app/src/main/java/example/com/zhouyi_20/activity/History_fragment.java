package example.com.zhouyi_20.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.adapter.HistoryAdapter;
import example.com.zhouyi_20.object.Divination;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;
import example.com.zhouyi_20.object.User;
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
    private View view;

    private final String address ="http://120.76.128.110:12510/app/getRecord";

    private List<Divination> search_divinations = new ArrayList<>();

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        if(User.getState()){
            view = inflater.inflate(R.layout.history_fragment,container,false);
            HttpsConnect.sendRequest(address, "POST", getJsonData(), new HttpsListener() {
                @Override
                public void success(String response) {
                    catchResponse(response);



                }

                @Override
                public void failed(Exception exception) {
                    exception.printStackTrace();
                }
            });



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
                                    ||temp_divination.getTime().equals(query)||temp_divination.getXingqi().equals(query)||temp_divination.getName().equals(query))
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
                                    ||temp_divination.getTime().contains(newText)||temp_divination.getXingqi().contains(newText)||temp_divination.getName().contains(newText))
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


        }



        return view;
    }


    public void dividantion_init(){
//        divinations.add(new Divination("戊戌年3,25日阮再敏问事业婚姻儿子","2018/3/25","星期日","六爻","张三"));
//        divinations.add(new Divination( "邵小姐问家庭关系","2018/3/24","星期六","六爻","李四"));
//        divinations.add(new Divination( "老林问事业去处68年11月24日","2018/3/24","星期二","自定","王刚"));
//        divinations.add(new Divination( "建生67年阳历617","2018/3/3","星期六","六爻","刘凯华"));
//        divinations.add(new Divination("潘跃华77年11月21日","2018/3/3","星期六","六爻","谢最"));
//        divinations.add(new Divination( "潘孝生问合作财运","2018/3/3","星期六","自定","李一一"));
//        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻","刘凯华"));
//        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定","王刚"));
//        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻","谢最"));
//        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻","李一一"));
//        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定","张三"));
//        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻","李四"));
//        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻","李四"));
//        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定","刘凯华"));
//        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻","刘凯华"));
//        divinations.add(new Divination("与成叶合作可否18年3月3日","2018/3/3","星期六","六爻","李一一"));
//        divinations.add(new Divination("天有大火","2018/3/2","星期五","自定","李一一"));
//        divinations.add(new Divination( "炳贤问事业","2018/2/21","星期三","六爻","李四"));
    }


    private JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("method", "time_new");
            jsonObject.put("keyword", "");
            jsonObject.put("page", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject RootJsonObject = new JSONObject(response);
                    Log.e("SSS",response.toString());
                    String result = RootJsonObject.getString("result");
                    String reason = RootJsonObject.getString("reason");
                    Toast.makeText(getActivity(),reason,Toast.LENGTH_SHORT).show();
                    if(result.compareTo("success")==0){
                        JSONArray record = RootJsonObject.getJSONArray("record");
                        divinations.clear();
                        for (int i =0;i<record.length();i++){
                            JSONObject eachObject = record.getJSONObject(i);
                            String _id = eachObject.getString("_id");
                            String userid = eachObject.getString("userid");
                            String date = eachObject.getString("date");
                            String yongshen = eachObject.getString("yongshen");
                            String name = eachObject.getString("name");
                            String reason1 = eachObject.getString("reason");
                            String note = eachObject.getString("note");
                            String way = eachObject.getString("way");
                            JSONArray guaxiang = eachObject.getJSONArray("guaxiang");
                            divinations.add(new Divination(_id,reason1,userid,yongshen,date,"星期日",way,name,note,guaxiang));
                        }

                        slideRecyclerView = (SlideRecyclerView)view.findViewById(R.id.history_list);
                        slideRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_inset));
                        slideRecyclerView.addItemDecoration(itemDecoration);

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
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
