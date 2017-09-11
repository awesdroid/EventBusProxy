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

/**
 *  Factory of Event Bus
 *
 */

final class EventBusFactory {
    public static final String DEFAULT_EVENTBUS_NAME = EventBusImplGb.class.getSimpleName();

    private static IEventBus sIEventBus;

    private EventBusFactory() {}

    /**
     *  Create sole instance of Event Bus
     *
     * @return The sole instance of Event Bus
     */
    static IEventBus getEventBus() {
        String eventBusName = getEventBusName();
        if (eventBusName.equals(DEFAULT_EVENTBUS_NAME)) {
            sIEventBus = new EventBusImplGb();
        }
        return sIEventBus;
    }

    private static String getEventBusName() {
        // TODO
        return DEFAULT_EVENTBUS_NAME;
    }
}
