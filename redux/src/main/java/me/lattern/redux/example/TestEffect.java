package me.lattern.redux.example;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.effect.CombinedEffect;
import me.lattern.redux.effect.RdxEffect;
import me.lattern.redux.store.RdxContext;

public class TestEffect extends CombinedEffect<TestState> {
    public TestEffect() {
        add(TestActions.Type.ACTION1, new Effect1());
        add(TestActions.Type.ACTION2, new Effect2());
        add(TestActions.Type.ACTION3, new Effect3());
    }

    static class Effect1 implements RdxEffect<TestState> {

        @Override
        public Object onAction(RdxAction action, RdxContext<TestState> context) {
            return null;
        }
    }

    static class Effect2 implements RdxEffect<TestState> {

        @Override
        public Object onAction(RdxAction action, RdxContext<TestState> context) {
            return null;
        }
    }

    static class Effect3 implements RdxEffect<TestState> {

        @Override
        public Object onAction(RdxAction action, RdxContext<TestState> context) {
            return null;
        }
    }
}
