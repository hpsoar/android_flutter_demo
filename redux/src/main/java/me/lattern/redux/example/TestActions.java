package me.lattern.redux.example;

import me.lattern.redux.action.RdxAction;
import me.lattern.redux.action.RdxActionType;

public class TestActions {
    public enum Type implements RdxActionType {
        ACTION1("action1"),
        ACTION2("action2"),
        ACTION3("action3") ;

        Type(String t) {
            v = t;
        }

        private String v;

        @Override
        public String getValue() {
            return v;
        }
    }

    public static RdxAction action1(String args) {
        return new RdxAction(Type.ACTION1, args);
    }

    public static RdxAction action2(Object args) {
        return new RdxAction(Type.ACTION2, args);
    }

    public static RdxAction action3() {
        return new RdxAction(Type.ACTION3);
    }
}
