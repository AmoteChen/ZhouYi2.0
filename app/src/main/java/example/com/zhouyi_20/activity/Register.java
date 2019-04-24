package example.com.zhouyi_20.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;
import example.com.zhouyi_20.object.User;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_account;
    private EditText et_code;
    private EditText et_password;
    private EditText et_pwd_com;
    private Button bt_getcode;
    private Button bt_register;


    private String name;
    private String account;
    private String code;
    private String password;
    private String password_com;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private final String address = "http://120.76.128.110:12510/user/UserSignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        et_name = (EditText)findViewById(R.id.register_et_name);
        et_account = (EditText)findViewById(R.id.register_et_account);
        et_code = (EditText)findViewById(R.id.register_et_code);
        et_password=(EditText)findViewById(R.id.register_et_password);
        et_pwd_com=(EditText)findViewById(R.id.register_et_pwd_com);
        bt_getcode = (Button)findViewById(R.id.register_bt_getcode);
        bt_getcode.setOnClickListener(this);
        bt_register = (Button)findViewById(R.id.register_bt_register);
        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_bt_getcode:
                break;
            case R.id.register_bt_register:
                name = et_name.getText().toString();
                account = et_account.getText().toString();
                code = et_code.getText().toString();
                password=et_password.getText().toString();
                password_com=et_pwd_com.getText().toString();
                if (password.compareTo(password_com) == 0) {
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
                } else {
                    Toast.makeText(this, "密码不一致!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("phone", account);
            jsonObject.put("passwd", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String reason = jsonObject.getString("reason");
                    Toast.makeText(Register.this, result + "\n" + reason, Toast.LENGTH_SHORT).show();
                    if (result.compareTo("success") == 0) {
                        User.setName(name);
                        User.setAccount(account);
                        User.setPassword(password);
                        User.setState(false);
                        sp = getSharedPreferences(account, Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("name", name);
                        editor.putString("account", account);
                        editor.putString("password", password);
                        editor.putBoolean("state", false);
                        editor.commit();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
