package com.example.mymemory2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // 设置点击事件监听器
        findViewById(R.id.btn_accounting).setOnClickListener(this);
        findViewById(R.id.btn_check_record).setOnClickListener(this);
        findViewById(R.id.btn_record).setOnClickListener(this);
        findViewById(R.id.btn_budget).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_accounting:
                // 跳转到 ManageActivity 页面
                intent = new Intent(this, ManageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_record:
                // 跳转到 RecordActivity 页面
                intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_budget:
                // 跳转到 SearchRecordActivity 页面
                intent = new Intent(this, SearchRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_check_record:
                // 跳转到 NotebookActivity 页面
                intent = new Intent(this, NotebookActivity.class);
                startActivity(intent);
                break;
        }
    }
}
