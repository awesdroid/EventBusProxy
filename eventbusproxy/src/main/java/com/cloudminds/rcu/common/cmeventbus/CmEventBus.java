/*
 * Copyright (c) 2017. CloudMinds Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudminds.rcu.common.cmeventbus;

import com.cloudminds.rcu.common.cmeventbus.model.BaseEvent;
import com.cloudminds.rcu.common.cmeventbus.model.Proxy;
import com.cloudminds.rcu.common.cmeventbus.model.Subscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility wrapper class of IEventBus
 *
 * @author Bin YANG <michael.yang@cloudminds.com>
 */

final public class CmEventBus {
    public static final String TAG = "CmEventBus";

    private static final IEventBus sEventBus = EventBusFactory.getEventBus();

    private static ConcurrentHashMap<Class<?>, Subscriber> subscriptionsByEventType =
            new ConcurrentHashMap<>();

    private static Proxy<BaseEvent> proxy = new Proxy<BaseEvent>() {
        @Override
//        @Subscribe(threadMode = ThreadMode.ASYNC)
        @Subscribe(threadMode = ThreadMode.BACKGROUND)
        public void handle(BaseEvent event) throws Exception {
            if (subscriptionsByEventType.isEmpty()) {
                throw new Exception("No subscriber at all!");
            } else  {
                Subscriber subscriber = subscriptionsByEventType.get(event.getClass());
                if (subscriber == null) {
                    throw new Exception("No subscriber found for " + event);
                }
                subscriber.onEvent(event);
            }
        }
    };

    private CmEventBus() {}

    static public void register(Subscriber subscriber) {
//    static public void register(Object subscriber) {
//        sEventBus.register(subscriber);
        Type type = getEventType(subscriber);
        subscriptionsByEventType.put((Class)type, subscriber);
//        init(subscriber);
    }
    static public void unregister(Subscriber subscriber) {
//    static public void unregister(Object subscriber) {
//        sEventBus.unregister(subscriber);
        unregister0(subscriber);

    }
    static public void send(Object event) {
        sEventBus.send(event);
    }

    public static void init() {
//        Type type = getEventType(subscriber);
//        subscriptionsByEventType.put((Class)type, subscriber);
        subscriptionsByEventType.clear();
        sEventBus.register(proxy);
    }

    static void unregister0(Subscriber subscriber) {
        Set set = subscriptionsByEventType.entrySet();
        for (Iterator<Map.Entry> it = set.iterator(); it.hasNext(); ) {
            Map.Entry entry = it.next();
            if (entry.getValue() == subscriber) {
                subscriptionsByEventType.remove(entry.getKey());
                System.out.println("remove entry" + entry);
            }
        }
        if (subscriptionsByEventType.size() == 0) {
            sEventBus.unregister(proxy);
        }
    }

    static Type getGenericEventType(Subscriber subscriber) {
        Type eventType = null;
        try {
            Method method = subscriber.getClass().getMethod("onEvent", BaseEvent.class);
            Type[] types = method.getGenericParameterTypes();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    Type[] aType = ((ParameterizedType) type).getActualTypeArguments();
                    if (aType.length == 1) {
                        eventType = aType[0];
                        return eventType;
                    } else {
                        throw new RuntimeException("More than 1 ParameterizedType for onEvent()");
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Type getEventType(Subscriber subscriber) {
//        try {
            Class<?> clazz = subscriber.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("onEvent")) {
                    Class<?>[] types = method.getParameterTypes();
                    if (types.length == 1) {
                        if (!types[0].getSimpleName().equals("BaseEvent")){
                            return types[0];
                        }
                    } else {
                        throw new RuntimeException("More than 1 Type for onEvent()");
                    }
                }
            }
//            Method method = subscriber.getClass().getMethod("onEvent");   // Doesn't work on Android
//            Method method = subscriber.getClass().getMethod("onEvent", BaseEvent.class); / Doesn't work on Android
//            Type[] types = method.getParameterTypes();
//            if (types.length == 1) {
//                return types[0];
//            } else {
//                throw new RuntimeException("More than 1 ParameterizedType for onEvent()");
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
