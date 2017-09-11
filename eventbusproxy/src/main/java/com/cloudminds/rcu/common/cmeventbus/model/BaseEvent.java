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

package com.cloudminds.rcu.common.cmeventbus.model;

/**
 * Base definition of events. All concrete events should be subclassed from it.
 *
 * @author Bin YANG <michael.yang@cloudminds.com>
 */

public abstract class BaseEvent<T extends Id> {
    private T id;
    private Object data;

    /**
     * Default ctor
     */
    public BaseEvent() {
        this(null);
    }

    /**
     * Ctor with event id
     *
     * @param id Event id
     */
    public BaseEvent(T id) {
        this(id, null);
    }

    /**
     * Ctor with event id and conveyed data
     *
     * @param id Event id
     * @param data Conveyed data by the event
     */
    protected BaseEvent(T id, Object data) {
        this.id = id;
        this.data = data;
    }

    /**
     * Get event id
     *
     * @return Event id
     */
    public T getId() { return id;}

    /**
     * Set event id
     *
     * @param id Event id
     * @return The event object
     */
    public BaseEvent setId(T id) {
        this.id = id;
        return this;
    }

    /**
     * Get conveyed data of this event
     *
     * @return The conveyed data
     */
    public Object getData() {
        return data;
    }

    /**
     * Set data of this event
     *
     * @param data The data of this event
     * @return The event object
     */
    public BaseEvent setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.')+1);
        return className + "{" +
                "id=" + id +
                ", data=" + data +
                '}';
    }
}
