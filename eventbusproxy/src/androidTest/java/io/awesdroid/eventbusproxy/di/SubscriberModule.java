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

import android.util.Log;

import dagger.Module;
import dagger.Provides;
import io.awesdroid.eventbusproxy.TestEvent0;
import io.awesdroid.eventbusproxy.TestEvent1;
import io.awesdroid.eventbusproxy.model.Subscriber;

import static io.awesdroid.eventbusproxy.EventBusProxy.TAG;

/**
 * @author Bin YANG
 */

@Module
public class SubscriberModule {
    @Provides
    Subscriber<TestEvent0> provideSubscriber0() {
        return new Subscriber<TestEvent0>() {
            @Override
            public void onEvent(TestEvent0 event) {
                Log.d(TAG, "onEvent(): **** RECV " + event + " **** in thread <" +
                        Thread.currentThread().getName() + ">");
            }
        };
    }

    @Provides
    Subscriber<TestEvent1> provideSubscriber1() {
        return new Subscriber<TestEvent1>() {
            @Override
            public void onEvent(TestEvent1 event) {
                Log.d(TAG, "onEvent(): **** RECV " + event + " **** in thread <" +
                        Thread.currentThread().getName() + ">");
            }
        };
    }
}
