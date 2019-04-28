package me.lattern.redux.store;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.reducer.Middleware;
import me.lattern.redux.reducer.Reducer;

public class RdxStore<State> implements Dispatcher, StateSubscriber<State> {
    public State getState() {
        return state_;
    }

    private State state_;
    private Reducer<State> reducer_;
    private Dispatcher dispatcher_;

    private final List<RdxStateChangeListener<State>> listeners_ = new CopyOnWriteArrayList<>();

    public RdxStore(Reducer<State> reducer, State initalState, Middleware[] middlewares) {
        reducer_ = reducer;

        state_ = initalState;

        dispatcher_ = processDispather(new DispatcherWrapper(), middlewares);
    }

    protected Dispatcher processDispather(Dispatcher dispatcher, Middleware[] middlewares) {
        for (int i = middlewares.length - 1; i >= 0; i--) {
            Middleware<State> middleware = middlewares[i];
            dispatcher = middleware.create(this, dispatcher);
        }

        return dispatcher;
    }

    @Override
    public void dispatch(RdxAction action) {
        dispatcher_.dispatch(action);
    }

    @Override
    public AbsDisposable subscribe(final RdxStateChangeListener<State> listener) {
        listeners_.add(listener);

        return new AbsDisposable() {
            @Override
            public void dispose() {
                listeners_.remove(listener);
            }
        };
    }

    class DispatcherWrapper implements Dispatcher {

        @Override
        public void dispatch(RdxAction action) {
            synchronized (this) {
                state_ = reducer_.reduce(state_, action);
            }

            for (RdxStateChangeListener<State> listener : listeners_) {
                listener.onStateChanged(state_);
            }
        }
    }
}
