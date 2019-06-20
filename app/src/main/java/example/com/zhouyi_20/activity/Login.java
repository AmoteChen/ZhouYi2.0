package example.com.zhouyi_20.activity;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener {
    /**
     * 更新：用手机号/密码登陆
     * 用户名、验证码等功能暂时取缔
     * 此处暂时注释，以备需求变更
     */

    //private EditText et_name;
    private EditText et_password;
    private EditText et_account;  //用户登陆所需的手机号
    //  private EditText et_code;  //验证码编辑框，已注释
    //  private Button bt_code;  //验证码获取按钮，已注释
    private Button bt_login;
    private Button bt_register;

    private String name;   //用户名，保留，设置用户信息时仍需要使用
    private String password;
    private String account;
    // private String code;   //验证码，已注释

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;



    private final String address = "http://120.76.128.110:12510/user/UserLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        et_password = (EditText)findViewById(R.id.login_et_password);
        et_account = (EditText)findViewById(R.id.login_et_account);
        /**
         * 修改：用户名、获取验证码等部分在布局中已注释
         * 此处暂时注释，以备需求变更
         */

//        et_code = (EditText)findViewById(R.id.login_et_code);
//        et_code.setVisibility(View.GONE);
//        bt_code = (Button) findViewById(R.id.login_bt_getcode);
//        bt_code.setVisibility(View.GONE);

        bt_login = (Button)findViewById(R.id.login_bt_login);
        bt_login.setOnClickListener(this);
        bt_register = (Button)findViewById(R.id.login_bt_register);
        bt_register.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        //   et_name.setText(User.getName());
        et_password.setText(User.getPassword());
        et_account.setText(User.getAccount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_login:
                //  name = et_name.getText().toString();
                password = et_password.getText().toString();
                account = et_account.getText().toString();
                //  code = et_code.getText().toString();
                HttpsConnect.sendRequest(address, "POST", getJsonData(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchResponse(response);
                        Toast.makeText(Login.this,"已成功登录",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failed(Exception exception) {
                        Looper.prepare();
                        Toast.makeText(Login.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        exception.printStackTrace();
                    }
                });


                break;
            case R.id.login_bt_register:
                Intent toRegister = new Intent(this, Register.class);
                startActivity(toRegister);
                break;
            default:
                break;
        }
    }
    /**
     * 打包 request body
     * phone:用户手机
     * password:密码
     */
    private JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("name", name);
            jsonObject.put("phone", account);
            jsonObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("LOGIN_TAG", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    /*********************** 从response中获取用户数据 **************************/
                    String result = jsonObject.getString("result");
                    String reason = jsonObject.getString("reason");
                    String token = jsonObject.getString("token");
                    String id = jsonObject.getString("userId");
                    String realname = jsonObject.getString("realname");
                    String birthYM = jsonObject.getString("birthYM");
                    Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();

                    if (result.compareTo("success") == 0) {
                        /*********************** User的数据设置 **************************/
                        name = jsonObject.getString("name");
                        account = jsonObject.getString("phone");
                        //password在后端是非明文存储，此处不从后端拿取，直接沿用用户输入
                        User.setName(name);
                        User.setAccount(account);
                        User.setPassword(password);
                        User.setState(true);
                        User.setToken(token);
                        User.setId(id);
                        User.setTrue_name(realname);
                        User.setBirthday(birthYM);

                        /*********************** 本地备份文件的数据设置 **************************/
                        sp = getSharedPreferences(User.getAccount(), Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("name", User.getName());
                        editor.putString("account", User.getAccount());
                        editor.putString("password", User.getPassword());
                        editor.putBoolean("state", User.getState());
                        editor.putString("token", User.getToken());
                        editor.putString("id", User.getId());
                        editor.commit();

                        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("name", User.getName());
                        editor.putString("account", User.getAccount());
                        editor.putString("password", User.getPassword());
                        editor.putBoolean("state", User.getState());
                        editor.putString("token", User.getToken());
                        editor.putString("id", User.getId());
                        editor.commit();

                        User.setState(true);
                        restartApplication();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    private void restartApplication() {
        final Intent intent = new Intent();

        intent.setClass(Login.this,Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

}
