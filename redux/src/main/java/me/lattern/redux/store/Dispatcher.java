package me.lattern.redux.store;

import me.lattern.redux.action.RdxAction;

public interface Dispatcher {
    void dispatch(RdxAction action);
}
