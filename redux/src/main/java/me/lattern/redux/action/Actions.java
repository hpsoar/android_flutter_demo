package me.lattern.redux.action;

import java.util.HashMap;

import me.lattern.redux.reducer.CombinedReducer;
import me.lattern.redux.reducer.Reducer;

public class Actions {
    public enum Type implements RdxActionType {
        ACTION_INIT("init"),
        ACTION_TEST("test");

        String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return null;
        }
    }

    public static RdxAction initAction() {
        return new RdxAction(Type.ACTION_INIT, null);
    }

    public static RdxAction testAction() {
        return new RdxAction(Type.ACTION_TEST, new HashMap<>());
    }

    public static class TestState {
        public String value;
    }

    public static class InitState {

    }

    public class TestReducer implements Reducer<TestState> {
        @Override
        public TestState reduce(TestState testState, RdxAction action) {
            if (action.type.equals(Type.ACTION_INIT)) {

            } else if (action.type.equals(Type.ACTION_TEST)) {

            }
            return testState;
        }
    }

    public class InitReducer implements Reducer<TestState> {
        @Override
        public TestState reduce(TestState testState, RdxAction action) {
            return null;
        }
    }

    public void test() {
        TestState state = new TestState();

        CombinedReducer<TestState> reducer = new CombinedReducer<>();
        reducer.add(Type.ACTION_INIT, new TestReducer());
        reducer.add(Type.ACTION_TEST, new TestReducer());

        reducer.reduce(state, Actions.initAction());
        reducer.reduce(state, Actions.testAction());
    }
}
