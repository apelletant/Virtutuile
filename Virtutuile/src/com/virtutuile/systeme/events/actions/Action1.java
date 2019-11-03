package com.virtutuile.systeme.events.actions;

public interface Action1<T> extends Action {
    public void call(T t);
}
