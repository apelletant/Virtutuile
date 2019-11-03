package com.virtutuile.systeme.events;

import com.virtutuile.systeme.annotations.NotNull;
import com.virtutuile.systeme.events.actions.Action0;
import com.virtutuile.systeme.events.actions.Action1;

public class LambdaObserver<T> implements Observer<T> {

    private final Action1<? super T> onNext;
    private final Action1<? super Throwable> onError;
    private final Action0 onComplete;

    public LambdaObserver() {
        this.onNext = (t) -> {
        };
        this.onError = Throwable::printStackTrace;
        this.onComplete = () -> {
        };
    }

    public LambdaObserver(@NotNull Action1<? super T> onNext) {
        assert onNext != null : "onNext is null.";

        this.onNext = onNext;
        this.onError = Throwable::printStackTrace;
        this.onComplete = () -> {
        };
    }

    public LambdaObserver(@NotNull Action1<? super T> onNext, @NotNull Action1<? super Throwable> onError) {
        assert onNext != null : "onNext is null.";
        assert onError != null : "onError is null.";

        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = () -> {
        };
    }

    public LambdaObserver(@NotNull Action1<? super T> onNext, @NotNull Action1<? super Throwable> onError, @NotNull Action0 onComplete) {
        assert onNext != null : "onNext is null.";
        assert onError != null : "onError is null.";
        assert onComplete != null : "onComplete is null.";

        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
    }

    @Override
    public void complete() {
        try {
            this.onComplete.call();
        } catch (Exception except) {
            error(except);
        }
    }

    @Override
    public void error(Throwable error) {
        this.onError.call(error);
    }

    @Override
    public void next(T next) {
        try {
            this.onNext.call(next);
        } catch (Exception except) {
            error(except);
        }
    }
}
