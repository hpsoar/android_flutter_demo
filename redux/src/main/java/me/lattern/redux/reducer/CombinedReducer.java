package me.lattern.redux.reducer;

import java.util.HashMap;
import java.util.Map;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.action.RdxActionType;

public class CombinedReducer<State> implements Reducer<State> {
    private Map<String, Reducer<State>> reducers_ = new HashMap<>();

    public CombinedReducer() {

    }

    public CombinedReducer add(RdxActionType actionType, Reducer reducer) {
        return add(actionType.getValue(), reducer);
    }

    public CombinedReducer add(String actionType, Reducer reducer) {
        if (actionType != null && reducer != null) {
            reducers_.put(actionType, reducer);
        }
        return this;
    }

    @Override
    public State reduce(State state, RdxAction action) {
        Reducer<State> reducer = reducers_.get(action.getType());

        if (reducer == null) {
            return state;
        } else {
            return reducer.reduce(state, action);
        }
    }
}
