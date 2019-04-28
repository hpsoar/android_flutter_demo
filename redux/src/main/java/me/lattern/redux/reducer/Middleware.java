package me.lattern.redux.reducer;

import me.lattern.redux.store.Dispatcher;
import me.lattern.redux.store.RdxStore;

public interface Middleware<State> {
    Dispatcher create(RdxStore<State> store, Dispatcher nextDispatcher);
}
