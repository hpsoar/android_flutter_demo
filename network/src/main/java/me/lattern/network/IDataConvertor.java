package me.lattern.network;

public abstract class IDataConvertor {
    public abstract <T> T convert(Object data, Class<T> clazz);
}
