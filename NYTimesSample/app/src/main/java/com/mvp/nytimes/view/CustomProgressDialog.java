package com.mvp.nytimes.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import com.mvp.nytimes.R;

public class CustomProgressDialog extends Dialog {
    public Activity c;
    public Dialog d;
    public TextView tv;

    public CustomProgressDialog(@NonNull Activity activity) {
        super(activity);
        this.c = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_bar);
        tv = findViewById(R.id.loadTv);
        tv.setText(R.string.loading_text);
    }

    public void showDialog(){
        show();
    }

    public void hideDialog()
    {
        hide();
    }
}
