package me.lattern.redux.effect;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.store.RdxContext;

public interface RdxEffect<State> {
    Object onAction(RdxAction action, RdxContext<State> context);
}
