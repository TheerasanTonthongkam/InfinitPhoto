package com.playtech.infinitphoto.schedulers;


import rx.Scheduler;

public class ThreadScheduler {
    Scheduler subscribeScheduler;
    Scheduler androidMainTread;

    public ThreadScheduler(Scheduler subscribeScheduler, Scheduler androidMainTread) {
        this.subscribeScheduler = subscribeScheduler;
        this.androidMainTread = androidMainTread;
    }

    public Scheduler getSubscribeScheduler() {
        return subscribeScheduler;
    }

    public Scheduler getAndroidMainTread() {
        return androidMainTread;
    }
}
