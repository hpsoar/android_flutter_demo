package me.lattern.redux.store;

public interface StateSubscriber<State> {
    AbsDisposable subscribe(RdxStateChangeListener<State> listener);
}
