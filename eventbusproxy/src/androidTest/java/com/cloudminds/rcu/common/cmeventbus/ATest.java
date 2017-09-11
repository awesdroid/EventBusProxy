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

import android.support.test.runner.AndroidJUnit4;

import com.cloudminds.rcu.common.cmeventbus.model.Subscriber;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Bin YANG
 * @date 2017/9/11
 */

@RunWith(AndroidJUnit4.class)
public class ATest {

    List<Subscriber> subscribers = new ArrayList<>();
    CountDownLatch countDownLatch = new CountDownLatch(2);

    @Before
    public void init() {
        System.out.println(CmEventBus.TAG + " init()");
        CmEventBus.init();
        subscribers.clear();

    }

    @Test
    public void testSubscriber0() {
        Subscriber0 subscriber0 = new Subscriber0();
        CmEventBus.register(subscriber0);
        subscribers.add(subscriber0);

        TestEvent0 testEvent0 = new TestEvent0();
        System.out.println(CmEventBus.TAG + " send: " + testEvent0);
        CmEventBus.send(testEvent0);
    }

    @Test
    public void testSubscriber1() {
        Subscriber1 subscriber1 = new Subscriber1();
        CmEventBus.register(subscriber1);
        subscribers.add(subscriber1);

        TestEvent1 testEvent1 = new TestEvent1();
        System.out.println(CmEventBus.TAG + " send: " + testEvent1);
        CmEventBus.send(testEvent1);
    }

    @Test
    public void testSubscribers() {
        Thread thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                testSubscriber0();
                countDownLatch.countDown();
            }
        });
        thread0.start();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testSubscriber1();
                countDownLatch.countDown();
            }
        });
        thread1.start();

        // join() works for ThreadMode.BACKGROUND as well, but not works for ASYNC either.
//        try {
//            thread0.join();
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @After
    public void destroy() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (subscribers.size() > 0) {
            for (Subscriber subscriber : subscribers) {
                // If unregister all subscribers, ThreadMode.ASYNC will result in some events not be
                // able to be handled due to unregistration.
                CmEventBus.unregister(subscriber);
            }
        }
        subscribers.clear();
        System.out.println(CmEventBus.TAG + " destroy()");
    }
}
