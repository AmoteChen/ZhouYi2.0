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

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private String name;
    private String account;
    private String password;
    private String token;
    private String id;
    private String reason;
    private String realname;
    private String birthYM;
    private boolean state;

    private String check_address = "http://120.76.128.110:12510/web/CheckLogin";
    private String address = "http://120.76.128.110:12510/web/UserLogin";

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhouyi_fragment,container,false);

        bt_guayao=(ImageView)view.findViewById(R.id.main_bt_guayao);
        bt_guayao.setOnClickListener(this);
        bt_ziding=(ImageView)view.findViewById(R.id.main_bt_zidingyi);
        bt_ziding.setOnClickListener(this);




        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        name = sp.getString("name", "");
        account = sp.getString("account", "");
        password = sp.getString("password", "");
        token = sp.getString("token", "");
        id = sp.getString("id", "-1");
        state = sp.getBoolean("state", false);

        User.setName(name);
        User.setAccount(account);
        User.setPassword(password);
        User.setToken(token);
        User.setId(id);
        User.setState(state);
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();

        if (!token.equals("no token")) {
            Toast.makeText(getActivity(), "checking", Toast.LENGTH_SHORT).show();
            HttpsConnect.sendRequest(check_address, "POST", getCheckJsonData(), new HttpsListener() {
                @Override
                public void success(String response) {
                    catchCheckResponse(response);
                }

                @Override
                public void failed(Exception exception) {
                    exception.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "no token", Toast.LENGTH_SHORT).show();
        }
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

    JSONObject getCheckJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", User.getToken());
            jsonObject.put("id", User.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchCheckResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String reason = jsonObject.getString("reason");
                    if (result.equals("success")) {
                        if (state) {
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
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("password", password);
            jsonObject.put("phone", account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    reason = jsonObject.getString("reason");
                    realname = jsonObject.getString("realname");
                    birthYM = jsonObject.getString("birthYM");
                    name = jsonObject.getString("name");
                    token = jsonObject.getString("token");
                    id = jsonObject.getString("userId");
                    if (result.compareTo("success") == 0) {
                        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                        item_view.setState(true);

//                        name = jsonObject.getString("name");
//                        account = jsonObject.getString("phone");
//                        password = jsonObject.getString("password");
//                        User.setName(name);
//                        User.setAccount(account);
//                        User.setPassword(password);
                        User.setName(User.getName());
                        User.setState(true);
                        User.setToken(token);
                        User.setId(id);
                        User.setTrue_name(realname);
                        User.setBirthday(birthYM);

                        sp = getActivity().getSharedPreferences(User.getAccount(), Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("name", User.getName());
//                        editor.putString("account", User.getAccount());
                        editor.putString("password", User.getPassword());
                        editor.putBoolean("state", User.getState());
                        editor.putString("token", User.getToken());
                        editor.putString("id", User.getId());
                        editor.commit();

                        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("name", User.getName());
//                        editor.putString("account", User.getAccount());
                        editor.putString("password", User.getPassword());
                        editor.putBoolean("state", User.getState());
                        editor.putString("token", User.getToken());
                        editor.putString("id", User.getId());
                        editor.commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
