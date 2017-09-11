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
 * Interface of Event Bus
 *
 */

interface IEventBus {

    /**
     * Registers the given subscriber to receive events.
     * Subscribers must call {@link #unregister(Object)} once they are no longer
     * interested in receiving events.
     *
     * @param subscriber he subscriber
     */
    void register(Object subscriber);

    /**
     * Unregisters the given subscriber from all event classes.
     * @param subscriber The subscriber
     */
    void unregister(Object subscriber);

    /**
     * Posts the given event to the event bus
     * @param event The event to be posted
     */
    void send(Object event);
}
