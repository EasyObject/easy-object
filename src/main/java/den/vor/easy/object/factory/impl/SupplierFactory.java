/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.Value;

import java.util.function.Supplier;

/**
 * Factory that returns values generated by a provided {@link Supplier}.
 *
 * @param <T> type of underlying supplier's value
 * @param <R> type of value that supplier returns
 */
public class SupplierFactory<T, R extends Value<T>> extends Factory<T, R> {

    private final Supplier<R> supplier;

    public SupplierFactory(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Generator<R> getGenerator() {
        return context -> supplier.get();
    }
}
