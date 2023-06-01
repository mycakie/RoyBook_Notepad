package com.example.mymemory2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.mymemory2.Bean.User;
import com.example.mymemory2.Database.UserTB;
import com.example.mymemory2.Utils.ToastUtil;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_account;
    private EditText et_password;
    private CheckBox ck_rememberPwd;
    private UserTB userTB;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        ck_rememberPwd = findViewById(R.id.ck_rememberPwd);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_forgetPwd).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userTB = new UserTB(this);
        userTB.open();
        reload();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userTB.close();
    }

    // 重新加载记住密码的显示功能
    private void reload() {
        SharedPreferences spf = getSharedPreferences("spfRecorid", MODE_PRIVATE);

        boolean isRem = spf.getBoolean("isRemember", false);
        String account = spf.getString("account", "");
        String password = spf.getString("password", "");

        if (isRem) {
            et_account.setText(account);
            et_password.setText(password);
            ck_rememberPwd.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 登录按钮
            case R.id.btn_login:
                Login();
                break;
            // 注册按钮
            case R.id.btn_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            // 忘记密码按钮
            case R.id.btn_forgetPwd:
                // 处理忘记密码逻辑
                break;
        }
    }

    // 登录操作
    private void Login() {
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();

        if (TextUtils.isEmpty(account)) {
            ToastUtil.show(this, "请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(this, "请输入密码");
            return;
        }

        List<User> userList = userTB.selectByAccountAndPass(account, password);

        // 非空说明查到了用户
        if (!userList.isEmpty()) {
            ToastUtil.show(this, "登录成功");

            // 判断是否勾选了记住密码
            // 实现记住密码功能
            if (ck_rememberPwd.isChecked()) {
                SharedPreferences spf = getSharedPreferences("spfRecorid", MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putString("account", account);
                edit.putString("password", password);
                edit.putBoolean("isRemember", true);
                edit.apply();
            } else {
                SharedPreferences spf = getSharedPreferences("spfRecorid", MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putBoolean("isRemember", false);
                edit.apply();
            }

            // 登录成功跳转到主页
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
        } else {
            ToastUtil.show(this, "账号或密码错误");
        }
    }
}
