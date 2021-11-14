package com.Schedo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Toolbars extends LinearLayout {
    private boolean tb_show_back = false;
    private String tb_title = "";
    private int tb_mode = 0;


    public Toolbars(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.toolbars, this);
    }

    public Toolbars(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Toolbars(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @NonNull AttributeSet attrs){
        TypedArray data = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Toolbars,
                0,
                0
        );
        try{
            tb_show_back = data.getBoolean(R.styleable.Toolbars_tb_show_back, false);
            tb_title = data.getString(R.styleable.Toolbars_tb_title);
            tb_mode = data.getInt(R.styleable.Toolbars_tb_mode, 0);
        }finally {
            data.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.toolbars, this);

        ImageView btn_back = this.findViewById(R.id.btn_back_toolbars);
        btn_back.setVisibility(tb_show_back ? VISIBLE : GONE);
        btn_back.setColorFilter(tb_mode == 0 ? Color.WHITE : Color.BLACK);

        TextView title = this.findViewById(R.id.title_toolbars);
        title.setText(TextUtils.isEmpty(tb_title) ? getResources().getString(R.string.app_name) : tb_title);
        title.setTextColor(tb_mode == 0 ? Color.WHITE : Color.BLACK);

        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarClick.onBackClick();
            }
        });
    }

    public interface OnToolbarClick{
        void onBackClick();
    }

    OnToolbarClick onToolbarClick;
    public void setOnToolbarBackClick(OnToolbarClick onToolbarClick){
        this.onToolbarClick = onToolbarClick;
    }

    // ATTR

    public boolean isTb_show_back() {
        return tb_show_back;
    }

    public void setTb_show_back(boolean tb_show_back) {
        this.tb_show_back = tb_show_back;
    }

    public String getTb_title() {
        return tb_title;
    }

    public void setTb_title(String tb_title) {
        this.tb_title = tb_title;
    }

    public int getTb_mode() {
        return tb_mode;
    }

    public void setTb_mode(int tb_mode) {
        this.tb_mode = tb_mode;
    }
}
