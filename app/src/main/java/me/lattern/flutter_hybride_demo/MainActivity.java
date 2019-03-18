package me.lattern.flutter_hybride_demo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.lattern.flutter_utils.bridge.flutter.CYFlutterFragment;
import me.lattern.widgets.bar.MainBottomView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_container)
    MainBottomView vTabContainer;

    @BindView(R.id.tab_content)
    ViewPager vTabContent;

    private Unbinder unBinderKnife;

    private MainBottomView.TabSpec tabHospital;//首页
    private MainBottomView.TabSpec tabUserCenter;//个人中心
    private MainBottomView.TabSpec tabFlutter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unBinderKnife = ButterKnife.bind(this);


        //首页
        tabHospital = vTabContainer.newTabSpec(R.drawable.tab_hospital, R.string.home_tab, HomeFragment.class);
        vTabContainer.addTab(tabHospital);

        //个人中心
        tabUserCenter = vTabContainer.newTabSpec(R.drawable.tab_usercenter, R.string.usercenter_tab, UserCenterFragment.class);
        vTabContainer.addTab(tabUserCenter);

        //test
        tabFlutter = vTabContainer.newTabSpec(R.drawable.tab_usercenter, R.string.usercenter_tab, CYFlutterFragment.class);
        vTabContainer.addTab(tabFlutter);

        vTabContainer.setDefaultSelected(0);
        vTabContainer.setTextColor(getResources().getColorStateList(R.color.tab_text_color));
        vTabContainer.apply(vTabContent, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinderKnife.unbind();
    }
}
