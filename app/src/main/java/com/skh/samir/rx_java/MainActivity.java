package com.skh.samir.rx_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;


public class MainActivity extends AppCompatActivity {
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // demoObservalFrom();
                //  demoObservalJust();
                //  demoObservalJust1();
                //demoObservalDefer();
                // demoObservalInterval();
                demoObservalCreate();
            }
        });
    }

    private void demoObservalCreate() {
        /*
         * Observable.create() method :
         * It gives us result same to Observable.just(1,2,3,4,5);
         * create() method also provide behaviour as defer() method -- stores the value of the item when subscribe,not initialized.
         * However,in most cases you should not use this function because it has some rules that we must follow as only called subscriber.
         * oncompleted() method or subscriber.onError() only ones and are call.
         *
         *
         * */
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onNext(5);
                subscriber.onNext(6);

                subscriber.onCompleted();

            }
        });


        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                logPrint("Create onNext:" + integer);
            }
        });


    }

    private void demoObservalInterval() {
        /*
         * Observable.interval() method :
         * we will create task with call after 5 min or 2 sec and after 6 time it will finnish!
         * interval will be useful when we want to schedule update data.
         *
         * Example: update data after 5 min
         * */


        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {

                if (aLong == 10) {   ///when 10 time called onNext() method it's unsubscribe() method call
                    unsubscribe();
                }
                logPrint("interval onNext:" + aLong);  //every 2 second after called onNext() method
            }
        });
    }

    private void demoObservalDefer() {
        food = new Food("Mango");

        Observable<Food> observable = Observable.defer(new Func0<Observable<Food>>() {
            @Override
            public Observable<Food> call() {
                return Observable.just(food);
            }
        });


        food = new Food("Orange");
        food = new Food("Apple");  //we get this object. before subscribe add last object for subscriber

        observable.subscribe(new Subscriber<Food>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Food food) {
                logPrint("Defer onNext:" + food.getName());
            }
        });

        food = new Food("Apple2");   // this object not add in subscribe
    }

    private void demoObservalJust1() {
        Food food = new Food("Mango");


        Observable observable = Observable.just(food);

        food = new Food("Apple");  //this object is not work because when jest() method called observable is created
        // In many case ,we wile want to our data is newest,so if we do not subscribe as soon as create then we have data is not newest
        //so what is solution for this case????  Defer() method will help us for this problem

        observable.subscribe(new Subscriber<Food>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Food food) {
                logPrint("Just1 onNext:" + food.getName());
            }
        });
    }

    private void demoObservalJust() {
         /*
        Observable.Just() method take on an Unlimited number of parameters and can be of any type.
        (Here i pass 10 number (1,2,3,4,5,6,7,8,9,10)).
        Observable.subcribe() method will created a subscribe with three onCompleted(), onError() , onNext() function to use the item passed in above.

        The result is same with From() and just() method !
        * But what is different between from() and just()?
        *
        * explain in     private void demoObservalFrom() method {

         */

        int[] num = {1, 2, 3, 4, 5};

        Observable.just(num).subscribe(new Subscriber<int[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(int[] ints) {
                logPrint("From onNext:" + String.valueOf(ints));
            }
        });


        Observable.just(1, 2, 3).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                logPrint("From onNext:" + String.valueOf(integer));
            }
        });

    }

    private void demoObservalFrom() {
        /*
        Observable.From() method take on an Unlimited number of parameters and can be of any type.
        (Here i pass an array of 10 number (1,2,3,4,5,6,7,8,9,10)).
        Observable.subcribe() method will created a subscribe with three onCompleted(), onError() , onNext() function to use the item passed in above.
         */

        /*
         * The result is same with From() and just() method !
         * But what is different between from() and just()?
         * --From() method: when we pass into an array or list item,it will issue an array and list that item and the subscriber will also take the parameter as an array or list.
         *
         * --just() method : it will issue each item in the list (will call onNext() time the size of the list in error() condition don't available)
         *
         * */
        Integer[] numArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        Observable observable = Observable.from(numArray);


        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                logPrint("From onNext:" + String.valueOf(integer));
            }
        });

    }

    private void logPrint(String message) {
        Log.i("SKH1:", message);
    }


}
