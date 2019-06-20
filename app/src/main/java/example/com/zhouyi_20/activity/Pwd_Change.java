package example.com.zhouyi_20.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import example.com.zhouyi_20.R;
import example.com.zhouyi_20.object.User;
import example.com.zhouyi_20.tool.HttpsConnect;
import example.com.zhouyi_20.tool.HttpsListener;

public class Pwd_Change extends AppCompatActivity implements View.OnClickListener {
    private EditText et_code;  //验证码框
    private EditText et_password;  //密码框
    private EditText et_pwd_com;  //确认密码框
    private Button bt_getcode;  //获取验证码按钮
    private Button bt_confirm;  //确认修改按钮

    //相关数据
    private String account;
    private String captcha;
    private String password;
    private String password_com;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    //密码重置接口
    private final String regist_address = "http://120.76.128.110:12510/user/ResetPassword";
    //获取验证码接口
    private final String pwdcaptcha_address = "http://120.76.128.110:12510/user/GetResetPasswordCAPTCHA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwd_change);

        et_code = (EditText)findViewById(R.id.pwdchange_et_code);
        et_password = (EditText)findViewById(R.id.pwdchange_et_password);
        et_pwd_com = (EditText)findViewById(R.id.pwdchange_et_pwd_com);
        bt_getcode = (Button)findViewById(R.id.pwdchange_bt_getcode);
        bt_getcode.setOnClickListener(this);
        bt_confirm = (Button)findViewById(R.id.pwdchange_bt_confirm);
        bt_confirm.setOnClickListener(this);
    }
    /**
     * 按钮功能
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //获取验证码按钮
            case R.id.pwdchange_bt_getcode:
                if(User.getAccount().length()!=0){
                    account = User.getAccount();
                    HttpsConnect.sendRequest(pwdcaptcha_address, "POST", captcha_getJsonData(), new HttpsListener() {
                        @Override
                        public void success(String response) {
                            capcha_catchResponse(response);
                        }

                        @Override
                        public void failed(Exception exception) {
                            //报错
                            Looper.prepare();
                            Toast.makeText(Pwd_Change.this, "验证码获取失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            Looper.loop();

                            exception.printStackTrace();
                        }
                    });
                }
                else{
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;

            //确认按钮
            case R.id.pwdchange_bt_confirm:
                account = User.getAccount();
                captcha = et_code.getText().toString();
                password=et_password.getText().toString();
                password_com=et_pwd_com.getText().toString();
                if (password.compareTo(password_com) == 0) {
                    HttpsConnect.sendRequest(regist_address, "POST", pwdchange_getJsonData(), new HttpsListener() {
                        @Override
                        public void success(String response) {
                            pwdchange_catchResponse(response);
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
     * 修改密码信息的Json设置
     */
    private JSONObject pwdchange_getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", account);
            jsonObject.put("captcha",captcha);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
                    Toast.makeText(Pwd_Change.this, "验证码已发送至短信，请注意查收！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 密码修改成功的本地响应
     */
    private void pwdchange_catchResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String reason = jsonObject.getString("reason");
                    Toast.makeText(Pwd_Change.this, result + "\n" + reason, Toast.LENGTH_SHORT).show();
                    if (result.compareTo("success") == 0) {
                        User.setAccount(account);
                        User.setPassword(password);
                        User.setState(false);
                        sp = getSharedPreferences(account, Context.MODE_PRIVATE);
                        editor = sp.edit();
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
