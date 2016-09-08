/*
 *  Copyright 2016 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.runtime;

import org.teavm.interop.Address;

final class MarkQueue {
    private MarkQueue() {
    }

    private static int head;
    private static int tail;

    static void init() {
        head = 0;
        tail = 0;
    }

    private static native Address getBase();

    private static native int getSize();

    static void enqueue(RuntimeObject object) {
        getBase().add(Address.sizeOf() * tail).putAddress(object.toAddress());
        if (++tail >= getSize()) {
            tail = 0;
        }
    }

    static RuntimeObject dequeue() {
        Address result = getBase().add(Address.sizeOf() * head).getAddress();
        if (++head >= getSize()) {
            head = 0;
        }
        return result.toStructure();
    }

    static boolean isEmpty() {
        return head == tail;
    }
}
