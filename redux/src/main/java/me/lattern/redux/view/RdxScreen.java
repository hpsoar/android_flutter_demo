package me.lattern.redux.view;

import android.view.View;
import android.widget.TextView;

import java.util.Map;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.reducer.Reducer;
import me.lattern.redux.store.RdxContext;
import me.lattern.redux.store.RdxStore;

public abstract class RdxScreen<S> implements RdxView<S> {
    RdxStore store;

    RdxService service;

    View rootView;

    Map<String, Object> initialState;

    public RdxScreen(Map<String, Object> initialState, View rootView) {
        this.rootView = rootView;
        this.initialState = initialState;

        store = createStore(initialState);
    }

    protected abstract RdxStore createStore(Map<String, Object> initialState);

    public void test() {

    }


    public static class MyScreen extends RdxScreen<MyState> {
        TextView tvTitle;
        TextView tvValue;

        public MyScreen(Map<String, Object> initialState, View rootView) {
            super(initialState, rootView);


        }

        @Override
        protected RdxStore createStore(Map<String, Object> initialState) {
            return new RdxStore(new MyReducer(), new MyState());
        }

        @Override
        public View buildView(RdxContext<MyState> context) {
            return null;
        }

        @Override
        public void updateView(final RdxContext<MyState> context) {
            tvTitle.setText(context.getState().name);
            tvValue.setText(context.getState().value);

            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.dispatch(new MyAction());
                }
            });

        }
    }

    public static class MyState {
        public String name;
        public String value;
    }

    public static class MyReducer implements Reducer<MyState> {
        @Override
        public void onAction(RdxAction action) {

        }
    }

    public static class MyAction extends RdxAction {

    }
}
