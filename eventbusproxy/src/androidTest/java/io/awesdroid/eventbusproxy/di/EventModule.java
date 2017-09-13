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

import dagger.Module;
import dagger.Provides;
import io.awesdroid.eventbusproxy.TestEvent0;
import io.awesdroid.eventbusproxy.TestEvent1;

/**
 * @author Bin YANG
 */

@Module
public class EventModule {
    @Provides
    TestEvent0  provideTestEvent0() {
        return (TestEvent0)new TestEvent0()
                .setData(this.getClass().getEnclosingMethod().getName());
    }

    @Provides
    TestEvent1 provideTestEvent1() {
        return (TestEvent1)new TestEvent1()
                .setData(this.getClass().getEnclosingMethod().getName());
    }

}
