package me.lattern.redux;

import android.content.Context;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.effect.RdxEffect;
import me.lattern.redux.reducer.Middleware;
import me.lattern.redux.reducer.Reducer;
import me.lattern.redux.store.AbsDisposable;
import me.lattern.redux.store.Disposable;
import me.lattern.redux.store.Dispatcher;
import me.lattern.redux.store.RdxContext;
import me.lattern.redux.store.RdxStateChangeListener;
import me.lattern.redux.store.RdxStore;
import me.lattern.redux.store.StateSubscriber;

public class Logic<S> implements Dispatcher, StateSubscriber {
    public Logic(Context context, S state, Reducer<S> reducer, RdxEffect<S> effect, Middleware<S>[] middlewares) {
        store_ = new RdxStore<>(reducer, state, middlewares);

        effect_ = effect;

        context_ = new RdxContext<>(context, store_);
    }

    @Override
    public void dispatch(RdxAction action) {
        Object r = effect_.onAction(action, context_);
        if (r != null && r instanceof Boolean) {
            if ((Boolean)r) {
                return;
            }
        }

        store_.dispatch(action);
    }

    @Override
    public AbsDisposable subscribe(RdxStateChangeListener listener) {
        return store_.subscribe(listener);
    }

    private RdxEffect<S> effect_;
    private RdxStore<S> store_;
    private RdxContext<S> context_;
}
