package com.virtutuile.systeme.events;

import java.util.Vector;

public class Subscription<T> {
    Vector<Observer<? extends T>> _observers = new Vector<>();
    boolean isCompleted = false;
    Throwable throwable = null;

    Subscription() {
    }

    public Subscription(Observer<? extends T> observer) {
        _observers.add(observer);
    }

    public Observer<? extends T> add(Observer<? extends T> observer) {
        _observers.add(observer);
        return observer;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Subscription<T> setCompleted() {
        isCompleted = true;
        return this;
    }

    public boolean hasThrow() {
        return throwable != null;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    void invokeComplete() {
        if (this.isCompleted) {
            return;
        }
        try {
            for (Observer observer : _observers) {
                observer.complete();
            }
            this.isCompleted = true;
        } catch (Throwable err) {
            invokeError(err);
            this.throwable = err;
        }
    }

    void invokeError(Throwable error) {
        if (this.isCompleted) {
            return;
        }

        try {
            for (Observer observer : _observers) {
                observer.error(error);
            }
        } catch (Throwable err) {
            this.throwable = err;
        }
    }

    void invokeNext(T next) {
        if (this.isCompleted) {
            return;
        }

        try {
            for (Observer observer : _observers) {
                observer.next(next);
            }
        } catch (Throwable err) {
            invokeError(err);
            this.throwable = err;
        }
    }
}
