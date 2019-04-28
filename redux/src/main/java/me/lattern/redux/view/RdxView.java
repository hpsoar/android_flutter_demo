package me.lattern.redux.view;

import android.view.View;

import me.lattern.redux.store.RdxContext;

public interface RdxView<S> {
    View buildView(RdxContext<S> context);

    void updateView(RdxContext<S> context);
}
