package me.lattern.redux.reducer;

import me.lattern.redux.action.RdxAction;

public class HighOrderReducer<State> implements Reducer<State> {
    public HighOrderReducer(Reducer<State> reducer) {
        sourceReducer = reducer;
    }

    protected Reducer<State> sourceReducer;

    @Override
    public <P> State reduce(State state, RdxAction<P> action) {
        if (sourceReducer != null) {
            return sourceReducer.reduce(state, action);
        } else {

        }

        return state;
    }
}
