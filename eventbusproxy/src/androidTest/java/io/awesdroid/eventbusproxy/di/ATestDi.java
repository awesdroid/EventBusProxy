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

package io.awesdroid.eventbusproxy.di;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.awesdroid.eventbusproxy.EventBusProxy;
import io.awesdroid.eventbusproxy.TestEvent0;
import io.awesdroid.eventbusproxy.TestEvent1;
import io.awesdroid.eventbusproxy.model.Subscriber;

import static io.awesdroid.eventbusproxy.EventBusProxy.TAG;

/**
 * @author Bin YANG
 */

@RunWith(AndroidJUnit4.class)
public class ATestDi {
    @Inject
    Subscriber<TestEvent0> subscriber0;

    @Inject
    Subscriber<TestEvent1> subscriber1;

    @Inject
    Subscriber<TestEvent2> subscriber2;

    @Inject
    Subscriber<TestEvent3> subscriber3;

    @Inject
    TestEvent2 testEvent2;

    @Inject
    TestEvent3 testEvent3;

    @Before
    public void init() {
        Log.d(TAG, "<" + Thread.currentThread().getName() + "> " +
                "init(): ");
        SubscriberComponent subscriberComponent = DaggerSubscriberComponent.builder()
                .subscriberModule(new SubscriberModule()).build();
        subscriberComponent.inject(this);
        EventBusProxy.init();
        EventBusProxy.setDebug(false);
    }

    @Test
    public void testSubscriber0() {
        TestEvent0 testEvent = new TestEvent0();
        EventBusProxy.register(subscriber0);
        EventBusProxy.send(testEvent);
    }

    @Test
    public void testSubscriber1() {
        TestEvent1 testEvent = new TestEvent1();
        EventBusProxy.register(subscriber1);
        EventBusProxy.send(testEvent);
    }

    @Test
    public void testSubscriber2() {
        EventBusProxy.register(subscriber2);
        Log.d(TAG, "<" + Thread.currentThread().getName() + "> " +
                "testSubscriber2(): ==== SEND " + testEvent2);
        EventBusProxy.send(testEvent2);
    }

    @Test
    public void testSubscriber3() {
        EventBusProxy.register(subscriber3);
        Log.d(TAG, "<" + Thread.currentThread().getName() + "> " +
                "testSubscriber3(): ==== SEND " + testEvent3);
        EventBusProxy.send(testEvent3);
    }

    @Test
    public void testSubscribers() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i<10; i++) {
            Future future0 = executor.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    testSubscriber0();
                    return null;
                }
            });
            Future future1 = executor.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    testSubscriber1();
                    return null;
                }
            });

            try {
                future0.get();
                future1.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testSubscriber23() {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            Future future2 = executor.submit(new Runnable() {
                @Override
                public void run() {
                    testSubscriber2();
                }
            });
            Future future3 = executor.submit(new Runnable() {
                @Override
                public void run() {
                    testSubscriber3();
                }
            });

            try {
                future2.get();
                future3.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    @After
    public void clean() {
        Log.d(TAG, "<" + Thread.currentThread().getName() + "> " +
                "clean(): ");
        EventBusProxy.unregister(subscriber0);
        EventBusProxy.unregister(subscriber1);
        EventBusProxy.unregister(subscriber2);
        EventBusProxy.unregister(subscriber3);
    }
}
