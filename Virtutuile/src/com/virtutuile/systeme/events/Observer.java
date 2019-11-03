package com.virtutuile.systeme.events;

/**
 * Light implementation of ReactiveX Java Observer.
 * (See here for more http://reactivex.io/RxJava/javadoc/rx/Observer.html)
 * @param <T> The observer data type
 */
public interface Observer<T> {
    public void complete();

    public void error(Throwable error);

    public void next(T next);
}
