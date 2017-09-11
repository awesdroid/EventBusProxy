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

import com.cloudminds.rcu.common.cmeventbus.model.Subscriber;


/**
 * @author Bin YANG
 * @date 2017/9/11
 */

public class Subscriber0 implements Subscriber<TestEvent0> {
    @Override
    public void onEvent(TestEvent0 event) {
        System.out.println(CmEventBus.TAG + " **** " +
                this.getClass().getSimpleName() + ".onEvent(): " + event);
    }
}
