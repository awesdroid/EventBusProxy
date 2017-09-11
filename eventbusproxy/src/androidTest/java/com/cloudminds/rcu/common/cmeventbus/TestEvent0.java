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
import com.cloudminds.rcu.common.cmeventbus.model.Id;

/**
 * Created by myang on 2017/4/8.
 */

public class TestEvent0 extends BaseEvent<TestEvent0.EventId> {
    public enum EventId implements Id {
        EVT_START,
        EVT_STOP
    }
}