package me.lattern.redux.action;

public class RdxAction {
    RdxActionType type;
    Object data;

    public RdxAction(RdxActionType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public RdxAction(RdxActionType type) {
        this(type, null);
    }

    public String getType() {
        return type == null ? "invalid type" : type.getValue();
    }

    public <T> T getData() {
        return (T)data;
    }
}
