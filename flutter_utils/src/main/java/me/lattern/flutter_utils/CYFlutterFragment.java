package me.lattern.flutter_utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.view.FlutterView;

public class CYFlutterFragment extends Fragment implements PluginRegistry {
    FlutterView flutterView = null;

    CYFlutterChannel mChannel = null;

    String mRoute = null;

//    @Override
//    protected View onCreateLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_cyflutter, container, false);
//
//        init(view);
//
//        return view;
//    }

    void init(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRoute = bundle.getString("route");
        }

        LinearLayout flutterContainer = view.findViewById(R.id.flutter_container);

        flutterView = Flutter.createView(getActivity(), getLifecycle(), getRoute());

        flutterContainer.addView(flutterView);

        mChannel = new CYFlutterChannel(getContext(), this, flutterView, CYFlutterChannel.METHOD_CHANNEL);
    }

    String getRoute() {
        return mRoute != null ? mRoute : "route1";
    }

    @Override
    public Registrar registrarFor(String pluginKey) {
        return this.flutterView.getPluginRegistry().registrarFor(pluginKey);
    }

    @Override
    public boolean hasPlugin(String pluginKey) {
        return this.flutterView.getPluginRegistry().hasPlugin(pluginKey);
    }

    @Override
    public <T> T valuePublishedByPlugin(String pluginKey) {
        return this.flutterView.getPluginRegistry().valuePublishedByPlugin(pluginKey);
    }
}
