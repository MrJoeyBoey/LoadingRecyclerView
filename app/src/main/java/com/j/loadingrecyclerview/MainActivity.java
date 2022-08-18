package com.j.loadingrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadingRecyclerView lrv = findViewById(R.id.lrv);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            lrv.setStatus(LoadingRecyclerView.LOADING);
        });
        findViewById(R.id.btn2).setOnClickListener(v -> {
            lrv.setStatus(LoadingRecyclerView.LOAD_SUCCESS);
        });
        findViewById(R.id.btn3).setOnClickListener(v -> {
            lrv.setStatus(LoadingRecyclerView.LOAD_FAILED);
        });
        findViewById(R.id.btn4).setOnClickListener(v -> {
            lrv.setStatus(LoadingRecyclerView.NO_DATA);
        });

    }
}