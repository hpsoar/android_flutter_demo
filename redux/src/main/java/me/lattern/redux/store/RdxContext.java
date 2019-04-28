package me.lattern.redux.store;

import android.content.Context;

import me.lattern.redux.action.RdxAction;

public class RdxContext<S> implements Dispatcher {
    RdxStore<S> store;
    Context context;

    public RdxContext(Context context, RdxStore<S> store) {
        this.context = context;
        this.store = store;
    }

    public S getState() {
        return store.getState();
    }

    @Override
    public void dispatch(RdxAction action) {
        store.dispatch(action);
    }
}
