package com.virtutuile;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.systeme.events.Observable;
import com.virtutuile.systeme.events.Subscription;

public class VirtuTuile {
    public static void main(String[] args) {
        /*Observable<String> observable = Observable.from("Bonjour");

        Subscription sub = observable.subscribe((message) -> {
            System.out.println("Subscriber 1" + message);
        }, System.err::println, () -> System.out.println("completed"));

        observable.subscribe((message) -> System.out.println("Message -> " + message), System.err::println, () -> System.out.println("Completed -> too"));

        Subscription subThrow = observable.subscribe((message) -> {
            ((String) null).length(); // Exception handling
        }, System.err::println, () -> System.out.println("Completed -> too"));

        observable.next("zefzeofzefoi");

        sub.setCompleted(); // Stopping the subscription

        if (subThrow.hasThrow()) {
            System.err.println(subThrow.getThrowable());
        }
        subThrow.setCompleted();

        observable.next("zefzeofzefoi");
        observable.next("zefzeofzefoi");
        observable.complete();
*/
        MainWindow window = new MainWindow();
    }
}
