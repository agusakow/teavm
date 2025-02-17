/*
 *  Copyright 2017 Alexey Andreev.
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
package org.teavm.classlib.java.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public interface TSpliterator<T> {
    int ORDERED = 16;
    int DISTINCT = 1;
    int SORTED = 4;
    int SIZED = 64;
    int NONNULL = 256;
    int IMMUTABLE = 1024;
    int CONCURRENT = 4096;
    int SUBSIZED = 16384;

    boolean tryAdvance(Consumer<? super T> action);

    default void forEachRemaining(Consumer<? super T> action) {
        while (tryAdvance(action)) {
            // just repeat
        }
    }

    TSpliterator<T> trySplit();

    long estimateSize();

    default long getExactSizeIfKnown() {
        return (characteristics() & SIZED) != 0 ? estimateSize() : -1;
    }

    int characteristics();

    default boolean hasCharacteristics(int characteristics) {
        return (characteristics() & characteristics) == characteristics;
    }

    default Comparator<? super T> getComparator() {
        throw new IllegalStateException();
    }

    public interface OfPrimitive<T, C, S extends OfPrimitive<T, C, S>> {
        S trySplit();

        boolean tryAdvance(C action);

        default void forEachRemaining(C action) {
            while (tryAdvance(action)) {
                // continue
            }
        }
    }

    public interface OfInt extends OfPrimitive<Integer, IntConsumer, OfInt> {
        default boolean tryAdvance(Consumer<? super Integer> action) {
            return tryAdvance((IntConsumer) action::accept);
        }

        default void forEachRemaining(Consumer<? super Integer> action) {
            while (tryAdvance(action)) {
                // continue
            }
        }
    }

    public interface OfLong extends OfPrimitive<Long, LongConsumer, OfLong> {
        default boolean tryAdvance(Consumer<? super Long> action) {
            return tryAdvance((LongConsumer) action::accept);
        }

        default void forEachRemaining(Consumer<? super Long> action) {
            while (tryAdvance(action)) {
                // continue
            }
        }
    }

    public interface OfDouble extends OfPrimitive<Double, DoubleConsumer, OfDouble> {
        default boolean tryAdvance(Consumer<? super Double> action) {
            return tryAdvance((DoubleConsumer) action::accept);
        }

        default void forEachRemaining(Consumer<? super Double> action) {
            while (tryAdvance(action)) {
                // continue
            }
        }
    }
}
