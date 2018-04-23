package com.neil.demo.guavaEventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * Created by Neil on 2016/12/8.
 */
@Component
public class GuavaEventBus {

    private final static EventBus eventBus = new EventBus();
    private final static AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newCachedThreadPool());


    public static void post(Object event) {
        eventBus.post(event);
    }

    public static void asyncPost(Object event) {
        asyncEventBus.post(event);
    }

    public static void register(Object handler) {
        eventBus.register(handler);
    }

    public static void asyncRegister(Object handler) {
        asyncEventBus.register(handler);
    }

    public static void unregister(Object handler) {
        eventBus.unregister(handler);
    }

    public static void asyncUnregister(Object handler) {
        asyncEventBus.unregister(handler);
    }
}
