package com.j.loadingrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import java.util.HashMap;
import java.util.Map;

public class LoadingRecyclerView extends FrameLayout {

    private final Context c;

    private LottieAnimationView lav;
    private TextView tvLoadingFailed;
    private TextView tvNoData;
    private FrameLayout flContainer;
    private RecyclerView recyclerView;

    private int status = 0;
    public static final int LOADING = 0;
    public static final int LOAD_SUCCESS = 1;
    public static final int LOAD_FAILED = 2;
    public static final int NO_DATA = 3;

    private final Map<String, String> map = new HashMap<>();

    public LoadingRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoadingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.c = context;

        initView();
    }

    private void initView() {
        lav = new LottieAnimationView(c);
        LayoutParams lavLp = new LayoutParams(DisplayUtil.dp2px(80), DisplayUtil.dp2px(80), Gravity.CENTER);
        lav.setLayoutParams(lavLp);
        lav.setBackgroundColor(Color.WHITE);
        lav.setAnimationFromUrl("https://assets8.lottiefiles.com/packages/lf20_ye3gmrc8.json");
        lav.setRepeatCount(ValueAnimator.INFINITE);
        lav.playAnimation();

        tvLoadingFailed = new TextView(c);
        LayoutParams tvLp1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tvLoadingFailed.setLayoutParams(tvLp1);
        tvLoadingFailed.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, ContextCompat.getDrawable(c, R.mipmap.bg_load_failed), null, null
        );
        tvLoadingFailed.setTextSize(20);
        tvLoadingFailed.setTextColor(Color.BLACK);
        tvLoadingFailed.setGravity(Gravity.CENTER);
        tvLoadingFailed.setText("加载失败，点我重试");

        tvNoData = new TextView(c);
        LayoutParams tvLp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tvNoData.setLayoutParams(tvLp2);
        tvNoData.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, ContextCompat.getDrawable(c, R.mipmap.bg_no_data_yet), null, null
        );
        tvNoData.setTextSize(20);
        tvNoData.setTextColor(Color.BLACK);
        tvNoData.setGravity(Gravity.CENTER);
        tvNoData.setText("暂无数据");

        flContainer = new FrameLayout(c);
        LayoutParams flLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        flContainer.setLayoutParams(flLp);
        flContainer.setBackgroundColor(Color.WHITE);
        flContainer.addView(lav);
        flContainer.addView(tvLoadingFailed);
        flContainer.addView(tvNoData);

        recyclerView = new RecyclerView(c);
        LayoutParams rvLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(rvLp);
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);

        this.addView(recyclerView);
        this.addView(flContainer);

        setStatus(LOADING);
    }

    public void setAnimationFromUrl(String url) {
        lav.setAnimationFromUrl(url);
    }

    public void setStatus(int status){
        this.status = status;
        switch (status){
            case LOADING :
                flContainer.setVisibility(VISIBLE);
                lav.setVisibility(VISIBLE);
                tvLoadingFailed.setVisibility(GONE);
                tvNoData.setVisibility(GONE);
                break;
            case LOAD_SUCCESS:
                flContainer.setVisibility(GONE);
                break;
            case LOAD_FAILED:
                flContainer.setVisibility(VISIBLE);
                lav.setVisibility(GONE);
                tvLoadingFailed.setVisibility(VISIBLE);
                tvNoData.setVisibility(GONE);
                break;
            case NO_DATA:
                flContainer.setVisibility(VISIBLE);
                lav.setVisibility(GONE);
                tvLoadingFailed.setVisibility(GONE);
                tvNoData.setVisibility(VISIBLE);
                break;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setNoDataText(CharSequence text){
        tvNoData.setText(text);
    }

    public void setNoDataSource(int source){
        tvNoData.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, ContextCompat.getDrawable(c, source), null, null
        );
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration){
        recyclerView.addItemDecoration(decoration);
    }

    public void setAdapter(@Nullable RecyclerView.Adapter<?> adapter){
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView.Adapter<?> getAdapter() {
        return recyclerView.getAdapter();
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key){
        return map.get(key);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setOnLoadFailClickListener(OnClickListener onClickListener) {
        tvLoadingFailed.setOnClickListener(onClickListener);
    }

}
