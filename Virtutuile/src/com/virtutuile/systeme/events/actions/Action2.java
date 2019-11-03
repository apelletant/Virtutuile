package com.virtutuile.systeme.events.actions;

public interface Action2<T1, T2> extends Action {
    public void call(T1 t1, T2 t2);
}
