package me.lattern.redux.reducer;

import me.lattern.redux.action.RdxAction;

/*
 * a Reducer may handle multiple kind of actions
 */
public interface Reducer<State> {
    State reduce(State state, RdxAction action);
}
