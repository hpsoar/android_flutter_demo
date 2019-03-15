package me.lattern.flutter_hybride_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.lattern.widgets.bar.ActionBar;
import me.lattern.widgets.page.BaseFragment;

import static me.lattern.widgets.bar.ActionBar.FLAG_VISUAL_BACK;


/**
 */
public class HomeFragment extends BaseFragment {

    @Override
    protected void onActionBar(ActionBar.Builder builder) {
        builder.gone(FLAG_VISUAL_BACK).setTitle("主页");
    }

    @Override
    protected View onCreateLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
