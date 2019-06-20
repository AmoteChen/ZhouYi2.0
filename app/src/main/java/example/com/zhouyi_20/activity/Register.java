package example.com.zhouyi_20.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    /**
     * 更新：增设验证码功能
     */

    private EditText et_name;  //用户名框
    private EditText et_account;  //手机号框
    private EditText et_code;  //验证码框
    private EditText et_password;  //密码框
    private EditText et_pwd_com;  //确认密码框
    private Button bt_getcode;  //获取验证码按钮
    private Button bt_register;  //注册按钮


    private String account;
    private String name;
    private String captcha; //短信验证码
    private String password;
    private String password_com;


    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    //注册接口
    private final String regist_address = "http://120.76.128.110:12510/user/UserSignUp";
    //获取短信验证码接口
    private final String captcha_address = "http://120.76.128.110:12510/user/GetCAPTCHA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        et_account = (EditText)findViewById(R.id.register_et_account);
        et_name = (EditText)findViewById(R.id.register_et_name);
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
            //获取验证码按钮
            case R.id.register_bt_getcode:
                if(et_account.getText().length()!=0){
                    account = et_account.getText().toString();
                    HttpsConnect.sendRequest(captcha_address, "POST", captcha_getJsonData(), new HttpsListener() {
                        @Override
                        public void success(String response) {
                            capcha_catchResponse(response);
                        }

                        @Override
                        public void failed(Exception exception) {
                            //报错
                            Looper.prepare();
                            Toast.makeText(Register.this, "验证码获取失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            Looper.loop();

                            exception.printStackTrace();
                        }
                    });
                }
                else{
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;

            //注册按钮
            case R.id.register_bt_register:
                name = et_name.getText().toString();
                account = et_account.getText().toString();
                captcha = et_code.getText().toString();
                password=et_password.getText().toString();
                password_com=et_pwd_com.getText().toString();
                if (password.compareTo(password_com) == 0) {
                    HttpsConnect.sendRequest(regist_address, "POST", regist_getJsonData(), new HttpsListener() {
                        @Override
                        public void success(String response) {
                            regist_catchResponse(response);
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
    /**
     * 注册信息的Json设置
     */
    private JSONObject regist_getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("phone", account);
            jsonObject.put("passwd", password);
            jsonObject.put("captcha",captcha);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    /**
     * 验证码的Json设置
     */
    private JSONObject captcha_getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", account);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.e("CAPTCH",jsonObject.toString());
        return jsonObject;
    }
    /**
     * 注册成功的本地响应
     */
    private void regist_catchResponse(final String response) {
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
    /**
     * 获取短信验证码成功的本地响应
     * 留以函数形式，以便需求变更
     */
    private void capcha_catchResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this, "验证码已发送至短信，请注意查收！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
