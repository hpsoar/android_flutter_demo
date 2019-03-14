package me.lattern.flutter_utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import io.flutter.app.FlutterActivity;
import io.flutter.view.FlutterMain;

public class CYFlutterActivity extends FlutterActivity {
    CYFlutterChannel mChannel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FlutterMain.startInitialization(this.getApplicationContext());

        super.onCreate(savedInstanceState);

        mChannel = new CYFlutterChannel(this, this, getFlutterView(), CYFlutterChannel.METHOD_CHANNEL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void setStatusBarColor() {
        getWindow().clearFlags(Integer.MAX_VALUE);
        getWindow().setStatusBarColor(0x40000000);
    }

    void setupNavi() {
//        ActionBar.Builder actionBuilder = new ActionBar.Builder(this);
//
//        LinearLayout naviContainer = findViewById(R.id.navi_container);
//        ActionBar actionBar = actionBuilder.build();
//        View defBar = actionBar.getHeader();
//        defBar.setBackgroundColor(Color.RED);
//        naviContainer.addView(defBar);
//
//        actionBar.getBackButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CYFlutterActivity.super.onBackPressed();
//            }
//        });
    }

    @Override
    public void setContentView(View view) {
        if (view == getFlutterView()) {
            super.setContentView(R.layout.activity_flutter_base);

            setStatusBarColor();

            setupNavi();

            LinearLayout container = findViewById(R.id.flutter_container);
            FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            container.addView(view, layout);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void onBackPressed() {
        // Activity中点击返回按钮时，先尝试pop flutter页面，再pop Activity
        mChannel.onBack(new CYFlutterChannel.PopResult() {
            @Override
            public void onResult(Boolean completed) {
                if (!completed) {
                    CYFlutterActivity.super.onBackPressed();
                }
            }
        });
    }
}
