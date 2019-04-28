package me.lattern.redux.effect;

import java.util.HashMap;
import java.util.Map;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.action.RdxActionType;
import me.lattern.redux.store.RdxContext;

public class CombinedEffect<S> implements RdxEffect<S> {

    private Map<String, RdxEffect<S>> effects_ = new HashMap<>();

    public CombinedEffect() {

    }

    public CombinedEffect add(RdxActionType actionType, RdxEffect reducer) {
        return add(actionType.getValue(), reducer);
    }

    public CombinedEffect add(String actionType, RdxEffect reducer) {
        if (actionType != null && reducer != null) {
            effects_.put(actionType, reducer);
        }
        return this;
    }

    @Override
    public Object onAction(RdxAction action, RdxContext<S> context) {
        RdxEffect<S> reducer = effects_.get(action.getType());

        if (reducer == null) {
            return null;
        } else {
            return reducer.onAction(action, context);
        }
    }
}
