/*
 * Copyright (c) 2017. Awesdroid
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

package io.awesdroid.eventbusproxy;

import io.awesdroid.eventbusproxy.model.Subscriber;


/**
 */

public class Subscriber1 implements Subscriber<TestEvent1> {
    @Override
    public void onEvent(TestEvent1 event) {
        System.out.println(EventBusProxy.TAG + " **** " +
                this.getClass().getSimpleName() + ".onEvent(): " + event);
    }
}
