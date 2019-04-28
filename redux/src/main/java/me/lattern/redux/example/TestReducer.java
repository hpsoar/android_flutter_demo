package me.lattern.redux.example;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.reducer.CombinedReducer;
import me.lattern.redux.reducer.Reducer;

public class TestReducer extends CombinedReducer<TestState> {
    public TestReducer() {
        add(TestActions.Type.ACTION1, new Reducer1());
        add(TestActions.Type.ACTION2, new Reducer2());
        add(TestActions.Type.ACTION3, new Reducer3());
    }

    static class Reducer1 implements Reducer<TestState> {

        @Override
        public TestState reduce(TestState testState, RdxAction action) {
            return testState;
        }
    }

    static class Reducer2 implements Reducer<TestState> {

        @Override
        public TestState reduce(TestState testState, RdxAction action) {
            return testState;
        }
    }

    static class Reducer3 implements Reducer<TestState> {

        @Override
        public TestState reduce(TestState testState, RdxAction action) {
            return testState;
        }
    }
}
