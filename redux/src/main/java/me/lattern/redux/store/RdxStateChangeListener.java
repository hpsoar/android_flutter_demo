package me.lattern.redux.store;

public interface RdxStateChangeListener<State> {
    void onStateChanged(State state);
}
