package com.virtutuile.systeme.events;

import com.virtutuile.systeme.annotations.NonNull;
import com.virtutuile.systeme.events.actions.*;

/**
 * Light implementation of ReactiveX Java Observable.
 * (See here for more http://reactivex.io/RxJava/javadoc/rx/Observable.html)
 * @param <T> The observable emission type
 */
public class Observable<T> implements Observer<T> {

    private Subscription<T> subscription = new Subscription<>();
    private T snapshot = null;

    public static <U> Observable<U> from(U from) {
        Observable<U> observable = new Observable<>();
        observable.snapshot = from;
        return observable;
    }

    private Observable() {
    }

    public Subscription<T> subscribe() {
        Observer<T> observer = new LambdaObserver<>();
        subscription.add(observer);
        return new Subscription<>(observer);
    }

    public Subscription subscribe(@NonNull Action1<T> next) {
        Observer<T> observer = new LambdaObserver<>(next);
        subscription.add(observer);
        return new Subscription<>(observer);
    }

    public Subscription subscribe(@NonNull Action1<T> next, @NonNull Action1<? super Throwable> error) {
        Observer<T> observer = new LambdaObserver<>(next, error);
        subscription.add(observer);
        return new Subscription<>(observer);
    }

    public Subscription subscribe(@NonNull Action1<T> next, @NonNull Action1<? super Throwable> error, @NonNull Action0 complete) {
        Observer<T> observer = new LambdaObserver<>(next, error, complete);
        subscription.add(observer);
        return new Subscription<>(observer);
    }

    @Override
    public void complete() {
        if (this.subscription.isCompleted)
            return;
        this.subscription.invokeComplete();
    }

    @Override
    public void error(Throwable error) {
        if (this.subscription.isCompleted) {
            return;
        }
        this.subscription.invokeError(error);
    }

    @Override
    public void next(T next) {
        if (this.subscription.isCompleted)
            return;
        this.subscription.invokeNext(next);
        this.snapshot = next;
    }
}
